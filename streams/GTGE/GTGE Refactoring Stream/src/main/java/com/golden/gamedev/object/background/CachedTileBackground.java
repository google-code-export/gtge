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
import java.awt.image.VolatileImage;

import com.golden.gamedev.engine.BaseGraphics;

/**
 * @deprecated The {@link CachedTileBackground} class is deprecated with no
 *             direct replacement - caching the background as it attempts to do
 *             is inefficient for all but the most simple of applications (where
 *             the background does not move), and furthermore, attempts to
 *             invoke {@link #render(Graphics2D)} will result in a
 *             {@link StackOverflowError} due to recursion issues. The
 *             {@link CachedTileBackground} class will be removed in 0.2.4.
 * @since 0.2.3
 */
public class CachedTileBackground extends TileBackground {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4682118408949172952L;
	private VolatileImage cache;
	private boolean validated;
	private double oldX;
	private double oldY;
	
	/**
	 * Create a regular TileBackground using the bsGraphics to generate a big
	 * managed image to cache the rending.
	 * @deprecated The {@link CachedTileBackground} class is deprecated and will
	 *             be removed in 0.2.4.
	 */
	public CachedTileBackground(BaseGraphics bsGraphics,
	        BufferedImage images[], int tiles[][]) {
		super(images, tiles);
		
		this.cache = bsGraphics.getComponent().createVolatileImage(
		        bsGraphics.getSize().width, bsGraphics.getSize().height);
	}
	
	/**
	 * Check the background to see if it must be fully rendered. (to see if the
	 * cache must be invalidate)
	 * 
	 * The cache is valid if : 1) It was already valid and 2) The background did
	 * not move.
	 * @deprecated The {@link CachedTileBackground} class is deprecated and will
	 *             be removed in 0.2.4.
	 */
	protected void checkMutations() {
		this.validated = this.validated && this.oldX == this.getX()
		        && this.oldY == this.getY();
	}
	
	/**
	 * @deprecated The {@link CachedTileBackground} class is deprecated and will
	 *             be removed in 0.2.4.
	 */
	public void update(long elapsedTime) {
		super.update(elapsedTime);
		
		this.checkMutations();
	}
	
	/**
	 * Render the background using the cache. If the cache is not valid, render
	 * the background to it to make it valid again. The draw the cache to the
	 * screen.
	 * 
	 * @param g
	 * @deprecated The {@link CachedTileBackground} class is deprecated and will
	 *             be removed in 0.2.4.
	 */
	public void render(Graphics2D g, int xbg, int ybg, int x, int y, int w, int h) {
		if (!this.validated) {
			// render the background in a big managed image
			super.render(this.cache.createGraphics());
			
			this.validated = true;
			this.oldX = this.getX();
			this.oldY = this.getY();
		}
		
		// draw the big managed image to the screen
		g.drawImage(this.cache, 0, 0, null);
	}
	
}
