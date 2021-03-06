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

import io.github.andyalvarezdev.primitive.collections.IntCollection;
import io.github.andyalvarezdev.primitive.collections.LongCollection;
import io.github.andyalvarezdev.primitive.iterators.LongIterator;
import io.github.andyalvarezdev.primitive.maps.impl.HashIntObjectMap;
import io.github.andyalvarezdev.primitive.maps.impl.HashLongObjectMap;
import io.github.andyalvarezdev.primitive.sets.IntSet;
import io.github.andyalvarezdev.primitive.sets.LongSet;
import io.github.andyalvarezdev.primitive.sets.abstracts.AbstractLongSet;

import java.util.ConcurrentModificationException;

/**
 * <p>
 * This class implements the Set interface, backed by a hash table
 * (actually a HashMap instance).  It makes no guarantees as to the
 * iteration order of the set; in particular, it does not guarantee that the
 * order will remain constant over time.  This class permits the null
 * element.
 * </p>
 * <p>This class offers constant time performance for the basic operations
 * (add, remove, contains and size),
 * assuming the hash function disperses the elements properly among the
 * buckets.  Iterating over this set requires time proportional to the sum of
 * the HashSet instance's size (the number of elements) plus the
 * "capacity" of the backing HashMap instance (the number of
 * buckets).  Thus, it's very important not to set the initial capacity too
 * high (or the load factor too low) if iteration performance is important.
 * </p>
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash set concurrently, and at least one of
 * the threads modifies the set, it <i>must</i> be synchronized externally.
 * This is typically accomplished by synchronizing on some object that
 * naturally encapsulates the set.
 * </p>
 * <p>
 * If no such object exists, the set should be "wrapped" using the
 * {@link java.util.Collections#synchronizedSet Collections.synchronizedSet}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the set:<pre>
 *   Set s = Collections.synchronizedSet(new HashSet(...));</pre>
 *
 * <p>The iterators returned by this class's iterator method are
 * <i>fail-fast</i>: if the set is modified at any time after the iterator is
 * created, in any way except through the iterator's own remove
 * method, the Iterator throws a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 * </p>
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw ConcurrentModificationException on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 * </p>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @see     IntCollection
 * @see	 IntSet
 * @see	 TreeIntSet
 * @see     HashIntObjectMap
 */
public class HashLongSet extends AbstractLongSet implements LongSet, Cloneable, java.io.Serializable
{

	public static final long serialVersionUID = -5863757022294757843L;

	private transient HashLongObjectMap<Object> map;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	/**
	 * Constructs a new, empty set; the backing HashMap instance has
	 * default initial capacity (16) and load factor (0.75).
	 */
	public HashLongSet()
	{
		map = new HashLongObjectMap<Object>();
	}

	/**
	 * Constructs a new set containing the elements in the specified
	 * collection.  The HashMap is created with default load factor
	 * (0.75) and an initial capacity sufficient to contain the elements in
	 * the specified collection.
	 *
	 * @param c the collection whose elements are to be placed into this set
	 * @throws NullPointerException if the specified collection is null
	 */
	public HashLongSet(LongCollection c)
	{
		map = new HashLongObjectMap<Object>(Math.max((int) (c.size() / .75f) + 1, 16));
		addAll(c);
	}

	/**
	 * Constructs a new, empty set; the backing HashMap instance has
	 * the specified initial capacity and the specified load factor.
	 *
	 * @param initialCapacity the initial capacity of the hash map
	 * @param loadFactor	  the load factor of the hash map
	 * @throws IllegalArgumentException if the initial capacity is less
	 *                                  than zero, or if the load factor is nonpositive
	 */
	public HashLongSet(int initialCapacity, float loadFactor)
	{
		map = new HashLongObjectMap<Object>(initialCapacity, loadFactor);
	}

	/**
	 * Constructs a new, empty set; the backing HashMap instance has
	 * the specified initial capacity and default load factor (0.75).
	 *
	 * @param initialCapacity the initial capacity of the hash table
	 * @throws IllegalArgumentException if the initial capacity is less
	 *                                  than zero
	 */
	public HashLongSet(int initialCapacity)
	{
		map = new HashLongObjectMap<Object>(initialCapacity);
	}

