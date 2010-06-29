/**
 * 
 */
package com.golden.gamedev.engine.graphics;

import java.awt.event.WindowListener;

import com.golden.gamedev.MockSecurityManager;

import junit.framework.TestCase;

/**
 * The {@link WindowExitListenerTest} class provides a {@link TestCase} that
 * tests the functionality of the {@link WindowExitListener} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see WindowExitListener
 * 
 */
public class WindowExitListenerTest extends TestCase {
	
	/**
	 * The {@link WindowExitListener} instance under test.
	 */
	private WindowListener listener;
	
	/**
	 * Creates a new {@link WindowExitListenerTest} instance with the given
	 * {@link String} name.
	 * @param name The {@link String} name of this
	 *        {@link WindowExitListenerTest} instance.
	 */
	public WindowExitListenerTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		listener = WindowExitListener.INSTANCE;
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.graphics.WindowExitListener#getInstance()}
	 * .
	 */
	public final void testGetInstance() {
		assertNotNull(WindowExitListener.getInstance());
		assertEquals(WindowExitListener.INSTANCE, WindowExitListener
		        .getInstance());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.graphics.WindowExitListener#windowClosing(java.awt.event.WindowEvent)}
	 * .
	 */
	public final void testWindowClosingWindowEvent() {
		SecurityManager current = System.getSecurityManager();
		MockSecurityManager manager = new MockSecurityManager();
		System.setSecurityManager(manager);
		try {
			listener.windowClosing(null);
			fail("Expected SecurityException - exit should be blocked by mock security manager.");
		}
		catch (SecurityException e) {
			// Intentionally blank
		}
		assertEquals(0, manager.exitStatusCode.intValue());
		System.setSecurityManager(current);
	}
	
}
