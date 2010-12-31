package com.golden.gamedev.funbox;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import junit.framework.TestCase;

import com.golden.gamedev.engine.MockBaseInput;

/**
 * The {@link KeyCaptureTest} class provides a {@link TestCase} to test the
 * behavior of the {@link KeyCapture} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see KeyCapture
 * @see TestCase
 * 
 */
public class KeyCaptureTest extends TestCase {
	
	/**
	 * The {@link MockKeyCapture} instance under test.
	 */
	private MockKeyCapture keyCapture;
	
	/**
	 * The {@link MockBaseInput} instance to use to interact with the
	 * {@link #keyCapture} instance under test.
	 */
	private MockBaseInput baseInput;
	
	protected void setUp() throws Exception {
		baseInput = new MockBaseInput();
		keyCapture = new MockKeyCapture(baseInput, new int[] {
			5
		}, 100);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#KeyCapture(com.golden.gamedev.engine.BaseInput, int[], int)}
	 * .
	 */
	public final void testKeyCaptureBaseInputIntArrayInt() {
		assertNotNull(keyCapture);
		assertEquals(100, keyCapture.getDelayTime());
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getKeySequence()));
		assertEquals(KeyEvent.getKeyText(5), keyCapture.getKeyString());
		assertTrue(keyCapture.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#KeyCapture(com.golden.gamedev.engine.BaseInput, java.lang.String, int)}
	 * .
	 */
	public final void testKeyCaptureBaseInputStringInt() {
		keyCapture = new MockKeyCapture(baseInput, "5", 100);
		assertNotNull(keyCapture);
		assertEquals(100, keyCapture.getDelayTime());
		assertTrue(Arrays.equals(new int[] {
			KeyEvent.VK_NUMPAD5
		}, keyCapture.getKeySequence()));
		assertEquals(KeyEvent.getKeyText(KeyEvent.VK_NUMPAD5),
		        keyCapture.getKeyString());
		assertTrue(keyCapture.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#getKeySequence()}.
	 */
	public final void testGetKeySequence() {
		keyCapture.setKeySequence(new int[] {
			5
		});
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getKeySequence()));
		assertEquals(KeyEvent.getKeyText(5), keyCapture.getKeyString());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#getKeyString()}.
	 */
	public final void testGetKeyString() {
		keyCapture.setKeySequence(new int[] {
			5
		});
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getKeySequence()));
		assertEquals(KeyEvent.getKeyText(5), keyCapture.getKeyString());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setKeySequence(int[])}.
	 */
	public final void testSetKeySequenceIntArray() {
		keyCapture.setKeySequence(new int[] {
			5
		});
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getKeySequence()));
		assertEquals(KeyEvent.getKeyText(5), keyCapture.getKeyString());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setKeySequence(java.lang.String)}
	 * .
	 */
	public final void testSetKeySequenceString() {
		// Succeeds.
		final String success = "AbCdEfGhIjKlMnOpQrStUvWxYz 0123456789";
		keyCapture.setKeySequence(success);
		assertTrue(Arrays.equals(new int[] {
		        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D,
		        KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H,
		        KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
		        KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P,
		        KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T,
		        KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
		        KeyEvent.VK_Y, KeyEvent.VK_Z, KeyEvent.VK_SPACE,
		        KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2,
		        KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5,
		        KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8,
		        KeyEvent.VK_NUMPAD9
		}, keyCapture.getKeySequence()));
		
		assertEquals(
		        "AbCdEfGhIjKlMnOpQrStUvWxYz".toUpperCase()
		                + KeyEvent.getKeyText(KeyEvent.VK_SPACE)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD0)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD1)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD2)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD3)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD4)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD5)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD6)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD7)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD8)
		                + KeyEvent.getKeyText(KeyEvent.VK_NUMPAD9),
		        keyCapture.getKeyString());
		
		// Fails.
		try {
			keyCapture.setKeySequence((String) null);
			fail("Expected RuntimeException - string may not be null.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		try {
			keyCapture.setKeySequence(success + "\"");
			fail("Expected RuntimeException - quotes are not supported.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#getModifiers()}.
	 */
	public final void testGetModifiers() {
		assertNull(keyCapture.getModifiers());
		keyCapture.setModifiers(new int[] {
			5
		});
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getModifiers()));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setModifiers(int[])}.
	 */
	public final void testSetModifiersIntArray() {
		assertNull(keyCapture.getModifiers());
		keyCapture.setModifiers(new int[] {
			5
		});
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getModifiers()));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setModifiers(int)}.
	 */
	public final void testSetModifiersInt() {
		assertNull(keyCapture.getModifiers());
		keyCapture.setModifiers(5);
		assertTrue(Arrays.equals(new int[] {
			5
		}, keyCapture.getModifiers()));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#keyCaptured()}.
	 */
	public final void testKeyCaptured() {
		assertFalse(keyCapture.keyCaptured);
		baseInput.placeKeyPressed(5);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.funbox.KeyCapture#refresh()}.
	 */
	public final void testRefresh() {
		assertFalse(keyCapture.keyCaptured);
		baseInput.placeKeyPressed(5);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
		baseInput.clearPressedKeys();
		keyCapture.keyCaptured = false;
		keyCapture.refresh();
		keyCapture.update(100);
		assertFalse(keyCapture.keyCaptured);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.funbox.KeyCapture#update(long)}
	 * .
	 */
	public final void testUpdate() {
		// Setting active disabled makes update not do anything.
		keyCapture.setActive(false);
		keyCapture.update(1000);
		
		// Now test failure due to too long a delay.
		keyCapture.setActive(true);
		keyCapture.setKeySequence(new int[] {
		        5, 6
		});
		baseInput.placeKeyPressed(5); // 5, but not 6!
		keyCapture.update(99);
		keyCapture.update(101);
		
		// Now enable debug logging.
		keyCapture.DEBUG = true;
		keyCapture.update(99);
		keyCapture.update(101);
		
		// Disable debug logging - allow keys to be captured.
		// keyCapture.refresh();
		assertFalse(keyCapture.keyCaptured);
		keyCapture.DEBUG = false;
		baseInput.placeKeyPressed(6);
		keyCapture.update(99);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
		
		// Enable debug logging.
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		keyCapture.DEBUG = true;
		keyCapture.update(99);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
		
		// Modifiers
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		keyCapture.setModifiers(new int[] {
			4
		});
		baseInput.placeKeyDown(4);
		keyCapture.update(99);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
		
		// Now, key will not be captured (modifier won't be pressed for second
		// key).
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		keyCapture.update(99);
		baseInput.clearDownKeys();
		keyCapture.update(100);
		assertFalse(keyCapture.keyCaptured);
		
		// Key will not be captured because the modifier will not be pressed for
		// the first key.
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		baseInput.clearDownKeys();
		keyCapture.update(99);
		keyCapture.setModifiers(null);
		assertFalse(keyCapture.keyCaptured);
		
		// Test that the delay doesn't not correctly make a key combination
		// pressed.
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		baseInput.clearPressedKeys();
		keyCapture.update(300);
		baseInput.placeKeyPressed(6);
		keyCapture.update(20);
		baseInput.placeKeyPressed(5);
		keyCapture.update(10);
		keyCapture.update(100);
		assertTrue(keyCapture.keyCaptured);
		
		// Test that the first key was pressed, second key was not and then the
		// first key was reset.
		keyCapture.refresh();
		keyCapture.keyCaptured = false;
		keyCapture.update(100);
		baseInput.clearPressedKeys();
		baseInput.placeKeyPressed(3);
		keyCapture.update(100);
		assertFalse(keyCapture.keyCaptured);
		
		// This is the trickiest test because it requires the use of special
		// modifications
		// to the mock class.
		
		// This code is only able to be hit if the sequence is multiple
		// characters
		// character and it wasn't down with the first check, but it becomes
		// down in the second check and it changes to a single character.
		
		keyCapture.refresh();
		keyCapture.mockBaseInput = baseInput;
		keyCapture.keyCodeToPlaceOnRefresh = new Integer(3);
		baseInput.clearPressedKeys();
		baseInput.placeKeyPressed(5); // 5, but not 6!
		keyCapture.update(99); // good first capture.
		keyCapture.update(100); // Bad second capture, but now the refresh
		                        // modifications will come into play.
		assertTrue(keyCapture.keyCaptured);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.funbox.KeyCapture#isActive()}.
	 */
	public final void testIsActive() {
		keyCapture.setActive(true);
		assertTrue(keyCapture.isActive());
		keyCapture.setActive(false);
		assertFalse(keyCapture.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setActive(boolean)}.
	 */
	public final void testSetActive() {
		keyCapture.setActive(true);
		assertTrue(keyCapture.isActive());
		keyCapture.setActive(false);
		assertFalse(keyCapture.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#getDelayTime()}.
	 */
	public final void testGetDelayTime() {
		assertEquals(100, keyCapture.getDelayTime());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.funbox.KeyCapture#setDelayTime(int)}.
	 */
	public final void testSetDelayTime() {
		keyCapture.setDelayTime(1000);
		assertEquals(1000, keyCapture.getDelayTime());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.funbox.KeyCapture#toString()}.
	 */
	public final void testToString() {
		assertNotNull(keyCapture.toString());
		// Now with modifiers for code coverage.
		keyCapture.setModifiers(new int[] {
			3
		});
		assertNotNull(keyCapture.toString());
	}
	
}
