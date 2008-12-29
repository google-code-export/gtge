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
package com.golden.gamedev.object.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.golden.gamedev.object.Sprite;

/**
 * The {@link InvisibleSprite} class is a {@link Sprite} that will not render
 * its {@link #getImage() image} by default, but is useful for simulating a
 * collision against an invisible block. <br />
 * <br />
 * Since version 1.1 of this class, the {@link #setVisible(boolean)} method may
 * now be invoked to re-enable the rendering of the {@link InvisibleSprite}
 * instance, for example, when a power up block is hit, the power up block then
 * displays its image and is no longer invisible to the user. <br />
 * <br />
 * <b><i>Warning: The {@link InvisibleSprite} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link InvisibleSprite}
 * class.</i></b>
 * 
 * @since 0.2.3
 * @version 1.1
 * @see Sprite
 */
public class InvisibleSprite extends Sprite {
	
	/**
	 * A serialVersionUID for the {@link InvisibleSprite} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 5126744876218108841L;
	
	/**
	 * Whether or not this {@link InvisibleSprite} instance is visible, that is,
	 * whether or not it will {@link #render(Graphics2D) render} to the
	 * {@link Graphics2D graphics instance}.
	 * @see #render(Graphics2D)
	 * @see #render(Graphics2D, int, int)
	 */
	private boolean visible = false;
	
	/**
	 * Creates a new {@link InvisibleSprite} instance with the
	 * {@link Sprite#Sprite()} constructor, set to be invisible by default.
	 * @see Sprite#Sprite()
	 */
	public InvisibleSprite() {
		super();
	}
	
	/**
	 * Creates a new {@link InvisibleSprite} instance with the
	 * {@link Sprite#Sprite(BufferedImage)} constructor, set to be invisible by
	 * default.
	 * @param image The {@link #getImage() image} of this
	 *        {@link InvisibleSprite} to render if {@link #setVisible(boolean)
	 *        set to be visible}.
	 * @see Sprite#Sprite(BufferedImage)
	 */
	public InvisibleSprite(BufferedImage image) {
		super(image);
	}
	
	/**
	 * Creates a new {@link InvisibleSprite} instance with the
	 * {@link Sprite#Sprite(BufferedImage, double, double)} constructor, set to
	 * be invisible by default.
	 * @param image The {@link #getImage() image} of this
	 *        {@link InvisibleSprite} to render if {@link #setVisible(boolean)
	 *        set to be visible}.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @see Sprite#Sprite(BufferedImage, double, double)
	 */
	public InvisibleSprite(BufferedImage image, double x, double y) {
		super(image, x, y);
	}
	
	/**
	 * Creates a new {@link InvisibleSprite} instance with the
	 * {@link Sprite#Sprite(double, double)} constructor, set to be invisible by
	 * default. This {@link InvisibleSprite} instance will not be associated
	 * with an {@link #getImage() image} unless the
	 * {@link #setImage(java.awt.image.BufferedImage)} method is invoked after
	 * construction.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @see Sprite#Sprite(double, double)
	 */
	public InvisibleSprite(double x, double y) {
		super(x, y);
	}
	
	/**
	 * Creates a new {@link InvisibleSprite} instance with the specified
	 * position and size, set to be invisible by default. This
	 * {@link InvisibleSprite} instance will not be associated with an
	 * {@link #getImage() image} unless the
	 * {@link #setImage(java.awt.image.BufferedImage)} method is invoked after
	 * construction.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        {@link InvisibleSprite} instance.
	 * @param width The {@link #getWidth() width} of this
	 *        {@link InvisibleSprite} instance.
	 * @param height The {@link #getHeight() height} of this
	 *        {@link InvisibleSprite} instance.
	 */
	public InvisibleSprite(double x, double y, int width, int height) {
		super(x, y);
		
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics2D g, int x, int y) {
		if (this.isVisible()) {
			super.render(g, x, y);
		}
	}
	
	/**
	 * Gets whether or not this {@link InvisibleSprite} instance is visible,
	 * that is, whether or not it will {@link #render(Graphics2D) render} to the
	 * {@link Graphics2D graphics instance}.
	 * @return Whether or not this {@link InvisibleSprite} instance is visible,
	 *         that is, whether or not it will {@link #render(Graphics2D)
	 *         render} to the {@link Graphics2D graphics instance}.
	 * @see #render(Graphics2D)
	 * @see #render(Graphics2D, int, int)
	 */
	public boolean isVisible() {
		return this.visible;
	}
	
	/**
	 * Sets whether or not this {@link InvisibleSprite} instance is visible,
	 * that is, whether or not it will {@link #render(Graphics2D) render} to the
	 * {@link Graphics2D graphics instance}.
	 * @param visible Whether or not this {@link InvisibleSprite} instance is
	 *        visible, that is, whether or not it will
	 *        {@link #render(Graphics2D) render} to the {@link Graphics2D
	 *        graphics instance}.
	 * @see #render(Graphics2D)
	 * @see #render(Graphics2D, int, int)
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
