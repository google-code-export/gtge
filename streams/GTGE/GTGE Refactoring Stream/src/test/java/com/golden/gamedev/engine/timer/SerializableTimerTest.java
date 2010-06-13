/**
 * 
 */
package com.golden.gamedev.engine.timer;

import junit.framework.TestCase;

import com.golden.gamedev.ActionExecutor;
import com.golden.gamedev.MockActionExecutor;
import com.golden.gamedev.NullActionExecutor;

/**
 * The {@link SerializableTimerTest} class provides a {@link TestCase} extension
 * to test the functionality of the {@link SerializableTimer} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see SerializableTimer
 * 
 */
public class SerializableTimerTest extends TestCase {
	
	/**
	 * The {@link SerializableTimer} instance under test.
	 */
	private SerializableTimer timer;
	
	/**
	 * Creates a new {@link SerializableTimerTest} instance with the given name.
	 * @param name The {@link String} name of this {@link SerializableTimerTest}
	 *        instance.
	 */
	public SerializableTimerTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		timer = new SerializableTimer(50, NullActionExecutor.INSTANCE);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#SerializableTimer(long, com.golden.gamedev.ActionExecutor)
	 * )}.
	 */
	public void testSerializableTimerLongActionExecutor() {
		try {
			timer = new SerializableTimer(24, null);
			fail("Expected IllegalArgumentException - a Serializable ActionExecutor instance must be specified.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
		try {
			timer = new SerializableTimer(24, new ActionExecutor() {
				
				public void execute() {
					// TODO Auto-generated method stub
					
				}
			});
			fail("Expected IllegalArgumentException - a Serializable ActionExecutor instance must be specified.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
		timer = new SerializableTimer(353, new MockActionExecutor());
		assertNotNull(timer);
		assertEquals(353, timer.getDelay());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#action(long)}.
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
		timer = new SerializableTimer(100, executor);
		assertFalse(timer.action(50));
		assertFalse(executor.actionExecuted);
		assertTrue(timer.action(50));
		assertTrue(executor.actionExecuted);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#setEquals(com.golden.gamedev.object.SerializableTimer)}
	 * .
	 */
	public void testSetEquals() {
		SerializableTimer newSerializableTimer = new SerializableTimer(100,
		        NullActionExecutor.INSTANCE);
		assertEquals(0, newSerializableTimer.getCurrentTick());
		assertEquals(100, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
		timer.action(10);
		newSerializableTimer.setEquals(timer);
		assertEquals(10, newSerializableTimer.getCurrentTick());
		assertEquals(50, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
		timer.action(40);
		newSerializableTimer.setEquals(timer);
		assertEquals(0, newSerializableTimer.getCurrentTick());
		assertEquals(50, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
	}
	
	public void testCopy() throws Exception {
		SerializableTimer newSerializableTimer = new SerializableTimer(100,
		        NullActionExecutor.INSTANCE);
		assertEquals(0, newSerializableTimer.getCurrentTick());
		assertEquals(100, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
		timer.action(10);
		SerializableTimer copy = (SerializableTimer) newSerializableTimer
		        .copy(timer);
		assertTrue(copy == newSerializableTimer);
		assertEquals(10, newSerializableTimer.getCurrentTick());
		assertEquals(50, newSerializableTimer.getDelay());
		assertTrue(newSerializableTimer.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#isActive()}.
	 */
	public void testIsActive() {
		assertTrue(timer.isActive());
		timer.setActive(false);
		assertFalse(timer.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#setActive(boolean)}.
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
	 * {@link com.golden.gamedev.object.SerializableTimer#getDelay()}.
	 */
	public void testGetDelay() {
		assertEquals(50, timer.getDelay());
		timer.setDelay(100);
		assertEquals(100, timer.getDelay());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#setDelay(long)}.
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
	 * {@link com.golden.gamedev.object.SerializableTimer#getCurrentTick()}.
	 */
	public void testGetCurrentTick() {
		assertEquals(0, timer.getCurrentTick());
		timer.action(10);
		assertEquals(10, timer.getCurrentTick());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#setCurrentTick(long)}.
	 */
	public void testSetCurrentTick() {
		assertEquals(0, timer.getCurrentTick());
		timer.setCurrentTick(10);
		assertEquals(10, timer.getCurrentTick());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.SerializableTimer#reset()}.
	 */
	public void testReset() {
		timer.action(30);
		assertEquals(30, timer.getCurrentTick());
		timer.reset();
		assertEquals(0, timer.getCurrentTick());
	}
	
	public void testExecute() throws Exception {
		MockActionExecutor executor = new MockActionExecutor();
		timer = new SerializableTimer(100, executor);
		assertFalse(executor.actionExecuted);
		timer.execute();
		assertTrue(executor.actionExecuted);
	}
	
}
