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

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.Serializable;

import com.golden.gamedev.engine.BaseGraphics;

/**
 * The {@link CachedTileBackground} class is a {@link TileBackground} that
 * stores a {@link TileBackground#render(Graphics2D) render} of the
 * {@link TileBackground} class into a {@link VolatileImage cache} to attempt to
 * speed up the display, however, this strategy is only applicable for games
 * with very slowly-changing backgrounds, thus, the use of the
 * {@link CachedTileBackground} class is officially discouraged and the
 * {@link CachedTileBackground} class may be deprecated and removed in a later
 * release. <br />
 * <br />
 * If a game has a {@link TileBackground} that rarely changes, but will change,
 * the {@link CachedTileBackground} class will provide for better rendering than
 * using the {@link TileBackground} class directly. However, if the background
 * changes constantly, this class performs worse than using the
 * {@link TileBackground} class directly, and if the background never changes,
 * the {@link StaticTileBackground} class should be used instead. <br />
 * <br />
 * <b><i>NOTE: The previous version of the {@link CachedTileBackground} class
 * did not mark its {@link VolatileImage} instance as transient, but version 1.1
 * remedies this. Version 1.1 of the {@link CachedTileBackground} class,
 * therefore, does not retain {@link Serializable serialization} compatibility
 * with previous class versions.</i></b> <br />
 * <br />
 * <b><i>Warning: The {@link CachedTileBackground} class is not threadsafe.
 * Multiple threads will have to use different instances of the
 * {@link CachedTileBackground} class.</i></b>
 * 
 * @version 1.1
 * @since 0.2.3
 * @see TileBackground
 * @see VolatileImage
 * 
 */
public class CachedTileBackground extends TileBackground {
	
	/**
	 * A serializableUID for the {@link CachedTileBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * The cached {@link VolatileImage image} to display.
	 */
	private transient VolatileImage cache;
	
	/**
	 * The old {@link #getX() x-coordinate} that the
	 * {@link CachedTileBackground} had at the last {@link #cache} update.
	 */
	private double oldX;
	
	/**
	 * The old {@link #getY() y-coordinate} that the
	 * {@link CachedTileBackground} had at the last {@link #cache} update.
	 */
	private double oldY;
	
	/**
	 * Whether or not the {@link #cache} can be displayed directly onto the
	 * screen.
	 */
	private boolean validated;
	
	/**
	 * Creates a new {@link CachedTileBackground} instance, using the given
	 * {@link BaseGraphics} instance to create a cached {@link VolatileImage},
	 * which has its contents drawn to based off the {@link BufferedImage
	 * images} and tiles array specified.
	 * @param bsGraphics The {@link BaseGraphics} instance containing a
	 *        {@link BaseGraphics#getComponent() component} that may be used to
	 *        {@link Component#createVolatileImage(int, int) create
	 *        VolatileImage} instances.
	 * @param images A one-dimensional array of {@link BufferedImage} instances
	 *        containing the images of all the tiles in this
	 *        {@link StaticTileBackground} instance.
	 * @param tiles A two-dimensional array of integers which specify the index
	 *        of the tile in the images array that should be used for the tile
	 *        in the matrix at that position when rendering.
	 * @see TileBackground#TileBackground(BufferedImage[], int[][])
	 */
	public CachedTileBackground(BaseGraphics bsGraphics,
	        BufferedImage images[], int tiles[][]) {
		super(images, tiles);
		
		this.cache = bsGraphics.getComponent().createVolatileImage(
		        bsGraphics.getSize().width, bsGraphics.getSize().height);
	}
	
	public void render(Graphics2D g) {
    	if (!this.validated) {
    		// Render the new background into the cache
    		super.render(this.cache.createGraphics());
    		
    		// Store state for the next checkMutations call.
    		this.validated = true;
    		this.oldX = this.getX();
    		this.oldY = this.getY();
    	}
    	
    	// Draw the cached VolatileImage onto the screen.
    	g.drawImage(this.cache, 0, 0, null);
    }

	public void update(long elapsedTime) {
		super.update(elapsedTime);
		
		this.checkMutations();
	}
	
	/**
	 * Sets the validated flag to false if it was false, or if the old x
	 * coordinate value or old y coordinate value did not match the current
	 * values of {@link #getX() x} and {@link #getY() y}.
	 */
	protected void checkMutations() {
		this.validated = this.validated && this.oldX == this.getX()
		        && this.oldY == this.getY();
	}
}
