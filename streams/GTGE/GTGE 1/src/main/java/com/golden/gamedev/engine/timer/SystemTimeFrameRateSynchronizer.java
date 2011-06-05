/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.engine.timer;

import com.golden.gamedev.engine.BaseTimer;

/**
 * The {@link SystemTimeFrameRateSynchronizer} class provides a {@link BaseTimer} implementation that uses the
 * {@link System#currentTimeMillis() current system time}, in milliseconds, to provide for the {@link #delayForFrame()
 * delay} for a frame to be rendered.
 * 
 * @version 1.0
 * @since 1.0
 * @author MetroidFan2002 - Changed to extend the {@link BaseFrameRateSynchronizer} class, some internal refactoring.
 * @author Paulus Tuerah - Original Author
 * 
 */
public final class SystemTimeFrameRateSynchronizer extends BaseFrameRateSynchronizer {
	
	/**
	 * Whether or not to throw {@link InterruptedException} instances wrapped in a {@link RuntimeException} if one
	 * occurs when {@link #delayForFrame()} is invoked.
	 */
	private boolean throwInterruptedExceptions;
	
	/**
	 * The number of milliseconds each call to {@link #delayForFrame()} should attempt to {@link Thread#sleep(long)
	 * sleep} for a single frame.
	 */
	private long delayForFrame;
	
	/**
	 * The time, in milliseconds since the epoch, of the last time {@link #delayForFrame()} was invoked (or the
	 * {@link SystemTimeFrameRateSynchronizer} was {@link #reset() reset}).
	 */
	private long timeOfLastSleep;
	
	/**
	 * The time, in milliseconds, that the last call to {@link Thread#sleep(long) sleep} went over - this will be
	 * deducted from the next {@link #delayForFrame()} invocation to attempt to stabilize the frame rate.
	 */
	private long overSleepTime;
	
	/**
	 * The {@link FPSCounter} instance responsible for counting frames per second.
	 * 
	 */
	private final FPSCounter fpsCounter = new FPSCounter();
	
	/**
	 * Creates a new {@link SystemTimeFrameRateSynchronizer} instance with the default requested frames per second value
	 * of 50 frames per second, which will not throw a {@link RuntimeException} if an {@link InterruptedException}
	 * occurred when {@link #delayForFrame()} is invoked.
	 */
	public SystemTimeFrameRateSynchronizer() {
		this(50);
	}
	
	/**
	 * Creates a new {@link SystemTimeFrameRateSynchronizer} instance with the requested frames per second value, which
	 * will not throw a {@link RuntimeException} if an {@link InterruptedException} occurred when
	 * {@link #delayForFrame()} is invoked.
	 * 
	 * @param fps
	 *            The requested number of frames per second.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the requested number of frames per second is less than
	 *             or equal to 0.
	 */
	public SystemTimeFrameRateSynchronizer(final int fps) {
		this(fps, false);
	}
	
	/**
	 * Creates a new {@link SystemTimeFrameRateSynchronizer} instance with the requested frames per second value, which
	 * may throw a {@link RuntimeException} if an {@link InterruptedException} occurred when {@link #delayForFrame()} is
	 * invoked.
	 * 
	 * @param fps
	 *            The requested number of frames per second.
	 * @param throwInterruptedExceptions
	 *            Whether or not to throw {@link InterruptedException} instances wrapped in a {@link RuntimeException}
	 *            if one occurs when {@link #delayForFrame()} is invoked.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the requested number of frames per second is less than
	 *             or equal to 0.
	 */
	public SystemTimeFrameRateSynchronizer(final int fps, final boolean throwInterruptedExceptions) {
		super(fps);
		this.throwInterruptedExceptions = throwInterruptedExceptions;
	}
	
	@Override
	public long delayForFrame() {
		if (!isRunning()) {
			throw new IllegalStateException(
					"The timer is not running - call the startTimer() method before the sleep() method is invoked!");
		}
		
		long end = System.currentTimeMillis();
		
		final long sleepTime = (delayForFrame - (end - timeOfLastSleep)) - overSleepTime;
		
		if (sleepTime > 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (final InterruptedException e) {
				if (throwInterruptedExceptions) {
					throw new RuntimeException("An InterruptedException occurred in the delayForFrame method "
							+ "of SystemTimer and InterruptedExceptions were requested to be thrown", e);
				}
			}
			
			overSleepTime = (System.currentTimeMillis() - end) - sleepTime;
		}
		
		fpsCounter.calculateFPS();
		final long delayTime = System.currentTimeMillis() - timeOfLastSleep;
		timeOfLastSleep = System.currentTimeMillis();
		
		return delayTime;
	}
	
	@Override
	public int getCurrentFPS() {
		return fpsCounter.getCurrentFPS();
	}
	
	@Override
	public void reset() {
		timeOfLastSleep = System.currentTimeMillis();
		overSleepTime = 0;
		fpsCounter.reset();
		delayForFrame = 1000 / getFps();
	}
}