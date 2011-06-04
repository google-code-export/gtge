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
 * The {@link FPSCounter} class counts the frames rendered for the current second, and keeps the value for retrieval of
 * the frames rendered for the last second for retrieval purposes.
 * 
 * @author MetroidFan2002 - Changed to be package-private to support the {@link SystemTimer} class only, and made the
 *         class final.
 * @author Paulus Tuerah - Original Author
 * @version 1.0
 * @since 1.0
 * @see SystemTimer For the use of this class.
 */
final class FPSCounter {
	
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
	FPSCounter() {
		super();
		reset();
	}
	
	/**
	 * Resets the current count of frames per second to 0 and the timestamp for counting frames to the current time.
	 */
	void reset() {
		frameCountForCurrentSecond = 0;
		clockStartTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculates the current frames per second by incrementing the frame count. If the time elapsed since the last time
	 * this method was invoked was greater than a second, the count is stored into the {@link #getCurrentFPS() frames
	 * per second interval} and the timestamp of the timer is reset to the current time to begin counting for the next
	 * second.
	 */
	void calculateFPS() {
		frameCountForCurrentSecond++;
		if (System.currentTimeMillis() - clockStartTime > 1000) {
			clockStartTime = System.currentTimeMillis();
			framesCountForLastSecond = frameCountForCurrentSecond;
			frameCountForCurrentSecond = 0;
		}
	}
	
	/**
	 * Gets the number of frames counted for the last second interval.
	 * 
	 * @return The number of frames counted for the last second interval.
	 */
	int getCurrentFPS() {
		return framesCountForLastSecond;
	}
}
