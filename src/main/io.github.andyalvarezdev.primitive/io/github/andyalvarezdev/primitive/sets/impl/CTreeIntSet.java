/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package io.github.andyalvarezdev.primitive.sets.impl;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import io.github.andyalvarezdev.primitive.Comparators;
import io.github.andyalvarezdev.primitive.collections.IntCollection;
import io.github.andyalvarezdev.primitive.comparators.IntComparator;
import io.github.andyalvarezdev.primitive.iterators.IntIterator;
import io.github.andyalvarezdev.primitive.maps.CNavigableIntObjectMap;
import io.github.andyalvarezdev.primitive.maps.impl.CTreeIntObjectMap;
import io.github.andyalvarezdev.primitive.pair.IntObjectPair;
import io.github.andyalvarezdev.primitive.sets.abstracts.AbstractIntSet;
import io.github.andyalvarezdev.primitive.sets.IntSet;
import io.github.andyalvarezdev.primitive.sets.NavigableIntSet;
import io.github.andyalvarezdev.primitive.sets.SortedIntSet;
import sun.misc.Unsafe;

/**
 * <p>
 * A scalable concurrent {@link NavigableSet} implementation based on
 * a {@link ConcurrentSkipListMap}.  The elements of the set are kept
 * sorted according to their {@linkplain Comparable natural ordering},
 * or by a {@link Comparator} provided at set creation time, depending
 * on which constructor is used.
 * </p>
 * <p>This implementation provides expected average <i>log(n)</i> time
 * cost for the contains, add, and remove
 * operations and their variants.  Insertion, removal, and access
 * operations safely execute concurrently by multiple threads.
 * Iterators are <i>weakly consistent</i>, returning elements
 * reflecting the state of the set at some point at or since the
 * creation of the iterator.  They do <em>not</em> throw {@link
 * java.util.ConcurrentModificationException}, and may proceed concurrently with
 * other operations.  Ascending ordered views and their iterators are
 * faster than descending ones.
 * </p>
 * <p>Beware that, unlike in most collections, the size
 * method is <em>not</em> a constant-time operation. Because of the
 * asynchronous nature of these sets, determining the current number
 * of elements requires a traversal of the elements. Additionally, the
 * bulk operations addAll, removeAll,
 * retainAll, and containsAll are <em>not</em>
 * guaranteed to be performed atomically. For example, an iterator
 * operating concurrently with an addAll operation might view
 * only some of the added elements.
 * </p>
 * <p>This class and its iterators implement all of the
 * <em>optional</em> methods of the {@link Set} and {@link Iterator}
 * interfaces. Like most other concurrent collection implementations,
 * this class does not permit the use of null elements,
 * because null arguments and return values cannot be reliably
 * distinguished from the absence of elements.
 * </p>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 */
public class CTreeIntSet extends AbstractIntSet implements NavigableIntSet, Cloneable, java.io.Serializable
{

	public static final long serialVersionUID = 1638657097853629391L;

	/**
	 * The underlying map. Uses Boolean.TRUE as value for each
	 * element.  This field is declared final for the sake of thread
	 * safety, which entails some ugliness in clone()
	 */
	private final CNavigableIntObjectMap<Object> m;

	/**
	 * Constructs a new, empty set that orders its elements according to
	 * their {@linkplain Comparable natural ordering}.
	 */
	public CTreeIntSet()
	{
		m = new CTreeIntObjectMap<>();
	}

	/**
	 * Constructs a new, empty set that orders its elements according to
	 * the specified comparator.
	 *
	 * @param comparator the comparator that will be used to order this set.
	 *                   If null, the {@linkplain Comparable natural
	 *                   ordering} of the elements will be used.
	 */
	public CTreeIntSet(IntComparator comparator)
	{
		m = new CTreeIntObjectMap<>(comparator);
	}

	/**
	 * Constructs a new set containing the elements in the specified
	 * collection, that orders its elements according to their
	 * {@linkplain Comparable natural ordering}.
	 *
	 * @param c The elements that will comprise the new set
	 * @throws ClassCastException   if the elements in c are
	 *                              not {@link Comparable}, or are not mutually comparable
	 * @throws NullPointerException if the specified collection or any
	 *                              of its elements are null
	 */
	public CTreeIntSet(IntCollection c)
	{
		this();
		addAll(c);
	}

	/**
	 * Constructs a new set containing the same elements and using the
	 * same ordering as the specified sorted set.
	 *
	 * @param s sorted set whose elements will comprise the new set
	 * @throws NullPointerException if the specified sorted set or any
	 *                              of its elements are null
	 */
	public CTreeIntSet(SortedIntSet s)
	{
		m = new CTreeIntObjectMap<>(s.comparator());
		addAll(s);
	}

