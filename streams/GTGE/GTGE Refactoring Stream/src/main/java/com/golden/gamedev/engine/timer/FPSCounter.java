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

/**
 * The {@link FPSCounter} class provides an easy and convenient way to calculate
 * the frames per second (FPS) for any game. <br />
 * <br />
 * An example of how to use the {@link FPSCounter} class is shown below:
 * 
 * <pre>
 * FPSCounter counter;
 * // game loop
 * while (true) {
 * 	counter.getCurrentFPS(); // returns current fps
 * 	counter.calculateFPS(); // calculates fps after each frame is rendered.
 * }
 * </pre>
 * 
 * <b><i>Warning: The {@link FPSCounter} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link FPSCounter}
 * class.</i></b>
 * 
 * @version 1.0
 * @since 0.2.4
 * 
 */
public class FPSCounter {
	
	/**
	 * The frames per second (FPS) counted in the previous second.
	 */
	private int currentFPS;
	
	/**
	 * The running total of frames counted in the current second.
	 */
	private int frameCount;
	
	/**
	 * The time, in milliseconds since the epoch, that the frames per second
	 * (FPS) was last counted.
	 */
	private long lastCount;
	
	/**
	 * Creates a new {@link FPSCounter} instance.
	 */
	public FPSCounter() {
		super();
	}
	
	/**
	 * Calculates the frames per second when called after each frame is
	 * rendered.
	 */
	public void calculateFPS() {
		this.frameCount++;
		if (System.currentTimeMillis() - this.lastCount > 1000) {
			this.lastCount = System.currentTimeMillis();
			this.currentFPS = this.frameCount;
			this.frameCount = 0;
		}
	}
	
	/**
	 * Gets the current frames per second (FPS) count.
	 * @return The current frames per second (FPS) count.
	 * @see #calculateFPS()
	 */
	public int getCurrentFPS() {
		return this.currentFPS;
	}
	
	/**
	 * Refreshes the {@link FPSCounter} instance by resetting the frames per
	 * second (FPS) count to 0, and setting the time of the last count to the
	 * current time.
	 * @deprecated Deprecated due to naming, use {@link #reset()} instead.
	 */
	public void refresh() {
		reset();
	}
	
	/**
	 * Resets the {@link FPSCounter} instance by resetting the frames per second
	 * (FPS) count to 0, and setting the time of the last count to the current
	 * time.
	 */
	public void reset() {
		this.frameCount = 0;
		this.lastCount = System.currentTimeMillis();
	}
}
