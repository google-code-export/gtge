/**
 * 
 */
package com.golden.gamedev.engine.timer;

import com.golden.gamedev.ActionExecutor;

/**
 * The {@link ThrowawayTimer} class provides a {@link Timer} instance that does
 * not implement {@link java.io.Serializable}, and thus its
 * {@link ActionExecutor} instance does not need to be
 * {@link java.io.Serializable}. This class is useful when a particular thread
 * or non-{@link java.io.Serializable} instance needs to execute an action at
 * certain periods, thus the provided {@link ActionExecutor} can be used. <br />
 * <br />
 * It is recommended that the {@link ThrowawayTimer} be used in preference to
 * the {@link SerializableTimer} class wherever possible, as it makes little
 * sense to store the values of a repeating {@link Timer} to a bytestream - the
 * {@link SerializableTimer} class is provided mainly for convenience and
 * backwards-compatibility and may be removed in a future release. <br />
 * <br />
 * For convenience, the provided {@link Timer} implementations in GTGE are also
 * {@link ActionExecutor} instances which delegate their
 * {@link ActionExecutor#execute() execution} to their configured
 * {@link ActionExecutor} instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public final class ThrowawayTimer implements Timer, ActionExecutor {
	
	/**
	 * The non-null {@link TimerDelegate} instance to actually perform the
	 * tracking of the state of the {@link Timer} interface implementation.
	 */
	private final TimerDelegate delegate;
	
	/**
	 * The non-null {@link ActionExecutor} instance to use to
	 * {@link ActionExecutor#execute() execute} an action before
	 * {@link #action(long)} returns true.
	 */
	private final ActionExecutor executor;
	
	/**
	 * Creates a new {@link ThrowawayTimer} instance with the given
	 * {@link #getDelay() delay} and non-null {@link ActionExecutor} instance to
	 * use to {@link ActionExecutor#execute() execute} an action before
	 * {@link #action(long)} returns true.
	 * @param delay The {@link #getDelay() delay}, in milliseconds, of this
	 *        {@link ThrowawayTimer} instance.
	 * @param executor The non-null {@link ActionExecutor} instance to use to
	 *        {@link ActionExecutor#execute() execute} an action before
	 *        {@link #action(long)} returns true.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given
	 *         {@link ActionExecutor} instance was null.
	 */
	public ThrowawayTimer(final long delay, final ActionExecutor executor) {
		super();
		if (executor == null) {
			throw new IllegalArgumentException(
			        "The provided ActionExecutor instance may not be null.");
		}
		this.delegate = new TimerDelegate(delay);
		this.executor = executor;
	}
	
	public boolean action(long elapsedTime) {
		if (delegate.action(elapsedTime)) {
			executor.execute();
			return true;
		}
		return false;
	}
	
	public long getCurrentTick() {
		return delegate.getCurrentTick();
	}
	
	public long getDelay() {
		return delegate.getDelay();
	}
	
	public boolean isActive() {
		return delegate.isActive();
	}
	
	public void reset() {
		delegate.reset();
	}
	
	public void setActive(boolean active) {
		delegate.setActive(active);
	}
	
	public void setDelay(long delay) {
		delegate.setDelay(delay);
	}
	
	public void execute() {
		executor.execute();
	}
	
	public final Timer copy(final Timer toCopy) {
		delegate.setActive(toCopy.isActive());
		delegate.setDelay(toCopy.getDelay());
		delegate.setCurrentTick(toCopy.getCurrentTick());
		return this;
	}
}
