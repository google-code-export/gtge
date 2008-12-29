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

import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Sprite;

/**
 * The {@link AdvanceSprite} class is an {@link AnimatedSprite} that has support
 * for storing a {@link Sprite sprite's} {@link #getDirection() direction} and
 * {@link #getStatus() status}, and the ability to change the default sequence
 * of animation {@link #getImages() frames} that the {@link AnimatedSprite}
 * provides. <br />
 * <br />
 * To change the default sequence of animation that the {@link AnimatedSprite}
 * provides, the method {@link #setAnimationFrame(int[])} is provided. See its
 * documentation for more details. <br />
 * <br />
 * Although the {@link AdvanceSprite} class can be used directly and its
 * {@link #getDirection() direction} and {@link #getStatus() status} set and
 * retrieved, the {@link AdvanceSprite} class should be subclassed if this
 * functionality is needed. Upon setting either the {@link #setDirection()
 * direction}, {@link #setStatus() status}, or
 * {@link #setStatusAndDirection(int, int) both}, the method {@link
 * animationChanged(int, int, int, int)} is invoked if one of the two changed,
 * and this method should be overridden to handle changes to the
 * {@link AdvanceSprite sprite's} state based on a {@link #getDirection()
 * direction} or {@link #getStatus() status} change.<br />
 * <br />
 * <b><i>Warning: The {@link AdvanceSprite} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link AdvanceSprite}
 * class.</i></b>
 * 
 * @version 1.1
 * @since 0.2.3
 * @see AnimatedSprite
 * @see #setAnimationFrame(int[])
 * @see #setStatusAndDirection(int, int)
 * @see #animationChanged(int, int, int, int)
 */
public class AdvanceSprite extends AnimatedSprite {
	
	/**
	 * A serialVersionUID for the {@link AdvanceSprite} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 6163338876818201162L;
	
	/**
	 * The sequence of animation frames to use.
	 * @see #getImage()
	 */
	private int[] animationFrame;
	
	/**
	 * The current status of this {@link AdvanceSprite} instance.
	 * @see #animationChanged(int, int, int, int)
	 */
	private int status = -1;
	
	/**
	 * The current direction of this {@link AdvanceSprite} instance.
	 * @see #animationChanged(int, int, int, int)
	 */
	private int direction = -1;
	
	/**
	 * Creates a new {@link AdvanceSprite} instance with the
	 * {@link AnimatedSprite#AnimatedSprite()} constructor.
	 * @see AnimatedSprite#AnimatedSprite()
	 */
	public AdvanceSprite() {
		super();
	}
	
	/**
	 * Creates a new {@link AdvanceSprite} instance with the
	 * {@link AnimatedSprite#AnimatedSprite(BufferedImage[])} constructor.
	 * @param image The {@link #getImages() images} to use in this
	 *        {@link AdvanceSprite} instance's animation sequence.
	 * @see AnimatedSprite#AnimatedSprite(BufferedImage[])
	 */
	public AdvanceSprite(BufferedImage[] image) {
		super(image);
	}
	
	/**
	 * Creates a new {@link AdvanceSprite} instance with the
	 * {@link AnimatedSprite#AnimatedSprite(BufferedImage[], double, double)}
	 * constructor.
	 * 
	 * @param image The {@link #getImages() images} to use in this
	 *        {@link AdvanceSprite} instance's animation sequence.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        {@link AdvanceSprite} instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        {@link AdvanceSprite} instance.
	 * @see AnimatedSprite#AnimatedSprite(BufferedImage[], double, double)
	 */
	public AdvanceSprite(BufferedImage[] image, double x, double y) {
		super(image, x, y);
	}
	
	/**
	 * Creates a new {@link AdvanceSprite} instance with the
	 * {@link AnimatedSprite#AnimatedSprite(double, double)} constructor.
	 * 
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        {@link AdvanceSprite} instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        {@link AdvanceSprite} instance.
	 * @see AnimatedSprite#AnimatedSprite(double, double)
	 */
	public AdvanceSprite(double x, double y) {
		super(x, y);
	}
	
	/**
	 * Sets an array of integers that overrides the default animation sequence
	 * inherited from {@link AnimatedSprite}. <br />
	 * <br />
	 * By default, {@link AdvanceSprite} has a null animation array, which means
	 * that it animates in the {@link #getImages() frame sequence} sequentially
	 * as {@link AnimatedSprite} does, as in frame 0, 1, 2, ...
	 * {@link #getImages() images.length} - 1. The animationFrame array
	 * overrides this sequence, and associates the current frame from the
	 * default sequence with a possibly different frame. <br />
	 * <br />
	 * Setting this sequence to {1, 3, 0, 2}, for example, makes the first frame
	 * (0) correspond to frame 1, the second frame (1) correspond to frame 3,
	 * etc. When the animationFrame is set, the
	 * {@link #getStartAnimationFrame() starting animation frame} is set to 0
	 * and the {@link #getFinishAnimationFrame() ending animation frame} is set
	 * to the length of the animationFrame array minus 1, to correspond with its
	 * last integer index.
	 */
	public void setAnimationFrame(int[] animationFrame) {
		this.animationFrame = animationFrame;
		this.resetAnimationFrameReferences();
	}
	
	/**
	 * Resets the animation frame references by calling
	 * {@link #setAnimationFrame(int, int)} with 0 as the first parameter, and
	 * {@link #retrieveDefaultEndingFrame()} for the second parameter. Marked
	 * final to always allow for calling 0 as the first parameter, if this is
	 * not desired, override the {@link #setAnimationFrame(int[])} and
	 * {@link #setImages(BufferedImage[])} methods to prevent calling this
	 * method.
	 */
	protected final void resetAnimationFrameReferences() {
		this.setAnimationFrame(0, retrieveDefaultEndingFrame());
	}
	
