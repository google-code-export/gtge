/**
 * 
 */
package com.golden.gamedev.engine.timer;

import junit.framework.TestCase;

/**
 * The {@link FPSCounterTest} is a {@link TestCase} that tests the behavior of
 * the {@link FPSCounter} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see FPSCounter
 */
public class FPSCounterTest extends TestCase {
	
	/**
	 * The {@link FPSCounter} instance under test.
	 */
	private FPSCounter counter;
	
	protected void setUp() throws Exception {
		setCounter(new FPSCounter());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#FPSCounter()}.
	 */
	public void testFPSCounter() {
		assertNotNull(getCounter());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#refresh()}.
	 */
	public void testRefresh() {
		getCounter().refresh();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#calculateFPS()}.
	 */
	public void testCalculateFPS() {
		getCounter().calculateFPS();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.FPSCounter#getCurrentFPS()}.
	 */
	public void testGetCurrentFPS() {
		assertEquals(0, getCounter().getCurrentFPS());
		long millisecondsToSleep = 1001;
		long currentTime = System.currentTimeMillis();
		long actualTimeSlept = 0;
		
		// The frames that have executed per this second equals 2.
		getCounter().calculateFPS();
		getCounter().calculateFPS();
		
		// Sleep for 1 second and 1 milliseconds to trigger the "greater than"
		// calculation.
		while (millisecondsToSleep > actualTimeSlept) {
			try {
				Thread.sleep(millisecondsToSleep - actualTimeSlept);
			}
			catch (InterruptedException e) {
				// Intentionally blank.
			}
			long currentInstant = System.currentTimeMillis();
			actualTimeSlept += currentInstant - currentTime;
			currentTime = currentInstant;
		}
		
		// Counter returns in getCurrentFPS the FPS calculated in the last
		// second.
		getCounter().calculateFPS();
		
		// 2 equals the frames executed in the last second.
		assertEquals(2, getCounter().getCurrentFPS());
	}
	
	/**
	 * Gets the {@link FPSCounter} instance under test.
	 * @return The {@link FPSCounter} instance under test.
	 */
	public FPSCounter getCounter() {
		return counter;
	}
	
	/**
	 * Sets the {@link FPSCounter} instance under test.
	 * @param counter The {@link FPSCounter} instance under test.
	 */
	public void setCounter(FPSCounter counter) {
		this.counter = counter;
	}
	
}
