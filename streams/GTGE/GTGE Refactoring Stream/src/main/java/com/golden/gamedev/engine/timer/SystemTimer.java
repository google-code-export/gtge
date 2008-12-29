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
 * The {@link SystemTimer} class provides the standard implementation of the
 * {@link BaseTimer} interface that is used as the default {@link BaseTimer}
 * implementation in the Golden T Game Engine (GTGE) framework. <br />
 * <br />
 * The {@link SystemTimer} class uses {@link System#currentTimeMillis()} for
 * timing purposes, and its {@link #sleep()} method will execute a full sleep
 * cycle if its {@link Thread} has to sleep, regardless of any
 * {@link InterruptedException interrupted exceptions} it may encounter. <br />
 * <br />
 * <b><i>Warning: The {@link SystemTimer} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link SystemTimer}
 * class.</i></b>
 * 
 * @version 1.0
 * @since 0.2.3
 * @see BaseTimer
 */
public class SystemTimer implements BaseTimer {
	
	/**
	 * The requested frames per second (FPS) of the game.
	 * @see #getFPS()
	 * @see #setFPS(int)
	 */
	private int fps = 50;
	
	/**
     * The {@link FPSCounter} to use to calculate the {@link #getCurrentFPS()
     * current frames per second (FPS)}.
     */
    private FPSCounter fpsCounter = new FPSCounter();

	/**
     * Whether or not the current {@link SystemTimer} instance is running.
     */
    private boolean running;

	/**
	 * The time, in milliseconds, of the exit of the last call to
	 * {@link #sleep() sleep}.
	 * @see #sleep()
	 */
	private long startTime;
	
	/**
     * The total time, in milliseconds, that the {@link SystemTimer} instance
     * should {@link #sleep() sleep} for.
     * @see #sleep()
     */
    private long timeToSleep;

	/**
	 * Creates a new {@link SystemTimer} instance.
	 */
	public SystemTimer() {
		super();
	}
	
	public int getCurrentFPS() {
    	return this.fpsCounter.getCurrentFPS();
    }

	public int getFPS() {
    	return this.fps;
    }

	public long getTime() {
    	return System.currentTimeMillis();
    }

	public boolean isRunning() {
    	return this.running;
    }

	public void refresh() {
    	this.startTime = System.currentTimeMillis();
    }

	public void setFPS(int fps) {
    	this.fps = fps;
    	
    	if (this.running) {
    		this.startTimer();
    	}
    }

	public long sleep() {
    	long currentTime = System.currentTimeMillis();
    	
    	long timeDiff = currentTime - this.startTime;
    	long sleepTime = this.timeToSleep - timeDiff;
    	long lastSleepAttemptedTime = System.currentTimeMillis();
    	
    	while (sleepTime > 0) {
    		try {
    			Thread.sleep(sleepTime);
    		}
    		catch (InterruptedException e) {
    			// Intentionally blank
    		}
    		
    		long currentInstant = System.currentTimeMillis();
    		sleepTime -= currentInstant - lastSleepAttemptedTime;
    		lastSleepAttemptedTime = currentInstant;
    	}
    	
    	this.fpsCounter.calculateFPS();
    	
    	currentTime = System.currentTimeMillis();
    	long elapsedTime = currentTime - this.startTime;
    	this.startTime = currentTime;
    	
    	return elapsedTime;
    }

	public void startTimer() {
		if (this.running) {
			this.stopTimer();
		}
		this.running = true;
		
		this.timeToSleep = 1000 / this.fps;
		this.refresh();
		
		this.fpsCounter.reset();
	}
	
	public void stopTimer() {
		this.running = false;
	}
}
