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
package io.github.andyalvarezdev.primitive.iterators;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

/**
 * <p>
 * An iterator over a collection.  {@code Iterator} takes the place of
 * {@link java.util.Enumeration} in the Java Collections Framework.  Iterators
 * differ from enumerations in two ways:
 * </p>
 * <ul>
 * <li> Iterators allow the caller to remove elements from the
 * underlying collection during the iteration with well-defined
 * semantics.
 * <li> Method names have been improved.
 * </ul>
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @see java.util.Collection
 * @see java.util.ListIterator
 * @see Iterable
 */
public interface DoubleIterator
{
	/**
	 * Returns {@code true} if the iteration has more elements.
	 * (In other words, returns {@code true} if {@link #next} would
	 * return an element rather than throwing an exception.)
	 *
	 * @return {@code true} if the iteration has more elements
	 */
	boolean hasNext();

	/**
	 * Returns the next element in the iteration.
	 *
	 * @return the next element in the iteration
	 * @throws java.util.NoSuchElementException
	 *          if the iteration has no more elements
	 */
	double next();

	/**
	 * Removes from the underlying collection the last element returned
	 * by this iterator (optional operation).  This method can be called
	 * only once per call to {@link #next}.  The behavior of an iterator
	 * is unspecified if the underlying collection is modified while the
	 * iteration is in progress in any way other than by calling this
	 * method.
	 *
	 * @throws UnsupportedOperationException if the {@code remove}
	 *                                       operation is not supported by this iterator
	 * @throws IllegalStateException		 if the {@code next} method has not
	 *                                       yet been called, or the {@code remove} method has already
	 *                                       been called after the last call to the {@code next}
	 *                                       method
	 */
	void remove();

	/**
	 * Performs the given action for each element of the {@code Iterable}
	 * until all elements have been processed or the action throws an
	 * exception.  Actions are performed in the order of iteration, if that
	 * order is specified.  Exceptions thrown by the action are relayed to the
	 * caller.
	 * <p>
	 * The behavior of this method is unspecified if the action performs
	 * side-effects that modify the underlying source of elements, unless an
	 * overriding class has specified a concurrent modification policy.
	 *
	 * <p>The default implementation behaves as if:
	 * <pre>{@code
	 *     for (T t : this)
	 *         action.accept(t);
	 * }</pre>
	 *
	 * @param action The action to be performed for each element
	 * @throws NullPointerException if the specified action is null
	 */
	default void forEach(DoubleConsumer action) {
		Objects.requireNonNull(action);
		while (hasNext()) {
			action.accept(next());
		}
	}
}
