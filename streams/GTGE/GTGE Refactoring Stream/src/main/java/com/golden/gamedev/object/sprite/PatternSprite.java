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

// JFC
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.golden.gamedev.BufferedImageHolder;
import com.golden.gamedev.object.Sprite;

/**
 * The {@link PatternSprite} class provides a {@link Sprite} implementation that
 * retrieves its {@link BufferedImage} to {@link #render(Graphics2D, int, int)
 * render} via a non-null {@link Serializable} {@link BufferedImageHolder}
 * instance. <br />
 * <br />
 * The motivation for using the {@link PatternSprite} class is to make a series
 * of independently-moving {@link Sprite} instances that share the same
 * {@link BufferedImage} sequence when rendering, for instance, rendering the
 * same enemies in the same sequence on screen, but in multiple
 * physically-different locations, one for each enemy of the same type. <br />
 * <br />
 * If a {@link Sprite} created with the same images as another is started at a
 * different time than that {@link Sprite}, although it will have the same
 * sequence of images, they may be played back in a different sequence.
 * {@link PatternSprite} instances synchronize the sequence by delegating a
 * shared {@link Serializable} {@link BufferedImageHolder} to retrieve the same
 * {@link BufferedImage} sequence each time. Updating all {@link PatternSprite}
 * instances that share the same {@link Serializable}
 * {@link BufferedImageHolder} is then a matter of simply updating the
 * {@link Serializable} {@link BufferedImageHolder} instance as appropriate.
 * 
 * <br />
 * <br />
 * The given {@link BufferedImageHolder} must be {@link Serializable} because it
 * will be stored into this {@link PatternSprite} instance, which, since it is a
 * {@link Sprite}, is {@link Serializable} and must have its
 * {@link BufferedImageHolder} instance be {@link Serializable}. An
 * {@link IllegalArgumentException} will be thrown if a {@link PatternSprite} is
 * attempted to be constructed with a non-{@link Serializable}
 * {@link BufferedImageHolder} instance. <br />
 * <br />
 * 
 * Note that as of 0.2.4, {@link PatternSprite} instances do not need to store a
 * delegate {@link Sprite} instance, therefore {@link #getPattern()} and
 * {@link #setPattern(Sprite)} are deprecated. While it is still possible to
 * {@link #setPattern(Sprite) set} a {@link Sprite} as a delegate
 * {@link BufferedImageHolder} instance, in 0.2.5 this functionality will be
 * removed and {@link PatternSprite} will be immutable after construction. Use
 * {@link #getImageHolder()} to retrieve the (mutable)
 * {@link BufferedImageHolder} instance to update instead of setting another
 * instance into the {@link PatternSprite}. <br />
 * <br />
 * 
 * As of 0.2.4, this class has been marked as final - it is not suitable to
 * being extended by subclasses.
 * 
 */
public final class PatternSprite extends Sprite {
	
	/**
	 * Serializable ID of the {@link PatternSprite} class.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The non-null {@link Serializable} {@link BufferedImageHolder} instance to
	 * use to retrieve the {@link BufferedImageHolder#getImage() image} to
	 * {@link PatternSprite#render(Graphics2D, int, int) render}.
	 */
	private BufferedImageHolder imageHolder;
	
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
	 * Creates a new {@link PatternSprite} instance with the given non-null
	 * {@link Serializable} {@link BufferedImageHolder} instance, starting
	 * x-coordinate, and starting y-coordinate.
	 * @param imageHolder The non-null {@link Serializable}
	 *        {@link BufferedImageHolder} instance to use to retrieve the
	 *        {@link BufferedImageHolder#getImage() image} to
	 *        {@link PatternSprite#render(Graphics2D, int, int) render}.
	 * @param x The starting {@link #getX() x-coordinate} of this
	 *        {@link PatternSprite} instance.
	 * @param y The starting {@link #getY() y-coordinate} of this
	 *        {@link PatternSprite} instance.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link BufferedImageHolder} instance is null.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given
	 *         {@link BufferedImageHolder} instance is not {@link Serializable}.
	 */
	public PatternSprite(BufferedImageHolder imageHolder, double x, double y) {
		super(imageHolder.getImage(), x, y);
		validatePatternIsSerializable(imageHolder);
		this.imageHolder = imageHolder;
	}
	
	/**
	 * Creates a new {@link PatternSprite} instance with the given non-null
	 * {@link Serializable} {@link BufferedImageHolder} instance, starting with
	 * 0 for both its {@link #getX() x} and {@link #getY() y} coordinates.
	 * @param imageHolder The non-null {@link Serializable}
	 *        {@link BufferedImageHolder} instance to use to retrieve the
	 *        {@link BufferedImageHolder#getImage() image} to
	 *        {@link PatternSprite#render(Graphics2D, int, int) render}.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link BufferedImageHolder} instance is null.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given
	 *         {@link BufferedImageHolder} instance is not {@link Serializable}.
	 */
	public PatternSprite(BufferedImageHolder imageHolder) {
		this(imageHolder, 0, 0);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************* RENDER THE PATTERN ****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	public void render(Graphics2D g, int x, int y) {
		g.drawImage(this.imageHolder.getImage(), x, y, null);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * **************************** THE PATTERN ********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Validates that the given {@link BufferedImageHolder} instance is
	 * {@link Serializable} so that it may be stored via this
	 * {@link PatternSprite} instance, which is also {@link Serializable}.
	 * @param imageHolder The {@link BufferedImageHolder} instance to validate.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given
	 *         {@link BufferedImageHolder} instance is not {@link Serializable}.
	 */
	private static void validatePatternIsSerializable(BufferedImageHolder imageHolder) {
		if (!(imageHolder instanceof Serializable)) {
			throw new IllegalArgumentException(
			        "The given BufferedImageHolder instance must be serializable in order to be stored in a PatternSprite!");
		}
	}
	
	/**
	 * Returns the pattern sprite associates with this sprite.
	 * @deprecated As of 0.2.4, the {@link BufferedImageHolder} stored within
	 *             this {@link PatternSprite} instance may not be a
	 *             {@link Sprite} - use {@link #getImageHolder()} instead.
	 */
	public Sprite getPattern() {
		return (Sprite) this.imageHolder;
	}
	
	/**
	 * Sets the pattern of this sprite.
	 * @deprecated This method is deprecated - as of 0.2.5, the
	 *             {@link PatternSprite} class will be immutable and will not
	 *             support setting a new {@link BufferedImageHolder} instance
	 *             after it is constructed.
	 */
	public void setPattern(Sprite pattern) {
		this.imageHolder = pattern;
	}
	
	/**
	 * Gets the non-null {@link Serializable} {@link BufferedImageHolder}
	 * instance to use to retrieve the {@link BufferedImageHolder#getImage()
	 * image} to {@link PatternSprite#render(Graphics2D, int, int) render}.
	 * @return The non-null {@link Serializable} {@link BufferedImageHolder}
	 *         instance to use to retrieve the
	 *         {@link BufferedImageHolder#getImage() image} to
	 *         {@link PatternSprite#render(Graphics2D, int, int) render}.
	 */
	public BufferedImageHolder getImageHolder() {
		return imageHolder;
	}
}
