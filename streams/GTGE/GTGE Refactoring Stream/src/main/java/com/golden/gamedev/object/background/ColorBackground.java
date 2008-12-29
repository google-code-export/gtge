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

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

import com.golden.gamedev.object.Background;

/**
 * The {@link ColorBackground} class is a {@link Background} that renders to the
 * {@link #getClip() viewport} a single {@link Color}. <br />
 * <br />
 * <b><i>Warning: The {@link ColorBackground} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link ColorBackground}
 * class.</i></b>
 * 
 * @version 1.0
 * @since 0.2.3
 * @see Background
 * @see Color
 */
public class ColorBackground extends Background {
	
	/**
	 * A serialVersionUID for the {@link ColorBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 3668701023849676983L;
	
	/**
	 * The {@link Color} to display on the screen via this
	 * {@link ColorBackground} instance.
	 */
	private Color color;
	
	/**
	 * Creates a new {@link ColorBackground} instance with the given
	 * {@link Color}, width and height.
	 * @param color The {@link #getColor() background color} to use.
	 * @param width The width of the {@link ColorBackground} instance.
	 * @param height The height of the {@link ColorBackground} instance.
	 */
	public ColorBackground(Color color, int width, int height) {
		super(width, height);
		
		this.color = color;
	}
	
	/**
	 * Creates a new {@link ColorBackground} instance with the given
	 * {@link Color}, to be as large as the {@link Background#screen screen
	 * dimensions}.
	 */
	public ColorBackground(Color color) {
		super();
		
		this.color = color;
	}
	
	/**
	 * Gets the {@link Color} to display on the screen via this
	 * {@link ColorBackground} instance.
	 * @return The {@link Color} to display on the screen via this
	 *         {@link ColorBackground} instance.
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Sets the {@link Color} to display on the screen via this
	 * {@link ColorBackground} instance.
	 * @param color The {@link Color} to display on the screen via this
	 *        {@link ColorBackground} instance.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void render(Graphics2D g, int xbg, int ybg, int x, int y, int w, int h) {
		g.setColor(this.color);
		g.fillRect(x, y, w, h);
	}
}
