/**
 * 
 */
package com.golden.gamedev.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;

import junit.framework.TestCase;

/**
 * The {@link EqualsComparatorTest} class provides a {@link TestCase} to test
 * the behavior of the {@link EqualsComparator} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see EqualsComparator
 * 
 */
public class EqualsComparatorTest extends TestCase {
	
	/**
	 * The {@link EqualsComparator} instance under test.
	 */
	private Comparator comparator;
	
	/**
	 * Creates a new {@link EqualsComparatorTest} instance with the given name.
	 * @param name The {@link String} name of this {@link EqualsComparator}
	 *        instance.
	 */
	public EqualsComparatorTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		comparator = EqualsComparator.INSTANCE;
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.EqualsComparator#compare(java.lang.Object, java.lang.Object)}
	 * .
	 */
	public final void testCompare() {
		assertEquals(0, comparator.compare(null, null));
		assertEquals(0, comparator.compare(new Object(), null));
		assertEquals(0, comparator.compare(null, new Object()));
		assertEquals(0, comparator.compare(new Object(), new Object()));
	}
	
	/**
	 * Tests that serialization and deserialization only serialize and
	 * deserialize the singleton instance.
	 * 
	 * @throws Exception
	 */
	public final void testSerializationAndDeserialization() throws Exception {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(
		        byteOutputStream);
		outputStream.writeObject(comparator);
		byte[] byteStream = byteOutputStream.toByteArray();
		// I changed the bytes for no real reason - take that, society!
		byteStream[byteStream.length - 3] = 0;
		assertEquals(EqualsComparator.INSTANCE, new ObjectInputStream(
		        new ByteArrayInputStream(byteStream)).readObject());
	}
	
}
