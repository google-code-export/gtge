/**
 * 
 */
package com.golden.gamedev.object;

import junit.framework.TestCase;

/**
 * The {@link TimerTest} class provides a {@link TestCase} extension to test the
 * functionality of the {@link Timer} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see Timer
 * 
 */
public class TimerTest extends TestCase {
	
	/**
	 * The {@link Timer} instance under test.
	 */
	private Timer timer;
	
	/**
	 * Creates a new {@link TimerTest} instance with the given name.
	 * @param name The {@link String} name of this {@link TimerTest} instance.
	 */
	public TimerTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		timer = new Timer(50);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#Timer(int)}.
	 */
	public void testTimer() {
		assertNotNull(timer);
		assertEquals(0, timer.getCurrentTick());
		assertEquals(50, timer.getDelay());
		assertTrue(timer.isActive());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#action(long)}.
	 */
	public void testAction() {
		// 10 milliseconds doesn't trigger the action.
		assertFalse(timer.action(10));
		
		// 10 + 40 = 50, so trigger the action.
		assertTrue(timer.action(40));
		assertEquals(0, timer.getCurrentTick());
		assertTrue(timer.action(55));
		assertEquals(5, timer.getCurrentTick());
		
		timer.setActive(false);
		assertFalse(timer.isActive());
		// 45 increment would trigger the action, but the timer's not active, so
		// it doesn't.
		assertFalse(timer.action(45));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Timer#setEquals(com.golden.gamedev.object.Timer)}
	 * .
	 */
	public void testSetEquals() {
		Timer newTimer = new Timer(100);
		assertEquals(0, newTimer.getCurrentTick());
		assertEquals(100, newTimer.getDelay());
		assertTrue(newTimer.isActive());
		timer.action(10);
		newTimer.setEquals(timer);
		assertEquals(10, newTimer.getCurrentTick());
		assertEquals(50, newTimer.getDelay());
		assertTrue(newTimer.isActive());
		timer.action(40);
		newTimer.setEquals(timer);
		assertEquals(0, newTimer.getCurrentTick());
		assertEquals(50, newTimer.getDelay());
		assertTrue(newTimer.isActive());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#isActive()}.
	 */
	public void testIsActive() {
		assertTrue(timer.isActive());
		timer.setActive(false);
		assertFalse(timer.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Timer#setActive(boolean)}.
	 */
	public void testSetActive() {
		timer.setActive(false);
		assertFalse(timer.isActive());
		timer.setActive(true);
		assertTrue(timer.isActive());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#getDelay()}.
	 */
	public void testGetDelay() {
		assertEquals(50, timer.getDelay());
		timer.setDelay(100);
		assertEquals(100, timer.getDelay());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#setDelay(long)}.
	 */
	public void testSetDelay() {
		timer.setDelay(100);
		assertEquals(100, timer.getDelay());
		timer.setDelay(50);
		assertEquals(50, timer.getDelay());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#getCurrentTick()}.
	 */
	public void testGetCurrentTick() {
		assertEquals(0, timer.getCurrentTick());
		timer.setCurrentTick(10);
		assertEquals(10, timer.getCurrentTick());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Timer#setCurrentTick(long)}.
	 */
	public void testSetCurrentTick() {
		assertEquals(0, timer.getCurrentTick());
		timer.setCurrentTick(10);
		assertEquals(10, timer.getCurrentTick());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Timer#reset()}.
	 */
	public void testReset() {
		timer.setCurrentTick(30);
		assertEquals(30, timer.getCurrentTick());
		timer.reset();
		assertEquals(0, timer.getCurrentTick());
	}
	
}
