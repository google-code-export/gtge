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
package com.golden.gamedev.engine;

/**
 * <code>BaseTimer</code> interface is an interface for running a loop constantly in a requested frame per second.
 * <p>
 * 
 * Common methods of how-to-use <code>BaseTimer</code>:
 * 
 * <pre>
 *    public class TimerEngine implements BaseTimer {
 *       .....
 *       public static void main(String[] args) {
 *          BaseTimer engine = new TimerEngine(...);
 *          // set the target frame-per-second
 *          engine.setFPS(50); // 50 fps
 *          // start the timer!!
 *          engine.startTimer();
 *          // game loop
 *          while (true) {
 *             // sleep to achieve the target frame-per-second
 *             long elapsedTime = engine.sleep();
 *          }
 *          // stop the timer
 *          engine.stopTimer();
 *       }
 *    }
 * </pre>
 */
public interface FrameRateSynchronizer {
	
	/**
	 * Starts the {@link FrameRateSynchronizer timer} running, and optionally {@link #reset() resets} its state. Because a
	 * constructor may provide the ability to set the requested frames per second directly, this method will not throw
	 * an exception if {@link #setFps(int)} is not invoked prior to its invocation, but unanticipated side effects may
	 * occur if the requested frames per second is not set beforehand.
	 * 
	 * @see #setFps() To set the requested frames per second.
	 * @see #reset() To reset a running BaseTimer instance.
	 */
	public void beginSynchronization();
	
	/**
	 * Delays the execution of the current {@link Thread} in order for a frame to be rendered in a bounded amount of
	 * time. If this method is invoked on a {@link FrameRateSynchronizer} that is not {@link #isRunning() running}, an
	 * {@link IllegalStateException} will be thrown.
	 * 
	 * @return The amount of time delay that occurred, in milliseconds, in order to allow for the current frame to be
	 *         rendered for a single interval in order to support the {@link #setFps(int) requested frames per second}.
	 * @see #isRunning() Whether or not this BaseTimer instance is running.
	 * @throws IllegalStateException
	 *             Throws an {@link IllegalStateException} if this {@link FrameRateSynchronizer} instance is not
	 *             {@link #isRunning() running}.
	 * @throws RuntimeException
	 *             Throws a {@link RuntimeException} if the delay cannot be executed due to an unexpected error.
	 */
	public long delayForFrame();
	
	/**
	 * Gets the number of frames actually rendered for the previous second.
	 * 
	 * @return The number of frames actually rendered for the previous second.
	 * @see #getFps() To retrieve the requested number of frames per second.
	 */
	public int getCurrentFPS();
	
	/**
	 * Gets the requested number of frames per second. To retrieve the number of frames actually rendered for the
	 * previous second, use {@link #getCurrentFPS()}.
	 * 
	 * @return The requested number of frames per second.
	 * @see #getCurrentFPS() To retrieve the number of frames actually rendered for the previous second.
	 * @see #setFps(int) To set the requested number of frames per second.
	 */
	public int getFps();
	
	/**
	 * Sets the requested number of frames per second for this {@link FrameRateSynchronizer} instance. This value is a requested
	 * number of frames per second because due to system processing and hardware the current machine may not be capable
	 * of meeting this frame rate. The {@link FrameRateSynchronizer#reset() reset} method should be invoked on a
	 * {@link FrameRateSynchronizer#isRunning() running} {@link FrameRateSynchronizer} instance to ensure consistent results after this method
	 * is invoked.
	 * 
	 * @param fps
	 *            The requested number of frames per second, which must be greater than or equal to 1.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the requested number of frames per second is less than
	 *             or equal to 0.
	 * @see #getCurrentFPS() To retrieve the number of frames actually rendered for the previous second.
	 * @see #reset() To reset this BaseTimer instance after this method is invoked.
	 */
	public void setFps(int fps);
	
}
