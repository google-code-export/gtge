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

import com.golden.gamedev.Refreshable;

/**
 * A utility class to calculate timer frame per seconds (FPS) in convenient way.
 * <p>
 * 
 * How to use :
 * 
 * <pre>
 * FPSCounter counter;
 * // game loop
 * while (true) {
 * 	counter.getCurrentFPS(); // returns current fps
 * 	counter.calculateFPS(); // calculating fps
 * }
 * </pre>
 * 
 * As of 0.2.4, the {@link FPSCounter} class has been made final as it is not
 * suitable for extension by subclasses.
 */
public final class FPSCounter implements Refreshable {
	
	private long lastCount; // last time the fps is
	// counted
	private int currentFPS; // the real fps achieved
	private int frameCount;
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** CONSTRUCTOR *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Creates new <code>FPSCounter</code>. Effectively equivalent to using the
	 * {@link FPSCounter#FPSCounter(boolean)} constructor with the boolean
	 * argument set to false.
	 */
	public FPSCounter() {
		this(false);
	}
	
	/**
	 * Creates a new {@link FPSCounter} instance which may be initialized for
	 * counting immediately.
	 * @param initialize Whether or not to initialize this {@link FPSCounter}
	 *        instance for counting immediately. If true, this is the effective
	 *        equivalent of creating a new {@link FPSCounter} instance and
	 *        immediately invoking {@link #reset()} on it. If false, the first
	 *        call to {@link #calculateFPS()} will not apply for the current
	 *        second unless {@link #reset()} is invoked prior to
	 *        {@link #calculateFPS()}. This is illustrated in the following
	 *        table:
	 *        <table border="1">
	 *        <caption>True</caption>
	 *        <tr>
	 *        <th>Invocation #</th>
	 *        <th>Result</th>
	 *        </tr>
	 *        <tr>
	 *        <td>1</td>
	 *        <td>Counts for current second, getCurrentFPS returns 0</td>
	 *        </tr>
	 *        <tr>
	 *        <td>...</td>
	 *        <td>Counts for current second, getCurrentFPS returns 0</td>
	 *        </tr>
	 *        <tr>
	 *        <td>2</td>
	 *        <td>Counts for current next second, getCurrentFPS returns previous
	 *        second value.</td>
	 *        </tr>
	 *        </table>
	 * <br />
	 * <br />
	 *        <table border="1">
	 *        <caption>False</caption>
	 *        <tr>
	 *        <th>Invocation #</th>
	 *        <th>Result</th>
	 *        </tr>
	 *        <tr>
	 *        <td>1</td>
	 *        <td>Counts for no second, getCurrentFPS returns 0</td>
	 *        </tr>
	 *        <tr>
	 *        <td>2</td>
	 *        <td>Counts for current second, getCurrentFPS returns 1</td>
	 *        </tr>
	 *        <tr>
	 *        <td>...</td>
	 *        <td>Counts for current current second, getCurrentFPS returns 1</td>
	 *        </tr>
	 *        <tr>
	 *        <td>3</td>
	 *        <td>Counts for next second, getCurrentFPS returns previous second
	 *        value.</td>
	 *        </tr>
	 *        </table>
	 */
	public FPSCounter(boolean initialize) {
		super();
		if (initialize) {
			reset();
		}
	}
	
	/**
	 * Refresh the FPS counter, reset the fps to 0 and the timer counter to
	 * start counting from current time.
	 * @deprecated This method is deprecated in favor of {@link #reset()} and
	 *             will be removed in 0.2.5.
	 */
	public void refresh() {
		this.frameCount = 0;
		this.lastCount = System.currentTimeMillis();
	}
	
	/**
	 * The main method that calculating the frame per second.
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
	 * Returns current FPS.
	 * @return The current FPS.
	 * @see #calculateFPS()
	 */
	public int getCurrentFPS() {
		return this.currentFPS;
	}
	
	public void reset() {
		refresh();
	}
	
}
