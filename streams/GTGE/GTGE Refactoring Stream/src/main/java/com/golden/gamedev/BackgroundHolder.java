/**
 * 
 */
package com.golden.gamedev;

import com.golden.gamedev.object.background.Background;

/**
 * The {@link BackgroundHolder} interface defines an {@link Object} that can
 * store and retrieve a {@link Background} instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 */
public interface BackgroundHolder {
	
	/**
	 * Gets the (possibly null) {@link Background} instance stored in this
	 * {@link BackgroundHolder} instance.
	 * @return The (possibly null) {@link Background} instance stored in this
	 *         {@link BackgroundHolder} instance.
	 */
	Background getBackground();
	
	/**
	 * Sets the (possibly null) {@link Background} instance stored in this
	 * {@link BackgroundHolder} instance.
	 * @param background The (possibly null) {@link Background} instance stored
	 *        in this {@link BackgroundHolder} instance.
	 */
	void setBackground(final Background background);
}
