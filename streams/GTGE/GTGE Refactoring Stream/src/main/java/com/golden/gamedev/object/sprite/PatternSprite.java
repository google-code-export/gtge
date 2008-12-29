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

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.golden.gamedev.object.Sprite;

/**
 * The {@link PatternSprite} is a {@link Sprite} that
 * delegates to another {@link Sprite}, the "pattern", to provide it with the image that
 * this {@link PatternSprite} should display. <br />
 * <br />
 * A good use of the {@link PatternSprite} class is to create enemies that all
 * share the same animation sequence and timing. By using the
 * {@link PatternSprite} class, the enemies may have different locations and
 * collision detection information, but all of the enemies will display the same
 * images at the same time, as long as their {@link Sprite pattern} is kept
 * updated for the animation sequence. <br />
 * <br />
 * <b><i>Warning: The {@link PatternSprite} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link PatternSprite}
 * class.</i></b>
 * 
 * @version 1.0
 * @since 0.2.3
 * @see Sprite
 */
public class PatternSprite extends Sprite {
	
	/**
	 * A serialVersionUID for the {@link PatternSprite} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 1246202800220501766L;
	
	/**
	 * The {@link Sprite} to be delegated to for the rendering of images (the
	 * "pattern").
	 */
	private Sprite pattern;
	
	/**
	 * Creates a new {@link PatternSprite} instance with the specified
	 * {@link Sprite pattern}.
	 * @param pattern The {@link Sprite pattern} to delegate to for this
	 *        {@link PatternSprite} instance.
	 */
	public PatternSprite(Sprite pattern) {
		this(pattern, 0, 0);
	}
	
	/**
	 * Creates a new {@link PatternSprite} instance with the specified
	 * {@link Sprite pattern} and coordinate location.
	 * @param pattern The {@link Sprite pattern} to delegate to for this
	 *        {@link PatternSprite} instance.
	 * @param x The {@link #getX() x-coordinate} location to set for this
	 *        {@link PatternSprite} instance.
	 * @param y The {@link #getY() y-coordinate} location to set for this
	 *        {@link PatternSprite} instance.
	 */
	public PatternSprite(Sprite pattern, double x, double y) {
		super(x, y);
		
		this.pattern = pattern;
	}
	
	public BufferedImage getImage() {
		return this.pattern.getImage();
	}
	
	/**
	 * Gets the {@link Sprite} to be delegated to for the rendering of images
	 * (the "pattern").
	 * @return The {@link Sprite} to be delegated to for the rendering of images
	 *         (the "pattern").
	 */
	public Sprite getPattern() {
		return this.pattern;
	}
	
	/**
	 * Sets the {@link Sprite} to be delegated to for the rendering of images
	 * (the "pattern").
	 * @param pattern The {@link Sprite} to be delegated to for the rendering of
	 *        images (the "pattern").
	 */
	public void setPattern(Sprite pattern) {
		this.pattern = pattern;
	}
}
