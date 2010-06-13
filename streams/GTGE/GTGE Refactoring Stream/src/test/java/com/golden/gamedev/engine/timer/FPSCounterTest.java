/**
 * 
 */
package com.golden.gamedev.engine.timer;

import junit.framework.TestCase;

/**
 * The {@link FPSCounterTest} class provides a {@link TestCase} extension to
 * test the functionality of the {@link FPSCounter} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see FPSCounter
 * 
 */
public class FPSCounterTest extends TestCase {
	
	/**
	 * The {@link FPSCounter} instance under test.
	 */
	private FPSCounter counter;
	
	/**
	 * Creates a new {@link FPSCounterTest} instance with the given name.
	 * @param name The {@link String} name of this {@link FPSCounterTest}
	 *        instance.
	 */
	public FPSCounterTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		counter = new FPSCounter();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#FPSCounter()}.
	 */
	public final void testFPSCounter() {
		assertNotNull(counter);
		assertEquals(0, counter.getCurrentFPS());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#FPSCounter(boolean)}.
	 */
	public final void testFPSCounterBoolean() throws Exception {
		// False is tested via testFPSCounter and testCalculateFPS.
		counter = new FPSCounter(true);
		assertNotNull(counter);
		assertEquals(0, counter.getCurrentFPS());
		counter.calculateFPS();
		assertEquals(0, counter.getCurrentFPS());
		Thread.sleep(1500);
		counter.calculateFPS();
		assertEquals(2, counter.getCurrentFPS());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#calculateFPS()}.
	 */
	public final void testCalculateFPS() throws Exception {
		counter.reset();
		counter.calculateFPS();
		counter.calculateFPS();
		Thread.sleep(1500);
		// Any frame that already occurred in the last psuedo-second is added to
		// the count, so there will be three in this count.
		counter.calculateFPS();
		assertEquals(3, counter.getCurrentFPS());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#getCurrentFPS()}.
	 */
	public final void testGetCurrentFPS() {
		// This is more thoroughly tested in the testCalculateFPS method.
		assertEquals(0, counter.getCurrentFPS());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#reset()}.
	 */
	public final void testReset() {
		counter.calculateFPS();
		int fps = counter.getCurrentFPS();
		assertTrue(0 < fps);
		counter.calculateFPS();
		counter.reset();
		// Resetting doesn't make the current (previous second's) FPS go away -
		// it only resets the counter to count for the current second.
		assertEquals(fps, counter.getCurrentFPS());
	}
	
}
