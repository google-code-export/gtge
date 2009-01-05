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
import java.io.Serializable;

import com.golden.gamedev.object.Background;

/**
 * The {@link ParallaxBackground} class is a {@link Background} that consists of
 * multiple {@link Background} instances that are all rendered together for a
 * smooth composite {@link Background}. The {@link ParallaxBackground} class
 * automatically handles displaying and scrolling of the
 * {@link #getParallaxBackground() stacked backgrounds}. The {@link Background}
 * instances are normalized to the size and position of the largest
 * {@link Background} {@link Background#getWidth() width} and
 * {@link Background#getHeight() height} in the stack. This way, the largest
 * coordinate system is presented, and all of the {@link Background} instances
 * move together at a smooth rate. <br />
 * <br />
 * The {@link Background} instances are rendered from the first
 * {@link Background} in the {@link #getParallaxBackground() array} to the last
 * {@link Background} in the {@link #getParallaxBackground() array}, so that the
 * first {@link Background} will be rendered behind the second, the second
 * behind the third, etc. <br />
 * <br />
 * Here is an example of creating a {@link ParallaxBackground} instance:
 * 
 * <pre>
 * ParallaxBackground background;
 * Background bg1, bg2, bg3;
 * background = new ParallaxBackground(new Background[] {
 *         bg1, bg2, bg3
 * });
 * // bg1 is rendered below bg2, and bg2 is rendered below bg3
 * </pre>
 * 
 * <br />
 * NOTE: due to the removal of a field, the {@link Serializable serializability}
 * of version 1.1 of the {@link ParallaxBackground} class is not backwards
 * compatibile with previous versions. <br />
 * <br />
 * <b><i>Warning: The {@link ParallaxBackground} class is not threadsafe.
 * Multiple threads will have to use different instances of the
 * {@link ParallaxBackground} class.</i></b>
 * 
 * @version 1.1
 * @since 0.2.3
 * @see Background
 */
public class ParallaxBackground extends Background {
	
	/**
	 * A serialVersionUID for the {@link ParallaxBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * The {@link Background} array that this {@link ParallaxBackground}
	 * instance stores.
	 */
	private Background[] stack;
	
	/**
	 * Creates a new {@link ParallaxBackground} instance with the given non-null
	 * {@link Background} array of {@link Background} instances to render.
	 * @param stack The {@link #getParallaxBackground() array of Background
	 *        instances} that are stored via this {@link ParallaxBackground}
	 *        instance.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link Background} array is null.
	 */
	public ParallaxBackground(Background[] stack) throws NullPointerException {
		this.stack = stack;
		
		this.normalizeView();
	}
	
	/**
	 * Sets the {@link #setSize(int, int) size} of this
	 * {@link ParallaxBackground} instance to the largest dimensions contained
	 * in each {@link Background} instance in the {@link #stack stack}.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         {@link #stack stack} is null.
	 */
	private void normalizeView() throws NullPointerException {
		int newWidth = getWidth();
		int newHeight = getHeight();
		
		for (int index = 0; index < this.stack.length; index++) {
			newWidth = Math.max(newWidth, stack[index].getWidth());
			newHeight = Math.max(newHeight, stack[index].getHeight());
		}
		
		this.setSize(newWidth, newHeight);
	}
	
	public void setLocation(double xb, double yb) {
		super.setLocation(xb, yb);
		
		int viewportWidth = this.getClip().width;
		int viewportHeight = this.getClip().height;
		int normalizationWidth = this.getWidth() - viewportWidth;
		int normalizationHeight = this.getHeight() - viewportHeight;
		
		for (int index = 0; index < this.stack.length; index++) {
			// Set each background to a scaled location relative to the largest
			// background drawn into the viewport. The largest background will
			// be unaffected (its scale will be 1 when computed).
			this.stack[index].setLocation(this.getX()
			        * (this.stack[index].getWidth() - viewportWidth)
			        / (normalizationWidth), this.getY()
			        * (this.stack[index].getHeight() - viewportHeight)
			        / (normalizationHeight));
		}
	}
	
	public void update(long elapsedTime) {
		for (int index = 0; index < this.stack.length; index++) {
			this.stack[index].update(elapsedTime);
		}
	}
	
	public void render(Graphics2D g) {
		for (int index = 0; index < this.stack.length; index++) {
			this.stack[index].render(g);
		}
	}
	
	/**
	 * Gets the {@link Background} array that this {@link ParallaxBackground}
	 * instance stores.
	 * @return The {@link Background} array that this {@link ParallaxBackground}
	 *         instance stores.
	 */
	public Background[] getParallaxBackground() {
		return this.stack;
	}
	
	/**
	 * Sets the {@link Background} array that this {@link ParallaxBackground}
	 * instance stores.
	 * @param stack The {@link Background} array that this
	 *        {@link ParallaxBackground} instance stores.
	 */
	public void setParallaxBackground(Background[] stack) {
		this.stack = stack;
		
		this.normalizeView();
	}
}
