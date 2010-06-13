/**
 * 
 */
package com.golden.gamedev.engine.timer;

import com.golden.gamedev.ActiveHolder;
import com.golden.gamedev.Resettable;
import com.golden.gamedev.engine.BaseTimer;

/**
 * The {@link Timer} interface specifies a {@link Resettable}
 * {@link ActiveHolder} extension that can execute an action based on whether or
 * not enough elapsed time has passed. <br />
 * <br />
 * Do not confuse the {@link Timer} interface with {@link BaseTimer}.
 * {@link BaseTimer} is specific to controlling loop execution, whereas
 * {@link Timer} provides a means to execute arbitrary actions after some delay
 * time has been met or exceeded.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Resettable
 * @see ActiveHolder
 * 
 */
public interface Timer extends ActiveHolder, Resettable {
	
	/**
	 * Updates this {@link Timer} instance's {@link #getCurrentTick() current
	 * tick} with the given time, and executes action logic if the
	 * {@link #getCurrentTick() current tick} plus the given elapsed time meets
	 * or exceeds the {@link #getDelay() delay}, returning true if the action
	 * was executed, false otherwise. <br />
	 * <br />
	 * Note that this method should do nothing if {@link #isActive()} returns
	 * false.
	 * 
	 * @param elapsedTime The long value representing the elapsed time that has
	 *        passed since the last method invocation of {@link #action(long)}
	 *        (if {@link #action(long)} had never been invoked, this time should
	 *        be assumed to be offset from 0).
	 * @return True if the {@link Timer} instance's action has executed based on
	 *         the given elapsed time, false otherwise.
	 */
	boolean action(long elapsedTime);
	
	/**
	 * Creates and returns a non-null {@link Timer} instance that deep copies
	 * the given non-null {@link Timer} instance to copy.
	 * @param toCopy The non-null {@link Timer} instance to copy.
	 * @return The non-null {@link Timer} insance that deep copies the given
	 *         {@link Timer} instance, typically a self-reference to use for
	 *         chaining method invocations.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link Timer} to copy was null.
	 */
	Timer copy(final Timer toCopy);
	
	/**
	 * Gets the delay of this timer in milliseconds, specifying the value at
	 * which if the {@link #getCurrentTick() current tick} plus the elapsed time
	 * specified in {@link #action(long)} meets or exceeds, then
	 * {@link #action(long)} will invoke its action logic and return true,
	 * storing a new value into the {@link #getCurrentTick() current tick} for
	 * the next {@link #action(long)} invocation.
	 * @return The delay of this timer in milliseconds.
	 */
	long getDelay();
	
	/**
	 * Sets the delay of this timer in milliseconds, specifying the value at
	 * which if the {@link #getCurrentTick() current tick} plus the elapsed time
	 * specified in {@link #action(long)} meets or exceeds, then
	 * {@link #action(long)} will invoke its action logic and return true,
	 * storing a new value into the {@link #getCurrentTick() current tick} for
	 * the next {@link #action(long)} invocation.
	 * @param delay The delay of this timer in milliseconds.
	 */
	void setDelay(final long delay);
	
	/**
	 * The current time that this {@link Timer} instance holds, which is in the
	 * span of [0-{@link #getDelay()}).
	 * @return The current time that this {@link Timer} instance holds, which is
	 *         in the span of [0-{@link #getDelay()}).
	 */
	long getCurrentTick();
	
	/**
	 * Gets whether or not {@link #action(long)} will ever execute and return
	 * true if enough time has passed.
	 * @return True if {@link #action(long)} may execute its action and return
	 *         true if enough time has passed, false otherwise.
	 */
	public boolean isActive();
	
	/**
	 * Sets whether or not {@link #action(long)} will ever execute and return
	 * true if enough time has passed.
	 * @param active True if {@link #action(long)} may execute its action and
	 *        return true if enough time has passed, false otherwise.
	 */
	public void setActive(final boolean active);
	
	/**
	 * Resets the {@link #getCurrentTick() current tick} to 0.
	 */
	public void reset();
}
