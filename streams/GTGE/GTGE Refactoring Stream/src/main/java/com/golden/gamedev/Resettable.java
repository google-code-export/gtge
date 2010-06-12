/**
 * 
 */
package com.golden.gamedev;

/**
 * The {@link Resettable} interface specifies an {@link Object} that is capable
 * of resetting its state back to some default state. The default state that is
 * set is implementation-specific, but it typically clears variables that were
 * set so that this {@link Resettable} instance can be used like it was created
 * for the first time.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface Resettable {
	
	/**
	 * Resets this {@link Resettable} instance to some default state. The
	 * default state that is set is implementation-specific, but it typically
	 * clears variables that were set so that this {@link Resettable} instance
	 * can be used like it was created for the first time.
	 */
	void reset();
}