	/**
	 * Returns the default {@link #setFrame(int) ending frame} for the
	 * {@link #setAnimationFrame(int[])} method to use when setting the
	 * {@link #setAnimationFrame(int, int) ending animation frame}. Defaults to
	 * using the {@link #animationFrame animation frame} length if non-null,
	 * then the {@link #getImages() images length} if non-null, then defaults to
	 * 0 if both are null.
	 * 
	 * @return The default {@link #setFrame(int) ending frame} for the
	 *         {@link #setAnimationFrame(int[])} method to use when setting the
	 *         {@link #setAnimationFrame(int, int) ending animation frame}.
	 */
	protected int retrieveDefaultEndingFrame() {
		return (this.animationFrame != null) ? (this.animationFrame.length - 1)
		        : ((this.getImages() != null) ? this.getImages().length - 1 : 0);
	}
	
	/**
	 * Returns sprite animation frame or null if the sprite use standard
	 * animation frame.
	 */
	public int[] getAnimationFrame() {
		return this.animationFrame;
	}
	
	public BufferedImage getImage() {
		return (this.animationFrame == null) ? super.getImage() : this
		        .getImage(this.animationFrame[this.getFrame()]);
	}
	
	public void setImages(BufferedImage[] image) {
		super.setImages(image);
		resetAnimationFrameReferences();
	}
	
	/**
	 * Notifies the {@link AdvanceSprite} instance that either the
	 * {@link #getDirection() direction} or {@link #getStatus() status} has
	 * changed, which may need to result in updates to the {@link AdvanceSprite}
	 * instance's internal state. <br />
	 * <br />
	 * By default, this method does nothing. Subclasses may override this method
	 * to execute specific behavior when the {@link #getDirection() direction}
	 * or {@link #getStatus() status} changes.
	 * 
	 * @param oldStatus The old {@link #getStatus() status} of this
	 *        {@link AdvanceSprite} instance.
	 * @param oldDirection The old {@link #getDirection() direction} of this
	 *        {@link AdvanceSprite} instance.
	 * @param status The current {@link #getStatus() status} of this
	 *        {@link AdvanceSprite} instance.
	 * @param direction The current {@link #getDirection() direction} of this
	 *        {@link AdvanceSprite} instance.
	 * @see #setStatusAndDirection(int, int)
	 */
	protected void animationChanged(int oldStatus, int oldDirection, int status, int direction) {
		// Intentionally blank
	}
	
	/**
	 * Sets the current direction of this {@link AdvanceSprite} instance. This
	 * method is a shortcut method for calling
	 * {@link #setStatusAndDirection(int, int)} with {@link #getStatus() the
	 * AdvanceSprite's status} as the {@link #getStatus() status} to set.
	 * @param direction The current direction of this {@link AdvanceSprite}
	 *        instance.
	 * @see #setStatusAndDirection(int, int)
	 */
	public void setDirection(int direction) {
		setStatusAndDirection(this.status, direction);
	}
	
	/**
	 * Gets the current direction of this {@link AdvanceSprite} instance.
	 * @return The current direction of this {@link AdvanceSprite} instance.
	 */
	public int getDirection() {
		return this.direction;
	}
	
	/**
	 * Sets the current status of this {@link AdvanceSprite} instance. This
	 * method is a shortcut method for calling
	 * {@link #setStatusAndDirection(int, int)} with {@link #getDirection() the
	 * AdvanceSprite's direction} as the {@link #getDirection() direction} to
	 * set.
	 * @param status The current status of this {@link AdvanceSprite} instance.
	 * @see #setStatusAndDirection(int, int)
	 */
	public void setStatus(int status) {
		setStatusAndDirection(status, this.direction);
	}
	
	/**
	 * Gets the current status of this {@link AdvanceSprite} instance.
	 * @return The current status of this {@link AdvanceSprite} instance.
	 */
	public int getStatus() {
		return this.status;
	}
	
	/**
	 * Sets both the {@link AdvanceSprite} instance's {@link #getStatus()
	 * status} and {@link #getDirection() direction} at once. If either the
	 * {@link #getStatus() status} or {@link #getDirection() direction} has
	 * changed, the {@link #animationChanged(int, int, int, int)} method is
	 * invoked.
	 * 
	 * @param stat The status of this {@link AdvanceSprite} instance.
	 * @param dir The direction of this {@link AdvanceSprite} instance.
	 * @see #animationChanged(int, int, int, int)
	 * @deprecated Deprecated due to naming, use
	 *             {@link #setStatusAndDirection(int, int)} instead.
	 */
	public void setAnimation(int stat, int dir) {
		setStatusAndDirection(stat, dir);
	}
	
	/**
	 * Sets both the {@link AdvanceSprite} instance's {@link #getStatus()
	 * status} and {@link #getDirection() direction} at once. If either the
	 * {@link #getStatus() status} or {@link #getDirection() direction} has
	 * changed, the {@link #animationChanged(int, int, int, int)} method is
	 * invoked.
	 * 
	 * @param status The status of this {@link AdvanceSprite} instance.
	 * @param direction The direction of this {@link AdvanceSprite} instance.
	 * @see #animationChanged(int, int, int, int)
	 */
	public void setStatusAndDirection(int status, int direction) {
		int oldStatus = this.status;
		int oldDirection = this.direction;
		
		this.status = status;
		this.direction = direction;
		
		if (oldStatus != this.status || oldDirection != this.direction) {
			this.animationChanged(oldStatus, oldDirection, status, direction);
		}
	}
}
