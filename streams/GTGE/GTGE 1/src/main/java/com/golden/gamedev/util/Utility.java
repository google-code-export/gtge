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
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Utility class provides general functions, such as array enlargement/shrinkment operation, array mixed, randomize, and
 * other functions that categorized as common functions.
 */
public final class Utility {
	
	/**
	 * This constructor would creates a new {@link Utility} instance, but as it's a utility class, it throws a new
	 * {@link UnsupportedOperationException} to prevent this.
	 * 
	 * @throws UnsupportedOperationException
	 *             Throws an {@link UnsupportedOperationException} to prevent instantiation of the {@link Utility}
	 *             class.
	 */
	private Utility() {
		throw new UnsupportedOperationException("The Utility class may not be instantiated!");
	}
	
	// TODO: When arrays are thrown out of GTGE in preference to Lists, delete this method.
	/**
	 * Generates and returns an array of objects with an expanded size, with the elements of the given array copied to
	 * it. The original array is not changed as a result of this operation.
	 * 
	 * @param <T>
	 *            The component type of the array to be expanded.
	 * @param src
	 *            The non-null, non-primitive array to be expanded.
	 * @param increase
	 *            The amount of size to expand this array by, which must be greater than zero.
	 * @param bottom
	 *            If this is set to true, the expanded array space is at the bottom (higher indices) of the array,
	 *            otherwise, the expanded space is at the top (lower indices) of the array.
	 * @return The expanded array.
	 * @throws NullPointerException
	 *             Throws a {@link NullPointerException} if the given array is null.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given increase is less than or equal to zero.
	 */
	public static <T> T[] expand(T[] src, int increase, boolean bottom) {
		Validate.isTrue(increase >= 1, "The increase must be greater than zero!");
		int size = src.length;
		@SuppressWarnings("unchecked")
		T[] dest = (T[]) Array.newInstance(src.getClass().getComponentType(), size + increase);
		System.arraycopy(src, 0, dest, (bottom) ? 0 : increase, size);
		return dest;
	}
	
	/**
	 * Shuffles elements in an array.
	 * 
	 * @param src
	 *            The array to be shuffled, which may be either a primitive or non-primitive array.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given array is null or is not an array.
	 */
	public static void shuffle(Object src) {
		int size = Array.getLength(src);
		
		for (int valueIndex = 0; valueIndex < size - 1; valueIndex++) {
			int swapIndex = valueIndex + RandomUtils.nextInt(size - valueIndex);
			Object swappedValue = Array.get(src, swapIndex);
			Array.set(src, swapIndex, Array.get(src, valueIndex));
			Array.set(src, valueIndex, swappedValue);
		}
	}
	
	// REVIEW-MEDIUM: better documentation needed, enforce non-nullality for the URL.
	// REVIEW-HIGH: Alter the signature of this method to simply "createFont" and allow the type to be passed in as a
	// nullable integer. Specify that if it is null, it will default to a TrueType font.
	// REVIEW-HIGH: Instead of catching the exceptions, throw them - this is a deficiency in Java that GTGE won't cure,
	// and this method
	// shouldn't simply use verdana fonts as defaults as it is completely unspecified. Can be rethrown as
	// RuntimeExceptions, though.
	/**
	 * Creates java.awt.Font from specified True Type Font URL (*.ttf).
	 */
	public static Font createTrueTypeFont(URL url, int style, float size) {
		Font f = null;
		
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (IOException e) {
			System.err.println("ERROR: " + url + " is not found or can not be read");
			f = new Font("Verdana", 0, 0);
		} catch (FontFormatException e) {
			System.err.println("ERROR: " + url + " is not a valid true type font");
			f = new Font("Verdana", 0, 0);
		}
		
		return f.deriveFont(style, size);
	}
}
