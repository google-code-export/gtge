/**
 * 
 */
package com.golden.gamedev.object.collision;

import com.golden.gamedev.Positionable;

/**
 * The {@link Dimensionable} interface specifies a {@link Positionable} instance
 * that also has a {@link #getWidth() width} and {@link #getHeight() height}.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Positionable
 * 
 */
public interface Dimensionable extends Positionable {
	
	/**
	 * Returns the width of this {@link Dimensionable} instance.
	 */
	int getWidth();
	
	/**
	 * Sets the width of this {@link Dimensionable} instance.
	 * @param width The width of this {@link Dimensionable} instance.
	 * @since 0.2.4
	 */
	void setWidth(final int width);
	
	/**
	 * Returns the height of this {@link Dimensionable} shape.
	 */
	int getHeight();
	
	/**
	 * Sets the height of this {@link Dimensionable} instance.
	 * @param height The height of this {@link Dimensionable} instance.
	 * @since 0.2.4
	 */
	void setHeight(final int height);
	
}
