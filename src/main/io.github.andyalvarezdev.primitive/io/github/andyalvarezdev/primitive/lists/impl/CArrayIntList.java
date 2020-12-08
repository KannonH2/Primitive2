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
package io.github.andyalvarezdev.primitive.lists.impl;

import java.util.*;

import io.github.andyalvarezdev.primitive.collections.IntCollection;
import io.github.andyalvarezdev.primitive.iterators.IntIterator;
import io.github.andyalvarezdev.primitive.iterators.IntListIterator;
import io.github.andyalvarezdev.primitive.lists.IntList;
import io.github.andyalvarezdev.primitive.lists.abstracts.AbstractIntList;

/**
 * <p>
 * A thread-safe variant of {@link ArrayIntList} in which all mutative
 * operations (add, set, and so on) are implemented by
 * making a fresh copy of the underlying array.
 * </p>
 * <p> This is ordinarily too costly, but may be <em>more</em> efficient
 * than alternatives when traversal operations vastly outnumber
 * mutations, and is useful when you cannot or don't want to
 * synchronize traversals, yet need to preclude interference among
 * concurrent threads.  The "snapshot" style iterator method uses a
 * reference to the state of the array at the point that the iterator
 * was created. This array never changes during the lifetime of the
 * iterator, so interference is impossible and the iterator is
 * guaranteed not to throw ConcurrentModificationException.
 * The iterator will not reflect additions, removals, or changes to
 * the list since the iterator was created.  Element-changing
 * operations on iterators themselves (remove, set, and
 * add) are not supported. These methods throw
 * UnsupportedOperationException.
 * </p>
 * <p>All elements are permitted, including null.
 * </p>
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code CopyOnWriteArrayList}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code CopyOnWriteArrayList} in another thread.
 * </p>
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 */
public class CArrayIntList implements IntList, RandomAccess, Cloneable, java.io.Serializable
{
    public static final long serialVersionUID = 7110861762579127969L;
    /**
     * The lock protecting all mutators
     */
    private transient final Object lock = new Object();

    /**
     * The array, accessed only via getArray/setArray.
     */
    private volatile transient int[] array;

    /**
     * Creates an empty list.
     */
    public CArrayIntList() {
        setArray(new int[0]);
    }

    /**
     * Creates a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection of initially held elements
     * @throws NullPointerException if the specified collection is null
     */
    public CArrayIntList(IntCollection c) {
        setArray(c.toArray());
    }

    /**
     * Creates a list holding a copy of the given array.
     *
     * @param source the array (a copy of this array is used as the
     *                 internal array)
     * @throws NullPointerException if the specified array is null
     */
    public CArrayIntList(int[] source) {
        setArray(Arrays.copyOf(source, source.length));
    }

    /**
     * Gets the array.  Non-private so as to also be accessible
     * from CopyOnWriteArraySet class.
     */
    final int[] getArray() {
        return array;
    }

