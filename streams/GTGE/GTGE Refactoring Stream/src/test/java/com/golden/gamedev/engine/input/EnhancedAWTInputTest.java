/**
 * 
 */
package com.golden.gamedev.engine.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.BitSet;

import com.golden.gamedev.engine.BaseInput;
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
	 * The {@link Component} instance being referenced by the {@link #input}.
	 */
	private Component component;
	
	/**
	 * The {@link EnhancedAWTInput} instance under test.
	 */
	private EnhancedAWTInput input;
	
	/**
	 * The {@link EnhancedInputListener} instance stored via the {@link #input}.
	 */
	private EnhancedInputListener listener;
	
	/**
	 * Creates a new {@link EnhancedAWTInputTest} instance with the given name.
	 * @param name The {@link String} name of this {@link EnhancedAWTInputTest}
	 *        instance.
	 */
	public EnhancedAWTInputTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		component = new MockComponent();
		input = new EnhancedAWTInput(component);
		listener = (EnhancedInputListener) component.getKeyListeners()[0];
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#getKeyDown()}.
	 */
	public void testGetKeyDown() {
		try {
			input.getKeyDown();
			fail("Expected IllegalArgumentException - the EnhancedAWTInput class does not support this method.");
		}
		catch (UnsupportedOperationException e) {
			// Intentionally blank.
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#isKeyDown(int)}.
	 */
	public void testIsKeyDown() {
		assertFalse(input.isKeyDown(KeyEvent.VK_A));
		KeyEvent event = new KeyEvent(component, 0, System.currentTimeMillis(),
		        0, KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		assertTrue(input.isKeyDown(KeyEvent.VK_A));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#reset()}.
	 */
	public void testReset() {
		// Key typed reset check
		KeyEvent event = new KeyEvent(component, 0, System.currentTimeMillis(),
		        0, KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		input.update(10);
		assertTrue(input.isKeyTyped(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyTyped());
		input.reset();
		assertFalse(input.isKeyTyped(KeyEvent.VK_A));
		assertFalse(input.getKeyTyped() == KeyEvent.VK_A);
		
		// Mouse event reset checks
		MouseEvent mouseEvent = new MouseEvent(component, 0, System
		        .currentTimeMillis(), 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		listener.mousePressed(mouseEvent);
		mouseEvent = new MouseEvent(component, 0, System.currentTimeMillis(),
		        0, 0, 0, 0, false, MouseEvent.BUTTON1);
		listener.mouseReleased(mouseEvent);
		assertTrue(input.isMousePressed(MouseEvent.BUTTON1));
		assertEquals(MouseEvent.BUTTON1, input.getMousePressed());
		assertTrue(input.isMouseReleased(MouseEvent.BUTTON1));
		assertEquals(MouseEvent.BUTTON1, input.getMouseReleased());
		input.reset();
		assertFalse(input.isMousePressed(MouseEvent.BUTTON1));
		assertFalse(MouseEvent.BUTTON1 == input.getMousePressed());
		assertFalse(input.isMouseReleased(MouseEvent.BUTTON1));
		assertFalse(MouseEvent.BUTTON1 == input.getMouseReleased());
		
		mouseEvent = new MouseEvent(component, 0, System.currentTimeMillis(),
		        0, 0, 0, 0, false, MouseEvent.BUTTON1);
		listener.mouseMoved(mouseEvent);
		input.update(10);
		assertTrue(input.getMouseDX() != 0);
		assertTrue(input.getMouseDY() != 0);
		input.reset();
		assertEquals(0, input.getMouseDX());
		assertEquals(0, input.getMouseDY());
		
		// Key pressed/released event checks
		event = new KeyEvent(component, 0, System.currentTimeMillis(), 0,
		        KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyPressed(event);
		event = new KeyEvent(component, 0, System.currentTimeMillis(), 0,
		        KeyEvent.VK_A, 'a', KeyEvent.KEY_LOCATION_STANDARD);
		listener.keyReleased(event);
		assertTrue(input.isKeyPressed(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyPressed());
		assertTrue(input.isKeyReleased(KeyEvent.VK_A));
		assertEquals(KeyEvent.VK_A, input.getKeyReleased());
		input.reset();
		assertFalse(input.isKeyPressed(KeyEvent.VK_A));
		assertFalse(input.getKeyPressed() == KeyEvent.VK_A);
		assertFalse(input.isKeyReleased(KeyEvent.VK_A));
		assertFalse(input.getKeyReleased() == KeyEvent.VK_A);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#EnhancedAWTInput(java.awt.Component)}
	 * .
	 */
	public void testEnhancedAWTInput() {
		assertNotNull(input);
		MockComponent button = (MockComponent) input.getComponent();
		assertTrue(button.focusRequested);
		assertTrue(listener == button.getKeyListeners()[0]);
		assertTrue(listener == button.getMouseListeners()[0]);
		assertTrue(listener == button.getMouseMotionListeners()[0]);
		assertTrue(listener == button.getFocusListeners()[0]);
		
		// No key pressed or released or typed
		assertEquals(BaseInput.NO_KEY, input.getKeyPressed());
		assertEquals(BaseInput.NO_KEY, input.getKeyReleased());
		assertEquals(BaseInput.NO_KEY, input.getKeyTyped());
		
		// Mouse exists and is visible.
		assertTrue(input.isMouseExists());
		assertTrue(input.isMouseVisible());
		
		// No mouse key pressed or released
		assertEquals(BaseInput.NO_BUTTON, input.getMousePressed());
		assertEquals(BaseInput.NO_BUTTON, input.getMouseReleased());
		
		// No mouse motion.
		assertEquals(0, input.getMouseDX());
		assertEquals(0, input.getMouseDY());
		
		// Mouse coordinates are wherever, but are greater than or equal to 0.
		assertTrue(input.getMouseX() >= 0);
		assertTrue(input.getMouseY() >= 0);
		
		// Focus traversal keys enabled set to false.
		assertFalse(button.focusTraversalKeysEnabled);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.input.EnhancedAWTInput#getKeyDownBitSet()}
	 * .
	 */
	public void testGetKeyDownBitSet() {
		assertNotNull(input.getKeyDownBitSet());
		assertTrue(input.getKeyDownBitSet().isEmpty());
	}
	
	/**
	 * Test method for the {@link EnhancedInputListener} class.
	 */
	public void testEnhancedAwtInputListener() {
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
