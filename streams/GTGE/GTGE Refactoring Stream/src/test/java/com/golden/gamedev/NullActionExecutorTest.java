/**
 * 
 */
package com.golden.gamedev;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

/**
 * The {@link NullActionExecutorTest} class provides a {@link TestCase}
 * extension to test the functionality of the {@link NullActionExecutor} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see NullActionExecutor
 * 
 */
public class NullActionExecutorTest extends TestCase {
	
	/**
	 * The {@link ActionExecutor} instance under test.
	 */
	private ActionExecutor executor;
	
	/**
	 * Creates a new {@link NullActionExecutorTest} instance with the given
	 * name.
	 * @param name The {@link String} name of this
	 *        {@link NullActionExecutorTest} instance.
	 */
	public NullActionExecutorTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		executor = NullActionExecutor.INSTANCE;
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.NullActionExecutor#execute()}.
	 */
	public final void testExecute() {
		// Nothing to test but the call - it does nothing.
		executor.execute();
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
		outputStream.writeObject(executor);
		byte[] byteStream = byteOutputStream.toByteArray();
		// I changed the bytes for no real reason - take that, society!
		byteStream[byteStream.length - 3] = 0;
		assertEquals(NullActionExecutor.INSTANCE, new ObjectInputStream(
		        new ByteArrayInputStream(byteStream)).readObject());
	}
	
}
