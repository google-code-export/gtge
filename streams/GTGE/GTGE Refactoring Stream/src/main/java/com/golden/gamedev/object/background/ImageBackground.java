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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.golden.gamedev.object.Background;

/**
 * The {@link ImageBackground} class is a {@link Background} that renders a
 * single {@link BufferedImage image} to the {@link #getClip() viewport}.
 * 
 * @version 1.0
 * @since 0.2.3
 * @see Background
 * @see BufferedImage
 */
public class ImageBackground extends Background {
	
	/**
	 * A serialVersionUID for the {@link ImageBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = -4083512078848542717L;
	
	/**
	 * The {@link BufferedImage image} to display on the screen via this
	 * {@link ImageBackground} instance.
	 */
	private transient BufferedImage image;
	
	/**
	 * Creates a new {@link ImageBackground} instance with the given
	 * {@link BufferedImage image}, setting the {@link #getWidth() width} and
	 * {@link #getHeight() height} of this {@link ImageBackground} instance to
	 * be equal to the dimensions of the given {@link BufferedImage image}.
	 * @param image The {@link #getImage() image} to display on the screen via
	 *        this {@link ImageBackground} instance.
	 */
	public ImageBackground(BufferedImage image) {
		super(image.getWidth(), image.getHeight());
		
		this.image = image;
	}
	
	/**
	 * Creates a new {@link ImageBackground} instance with the given
	 * {@link BufferedImage image}, width and height.
	 * @param image The {@link #getImage() image} to display on the screen via
	 *        this {@link ImageBackground} instance.
	 * @param width The {@link #getWidth() width} of this
	 *        {@link ImageBackground} instance.
	 * @param height The {@link #getHeight() height} of this
	 *        {@link ImageBackground} instance.
	 */
	public ImageBackground(BufferedImage image, int width, int height) {
		super(width, height);
		
		this.image = image;
	}
	
	public void render(Graphics2D g, int xbg, int ybg, int x, int y, int w, int h) {
		g.drawImage(this.image, x, y, x + w, y + h, // destination (screen area)
		        xbg, ybg, xbg + w, ybg + h, // source (image area)
		        null);
	}
	
	/**
	 * Gets the {@link BufferedImage image} to display on the screen via this
	 * {@link ImageBackground} instance.
	 * @return The {@link BufferedImage image} to display on the screen via this
	 *         {@link ImageBackground} instance.
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * Sets the {@link BufferedImage image} to display on the screen via this
	 * {@link ImageBackground} instance. The {@link #setSize(int, int) size} of
	 * this {@link ImageBackground} instance is set to the size of the given
	 * {@link BufferedImage image}.
	 * @param image The {@link BufferedImage image} to display on the screen via
	 *        this {@link ImageBackground} instance.
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		
		this.setSize(image.getWidth(), image.getHeight());
	}
}
