/**
 * 
 */
package com.golden.gamedev;

/**
 * The {@link UpdateAware} interface specifies an {@link Object} that can
 * {@link #update(long) update itself} based on the given long amount of time
 * elapsed, in milliseconds, from the last <i>virtual invocation</i> of the
 * {@link #update(long)} method.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface UpdateAware {
	
	/**
	 * Updates this {@link UpdateAware} instance based on the given long amount
	 * of time elapse, in milliseconds, from the last <i>virtual invocation</i>
	 * of the {@link #update(long)} method. <br />
	 * <br />
	 * The phrase <i>virtual invocation</i> means that the first invocation of
	 * this method should be treated as though the elapsed time occurred from a
	 * previous method call that had already happened before the actual
	 * invocation, so that the elapsedTime parameter would be the time elapsed
	 * since that first non-existent call and the first actual invocation of the
	 * method.
	 * 
	 * 
	 * @param elapsedTime The time, in milliseconds, since the last <i>virtual
	 *        invocation</i> of the {@link #update(long)} method.
	 */
	void update(long elapsedTime);
	
}