    /**
     * Sets the array.
     */
    final void setArray(int[] a) {
        array = a;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return getArray().length;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Test for equality, coping with nulls.
     */
    private static boolean eq(int o1, int o2) {
        return o1 == o2;
    }

    /**
     * static version of indexOf, to allow repeated calls without
     * needing to re-acquire array each time.
     *
     * @param o		element to search for
     * @param elements the array
     * @param index	first index to search
     * @param fence	one past last index to search
     * @return index of element, or -1 if absent
     */
    private static int indexOf(int o, int[] elements, int index, int fence) {
        for(int i = index; i < fence; i++) {
            if(o == elements[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * static version of lastIndexOf.
     *
     * @param o		element to search for
     * @param elements the array
     * @param index	first index to search
     * @return index of element, or -1 if absent
     */
    private static int lastIndexOf(int o, int[] elements, int index) {
        for(int i = index; i >= 0; i--) {
            if(o == elements[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if this list contains the specified element.
     * More formally, returns true if and only if this list contains
     * at least one element e such that
     * (o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e)).
     *
     * @param o element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    public boolean contains(int o) {
        int[] elements = getArray();
        return indexOf(o, elements, 0, elements.length) >= 0;
    }

    /**
     * {@inheritDoc}
     */
    public int indexOf(int o) {
        int[] elements = getArray();
        return indexOf(o, elements, 0, elements.length);
    }

    /**
     * Returns the index of the first occurrence of the specified element in
     * this list, searching forwards from index, or returns -1 if
     * the element is not found.
     * More formally, returns the lowest index i such that
     * (i&nbsp;&gt;=&nbsp;index&nbsp;&amp;&amp;&nbsp;(e==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;e.equals(get(i)))),
     * or -1 if there is no such index.
     *
     * @param e	 element to search for
     * @param index index to start searching from
     * @return the index of the first occurrence of the element in
     *         this list at position index or later in the list;
     *         -1 if the element is not found.
     * @throws IndexOutOfBoundsException if the specified index is negative
     */
    public int indexOf(int e, int index) {
        int[] elements = getArray();
        return indexOf(e, elements, index, elements.length);
    }

    /**
     * {@inheritDoc}
     */
    public int lastIndexOf(int o) {
        int[] elements = getArray();
        return lastIndexOf(o, elements, elements.length - 1);
    }

    /**
     * Returns the index of the last occurrence of the specified element in
     * this list, searching backwards from index, or returns -1 if
     * the element is not found.
     * More formally, returns the highest index i such that
     * (i&nbsp;&lt;=&nbsp;index&nbsp;&amp;&amp;&nbsp;(e==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;e.equals(get(i)))),
     * or -1 if there is no such index.
     *
     * @param e	 element to search for
     * @param index index to start searching backwards from
     * @return the index of the last occurrence of the element at position
     *         less than or equal to index in this list;
     *         -1 if the element is not found.
     * @throws IndexOutOfBoundsException if the specified index is greater
     *                                   than or equal to the current size of this list
     */
    public int lastIndexOf(int e, int index) {
        int[] elements = getArray();
        return lastIndexOf(e, elements, index);
    }

    /**
     * Returns a shallow copy of this list.  (The elements themselves
     * are not copied.)
     *
     * @return a clone of this list
     */
    public Object clone() {
        try {
            CArrayIntList c = (CArrayIntList) (super.clone());
            //TODO [VISTALL] c.resetLock();
            return c;
        }
        catch(CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    /**
     * <p>
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     * </p>
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     * </p>
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this list
     */
    public int[] toArray() {
        int[] elements = getArray();
        return Arrays.copyOf(elements, elements.length);
    }

    /**
     * <p>
     * Returns an array containing all of the elements in this list in
     * proper sequence (from first to last element); the runtime type of
     * the returned array is that of the specified array.  If the list fits
     * in the specified array, it is returned therein.  Otherwise, a new
     * array is allocated with the runtime type of the specified array and
     * the size of this list.
     * </p>
     * <p>If this list fits in the specified array with room to spare
     * (i.e., the array has more elements than this list), the element in
     * the array immediately following the end of the list is set to
     * null.  (This is useful in determining the length of this
     * list <i>only</i> if the caller knows that this list does not contain
     * any null elements.)
     * </p>
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * </p>
     * <p>Suppose x is a list known to contain only strings.
     * The following code can be used to dump the list into a newly
     * allocated array of String:
     * </p>
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that toArray(new Object[0]) is identical in function to
     * toArray().
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing all the elements in this list
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in
     *                              this list
     * @throws NullPointerException if the specified array is null
     */
    public int[] toArray(int a[]) {
        int[] elements = getArray();
        int len = elements.length;
        if(a.length < len) {
            return Arrays.copyOf(elements, len);
        }
        else {
            System.arraycopy(elements, 0, a, 0, len);
            if(a.length > len)
            {
                a[len] = 0;
            }
            return a;
        }
    }

    // Positional Access Operations

    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public int get(int index)
    {
        return (getArray()[index]);
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public int set(int index, int element) {
        synchronized (lock)  {
            int[] elements = getArray();
            int oldValue = elements[index];

            if(oldValue != element) {
                elements = elements.clone();
                elements[index] = element;
                setArray(elements);
            }
            return oldValue;
        }
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return true (as specified by {@link IntCollection#add(int)} )
     */
    public boolean add(int e) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            int[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        }
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, int element) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            if(index > len || index < 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
            }
            int[] newElements;
            int numMoved = len - index;
            if(numMoved == 0) {
                newElements = Arrays.copyOf(elements, len + 1);
            }
            else
            {
                newElements = new int[len + 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index, newElements, index + 1, numMoved);
            }
            newElements[index] = element;
            setArray(newElements);
        }
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the list.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public int removeByIndex(int index) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            int oldValue = elements[index];
            int numMoved = len - index - 1;
            if(numMoved == 0)
            {
                setArray(Arrays.copyOf(elements, len - 1));
            }
            else
            {
                int[] newElements = new int[len - 1];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index + 1, newElements, index, numMoved);
                setArray(newElements);
            }
            return oldValue;
        }
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * i such that
     * (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))
     * (if such an element exists).  Returns true if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    public boolean remove(int o) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            if(len != 0) {
                // Copy while searching for element to remove
                // This wins in the normal case of element being present
                int newlen = len - 1;
                int[] newElements = new int[newlen];

                for(int i = 0; i < newlen; ++i) {
                    if(eq(o, elements[i])) {
                        // found one;  copy remaining and exit
                        for(int k = i + 1; k < len; ++k) {
                            newElements[k - 1] = elements[k];
                        }
                        setArray(newElements);
                        return true;
                    }
                    else {
                        newElements[i] = elements[i];
                    }
                }

                // special handling for last cell
                if(eq(o, elements[newlen]))
                {
                    setArray(newElements);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Removes from this list all of the elements whose index is between
     * fromIndex, inclusive, and toIndex, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by (toIndex - fromIndex) elements.
     * (If toIndex==fromIndex, this operation has no effect.)
     *
     * @param fromIndex index of first element to be removed
     * @param toIndex   index after last element to be removed
     * @throws IndexOutOfBoundsException if fromIndex or toIndex out of
     *                                   range (fromIndex &lt; 0 || fromIndex &gt;= size() || toIndex
     *                                   &gt; size() || toIndex &lt; fromIndex)
     */
    private void removeRange(int fromIndex, int toIndex) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;

            if(fromIndex < 0 || fromIndex >= len || toIndex > len || toIndex < fromIndex) {
                throw new IndexOutOfBoundsException();
            }
            int newlen = len - (toIndex - fromIndex);
            int numMoved = len - toIndex;
            if(numMoved == 0) {
                setArray(Arrays.copyOf(elements, newlen));
            } else {
                int[] newElements = new int[newlen];
                System.arraycopy(elements, 0, newElements, 0, fromIndex);
                System.arraycopy(elements, toIndex, newElements, fromIndex, numMoved);
                setArray(newElements);
            }
        }
    }

    /**
     * Append the element if not present.
     *
     * @param e element to be added to this list, if absent
     * @return true if the element was added
     */
    public boolean addIfAbsent(int e) {
        synchronized (lock){
            // Copy while checking if already present.
            // This wins in the most common case where it is not present
            int[] elements = getArray();
            int len = elements.length;
            int[] newElements = new int[len + 1];
            for(int i = 0; i < len; ++i) {
                if(eq(e, elements[i])) {
                    return false; // exit, throwing away copy
                } else {
                    newElements[i] = elements[i];
                }
            }
            newElements[len] = e;
            setArray(newElements);
            return true;
        }
    }

    /**
     * Returns true if this list contains all of the elements of the
     * specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return true if this list contains all of the elements of the
     *         specified collection
     * @throws NullPointerException if the specified collection is null
     * @see #contains(int)
     */
    public boolean containsAll(IntCollection c) {
        int[] elements = getArray();
        int len = elements.length;
        for(int e : c.toArray()) {
            if(indexOf(e, elements, 0, len) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes from this list all of its elements that are contained in
     * the specified collection. This is a particularly expensive operation
     * in this class because of the need for an internal temporary array.
     *
     * @param c collection containing elements to be removed from this list
     * @return true if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection (optional)
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements (optional),
     *                              or if the specified collection is null
     * @see #remove(int)
     */
    public boolean removeAll(IntCollection c) {
        synchronized ( lock ) {
            int[] elements = getArray();
            int len = elements.length;
            if(len != 0) {
                // temp array holds those elements we know we want to keep
                int newlen = 0;
                int[] temp = new int[len];
                for (int element : elements) {
                    if (!c.contains(element)) {
                        temp[newlen++] = element;
                    }
                }
                if(newlen != len) {
                    setArray(Arrays.copyOf(temp, newlen));
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all of
     * its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return true if this list changed as a result of the call
     * @throws ClassCastException   if the class of an element of this list
     *                              is incompatible with the specified collection (optional)
     * @throws NullPointerException if this list contains a null element and the
     *                              specified collection does not permit null elements (optional),
     *                              or if the specified collection is null
     * @see #remove(int)
     */
    public boolean retainAll(IntCollection c) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            if(len != 0) {
                // temp array holds those elements we know we want to keep
                int newlen = 0;
                int[] temp = new int[len];
                for (int element : elements) {
                    if (c.contains(element)) {
                        temp[newlen++] = element;
                    }
                }
                if(newlen != len) {
                    setArray(Arrays.copyOf(temp, newlen));
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Appends all of the elements in the specified collection that
     * are not already contained in this list, to the end of
     * this list, in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param c collection containing elements to be added to this list
     * @return the number of elements added
     * @throws NullPointerException if the specified collection is null
     * @see #addIfAbsent(int)
     */
    public int addAllAbsent(IntCollection c) {
        int[] cs = c.toArray();
        return addAllAbsent(cs);
    }

    public int addAllAbsent(int[] items) {
        if(items.length == 0) {
            return 0;
        }
        int[] uniq = new int[items.length];
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            int added = 0;
            int i = 0;
            while (i < items.length) { // scan for duplicates
                int e = items[i];
                if(indexOf(e, elements, 0, len) < 0 && indexOf(e, uniq, 0, added) < 0) {
                    uniq[added++] = e;
                }
                ++i;
            }
            if(added > 0) {
                int[] newElements = Arrays.copyOf(elements, len + added);
                System.arraycopy(uniq, 0, newElements, len, added);
                setArray(newElements);
            }
            return added;
        }
    }

    /**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
    public void clear() {
        synchronized (lock) {
            setArray(new int[0]);
        }
    }

    /**
     * Appends all of the elements in the specified collection to the end
     * of this list, in the order that they are returned by the specified
     * collection's iterator.
     *
     * @param c collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     * @see #add(int)
     */
    @Override
    public boolean addAll(IntCollection c) {
        return addAll(c.toArray());
    }

    /**
     * Appends all of the elements in the specified array to the end
     * of this list
     *
     * @param items collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws NullPointerException if the specified array is null
     * @see #add(int)
     */
    @Override
    public boolean addAll(int[] items) {
        if(items.length == 0) {
            return false;
        }
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            int[] newElements = Arrays.copyOf(elements, len + items.length);
            System.arraycopy(items, 0, newElements, len, items.length);
            setArray(newElements);
            return true;
        }
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in this list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element
     *              from the specified collection
     * @param c	 collection containing elements to be added to this list
     * @return true if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException	  if the specified collection is null
     * @see #add(int, int)
     */
    public boolean addAll(int index, IntCollection c) {
        int[] cs = c.toArray();
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            if(index > len || index < 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + len);
            }
            if(cs.length == 0) {
                return false;
            }
            int numMoved = len - index;
            int[] newElements;
            if(numMoved == 0) {
                newElements = Arrays.copyOf(elements, len + cs.length);
            }
            else {
                newElements = new int[len + cs.length];
                System.arraycopy(elements, 0, newElements, 0, index);
                System.arraycopy(elements, index, newElements, index + cs.length, numMoved);
            }
            System.arraycopy(cs, 0, newElements, index, cs.length);
            setArray(newElements);
            return true;
        }
    }

    /**
     * Save the state of the list to a stream (i.e., serialize it).
     *
     * @param s the stream
     * @serialData The length of the array backing the list is emitted
     * (int), followed by all of its elements (each an Object)
     * in the proper order.
     *
     * @throws java.io.IOException if the stream throws an exception
     */
    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {

        // Write out element count, and any hidden stuff
        s.defaultWriteObject();

        int[] elements = getArray();
        int len = elements.length;
        // Write out array length
        s.writeInt(len);

        // Write out all elements in the proper order.
        for (int element : elements) {
            s.writeInt(element);
        }
    }

    /**
     * Reconstitute the list from a stream (i.e., deserialize it).
     *
     * @param s the stream
     *
     * @throws java.io.IOException if the stream throws an exception
     * @throws ClassNotFoundException if the stream represents an unknown class
     */
    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // bind to new lock
        //TODO [VISTALL] resetLock();

        // Read in array length and allocate array
        int len = s.readInt();
        int[] elements = new int[len];

        // Read in all elements in the proper order.
        for(int i = 0; i < len; i++)
        {
            elements[i] = s.readInt();
        }
        setArray(elements);
    }

    /**
     * Returns a string representation of this list.  The string
     * representation consists of the string representations of the list's
     * elements in the order they are returned by its iterator, enclosed in
     * square brackets ("[]").  Adjacent elements are separated by
     * the characters ", " (comma and space).  Elements are
     * converted to strings as by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this list
     */
    public String toString() {
        return Arrays.toString(getArray());
    }

    /**
     * Compares the specified object with this list for equality.
     * Returns {@code true} if the specified object is the same object
     * as this object, or if it is also a {@link java.util.List} and the sequence
     * of elements returned by an {@linkplain java.util.List#iterator() iterator}
     * over the specified list is the same as the sequence returned by
     * an iterator over this list.  The two sequences are considered to
     * be the same if they have the same length and corresponding
     * elements at the same position in the sequence are <em>equal</em>.
     * Two elements {@code e1} and {@code e2} are considered
     * <em>equal</em> if {@code (e1==null ? e2==null : e1.equals(e2))}.
     *
     * @param o the object to be compared for equality with this list
     * @return {@code true} if the specified object is equal to this list
     */
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof IntList)) {
            return false;
        }

        IntList list = (IntList) (o);
        IntIterator it = list.iterator();
        int[] elements = getArray();
        for (int element : elements) {
            if (!it.hasNext() || !eq(element, it.next())) {
                return false;
            }
        }
        return !it.hasNext();
    }

    /**
     * <p>
     * Returns the hash code value for this list.
     * </p>
     * <p>This implementation uses the definition in {@link java.util.List#hashCode}.
     *
     * @return the hash code value for this list
     */
    public int hashCode() {
        int hashCode = 1;
        int[] elements = getArray();
        int len = elements.length;
        for (Object obj : elements) {
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
    }

    /**
     * <p>
     * Returns an iterator over the elements in this list in proper sequence.
     * </p>
     * <p>The returned iterator provides a snapshot of the state of the list
     * when the iterator was constructed. No synchronization is needed while
     * traversing the iterator. The iterator does <em>NOT</em> support the
     * remove method.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public IntIterator iterator() {
        return new COWIterator(getArray(), 0);
    }

    /**
     * {@inheritDoc}
     * <p>The returned iterator provides a snapshot of the state of the list
     * when the iterator was constructed. No synchronization is needed while
     * traversing the iterator. The iterator does <em>NOT</em> support the
     * remove, set or add methods.
     */
    public IntListIterator listIterator() {
        return new COWIterator(getArray(), 0);
    }

    /**
     * {@inheritDoc}
     * <p>The returned iterator provides a snapshot of the state of the list
     * when the iterator was constructed. No synchronization is needed while
     * traversing the iterator. The iterator does <em>NOT</em> support the
     * remove, set or add methods.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public IntListIterator listIterator(final int index) {
        int[] elements = getArray();
        int len = elements.length;
        if(index < 0 || index > len)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        return new COWIterator(elements, index);
    }

    private static class COWIterator implements IntListIterator {
        /**
         * Snapshot of the array *
         */
        private final int[] snapshot;
        /**
         * Index of element to be returned by subsequent call to next.
         */
        private int cursor;

        private COWIterator(int[] elements, int initialCursor) {
            cursor = initialCursor;
            snapshot = elements;
        }

        public boolean hasNext() {
            return cursor < snapshot.length;
        }

        public boolean hasPrevious()
        {
            return cursor > 0;
        }

        public int nextInt() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            return snapshot[cursor++];
        }

        public int previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return snapshot[--cursor];
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * Not supported. Always throws UnsupportedOperationException.
         *
         * @throws UnsupportedOperationException always; remove
         *                                       is not supported by this iterator.
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Not supported. Always throws UnsupportedOperationException.
         *
         * @throws UnsupportedOperationException always; set
         *                                       is not supported by this iterator.
         */
        public void set(int e) {
            throw new UnsupportedOperationException();
        }

        /**
         * Not supported. Always throws UnsupportedOperationException.
         *
         * @throws UnsupportedOperationException always; add
         *                                       is not supported by this iterator.
         */
        public void add(int e) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * <p>
     * Returns a view of the portion of this list between
     * fromIndex, inclusive, and toIndex, exclusive.
     * The returned list is backed by this list, so changes in the
     * returned list are reflected in this list, and vice-versa.
     * While mutative operations are supported, they are probably not
     * very useful for CopyOnWriteArrayLists.
     * </p>
     * <p>The semantics of the list returned by this method become
     * undefined if the backing list (i.e., this list) is
     * <i>structurally modified</i> in any way other than via the
     * returned list.  (Structural modifications are those that change
     * the size of the list, or otherwise perturb it in such a fashion
     * that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public IntList subList(int fromIndex, int toIndex) {
        synchronized (lock) {
            int[] elements = getArray();
            int len = elements.length;
            if(fromIndex < 0 || toIndex > len || fromIndex > toIndex) {
                throw new IndexOutOfBoundsException();
            }
            return new COWSubList(this, fromIndex, toIndex);
        }
    }

    /**
     * Sublist for CopyOnWriteArrayList.
     * This class extends AbstractList merely for convenience, to
     * avoid having to define addAll, etc. This doesn't hurt, but
     * is wasteful.  This class does not need or use modCount
     * mechanics in AbstractList, but does need to check for
     * concurrent modification using similar mechanics.  On each
     * operation, the array that we expect the backing list to use
     * is checked and updated.  Since we do this for all of the
     * base operations invoked by those defined in AbstractList,
     * all is well.  While inefficient, this is not worth
     * improving.  The kinds of list operations inherited from
     * AbstractList are already so slow on COW sublists that
     * adding a bit more space/time doesn't seem even noticeable.
     */
    private static class COWSubList extends AbstractIntList {
        private final CArrayIntList l;
        private final int offset;
        private int size;
        private int[] expectedArray;

        // only call this holding l's lock
        private COWSubList(CArrayIntList list, int fromIndex, int toIndex) {
            l = list;
            expectedArray = l.getArray();
            offset = fromIndex;
            size = toIndex - fromIndex;
        }

        // only call this holding l's lock
        private void checkForComodification() {
            if(l.getArray() != expectedArray) {
                throw new ConcurrentModificationException();
            }
        }

        // only call this holding l's lock
        private void rangeCheck(int index) {
            if(index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ",Size: " + size);
            }
        }

        public int set(int index, int element) {
            synchronized (l.lock) {
                rangeCheck(index);
                checkForComodification();
                int x = l.set(index + offset, element);
                expectedArray = l.getArray();
                return x;
            }
        }

        public int get(int index) {
            synchronized (l.lock) {
                rangeCheck(index);
                checkForComodification();
                return l.get(index + offset);
            }
        }

        public int size() {
            synchronized (l.lock) {
                checkForComodification();
                return size;
            }
        }

        public void add(int index, int element) {
            synchronized (l.lock) {
                checkForComodification();
                if(index < 0 || index > size) {
                    throw new IndexOutOfBoundsException();
                }
                l.add(index + offset, element);
                expectedArray = l.getArray();
                size++;
            }
        }

        public void clear() {
            synchronized (l.lock) {
                checkForComodification();
                l.removeRange(offset, offset + size);
                expectedArray = l.getArray();
                size = 0;
            }
        }

        public int removeByIndex(int index) {
            synchronized (l.lock) {
                rangeCheck(index);
                checkForComodification();
                int result = l.removeByIndex(index + offset);
                expectedArray = l.getArray();
                size--;
                return result;
            }
        }

        public IntIterator iterator() {
            synchronized (l.lock) {
                checkForComodification();
                return new COWSubListIterator(l, 0, offset, size);
            }
        }

        public IntListIterator listIterator(final int index) {
            synchronized (l.lock) {
                checkForComodification();
                if(index < 0 || index > size) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
                }
                return new COWSubListIterator(l, index, offset, size);
            }
        }

        public IntList subList(int fromIndex, int toIndex) {
            synchronized (l.lock) {
                checkForComodification();
                if(fromIndex < 0 || toIndex > size) {
                    throw new IndexOutOfBoundsException();
                }
                return new COWSubList(l, fromIndex + offset, toIndex + offset);
            }
        }

    }


    private static class COWSubListIterator implements IntListIterator {
        private final IntListIterator i;
        private final int index;
        private final int offset;
        private final int size;

        private COWSubListIterator(IntList l, int index, int offset, int size) {
            this.index = index;
            this.offset = offset;
            this.size = size;
            i = l.listIterator(index + offset);
        }

        public boolean hasNext()
        {
            return nextIndex() < size;
        }

        public int nextInt() {
            if(hasNext()) {
                return i.nextInt();
            } else {
                throw new NoSuchElementException();
            }
        }

        public boolean hasPrevious()
        {
            return previousIndex() >= 0;
        }

        public int previous() {
            if(hasPrevious()) {
                return i.previous();
            } else {
                throw new NoSuchElementException();
            }
        }

        public int nextIndex()
        {
            return i.nextIndex() - offset;
        }

        public int previousIndex()
        {
            return i.previousIndex() - offset;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public void set(int e)
        {
            throw new UnsupportedOperationException();
        }

        public void add(int e)
        {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Returns a {@link Spliterator} over the elements in this list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#IMMUTABLE},
     * {@link Spliterator#ORDERED}, {@link Spliterator#SIZED}, and
     * {@link Spliterator#SUBSIZED}.
     *
     * <p>The spliterator provides a snapshot of the state of the list
     * when the spliterator was constructed. No synchronization is needed while
     * operating on the spliterator.
     *
     * @return a {@code Spliterator} over the elements in this list
     */
    @Override
    public Spliterator.OfInt spliterator() {
        return Spliterators.spliterator(getArray(), Spliterator.IMMUTABLE | Spliterator.ORDERED);
    }
}