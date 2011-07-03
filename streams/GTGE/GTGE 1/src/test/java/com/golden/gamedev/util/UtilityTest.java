package com.golden.gamedev.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

/**
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public class UtilityTest {
	
	@Test(expected = UnsupportedOperationException.class)
	public final void testUtility() throws Throwable {
		Constructor<?> constructor[] = Utility.class.getDeclaredConstructors();
		constructor[0].setAccessible(true);
		try {
			constructor[0].newInstance((Object[]) null);
			fail("Expected an exception to be thrown!");
		} catch (Exception e) {
			throw e.getCause();
		}
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#expand(T[], int, boolean)}.
	 */
	@Test
	public final void testExpandBottom() {
		Object[] threeArray = new Object[] { new Object(), new Object(), new Object() };
		Object[] fiveArray = Utility.expand(threeArray, 2, true);
		assertFalse(threeArray == fiveArray);
		assertEquals(5, fiveArray.length);
		assertEquals(threeArray[0], fiveArray[0]);
		assertEquals(threeArray[1], fiveArray[1]);
		assertEquals(threeArray[2], fiveArray[2]);
		assertNull(fiveArray[3]);
		assertNull(fiveArray[4]);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#expand(T[], int, boolean)}.
	 */
	@Test
	public final void testExpandTop() {
		Object[] threeArray = new Object[] { new Object(), new Object(), new Object() };
		Object[] fiveArray = Utility.expand(threeArray, 2, false);
		assertFalse(threeArray == fiveArray);
		assertEquals(5, fiveArray.length);
		assertEquals(threeArray[0], fiveArray[2]);
		assertEquals(threeArray[1], fiveArray[3]);
		assertEquals(threeArray[2], fiveArray[4]);
		assertNull(fiveArray[0]);
		assertNull(fiveArray[1]);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#expand(T[], int, boolean)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testExpandNull() {
		Utility.expand(null, 3, true);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#expand(T[], int, boolean)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testExpandZero() {
		Utility.expand(new Object[3], 0, true);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#expand(T[], int, boolean)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testExpandNegative() {
		Utility.expand(new Object[3], -4, true);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#shuffle(java.lang.Object)}.
	 */
	@Test
	public final void testShuffle() {
		Object[] toShuffle = new Object[52];
		for (int index = 0; index < 52; index++) {
			toShuffle[index] = new Object();
		}
		Object[] original = ArrayUtils.clone(toShuffle);
		Utility.shuffle(toShuffle);
		assertFalse(Arrays.equals(original, toShuffle));
	}
	
}
