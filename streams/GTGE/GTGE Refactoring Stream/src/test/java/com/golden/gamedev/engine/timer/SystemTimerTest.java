/**
 * 
 */
package com.golden.gamedev.engine.timer;

import junit.framework.TestCase;


/**
 * The {@link SystemTimerTest} is a {@link TestCase} that tests the behavior of the {@link SystemTimer} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see SystemTimer
 *
 */
public class SystemTimerTest extends TestCase {
	
	/**
	 * The {@link SystemTimer} instance under test.
	 */
	private SystemTimer timer;
	
	protected void setUp() throws Exception {
		timer = new SystemTimer();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#SystemTimer()}.
	 */
	public void testSystemTimer() {
		assertNotNull(timer);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#startTimer()}.
	 */
	public void testStartTimer() {
		timer.startTimer();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#stopTimer()}.
	 */
	public void testStopTimer() {
		timer.stopTimer();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#sleep()}.
	 */
	public void testSleep() {
		timer.sleep();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#isRunning()}.
	 */
	public void testIsRunning() {
		assertFalse(timer.isRunning());
		timer.startTimer();
		assertTrue(timer.isRunning());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#getCurrentFPS()}.
	 */
	public void testGetCurrentFPS() {
		assertEquals(0, timer.getCurrentFPS());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#getFPS()}.
	 */
	public void testGetFPS() {
		assertEquals(50, timer.getFPS());
		timer.setFPS(100);
		assertEquals(100, timer.getFPS());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#setFPS(int)}.
	 */
	public void testSetFPS() {
		timer.setFPS(100);
		assertEquals(100, timer.getFPS());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#getTime()}.
	 */
	public void testGetTime() {
		timer.getTime();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimer#refresh()}.
	 */
	public void testRefresh() {
		timer.refresh();
	}

	
    public SystemTimer getTimer() {
    	return timer;
    }

	
    public void setTimer(SystemTimer timer) {
    	this.timer = timer;
    }
	
}
