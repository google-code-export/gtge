/**
 * 
 */
package com.golden.gamedev.engine.timer;

import junit.framework.TestCase;

import com.golden.gamedev.MockActionExecutor;
import com.golden.gamedev.NullActionExecutor;

/**
 * The {@link ThrowawayTimerTest} class provides a {@link TestCase} extension to
 * test the functionality of the {@link ThrowawayTimer} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see ThrowawayTimer
 * 
 */
public class ThrowawayTimerTest extends TestCase {
	
	/**
	 * The {@link ThrowawayTimer} instance under test.
	 */
	private ThrowawayTimer timer;
	
	/**
	 * Creates a new {@link ThrowawayTimerTest} instance with the given name.
	 * @param name The {@link String} name of this {@link ThrowawayTimerTest}
	 *        instance.
	 */
	public ThrowawayTimerTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		timer = new ThrowawayTimer(50, NullActionExecutor.INSTANCE);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#ThrowawayTimer(long, com.golden.gamedev.ActionExecutor)
	 * )}.
	 */
	public void testSerializableTimerLongActionExecutor() {
		try {
			timer = new ThrowawayTimer(24, null);
			fail("Expected IllegalArgumentException - a non-null ActionExecutor instance must be specified.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
		timer = new ThrowawayTimer(353, new MockActionExecutor());
		assertNotNull(timer);
		assertEquals(353, timer.getDelay());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#action(long)}.
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
		
		MockActionExecutor executor = new MockActionExecutor();
		timer = new ThrowawayTimer(100, executor);
		assertFalse(timer.action(50));
		assertFalse(executor.actionExecuted);
		assertTrue(timer.action(50));
		assertTrue(executor.actionExecuted);
	}
	
	public void testCopy() throws Exception {
		ThrowawayTimer newSerializableTimer = new ThrowawayTimer(100,
		        NullActionExecutor.INSTANCE);
		assertEquals(0, newSerializableTimer.getCurrentTick());
		assertEquals(100, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
		timer.action(10);
		ThrowawayTimer copy = (ThrowawayTimer) newSerializableTimer.copy(timer);
		assertTrue(copy == newSerializableTimer);
		assertEquals(10, newSerializableTimer.getCurrentTick());
		assertEquals(50, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#isActive()}.
	 */
	public void testIsActive() {
		assertTrue(timer.isActive());
		timer.setActive(false);
		assertFalse(timer.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#setActive(boolean)}.
	 */
	public void testSetActive() {
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
		timer.setActive(false);
		assertFalse(timer.isActive());
		assertEquals(0, timer.getCurrentTick());
		timer.setActive(true);
		assertTrue(timer.isActive());
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
		timer.setActive(true);
		assertTrue(timer.isActive());
		assertEquals(0, timer.getCurrentTick());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#getDelay()}.
	 */
	public void testGetDelay() {
		assertEquals(50, timer.getDelay());
		timer.setDelay(100);
		assertEquals(100, timer.getDelay());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#setDelay(long)}.
	 */
	public void testSetDelay() {
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
		timer.setDelay(100);
		assertEquals(100, timer.getDelay());
		assertEquals(0, timer.getCurrentTick());
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
		timer.setDelay(50);
		assertEquals(50, timer.getDelay());
		assertEquals(0, timer.getCurrentTick());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.ThrowawayTimer#getCurrentTick()}.
	 */
	public void testGetCurrentTick() {
		assertEquals(0, timer.getCurrentTick());
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.ThrowawayTimer#reset()}.
	 */
	public void testReset() {
		timer.action(30);
		assertEquals(30, timer.getCurrentTick());
		timer.reset();
		assertEquals(0, timer.getCurrentTick());
	}
	
	public void testExecute() throws Exception {
		MockActionExecutor executor = new MockActionExecutor();
		timer = new ThrowawayTimer(100, executor);
		assertFalse(executor.actionExecuted);
		timer.execute();
		assertTrue(executor.actionExecuted);
	}
	
}