	/**
	 * For use by submaps
	 *
	 * @param m the backed map
	 */
	public CTreeIntSet(CNavigableIntObjectMap<Object> m)
	{
		this.m = m;
	}

	/**
	 * Returns a shallow copy of this ConcurrentSkipListSet
	 * instance. (The elements themselves are not cloned.)
	 *
	 * @return a shallow copy of this set
	 */
	public CTreeIntSet clone()
	{
		CTreeIntSet clone = null;
		try
		{
			clone = (CTreeIntSet) super.clone();
			clone.setMap(new CTreeIntObjectMap<>(m));
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}

		return clone;
	}

	/* ---------------- Set operations -------------- */

	/**
	 * <p>
	 * Returns the number of elements in this set.  If this set
	 * contains more than Integer.MAX_VALUE elements, it
	 * returns Integer.MAX_VALUE.
	 * </p>
	 * <p>Beware that, unlike in most collections, this method is
	 * <em>NOT</em> a constant-time operation. Because of the
	 * asynchronous nature of these sets, determining the current
	 * number of elements requires traversing them all to count them.
	 * Additionally, it is possible for the size to change during
	 * execution of this method, in which case the returned result
	 * will be inaccurate. Thus, this method is typically not very
	 * useful in concurrent applications.
	 *
	 * @return the number of elements in this set
	 */
	public int size()
	{
		return m.size();
	}

	/**
	 * Returns true if this set contains no elements.
	 *
	 * @return true if this set contains no elements
	 */
	public boolean isEmpty()
	{
		return m.isEmpty();
	}

	/**
	 * Returns true if this set contains the specified element.
	 * More formally, returns true if and only if this set
	 * contains an element e such that o.equals(e).
	 *
	 * @param o object to be checked for containment in this set
	 * @return true if this set contains the specified element
	 * @throws ClassCastException   if the specified element cannot be
	 *                              compared with the elements currently in this set
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean contains(int o)
	{
		return m.containsKey(o);
	}

	/**
	 * Adds the specified element to this set if it is not already present.
	 * More formally, adds the specified element e to this set if
	 * the set contains no element e2 such that e.equals(e2).
	 * If this set already contains the element, the call leaves the set
	 * unchanged and returns false.
	 *
	 * @param e element to be added to this set
	 * @return true if this set did not already contain the
	 *         specified element
	 * @throws ClassCastException   if e cannot be compared
	 *                              with the elements currently in this set
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean add(int e)
	{
		return m.putIfAbsent(e, Boolean.TRUE) == null;
	}

	/**
	 * Removes the specified element from this set if it is present.
	 * More formally, removes an element e such that
	 * o.equals(e), if this set contains such an element.
	 * Returns true if this set contained the element (or
	 * equivalently, if this set changed as a result of the call).
	 * (This set will not contain the element once the call returns.)
	 *
	 * @param o object to be removed from this set, if present
	 * @return true if this set contained the specified element
	 * @throws ClassCastException   if o cannot be compared
	 *                              with the elements currently in this set
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean remove(int o)
	{
		return m.remove(o, Boolean.TRUE);
	}

	/**
	 * Removes all of the elements from this set.
	 */
	public void clear()
	{
		m.clear();
	}

	/**
	 * Returns an iterator over the elements in this set in ascending order.
	 *
	 * @return an iterator over the elements in this set in ascending order
	 */
	public IntIterator iterator()
	{
		return m.navigableKeySet().iterator();
	}

	/**
	 * Returns an iterator over the elements in this set in descending order.
	 *
	 * @return an iterator over the elements in this set in descending order
	 */
	public IntIterator descendingIterator()
	{
		return m.descendingKeySet().iterator();
	}

	@Override
	public Spliterator.OfInt spliterator() {
		return m.keySet().spliterator();
	}

	/* ---------------- AbstractSet Overrides -------------- */

	/**
	 * Compares the specified object with this set for equality.  Returns
	 * true if the specified object is also a set, the two sets
	 * have the same size, and every member of the specified set is
	 * contained in this set (or equivalently, every member of this set is
	 * contained in the specified set).  This definition ensures that the
	 * equals method works properly across different implementations of the
	 * set interface.
	 *
	 * @param o the object to be compared for equality with this set
	 * @return true if the specified object is equal to this set
	 */
	public boolean equals(Object o)
	{
		// Override AbstractSet version to avoid calling size()
		if(o == this)
		{
			return true;
		}
		if(!(o instanceof IntSet))
		{
			return false;
		}
		IntCollection c = (IntCollection) o;
		try
		{
			return containsAll(c) && c.containsAll(this);
		}
		catch(ClassCastException | NullPointerException unused)
		{
			return false;
		}
	}

