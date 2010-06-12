/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.object.background;

// JFC
import java.awt.Color;
import java.awt.Graphics2D;

import com.golden.gamedev.object.Background;

/**
 * The very basic background type that only fill the background view port with a
 * single color.
 * <p>
 * 
 * This type of background use a fixed memory size. Memory used by small size
 * color background (e.g: 1 x 1) with an extremely large size color background
 * (e.g: 100,000,000 x 100,000,000) is equal.
 * 
 * <br />
 * <br />
 * As of 0.2.4, the {@link ColorBackground} class is final - it is not suitable
 * for subclassing. Also, previous versions of the {@link ColorBackground} had a
 * side-effect when invoking
 * {@link #render(Graphics2D, int, int, int, int, int, int)} that set the
 * {@link Graphics2D#setColor(Color) color} of the {@link Graphics2D} instance
 * to the {@link Color} contained in this {@link ColorBackground} instance -
 * this side effect has been removed. Users who wish for the
 * {@link Graphics2D#setColor(Color) color} to be set to the {@link Color}
 * contained in this {@link ColorBackground} instance are responsible for
 * manually invoking the operation directly, for example: <br />
 * <br />
 * 
 * <pre>
 * ColorBackground background = new ColorBackground// ... initialize background
 * 
 * public void render(Graphics2D g) {
 * // rendering...
 * background.render(g);
 * g.setColor(background.getColor());
 * // Now the color of the graphics context is set to the color of the background.
 * </pre>
 * 
 * Previously, users who did not want this side-effect to occur had to use this
 * strategy, which takes more effort than simply setting the color afterwards: <br />
 * <br />
 * 
 * <pre>
 * ColorBackground background = new ColorBackground// ... initialize background
 * 
 * public void render(Graphics2D g) {
 * // rendering...
 * Color oldColor = g.getColor();
 * background.render(g);
 * g.setColor(oldColor);
 * // Now the color of the graphics context is *not* set to the color of the background.
 * 
 * </pre>
 * 
 * This is no longer needed, making code that uses the {@link ColorBackground}
 * class easier to use via removing the side-effect.
 * 
 */
public final class ColorBackground extends Background {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668701023849676983L;
	private Color color;
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** CONSTRUCTOR *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Creates new <code>ColorBackground</code> with specified size.
	 */
	public ColorBackground(Color bgColor, int w, int h) {
		super(w, h);
		this.setColor(bgColor);
	}
	
	/**
	 * Creates new <code>ColorBackground</code> as large as screen dimension.
	 */
	public ColorBackground(Color bgColor) {
		this(bgColor, Background.screen.width, Background.screen.height);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************** BGCOLOR GET / SET ****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns this background color.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the background color.
	 */
	public void setColor(Color bgColor) {
		this.color = bgColor;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************ RENDER BACKGROUND ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	public void render(Graphics2D g, int xbg, int ybg, int x, int y, int w, int h) {
		final Color previousColor = g.getColor();
		g.setColor(this.color);
		g.fillRect(x, y, w, h);
		g.setColor(previousColor);
	}
	
}
