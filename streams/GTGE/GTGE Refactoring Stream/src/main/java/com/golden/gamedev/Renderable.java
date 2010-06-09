/**
 * 
 */
package com.golden.gamedev;

import java.awt.Graphics2D;

/**
 * The {@link Renderable} interface specifies an {@link Object} that is able to
 * {@link #render(Graphics2D) render} itself to a non-null {@link Graphics2D}
 * instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface Renderable {
	
	/**
	 * Renders this {@link Renderable} instance to the given non-null
	 * {@link Graphics2D} instance.
	 * @param g The non-null {@link Graphics2D} instance to use to render this
	 *        {@link Renderable} instance.
	 */
	void render(Graphics2D g);
}
