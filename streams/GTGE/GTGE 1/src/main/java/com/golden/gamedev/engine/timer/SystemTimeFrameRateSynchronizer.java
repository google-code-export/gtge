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

import org.apache.commons.lang.Validate;

import com.golden.gamedev.engine.FrameRateSynchronizer;

/**
 * The {@link SystemTimeFrameRateSynchronizer} class provides a {@link FrameRateSynchronizer} implementation that uses
 * the {@link System#currentTimeMillis() current system time}, in milliseconds, to provide for the
 * {@link #delayForFrame() delay} for a frame to be rendered.
 * 
 * @version 1.0
 * @since 1.0
 * @author MetroidFan2002 - Now implements the {@link FrameRateSynchronizer} interface, internal refactoring to improve
 *         performance.
 * @author Paulus Tuerah - Original Author
 * 
 */
public final class SystemTimeFrameRateSynchronizer implements FrameRateSynchronizer {
	
	/**
	 * The {@link FPSCounter} class counts the frames rendered for the current second, and keeps the value of the frames
	 * rendered for the last second for retrieval purposes.
	 * 
	 * @author MetroidFan2002 - Changed to be private and static to support the {@link SystemTimeFrameRateSynchronizer}
	 *         class only, and made the class final.
	 * @author Paulus Tuerah - Original Author
	 * @version 1.0
	 * @since 1.0
	 */
	private static final class FPSCounter {
		
		/**
		 * The timestamp, in milliseconds, when the frames per second clock started.
		 */
		private long clockStartTime;
		
		/**
		 * The number of frames counted for the last second interval.
		 */
		private int framesCountForLastSecond;
		
		/**
		 * The number of frames calculated for the current second.
		 */
		private int frameCountForCurrentSecond;
		
		/**
		 * Creates a new {@link FPSCounter} instance.
		 */
		private FPSCounter() {
			super();
		}
		
		/**
		 * Resets the current count of frames per second to 0 and the timestamp for counting frames to the current time.
		 */
		private void reset() {
			frameCountForCurrentSecond = 0;
			clockStartTime = System.currentTimeMillis();
		}
		
		/**
		 * Calculates the current frames per second by incrementing the frame count. If the time elapsed since the last
		 * time this method was invoked was greater than a second, the count is stored into the {@link #getCurrentFPS()
		 * frames per second interval} and the timestamp of the timer is reset to the current time to begin counting for
		 * the next second.
		 */
		private void calculateFPS() {
			frameCountForCurrentSecond++;
			if (System.currentTimeMillis() - clockStartTime > 1000) {
				clockStartTime = System.currentTimeMillis();
				framesCountForLastSecond = frameCountForCurrentSecond;
				frameCountForCurrentSecond = 0;
			}
		}
	}
	
	/**
	 * Whether or not to throw {@link InterruptedException} instances wrapped in a {@link RuntimeException} if one
	 * occurs when {@link #delayForFrame()} is invoked.
	 */
	private final boolean throwInterruptedExceptions;
	
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
	 * The requested number of frames per second.
	 */
	private int fps;
	
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
		super();
		setFps(fps);
		this.throwInterruptedExceptions = throwInterruptedExceptions;
	}
	
	@Override
	public void beginSynchronization() {
		timeOfLastSleep = System.currentTimeMillis();
		overSleepTime = 0;
		fpsCounter.reset();
		delayForFrame = 1000 / getFps();
	}
	
	@Override
	public long delayForFrame() {
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
		return fpsCounter.framesCountForLastSecond;
	}
	
	@Override
	public int getFps() {
		return fps;
	}
	
	@Override
	public void setFps(final int fps) {
		Validate.isTrue(fps >= 1, "FPS must be greater than or equal to 1!");
		this.fps = fps;
	}
}