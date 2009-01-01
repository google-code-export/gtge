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

import com.golden.gamedev.object.background.abstraction.AbstractTileBackground;

/**
 * The {@link TileBackground} class is a simple, one layer
 * {@link AbstractTileBackground} implementation that generates the tiles to
 * render based off a {@link #getTiles() two-dimensional matrix of integer
 * indices} into a {@link #getTileImages() one-dimensional array of images}. <br />
 * <br />
 * As a simple example, consider an image array with four tile images. The
 * indices of these images are the following: {0, 1, 2, 3}. The tile result of
 * this background should create a square tile background with the images in a
 * reverse order: <br />
 * <br />
 * <table style="border: thin">
 * <tr>
 * <td>TILE 3</td>
 * <td>TILE 2</td>
 * </tr>
 * <tr>
 * <td>TILE 1</td>
 * <td>TILE 0</td>
 * </tr>
 * </table>
 * <br />
 * To have this result, the integer matrix simply needs to reference the tile
 * indices in the same order, as:
 * 
 * <pre>
 * int[][] tiles = new int[][] { {3, 2} {1, 0}}
 * TileBackground background = new TileBackground(images, tiles);
 * </pre>
 * 
 * There are two items to note about the {@link TileBackground} class. The first
 * item is that it is possible, and in most cases very likely, to have a
 * {@link TileBackground} that exceeds the width of the {@link #getClip()
 * viewport}. This is handled directly by the {@link TileBackground} class and
 * as long as the {@link TileBackground} is manipulated appropriately, this
 * should not be an issue - scrolling the {@link TileBackground} will eventually
 * reveal all of the {@link #getTileImages() tiles} that are mapped to be shown
 * via the {@link #getTiles() tile matrix}. <br />
 * <br />
 * 
 * Second, note that an illegal index into the {@link #getTileImages() images}
 * array via the corresponding {@link #getTiles() tile matrix}, such as a
 * negative index or an index that is out of bounds for the array, will result
 * in an {@link ArrayIndexOutOfBoundsException} being thrown when the
 * {@link #renderTile(Graphics2D, int, int, int, int)} method is invoked. This
 * is a slight change from the previous version of the class, where the
 * {@link TileBackground} class would simply render nothing, but this behavior
 * may lead to confusion. This is an error on the part of the user and should be
 * compensated for by the user of this class. <br />
 * <br />
 * <b><i>Warning: The {@link TileBackground} class is not threadsafe. Multiple
 * threads will have to use different instances of the {@link TileBackground}
 * class.</i></b>
 * 
 * @version 1.1
 * @since 0.2.3
 * @see AbstractTileBackground
 */
public class TileBackground extends AbstractTileBackground {
	
	/**
	 * A serialVersionUID for the {@link TileBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = -1608779555653076202L;
	
	/**
	 * The array of {@link BufferedImage} instances representing all the images
	 * that can be used as a single tile.
	 * @see TileBackground
	 */
	private transient BufferedImage[] tileImages;
	
	/**
	 * The matrix of integers representing which tile in the
	 * {@link #getTileImages() tile image array} should be used for a particular
	 * tile in the grid.
	 * @see TileBackground
	 */
	private int[][] tiles;
	
	/**
	 * Creates a new {@link TileBackground} instance with the given
	 * {@link #getTileImages() array of images} and the two-dimensional
	 * {@link #getTiles() matrix of indices} into the array to determine which
	 * tiles get drawn.
	 * @param tileImages The {@link #getTileImages() array of tile images} to
	 *        use to construct this {@link TileBackground} instance.
	 * @param tiles The {@link #getTiles() matrix of indices} into the
	 *        {@link #getTileImages() array of tile images}.
	 * @see TileBackground
	 */
	public TileBackground(BufferedImage[] tileImages, int[][] tiles) {
		super(tiles.length, tiles[0].length, tileImages[0].getWidth(),
		        tileImages[0].getHeight());
		
		this.tileImages = tileImages;
		this.tiles = tiles;
	}
	
