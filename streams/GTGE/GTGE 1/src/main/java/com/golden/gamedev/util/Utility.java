/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.util;

// JFC
import java.lang.reflect.Array;

import org.apache.commons.lang.math.RandomUtils;

/**
 * Utility class provides general functions, such as array enlargement/shrinkment operation, array mixed, randomize, and
 * other functions that categorized as common functions.
 */
public class Utility {
	
	private Utility() {
		throw new UnsupportedOperationException("Cannot instantiate the Utility class!");
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************ ARRAY ENLARGEMENT ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Expands an array of object by specified size, <code>src</code> can not be <code>null</code>.
	 * <p>
	 * 
	 * The original array is not changed, this method creates and returns a new expanded array.
	 * 
	 * @param src
	 *            the array to be expanded (non-primitive)
	 * @param increase
	 *            array size increment
	 * @param bottom
	 *            true, the expanded array is at the bottom
	 * @return The expanded array.
	 */
	public static <T> T[] expand(T[] src, int increase, boolean bottom) {
		int size = Array.getLength(src);
		@SuppressWarnings("unchecked")
		T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), size + increase);
		System.arraycopy(src, 0, dest, (bottom) ? 0 : increase, size);
		
		return dest;
	}
	
	/**
	 * Cuts an array of object from specified position.
	 * <p>
	 * 
	 * The original array is not changed, this method creates and returns a new shrinked array.
	 * 
	 * @param src
	 *            the array to be cut (non-primitive)
	 * @param position
	 *            index position to be cut
	 * @return The shrinked array.
	 */
	public static <T> T[] cut(T[] src, int position) {
		int size = Array.getLength(src);
		if (size == 1) {
			// the array size is 1
			// return a empty array new Class[0];
			@SuppressWarnings("unchecked")
			T[] result = (T[]) Array.newInstance(src.getClass().getComponentType(), 0);
			return result;
		}
		
		int numMoved = size - position - 1;
		if (numMoved > 0) {
			System.arraycopy(src, position + 1, src, position, numMoved);
		}
		
		size--;
		@SuppressWarnings("unchecked")
		T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), size);
		System.arraycopy(src, 0, dest, 0, size);
		
		return dest;
	}
	
	/**
	 * Shuffles elements in an array.
	 * 
	 * @param src
	 *            the array to be mixed, could be an array of primitive or an array of Object
	 */
	public static void shuffle(Object src) {
		// size of the array
		int size = Array.getLength(src);
		
		// temporary for swapping value
		Object tempVal;
		// position to be swapped
		int tempPos;
		
		for (int i = 0; i < size; i++) {
			// get position in random
			tempPos = i + RandomUtils.nextInt(size - 1 - i + 1);
			
			// store the value
			tempVal = Array.get(src, tempPos);
			
			// swap the value in random position with current position
			Array.set(src, tempPos, Array.get(src, i));
			Array.set(src, i, tempVal);
		}
	}
}
