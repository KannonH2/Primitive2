/**
 * Primitive Collection Framework for Java
 * Copyright (C) 2010 Napile.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package io.github.andyalvarezdev.primitive;

import io.github.andyalvarezdev.primitive.sets.impl.TreeIntSet;

public class TestSet
{
	public static void main(String... arg)
	{
		TreeIntSet set = new TreeIntSet();
		set.add(1);
		set.add(1);
		set.add(2);
		set.add(2);
		for(int i : set.toArray())
		{
			System.out.println(i);
		}
	}
}
