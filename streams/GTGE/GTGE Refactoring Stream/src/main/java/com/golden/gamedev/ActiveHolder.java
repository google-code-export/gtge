/**
 * 
 */
package com.golden.gamedev;

/**
 * The {@link ActiveHolder} interface specifies an {@link Object} that holds a
 * value that determines whether or not it is active. The definition of what an
 * active {@link ActiveHolder} means is implementation-dependent, but typically
 * {@link ActiveHolder} instances will not perform certain actions if
 * {@link #isActive()} returns false that they would if {@link #isActive()}
 * returned true.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface ActiveHolder {
	
	/**
	 * Returns whether or not this {@link ActiveHolder} instance is active.
	 * @return True if this {@link ActiveHolder} instance is active, false
	 *         otherwise.
	 */
	boolean isActive();
	
	/**
	 * Sets whether or not this {@link ActiveHolder} instance is active.
	 * @param active True if this {@link ActiveHolder} instance is active, false
	 *        otherwise.
	 */
	void setActive(final boolean active);
}
