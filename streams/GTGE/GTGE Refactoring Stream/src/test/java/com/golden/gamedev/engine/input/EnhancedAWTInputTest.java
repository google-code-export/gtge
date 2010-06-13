/**
 * 
 */
package com.golden.gamedev.engine.input;

import java.awt.Button;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import com.golden.gamedev.engine.input.EnhancedAWTInput.EnhancedInputListener;

import junit.framework.TestCase;

/**
 * The {@link EnhancedAWTInputTest} class provides a {@link TestCase} to test
 * the functionality of the {@link EnhancedAWTInput} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see EnhancedAWTInput
 * 
 */
public class EnhancedAWTInputTest extends TestCase {
	
	/**
	 * Creates a new {@link EnhancedAWTInputTest} instance with the given name.
	 * @param name The {@link String} name of this {@link EnhancedAWTInputTest}
	 *        instance.
	 */
	public EnhancedAWTInputTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#getKeyDown()}.
	 */
	public void testGetKeyDown() {
		// TODO: implement
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#isKeyDown(int)}.
	 */
	public void testIsKeyDown() {
		// TODO: implement
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#reset()}.
	 */
	public void testReset() {
		// TODO: implement
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#EnhancedAWTInput(java.awt.Component)}
	 * .
	 */
	public void testEnhancedAWTInput() {
		// TODO: implement
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#getKeyDownBitSet()}
	 * .
	 */
	public void testGetKeyDownBitSet() {
		// TODO: implement
	}
	
	/**
	 * Test method for the {@link EnhancedInputListener} class.
	 */
	public void testEnhancedAwtInputListener() {
		Component component = new Button();
		EnhancedAWTInput input = new EnhancedAWTInput(component);
		EnhancedInputListener listener = (EnhancedInputListener) component
		        .getKeyListeners()[0];
		BitSet keyBitSet = input.getKeyDownBitSet();
		assertTrue(keyBitSet.isEmpty());
		KeyEvent event = new KeyEvent(component, 0, System.currentTimeMillis(),
		        0, KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		assertTrue(keyBitSet.get(KeyEvent.VK_A));
		assertTrue(input.isKeyPressed(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyPressed());
		
		// The same event keeps the key pressed.
		event = new KeyEvent(component, 0, System.currentTimeMillis(), 0,
		        KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		assertTrue(keyBitSet.get(KeyEvent.VK_A));
		assertTrue(input.isKeyPressed(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyPressed());
		
		// Now the key is released.
		event = new KeyEvent(component, 0, System.currentTimeMillis(), 0,
		        KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyReleased(event);
		// The key is still pressed until an update/reset occurs.
		assertTrue(input.isKeyPressed(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyPressed());
		
		// But it also registers as being released.
		assertFalse(keyBitSet.get(KeyEvent.VK_A));
		assertTrue(input.isKeyReleased(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyReleased());
		
		// With another event, it is pressed again, but is also kept as released
		// until a refresh.
		event = new KeyEvent(component, 0, System.currentTimeMillis(), 0,
		        KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		assertTrue(keyBitSet.get(KeyEvent.VK_A));
		assertTrue(input.isKeyPressed(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyPressed());
		assertTrue(input.isKeyReleased(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyReleased());
	}
}
