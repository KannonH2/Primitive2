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
package io.github.andyalvarezdev.primitive.maps;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import io.github.andyalvarezdev.primitive.collections.IntCollection;
import io.github.andyalvarezdev.primitive.maps.impl.CTreeIntObjectMap;
import io.github.andyalvarezdev.primitive.pair.IntObjectPair;
import io.github.andyalvarezdev.primitive.comparators.IntComparator;
import io.github.andyalvarezdev.primitive.sets.IntSet;
import io.github.andyalvarezdev.primitive.sets.SortedIntSet;

/**
 * <p>
 * A {@link Map} that further provides a <i>total ordering</i> on its keys.
 * The map is ordered according to the {@linkplain Comparable natural
 * ordering} of its keys, or by a {@link Comparator} typically
 * provided at sorted map creation time.  This order is reflected when
 * iterating over the sorted map's collection views (returned by the
 * entrySet, keySet and values methods).
 * Several additional operations are provided to take advantage of the
 * ordering.  (This interface is the map analogue of {@link
 * SortedIntSet}.)
 * </p>
 * <p>All keys inserted into a sorted map must implement the Comparable
 * interface (or be accepted by the specified comparator).  Furthermore, all
 * such keys must be <i>mutually comparable</i>: k1.compareTo(k2) (or
 * comparator.compare(k1, k2)) must not throw a
 * ClassCastException for any keys k1 and k2 in
 * the sorted map.  Attempts to violate this restriction will cause the
 * offending method or constructor invocation to throw a
 * ClassCastException.
 * </p>
 * <p>Note that the ordering maintained by a sorted map (whether or not an
 * explicit comparator is provided) must be <i>consistent with equals</i> if
 * the sorted map is to correctly implement the Map interface.  (See
 * the Comparable interface or Comparator interface for a
 * precise definition of <i>consistent with equals</i>.)  This is so because
 * the Map interface is defined in terms of the equals
 * operation, but a sorted map performs all key comparisons using its
 * compareTo (or compare) method, so two keys that are
 * deemed equal by this method are, from the standpoint of the sorted map,
 * equal.  The behavior of a tree map <i>is</i> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey the general
 * contract of the Map interface.
 * </p>
 * <p>All general-purpose sorted map implementation classes should
 * provide four "standard" constructors: 1) A void (no arguments)
 * constructor, which creates an empty sorted map sorted according to
 * the natural ordering of its keys.  2) A constructor with a
 * single argument of type Comparator, which creates an empty
 * sorted map sorted according to the specified comparator.  3) A
 * constructor with a single argument of type Map, which
 * creates a new map with the same key-value mappings as its argument,
 * sorted according to the keys' natural ordering.  4) A constructor
 * with a single argument of type SortedMap,
 * which creates a new sorted map with the same key-value mappings and
 * the same ordering as the input sorted map.  There is no way to
 * enforce this recommendation, as interfaces cannot contain
 * constructors.
 * </p>
 * <p>Note: several methods return submaps with restricted key ranges.
 * Such ranges are <i>half-open</i>, that is, they include their low
 * endpoint but not their high endpoint (where applicable).  If you need a
 * <i>closed range</i> (which includes both endpoints), and the key type
 * allows for calculation of the successor of a given key, merely request
 * the subrange from lowEndpoint to
 * successor(highEndpoint).  For example, suppose that m
 * is a map whose keys are strings.  The following idiom obtains a view
 * containing all of the key-value mappings in m whose keys are
 * between low and high, inclusive:</p>
 * <pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low, high+"\0");</pre>
 *
 * <p>
 * A similar technique can be used to generate an <i>open range</i>
 * (which contains neither endpoint).  The following idiom obtains a
 * view containing all of the key-value mappings in m whose keys
 * are between low and high, exclusive:</p>
 * <pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low+"\0", high);</pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <V> the type of mapped values
 * @see Map
 * @see CTreeIntObjectMap
 * @see SortedIntSet
 * @see Comparator
 * @see Comparable
 * @see IntCollection
 * @see ClassCastException
 */
public interface SortedIntObjectMap<V> extends IntObjectMap<V>
{
	/**
	 * Returns the comparator used to order the keys in this map, or
	 * null if this map uses the {@linkplain Comparable
	 * natural ordering} of its keys.
	 *
	 * @return the comparator used to order the keys in this map,
	 *         or null if this map uses the natural ordering
	 *         of its keys
	 */
	IntComparator comparator();

	/**
	 * <p>
	 * Returns a view of the portion of this map whose keys range from
	 * fromKey, inclusive, to toKey, exclusive.  (If
	 * fromKey and toKey are equal, the returned map
	 * is empty.)  The returned map is backed by this map, so changes
	 * in the returned map are reflected in this map, and vice-versa.
	 * The returned map supports all optional map operations that this
	 * map supports.
	 * </p>
	 * <p>The returned map will throw an IllegalArgumentException
	 * on an attempt to insert a key outside its range.
	 *
	 * @param fromKey low endpoint (inclusive) of the keys in the returned map
	 * @param toKey   high endpoint (exclusive) of the keys in the returned map
	 * @return a view of the portion of this map whose keys range from
	 *         fromKey, inclusive, to toKey, exclusive
	 * @throws ClassCastException	   if fromKey and toKey
	 *                                  cannot be compared to one another using this map's comparator
	 *                                  (or, if the map has no comparator, using natural ordering).
	 *                                  Implementations may, but are not required to, throw this
	 *                                  exception if fromKey or toKey
	 *                                  cannot be compared to keys currently in the map.
	 * @throws NullPointerException	 if fromKey or toKey
	 *                                  is null and this map does not permit null keys
	 * @throws IllegalArgumentException if fromKey is greater than
	 *                                  toKey; or if this map itself has a restricted
	 *                                  range, and fromKey or toKey lies
	 *                                  outside the bounds of the range
	 */
	SortedIntObjectMap<V> subMap(int fromKey, int toKey);

