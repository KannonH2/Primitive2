/*
 * Primitive Collection Framework for Java
 * Copyright (C) 2011 napile.org
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

package io.github.andyalvarezdev.primitive.pair.abstracts;

import io.github.andyalvarezdev.primitive.HashUtils;
import io.github.andyalvarezdev.primitive.pair.IntObjectPair;
import io.github.andyalvarezdev.primitive.pair.IntLongPair;

public abstract class AbstractIntObjectPair<G> implements IntObjectPair<G>
{
	protected int _key;
	protected G _value;

	public AbstractIntObjectPair(int key, G value)
	{
		_key = key;
		_value = value;
	}

	@Override
	public int getKey()
	{
		return _key;
	}

	@Override
	public G getValue()
	{
		return _value;
	}

	@Override
	public String toString()
	{
		return _key + "=" + _value;
	}

	@Override
	public int hashCode()
	{
		return HashUtils.hashCode(_key) ^ HashUtils.hashCode(_value);
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof IntLongPair))
			return false;
		else
		{
			IntObjectPair p = (IntObjectPair) o;
			return p.getKey() == _key && p.getValue() == _value;
		}
	}
}