	/**
	 * Removes from this set all of its elements that are contained in
	 * the specified collection.  If the specified collection is also
	 * a set, this operation effectively modifies this set so that its
	 * value is the <i>asymmetric set difference</i> of the two sets.
	 *
	 * @param c collection containing elements to be removed from this set
	 * @return true if this set changed as a result of the call
	 * @throws ClassCastException   if the types of one or more elements in this
	 *                              set are incompatible with the specified collection
	 * @throws NullPointerException if the specified collection or any
	 *                              of its elements are null
	 */
	public boolean removeAll(IntCollection c)
	{
		// Override AbstractSet version to avoid unnecessary call to size()
		boolean modified = false;
		for(IntIterator i = c.iterator(); i.hasNext();) {
			modified |= remove(i.nextInt());
		}
		return modified;
	}

	/* ---------------- Relational operations -------------- */

	/**
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException if the specified element is null
	 */
	public int lower(int e)
	{
		return m.lowerKey(e);
	}

	/**
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException if the specified element is null
	 */
	public int floor(int e)
	{
		return m.floorKey(e);
	}

	/**
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException if the specified element is null
	 */
	public int ceiling(int e)
	{
		return m.ceilingKey(e);
	}

	/**
	 * @throws ClassCastException   {@inheritDoc}
	 * @throws NullPointerException if the specified element is null
	 */
	public int higher(int e)
	{
		return m.higherKey(e);
	}

	public int pollFirst()
	{
		IntObjectPair<Object> e = m.pollFirstEntry();
		return e == null ? null : e.getKey();
	}

	public int pollLast()
	{
		IntObjectPair<Object> e = m.pollLastEntry();
		return e == null ? null : e.getKey();
	}


	/* ---------------- SortedSet operations -------------- */


	public IntComparator comparator()
	{
		return m.comparator();
	}

	/**
	 * @throws java.util.NoSuchElementException
	 *          {@inheritDoc}
	 */
	public int first()
	{
		return m.firstKey();
	}

	/**
	 * @throws java.util.NoSuchElementException
	 *          {@inheritDoc}
	 */
	public int last()
	{
		return m.lastKey();
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code fromElement} or
	 *                                  {@code toElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet subSet(int fromElement, boolean fromInclusive, int toElement, boolean toInclusive)
	{
		return new CTreeIntSet(m.subMap(fromElement, fromInclusive, toElement, toInclusive));
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code toElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet headSet(int toElement, boolean inclusive)
	{
		return new CTreeIntSet(m.headMap(toElement, inclusive));
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code fromElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet tailSet(int fromElement, boolean inclusive)
	{
		return new CTreeIntSet(m.tailMap(fromElement, inclusive));
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code fromElement} or
	 *                                  {@code toElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet subSet(int fromElement, int toElement)
	{
		return subSet(fromElement, true, toElement, false);
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code toElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet headSet(int toElement)
	{
		return headSet(toElement, false);
	}

	/**
	 * @throws ClassCastException	   {@inheritDoc}
	 * @throws NullPointerException	 if {@code fromElement} is null
	 * @throws IllegalArgumentException {@inheritDoc}
	 */
	public NavigableIntSet tailSet(int fromElement)
	{
		return tailSet(fromElement, true);
	}

	/**
	 * <p>
	 * Returns a reverse order view of the elements contained in this set.
	 * The descending set is backed by this set, so changes to the set are
	 * reflected in the descending set, and vice-versa.
	 * </p>
	 * <p>The returned set has an ordering equivalent to
	 * {@link Comparators#reverseOrder(IntComparator) }.
	 * The expression {@code s.descendingSet().descendingSet()} returns a
	 * view of {@code s} essentially equivalent to {@code s}.
	 *
	 * @return a reverse order view of this set
	 */
	public NavigableIntSet descendingSet()
	{
		return new CTreeIntSet(m.descendingMap());
	}

	// Support for resetting map in clone
	private static final Unsafe unsafe = Unsafe.getUnsafe();
	private static final long mapOffset;

	static
	{
		try
		{
			mapOffset = unsafe.objectFieldOffset(ConcurrentSkipListSet.class.getDeclaredField("m"));
		}
		catch(Exception ex)
		{
			throw new Error(ex);
		}
	}

	private void setMap(CNavigableIntObjectMap<Object> map)
	{
		unsafe.putObjectVolatile(this, mapOffset, map);
	}
}