	/**
	 * <p>
	 * Returns a view of the portion of this map whose keys are
	 * strictly less than toKey.  The returned map is backed
	 * by this map, so changes in the returned map are reflected in
	 * this map, and vice-versa.  The returned map supports all
	 * optional map operations that this map supports.
	 * </p>
	 * <p>The returned map will throw an IllegalArgumentException
	 * on an attempt to insert a key outside its range.
	 *
	 * @param toKey high endpoint (exclusive) of the keys in the returned map
	 * @return a view of the portion of this map whose keys are strictly
	 *         less than toKey
	 * @throws ClassCastException	   if toKey is not compatible
	 *                                  with this map's comparator (or, if the map has no comparator,
	 *                                  if toKey does not implement {@link Comparable}).
	 *                                  Implementations may, but are not required to, throw this
	 *                                  exception if toKey cannot be compared to keys
	 *                                  currently in the map.
	 * @throws NullPointerException	 if toKey is null and
	 *                                  this map does not permit null keys
	 * @throws IllegalArgumentException if this map itself has a
	 *                                  restricted range, and toKey lies outside the
	 *                                  bounds of the range
	 */
	SortedIntObjectMap<V> headMap(int toKey);

	/**
	 * <p>
	 * Returns a view of the portion of this map whose keys are
	 * greater than or equal to fromKey.  The returned map is
	 * backed by this map, so changes in the returned map are
	 * reflected in this map, and vice-versa.  The returned map
	 * supports all optional map operations that this map supports.
	 * </p>
	 * <p>The returned map will throw an IllegalArgumentException
	 * on an attempt to insert a key outside its range.
	 *
	 * @param fromKey low endpoint (inclusive) of the keys in the returned map
	 * @return a view of the portion of this map whose keys are greater
	 *         than or equal to fromKey
	 * @throws ClassCastException	   if fromKey is not compatible
	 *                                  with this map's comparator (or, if the map has no comparator,
	 *                                  if fromKey does not implement {@link Comparable}).
	 *                                  Implementations may, but are not required to, throw this
	 *                                  exception if fromKey cannot be compared to keys
	 *                                  currently in the map.
	 * @throws NullPointerException	 if fromKey is null and
	 *                                  this map does not permit null keys
	 * @throws IllegalArgumentException if this map itself has a
	 *                                  restricted range, and fromKey lies outside the
	 *                                  bounds of the range
	 */
	SortedIntObjectMap<V> tailMap(int fromKey);

	/**
	 * Returns the first (lowest) key currently in this map.
	 *
	 * @return the first (lowest) key currently in this map
	 * @throws java.util.NoSuchElementException if this map is empty
	 */
	int firstKey();

	/**
	 * Returns the last (highest) key currently in this map.
	 *
	 * @return the last (highest) key currently in this map
	 * @throws java.util.NoSuchElementException if this map is empty
	 */
	int lastKey();

	/**
	 * Returns a {@link Set} view of the keys contained in this map.
	 * The set's iterator returns the keys in ascending order.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  If the map is modified
	 * while an iteration over the set is in progress (except through
	 * the iterator's own remove operation), the results of
	 * the iteration are undefined.  The set supports element removal,
	 * which removes the corresponding mapping from the map, via the
	 * Iterator.remove, Set.remove,
	 * removeAll, retainAll, and clear
	 * operations.  It does not support the add or addAll
	 * operations.
	 *
	 * @return a set view of the keys contained in this map, sorted in
	 *         ascending order
	 */
	IntSet keySet();

	/**
	 * Returns a {@link Collection} view of the values contained in this map.
	 * The collection's iterator returns the values in ascending order
	 * of the corresponding keys.
	 * The collection is backed by the map, so changes to the map are
	 * reflected in the collection, and vice-versa.  If the map is
	 * modified while an iteration over the collection is in progress
	 * (except through the iterator's own remove operation),
	 * the results of the iteration are undefined.  The collection
	 * supports element removal, which removes the corresponding
	 * mapping from the map, via the Iterator.remove,
	 * Collection.remove, removeAll,
	 * retainAll and clear operations.  It does not
	 * support the add or addAll operations.
	 *
	 * @return a collection view of the values contained in this map,
	 *         sorted in ascending key order
	 */
	Collection<V> values();

	/**
	 * Returns a {@link Set} view of the mappings contained in this map.
	 * The set's iterator returns the entries in ascending key order.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  If the map is modified
	 * while an iteration over the set is in progress (except through
	 * the iterator's own remove operation, or through the
	 * setValue operation on a map entry returned by the
	 * iterator) the results of the iteration are undefined.  The set
	 * supports element removal, which removes the corresponding
	 * mapping from the map, via the Iterator.remove,
	 * Set.remove, removeAll, retainAll and
	 * clear operations.  It does not support the
	 * add or addAll operations.
	 *
	 * @return a set view of the mappings contained in this map,
	 *         sorted in ascending key order
	 */
	Set<IntObjectPair<V>> entrySet();
}
