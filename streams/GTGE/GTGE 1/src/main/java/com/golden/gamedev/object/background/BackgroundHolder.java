package com.golden.gamedev.object.background;

import com.golden.gamedev.object.Background;

/**
 * The {@link BackgroundHolder} interface specifies an {@link Object} that holds a mutable reference to a
 * {@link Background}.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public interface BackgroundHolder {
	
	/**
	 * Gets the (possibly null) {@link Background} instance contained within this {@link BackgroundHolder} instance.
	 * 
	 * @return The (possibly null) {@link Background} instance contained within this {@link BackgroundHolder} instance.
	 */
	Background getBackground();
	
	/**
	 * Sets the (possibly null) {@link Background} instance contained within this {@link BackgroundHolder} instance.
	 * 
	 * @param background
	 *            The (possibly null) {@link Background} instance contained within this {@link BackgroundHolder}
	 *            instance.
	 */
	void setBackground(Background background);
}
