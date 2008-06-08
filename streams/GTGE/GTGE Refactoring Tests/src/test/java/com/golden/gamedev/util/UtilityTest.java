/**
 * 
 */
package com.golden.gamedev.util;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * A simple {@link TestCase} for the {@link Utility} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Utility
 * @see TestCase
 */
public class UtilityTest extends TestCase {
	
	/**
	 * Creates a new <tt>UtilityTest</tt> instance with the given name.
	 * @param name The name of this <tt>UtilityTest</tt> instance.
	 */
	public UtilityTest(String name) {
		super(name);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#expand(java.lang.Object, int, boolean)}.
	 */
	public void testExpandObjectIntBoolean() {
		// TODO: actually implement this test.
		Object[] source = new Object[] {
		        "string", "another"
		};
		Utility.expand(source, 5, true);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#expand(java.lang.Object, int)}.
	 */
	public void testExpandObjectInt() {
		// TODO: actually implement this test.
		Object[] source = new Object[] {
		        "string", "another"
		};
		Utility.expand(source, 5);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#expand(java.lang.Object, int, boolean, java.lang.Class)}.
	 */
	public void testExpandObjectIntBooleanClass() {
		String[] array = new String[] {
		        "one", "two", "three"
		};
		String[] firstNewArray = (String[]) Utility.expand(array, 3, true);
		String[] secondNewArray = (String[]) Utility.expand(array, 3, true,
		        String.class);
		assertTrue(Arrays.equals(firstNewArray, secondNewArray));
		firstNewArray = (String[]) Utility.expand(array, 3, false);
		secondNewArray = (String[]) Utility.expand(array, 3, false,
		        String.class);
		assertTrue(Arrays.equals(firstNewArray, secondNewArray));
		
		// This portion of the test is the only reason the class argument is
		// specified:
		assertNotNull(Utility.expand(null, 5, true, String.class));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#cut(java.lang.Object, int)}.
	 */
	public void testAndProveCutHasASideEffect() {
		Object[] source = new Object[] {
		        "string", "another", "three"
		};
		Object[] holder = new Object[source.length];
		System.arraycopy(source, 0, holder, 0, source.length);
		Object[] result = (Object[]) Utility.cut(source, 1);
		
		//Cut has a side-effect which directly contradicts its documentation.
		assertFalse(Arrays.equals(source, holder));
		
		assertTrue(Arrays.equals(new String[] {"string", "three"}, result));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#cut(java.lang.Object, int)}.
	 */
	public void testAndProveSafeCutDoesNotHaveASideEffect() {
		Object[] source = new Object[] {
		        "string", "another", "three"
		};
		Object[] holder = new Object[source.length];
		System.arraycopy(source, 0, holder, 0, source.length);
		Object[] result = (Object[]) Utility.safeCut(source, 1);
		
		//Safe Cut does not have a side-effect which directly contradicts its documentation.
		assertTrue(Arrays.equals(source, holder));
		
		assertTrue(Arrays.equals(new String[] {"string", "three"}, result));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#shuffle(java.lang.Object)}.
	 */
	public void testShuffle() {
		// TODO: actually implement this test.
		Object[] source = new Object[] {
		        "string", "another"
		};
		Utility.expand(source, 5, true);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.Utility#getRandomObject()}.
	 */
	public void testGetRandomObject() {
		// TODO: actually implement this test.
		Object[] source = new Object[] {
		        "string", "another"
		};
		Utility.expand(source, 5, true);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#getRandom(int, int)}.
	 */
	public void testGetRandom() {
		// TODO: actually implement this test.
		Object[] source = new Object[] {
		        "string", "another"
		};
		Utility.expand(source, 5, true);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.Utility#compactStrings(java.lang.String[])}.
	 */
	public void testAndProveCompactStringsIsCompetelyUseless() {
		String[] sourceArray = new String[] {
		        "one", "two", "three"
		};
		String[] otherArray = Utility.compactStrings(sourceArray);
		assertFalse(sourceArray == otherArray);
		assertTrue(Arrays.equals(sourceArray, otherArray));
		
		String[] sourceArray2 = new String[] {
		        "three", "two", "one"
		};
		otherArray = Utility.compactStrings(sourceArray2);
		String[] arrayCopied = new String[sourceArray2.length];
		System.arraycopy(sourceArray2, 0, arrayCopied, 0, sourceArray2.length);
		
		assertFalse(sourceArray2 == otherArray);
		assertFalse(sourceArray2 == arrayCopied);
		assertFalse(otherArray == arrayCopied);
		
		assertTrue(Arrays.equals(sourceArray2, otherArray));
		assertTrue(Arrays.equals(sourceArray2, arrayCopied));
		assertTrue(Arrays.equals(arrayCopied, otherArray));
	}
}
