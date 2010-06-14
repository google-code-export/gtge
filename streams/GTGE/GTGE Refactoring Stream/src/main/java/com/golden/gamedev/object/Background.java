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
package com.golden.gamedev.object;

// JFC
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;

import com.golden.gamedev.Renderable;
import com.golden.gamedev.Updateable;
import com.golden.gamedev.object.background.BoundedBackground;
import com.golden.gamedev.object.collision.CollisionRect;
import com.golden.gamedev.object.collision.CollisionShape;

/**
 * <code>Background</code> is the area where every sprites lived.
 * <p>
 * 
 * A background has a view area/view port/clipping area, that is the area which
 * is seen to the player. By default the view area of a newly created background
 * is as big as game dimension. For example if the game is created as big as 640
 * x 480 dimension, the background view area would be also 640 x 480, thus
 * occupy all the game view. To set the background view area, use
 * {@linkplain #setClip(int, int, int, int) setClip(x, y, width, height)}.
 * <p>
 * 
 * If the background size is larger than view area, means there is area not
 * viewable, use {@linkplain #move(double, double) move(dx, dy)} or
 * {@linkplain #setLocation(double, double)} to move around.
 * <p>
 * 
 * To set a sprite to be the center of the background, use
 * {@linkplain #setToCenter(Sprite)}.
 * 
 * As of 0.2.4, {@link Background} implements {@link CollisionShape} and can now
 * be used in collisions if needed.
 * 
 * @see com.golden.gamedev.object.background
 * @deprecated This class has been deprecated and renamed to
 *             {@link BoundedBackground} to better match its functionality, and
 *             will be removed in 0.2.5.
 */
public class Background extends BoundedBackground {
	
	/**
	 * Creates new Background with specified size, and default clipping area (as
	 * large as screen size).
	 * <p>
	 * 
	 * Clipping area is the viewport of the background, anything outside
	 * viewport area will not be rendered.
	 * 
	 * @param w background width
	 * @param h background height
	 * @deprecated The {@link Background} class has been renamed to
	 *             {@link BoundedBackground} and will be removed in 0.2.5.
	 */
	public Background(int w, int h) {
		super(w, h);
	}
	
	/**
	 * Creates new Background, with size and clipping area as large as screen
	 * size.
	 * <p>
	 * 
	 * Clipping area is the view port of the background, anything outside
	 * clipping area will not be rendered.
	 * @deprecated The {@link Background} class has been renamed to
	 *             {@link BoundedBackground} and will be removed in 0.2.5.
	 */
	public Background() {
		super(BoundedBackground.screen.width, BoundedBackground.screen.height);
	}
	
	/**
	 * @deprecated The {@link Background} class has been renamed to
	 *             {@link BoundedBackground} and will be removed in 0.2.5.
	 */
	public void render(Graphics2D g, int xbg, int ybg, int x, int y, int w, int h) {
	}
}
