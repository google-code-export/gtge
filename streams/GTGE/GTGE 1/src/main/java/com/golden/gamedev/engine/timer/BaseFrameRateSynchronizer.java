package com.golden.gamedev.engine.timer;

import org.apache.commons.lang.Validate;

import com.golden.gamedev.engine.BaseTimer;

/**
 * The {@link BaseFrameRateSynchronizer} class provides a base to easily extend in order to implement the
 * {@link BaseTimer} interface.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * @see BaseTimer
 * 
 */
public abstract class BaseFrameRateSynchronizer implements BaseTimer {
	
	/**
	 * The requested number of frames per second.
	 */
	private int fps;
	
	/**
	 * Whether or not this {@link BaseFrameRateSynchronizer} instance is running.
	 */
	private boolean running;
	
	/**
	 * Creates a new {@link BaseFrameRateSynchronizer} instance with the default requested frames per second value of 50
	 * frames per second.
	 */
	public BaseFrameRateSynchronizer() {
		this(50);
	}
	
	/**
	 * Creates a new {@link BaseFrameRateSynchronizer} instance with the requested frames per second value.
	 * 
	 * @param fps
	 *            The requested number of frames per second.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the requested number of frames per second is less than
	 *             or equal to 0.
	 */
	public BaseFrameRateSynchronizer(final int fps) {
		super();
		setFps(fps);
	}
	
	@Override
	public final int getFps() {
		return fps;
	}
	
	@Override
	public final void setFps(final int fps) {
		Validate.isTrue(fps >= 1, "FPS must be greater than or equal to 1!");
		this.fps = fps;
	}
	
	@Override
	public final void startTimer() {
		reset();
		running = true;
	}
	
	@Override
	public final void stopTimer() {
		running = false;
	}
	
	@Override
	public final boolean isRunning() {
		return running;
	}
}