	/**
	 * Returns an iterator over the elements in this set.  The elements
	 * are returned in no particular order.
	 *
	 * @return an Iterator over the elements in this set
	 * @see ConcurrentModificationException
	 */
	public LongIterator iterator()
	{
		return map.keySet().iterator();
	}

	/**
	 * Returns the number of elements in this set (its cardinality).
	 *
	 * @return the number of elements in this set (its cardinality)
	 */
	public int size()
	{
		return map.size();
	}

	/**
	 * Returns true if this set contains no elements.
	 *
	 * @return true if this set contains no elements
	 */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/**
	 * Returns true if this set contains the specified element.
	 * More formally, returns true if and only if this set
	 * contains an element e such that
	 * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
	 *
	 * @param o element whose presence in this set is to be tested
	 * @return true if this set contains the specified element
	 */
	public boolean contains(long o)
	{
		return map.containsKey(o);
	}

	/**
	 * Adds the specified element to this set if it is not already present.
	 * More formally, adds the specified element e to this set if
	 * this set contains no element e2 such that
	 * (e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2)).
	 * If this set already contains the element, the call leaves the set
	 * unchanged and returns false.
	 *
	 * @param e element to be added to this set
	 * @return true if this set did not already contain the specified
	 *         element
	 */
	public boolean add(long e)
	{
		return map.put(e, PRESENT) == null;
	}

	/**
	 * Removes the specified element from this set if it is present.
	 * More formally, removes an element e such that
	 * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)),
	 * if this set contains such an element.  Returns true if
	 * this set contained the element (or equivalently, if this set
	 * changed as a result of the call).  (This set will not contain the
	 * element once the call returns.)
	 *
	 * @param o object to be removed from this set, if present
	 * @return true if the set contained the specified element
	 */
	public boolean remove(long o)
	{
		return map.remove(o) == PRESENT;
	}

	/**
	 * Removes all of the elements from this set.
	 * The set will be empty after this call returns.
	 */
	public void clear()
	{
		map.clear();
	}

	/**
	 * Returns a shallow copy of this HashSet instance: the elements
	 * themselves are not cloned.
	 *
	 * @return a shallow copy of this set
	 */
	@SuppressWarnings("unchecked")
	public Object clone()
	{
		try
		{
			HashLongSet newSet = (HashLongSet) super.clone();
			newSet.map = (HashLongObjectMap<Object>) map.clone();
			return newSet;
		}
		catch(CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	/**
	 * Save the state of this HashSet instance to a stream (that is,
	 * serialize it).
	 *
	 * @serialData The capacity of the backing HashMap instance
	 * (int), and its load factor (float) are emitted, followed by
	 * the size of the set (the number of elements it contains)
	 * (int), followed by all of its elements (each an Object) in
	 * no particular order.
	 *
	 * @param s the stream
	 * @throws java.io.IOException if the stream throws a exception
	 */
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException
	{
		// Write out any hidden serialization magic
		s.defaultWriteObject();

		// Write out HashMap capacity and load factor
		s.writeInt(map.capacity());
		s.writeFloat(map.loadFactor());

		// Write out size
		s.writeInt(map.size());

		// Write out all elements in the proper order.
		for(LongIterator i = map.keySet().iterator(); i.hasNext();)
		{
			s.writeLong(i.next());
		}
	}

	/**
	 * Reconstitute the HashSet instance from a stream (that is,
	 * deserialize it).
	 *
	 * @param s the stream
	 * @throws java.io.IOException if the stream throws a exception
	 * @throws  ClassNotFoundException it the stream represent a unknown class
	 */
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException
	{
		// Read in any hidden serialization magic
		s.defaultReadObject();

		// Read in HashMap capacity and load factor and create backing HashMap
		int capacity = s.readInt();
		float loadFactor = s.readFloat();
		//map = (((HashIntSet) this) instanceof LinkedHashSet ? new LinkedHashMap<E, Object>(capacity, loadFactor) : new HashMap<E, Object>(capacity, loadFactor));
		map = new HashLongObjectMap<Object>(capacity, loadFactor);

		// Read in size
		int size = s.readInt();

		// Read in all elements in the proper order.
		for(int i = 0; i < size; i++)
		{
			long e = s.readLong();
			map.put(e, PRESENT);
		}
	}
}
