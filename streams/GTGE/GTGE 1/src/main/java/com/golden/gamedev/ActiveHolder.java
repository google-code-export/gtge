package com.golden.gamedev;

/**
 * The {@link ActiveHolder} interface specifies an {@link Object} that is able to be selectively enabled or disabled.
 * {@link ActiveHolder} instances that are not {@link #isActive() active} should be skipped over and not processed until
 * they are {@link #isActive() active} again. Typically, {@link ActiveHolder} instances are {@link #isActive() active}
 * by default, but this is not an absolute requirement.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public interface ActiveHolder {
	
	/**
	 * Gets whether or not this {@link ActiveHolder} instance is active, and should be processed.
	 * 
	 * @return True if this {@link ActiveHolder} instance is active andshould be processed, false otherwise.
	 */
	boolean isActive();
	
	/**
	 * Sets the active status of this {@link ActiveHolder} instance.
	 * 
	 * @param active
	 *            True if this {@link ActiveHolder} instance is {@link #isActive() active}, false otherwise.
	 */
	void setActive(final boolean active);
}
