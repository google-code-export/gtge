package com.golden.gamedev.engine.timer;

import static org.junit.Assert.*;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link SystemTimeFrameRateSynchronizerTest} class provides a test case to verify the functionality of the
 * {@link SystemTimeFrameRateSynchronizer} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * @see SystemTimeFrameRateSynchronizer
 * 
 */
public class SystemTimeFrameRateSynchronizerTest {
	
	/**
	 * The {@link SystemTimeFrameRateSynchronizer} instance under test.
	 */
	private SystemTimeFrameRateSynchronizer synchronizer;
	
	/**
	 * Sets up each run of the {@link SystemTimeFrameRateSynchronizerTest}.
	 */
	@Before
	public void setUp() {
		synchronizer = new SystemTimeFrameRateSynchronizer();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer()}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testSystemTimeFrameRateSynchronizer() throws Exception {
		assertNotNull("Expected the synchronizer to not be null!", synchronizer);
		assertEquals("Expected the desired FPS to be equal to 50!", 50, synchronizer.getFps());
		assertFalse("Expected the flag to throw InterruptedExceptions to be false!",
				(Boolean) FieldUtils.readField(synchronizer, "throwInterruptedExceptions", true));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int)}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testSystemTimeFrameRateSynchronizerInt() throws Exception {
		synchronizer = new SystemTimeFrameRateSynchronizer(30);
		assertNotNull("Expected the synchronizer to not be null!", synchronizer);
		assertEquals("Expected the desired FPS to be equal to 30!", 30, synchronizer.getFps());
		assertFalse("Expected the flag to throw InterruptedExceptions to be false!",
				(Boolean) FieldUtils.readField(synchronizer, "throwInterruptedExceptions", true));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int)},
	 * providing a zero (invalid) integer for the frame rate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSystemTimeFrameRateZero() {
		synchronizer = new SystemTimeFrameRateSynchronizer(0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int)},
	 * providing a negative (invalid) integer for the frame rate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSystemTimeFrameRateNegative() {
		synchronizer = new SystemTimeFrameRateSynchronizer(-45);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int, boolean)}
	 * .
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testSystemTimeFrameRateSynchronizerIntBoolean() throws Exception {
		synchronizer = new SystemTimeFrameRateSynchronizer(30, false);
		assertNotNull("Expected the synchronizer to not be null!", synchronizer);
		assertEquals("Expected the desired FPS to be equal to 30!", 30, synchronizer.getFps());
		assertFalse("Expected the flag to throw InterruptedExceptions to be false!",
				(Boolean) FieldUtils.readField(synchronizer, "throwInterruptedExceptions", true));
		synchronizer = new SystemTimeFrameRateSynchronizer(30, true);
		assertNotNull("Expected the synchronizer to not be null!", synchronizer);
		assertEquals("Expected the desired FPS to be equal to 30!", 30, synchronizer.getFps());
		assertTrue("Expected the flag to throw InterruptedExceptions to be true!",
				(Boolean) FieldUtils.readField(synchronizer, "throwInterruptedExceptions", true));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int, boolean)}
	 * , providing a zero (invalid) integer for the frame rate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSystemTimeFrameRateSynchronizerIntBooleanZero() {
		synchronizer = new SystemTimeFrameRateSynchronizer(0, false);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#SystemTimeFrameRateSynchronizer(int, boolean)}
	 * , providing a negative (invalid) integer for the frame rate.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSystemTimeFrameRateSynchronizerIntBooleanNegative() {
		synchronizer = new SystemTimeFrameRateSynchronizer(-8989, false);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#beginSynchronization()}.
	 */
	@Test
	public final void testBeginSynchronization() {
		synchronizer.beginSynchronization();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#delayForFrame()}.
	 */
	@Test
	public final void testDelayForFrame() {
		long delayTime = synchronizer.delayForFrame();
		assertTrue("Expected the delay time to be greater than or equal to 20", delayTime >= 20);
		if (delayTime > 20) {
			delayTime = synchronizer.delayForFrame();
			assertTrue("Expected the delay time to be less than 20, for time drift calculation", delayTime < 20);
		} else {
			delayTime = synchronizer.delayForFrame();
			assertTrue("Expected the delay time to be greater than or equal to 20", delayTime >= 20);
		}
	}
	
	/**
	 * The {@link SynchRunner} class provides a {@link Runnable} implementation to test the
	 * {@link SystemTimeFrameRateSynchronizer} class so that it could be {@link Thread#interrupt() interrupted}.
	 * 
	 * @author MetroidFan2002
	 * @version 1.0
	 * @since 1.0
	 * @see Runnable
	 * 
	 */
	private static final class SynchRunner implements Runnable {
		
		/**
		 * Whether or not a {@link RuntimeException} was thrown for calls to
		 * {@link SystemTimeFrameRateSynchronizer#delayForFrame()}.
		 */
		private boolean exceptionThrown;
		
		/**
		 * The {@link SystemTimeFrameRateSynchronizer} instance to use via the {@link #run()} method.
		 */
		private SystemTimeFrameRateSynchronizer synchronizer;
		
		/**
		 * Creates a new {@link SynchRunner} instance.
		 * 
		 * @param synchronizer
		 *            The {@link SystemTimeFrameRateSynchronizer} instance to use via the {@link #run()} method.
		 */
		public SynchRunner(SystemTimeFrameRateSynchronizer synchronizer) {
			super();
			this.synchronizer = synchronizer;
		}
		
		@Override
		public void run() {
			synchronizer.beginSynchronization();
			try {
				synchronizer.delayForFrame();
				synchronizer.delayForFrame();
				synchronizer.delayForFrame();
				synchronizer.delayForFrame();
				synchronizer.delayForFrame();
			} catch (RuntimeException e) {
				exceptionThrown = true;
			}
		}
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#delayForFrame()} that
	 * ensures the InterruptedException thrown is ignored.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testDelayForFrameWithIgnoredInterrupt() throws Exception {
		SynchRunner runner = new SynchRunner(synchronizer);
		Thread runnerThread = new Thread(runner);
		runnerThread.start();
		Thread.sleep(30);
		runnerThread.interrupt();
		Thread.sleep(100);
		assertFalse("No exception should have been thrown", runner.exceptionThrown);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#delayForFrame()} that
	 * ensures the InterruptedException thrown is not ignored, but is thrown as a {@link RuntimeException}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testDelayForFrameWithThrownInterrupt() throws Exception {
		SynchRunner runner = new SynchRunner(new SystemTimeFrameRateSynchronizer(50, true));
		Thread runnerThread = new Thread(runner);
		runnerThread.start();
		Thread.sleep(30);
		runnerThread.interrupt();
		Thread.sleep(100);
		assertTrue("An exception should have been thrown", runner.exceptionThrown);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#getCurrentFPS()}.
	 * 
	 * @throws Exception
	 *             Throws an {@link Exception} if the test fails.
	 */
	@Test
	public final void testGetCurrentFPS() throws Exception {
		assertEquals(0, synchronizer.getCurrentFPS());
		synchronizer.beginSynchronization();
		Thread.sleep(1100);
		synchronizer.delayForFrame();
		assertEquals(1, synchronizer.getCurrentFPS());
		synchronizer.delayForFrame();
		synchronizer.delayForFrame();
		synchronizer.delayForFrame();
		Thread.sleep(1100);
		synchronizer.delayForFrame();
		assertTrue("Expected the frames to be greater than zero.", synchronizer.getCurrentFPS() > 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#getFps()}.
	 */
	@Test
	public final void testGetFps() {
		assertEquals("Expected the requested FPS to be 50!", 50, synchronizer.getFps());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#setFps(int)}.
	 */
	@Test
	public final void testSetFps() {
		synchronizer.setFps(20);
		assertEquals("Expected the requested FPS to be 20!", 20, synchronizer.getFps());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#setFps(int)}, giving a
	 * zero (invalid) value.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSetFpsZero() {
		synchronizer.setFps(0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.timer.SystemTimeFrameRateSynchronizer#setFps(int)}, giving a
	 * negative (invalid) value.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSetFpsNegative() {
		synchronizer.setFps(-220);
	}
	
}