	/**
	 * Creates a new {@link TileBackground} instance with the given
	 * {@link #getTileImages() array of images}, the number of tiles in the
	 * horizontal direction, and the number of tiles in the vertical direction.
	 * All of the tiles rendered by this {@link TileBackground} instance will
	 * use the first tile in the {@link #getTileImages() tile image array} by
	 * default, unless this is changed later by a call to
	 * {@link #setTiles(int[][])}.
	 * @param tileImages The {@link #getTileImages() array of tile images} to
	 *        use to construct this {@link TileBackground} instance.
	 * @param horizontalTileCount The number of tiles in the horizontal (left to
	 *        right) direction.
	 * @param verticalTileCount The number of tiles in the vertical (top to
	 *        bottom) direction.
	 * @see TileBackground
	 */
	public TileBackground(BufferedImage[] tileImages, int horizontalTileCount,
	        int verticalTileCount) {
		this(tileImages, new int[horizontalTileCount][verticalTileCount]);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         {@link Graphics2D graphics context}, {@link #getTileImages() tile
	 *         image array} or {@link #getTiles() tile position matrix} are
	 *         null.
	 * @throws ArrayIndexOutOfBoundsException Throws an
	 *         {@link ArrayIndexOutOfBoundsException} if tileX or tileY are
	 *         outside the range of the {@link #getTiles() tile matrix}, or if
	 *         their integer index returned is outside the range of the
	 *         {@link #getTileImages() tile image array}.
	 */
	public void renderTile(Graphics2D g, int tileX, int tileY, int x, int y) {
		g.drawImage(this.tileImages[this.tiles[tileX][tileY]], x, y, null);
	}
	
	public void setSize(int horiz, int vert) {
    	int[][] old = this.tiles;
    	
    	this.tiles = new int[horiz][vert];
    	
    	// Only copy the minimum sizes to avoid an
    	// ArrayIndexOutOfBoundsException.
    	int horizontalSize = Math.min(old.length, horiz);
    	int verticalSize = Math.min(old[0].length, vert);
    	
    	// For all of the horizontal indices that can be copied, copy the
    	// vertical arrays up to the minimum size allowed.
    	for (int index = 0; index < horizontalSize; index++) {
    		System.arraycopy(old[index], 0, tiles[index], 0, verticalSize);
    	}
    	
    	super.setSize(horiz, vert);
    }

	/**
	 * Gets the array of {@link BufferedImage} instances representing all the
	 * images that can be used as a single tile.
	 * @return The array of {@link BufferedImage} instances representing all the
	 *         images that can be used as a single tile.
	 * @see TileBackground
	 */
	public BufferedImage[] getTileImages() {
		return this.tileImages;
	}
	
	/**
	 * Sets the array of {@link BufferedImage} instances representing all the
	 * images that can be used as a single tile.
	 * @param tileImages The array of {@link BufferedImage} instances
	 *        representing all the images that can be used as a single tile.
	 * @see TileBackground
	 */
	public void setTileImages(BufferedImage[] tileImages) {
		this.tileImages = tileImages;
		
		this.setTileSize(tileImages[0].getWidth(), tileImages[0].getHeight());
	}
	
	/**
	 * Gets the matrix of integers representing which tile in the
	 * {@link #getTileImages() tile image array} should be used for a particular
	 * tile in the grid.
	 * @return The matrix of integers representing which tile in the
	 *         {@link #getTileImages() tile image array} should be used for a
	 *         particular tile in the grid.
	 * @see TileBackground
	 */
	public int[][] getTiles() {
		return this.tiles;
	}
	
	/**
	 * Sets the matrix of integers representing which tile in the
	 * {@link #getTileImages() tile image array} should be used for a particular
	 * tile in the grid.
	 * @param tiles The matrix of integers representing which tile in the
	 *        {@link #getTileImages() tile image array} should be used for a
	 *        particular tile in the grid.
	 * @see TileBackground
	 */
	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
		
		super.setSize(tiles.length, tiles[0].length);
	}
	
}
