package com.golden.gamedev.engine.timer;

import static org.junit.Assert.*;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link FPSCounterTest} class provides a test case for the {@link FPSCounter} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public class FPSCounterTest {
	
	/**
	 * The {@link FPSCounter} instance under test.
	 */
	private FPSCounter counter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		counter = new FPSCounter();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.FPSCounter#FPSCounter()}.
	 */
	@Test
	public final void testFPSCounter() {
		assertNotNull("The counter should not be null!", counter);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.FPSCounter#reset()}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testReset() throws Exception {
		long previousClock = (Long) FieldUtils.readDeclaredField(counter, "clockStartTime", true);
		counter.calculateFPS();
		counter.calculateFPS();
		assertTrue(
				"Expected the previous clock time to be less than or equal to the current clock (the second may have changed!)",
				previousClock <= (Long) FieldUtils.readDeclaredField(counter, "clockStartTime", true));
		assertTrue("Expected the frame count to be less than or equal to 2 (the second may have changed!)",
				(Integer) FieldUtils.readDeclaredField(counter, "frameCountForCurrentSecond", true) <= 2);
		counter.reset();
		assertTrue("Expected the previous clock time to be less than the current clock",
				previousClock < (Long) FieldUtils.readDeclaredField(counter, "clockStartTime", true));
		assertEquals("Expected the frame count to be equal to 0", 0,
				((Integer) FieldUtils.readDeclaredField(counter, "frameCountForCurrentSecond", true)).intValue());
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.FPSCounter#calculateFPS()}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testCalculateFPS() throws Exception {
		counter.reset();
		Thread.sleep(1100);
		counter.calculateFPS();
		assertEquals("Expected one frame as the count was only invoked once for the one second of elapsed time", 1,
				counter.getCurrentFPS());
		counter.calculateFPS();
		Thread.sleep(1100);
		counter.calculateFPS();
		assertEquals("Expected two frames as the count was only invoked twice during the one second of elapsed time",
				2, counter.getCurrentFPS());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.FPSCounter#getCurrentFPS()}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testGetCurrentFPS() throws Exception {
		assertEquals(0, counter.getCurrentFPS());
		Thread.sleep(1100);
		counter.calculateFPS();
		assertEquals(1, counter.getCurrentFPS());
	}
	
}
