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

import com.golden.gamedev.object.background.abstraction.AbstractIsometricBackground;

/**
 * The {@link IsometricBackground} class is a simple, one-layer
 * {@link AbstractIsometricBackground} implementation that generates the tiles
 * to render based off a {@link #getTiles() two-dimensional matrix of integer
 * indices} into a {@link #getTileImages() one-dimensional array of images}.
 * 
 * As a simple example, consider an image array with four tile images. The
 * indices of these images are the following: {0, 1, 2, 3}. The tile result of
 * this background should create an isometric tile background with the images in
 * a reverse order: <br />
 * <br />
 * <table>
 * <tr>
 * <td colspan="2" style="text-align: center">TILE 3</td>
 * </tr>
 * <tr>
 * <td>TILE 2</td>
 * <td>TILE 1</td>
 * </tr>
 * <tr>
 * <td colspan="2" style="text-align: center">TILE 0</td>
 * </tr>
 * </table>
 * <br />
 * To have this result, the integer matrix simply needs to reference the tile
 * indices in the same order, as:
 * 
 * <pre>
 * int[][] tiles = new int[][] { {3, 2} {1, 0}}
 * IsometricBackground background = new IsometricBackground(images, tiles);
 * </pre>
 * 
 * There are three items to note about the {@link IsometricBackground} class.
 * The first item is that it is possible, and in most cases very likely, to have
 * a {@link IsometricBackground} that exceeds the width of the
 * {@link #getClip() viewport}. This is handled directly by the
 * {@link IsometricBackground} class and as long as the
 * {@link IsometricBackground} is manipulated appropriately, this should not be
 * an issue - scrolling the {@link IsometricBackground} will eventually reveal
 * all of the {@link #getTileImages() tiles} that are mapped to be shown via the
 * {@link #getTiles() tile matrix}. <br />
 * <br />
 * 
 * Second, note that an illegal index into the {@link #getTileImages() images}
 * array via the corresponding {@link #getTiles() tile matrix}, such as a
 * negative index or an index that is out of bounds for the array, will result
 * in an {@link ArrayIndexOutOfBoundsException} being thrown when the
 * {@link #renderTile(Graphics2D, int, int, int, int)} method is invoked. This
 * is a slight change from the previous version of the class, where the
 * {@link IsometricBackground} class would simply render nothing, but this
 * behavior may lead to confusion. This is an error on the part of the user and
 * should be compensated for by the user of this class. <br />
 * <br />
 * 
 * Finally, the offsetTileHeight constructor parameter requires some
 * explanation. When this parameter is not set directly, or is explicitly set at
 * 0, the {@link IsometricBackground} class creates a background akin to the
 * following table of a background:
 * <table>
 * <tr>
 * <td colspan="2" style="text-align: center">TILE 0 TOP HALF SHOWN<br />
 * BOTTOM HALF HIDDEN</td>
 * </tr>
 * <tr>
 * <td>TILE 1 COVERS BOTTOM HALF TILE 0 SHOWS TOP HALF</td>
 * <td>TILE 2 COVERS BOTTOM HALF TILE 0 SHOWS TOP HALF</td>
 * </tr>
 * </table>
 * <br />
 * So, to create the isometric effect, by default, the first tile is half
 * hidden, then the tiles on top of it are half hidden by the next row, and so
 * on until the final row in the background where the tile is completely shown. <br />
 * <br />
 * The offsetTileHeight parameter, when not set to 0, allows for the changing of
 * this default behavior. Setting the offsetTileHeight parameter to a positive
 * number makes the tiles in front hide more of the tiles beneath them, which,
 * with an extreme enough value, would eventually result in a single row of
 * tiles, with the top rows completely hidden. Setting the offsetTileHeight
 * parameter to a negative number makes the tiles in front hide less of the
 * tiles beneath them, eventually resulting in all of the tiles being shown with
 * no overlap for extreme values. <br />
 * If the {@link IsometricBackground} display is not optimal, adjusting this
 * value may allow for a better display without changing the
 * {@link #getTileImages() images} that the {@link IsometricBackground} uses.
 * 
 * <br />
 * <br />
 * <b><i>Warning: The {@link IsometricBackground} class is not threadsafe.
 * Multiple threads will have to use different instances of the
 * {@link IsometricBackground} class.</i></b>
 * 
 * @version 1.1
 * @since 0.2.3
 * @see AbstractIsometricBackground
 */
public class IsometricBackground extends AbstractIsometricBackground {
	
	/**
	 * A serialVersionUID for the {@link IsometricBackground} class.
	 * @see Serializable
	 */
	private static final long serialVersionUID = -2383969026366897057L;
	
	/**
	 * The array of {@link BufferedImage} instances representing all the images
	 * that can be used as a single tile.
	 * @see IsometricBackground
	 */
	private transient BufferedImage[] tileImages;
	
	/**
	 * The matrix of integers representing which tile in the
	 * {@link #getTileImages() tile image array} should be used for a particular
	 * tile in the grid.
	 * @see IsometricBackground
	 */
	private int[][] tiles;
	
	/**
	 * Creates a new {@link IsometricBackground} instance with the given
	 * {@link #getTileImages() array of images} and the two-dimensional
	 * {@link #getTiles() matrix of indices} into the array to determine which
	 * tiles get drawn.
	 * @param tileImages The {@link #getTileImages() array of tile images} to
	 *        use to construct this {@link IsometricBackground} instance.
	 * @param tiles The {@link #getTiles() matrix of indices} into the
	 *        {@link #getTileImages() array of tile images}.
	 * @see IsometricBackground
	 */
	public IsometricBackground(BufferedImage[] tileImages, int[][] tiles) {
		this(tileImages, tiles, 0, 0);
	}
	
	/**
	 * Creates a new {@link IsometricBackground} instance with the given
	 * {@link #getTileImages() array of images}, the number of tiles in the
	 * horizontal direction, and the number of tiles in the vertical direction.
	 * All of the tiles rendered by this {@link IsometricBackground} instance
	 * will use the first tile in the {@link #getTileImages() tile image array}
	 * by default, unless this is changed later by a call to
	 * {@link #setTiles(int[][])}.
	 * @param tileImages The {@link #getTileImages() array of tile images} to
	 *        use to construct this {@link IsometricBackground} instance.
	 * @param horizontalTileCount The number of tiles in the horizontal (left to
	 *        right) direction.
	 * @param verticalTileCount The number of tiles in the vertical (top to
	 *        bottom) direction.
	 * @see IsometricBackground
	 */
	public IsometricBackground(BufferedImage[] tileImages,
	        int horizontalTileCount, int verticalTileCount) {
		this(tileImages, new int[horizontalTileCount][verticalTileCount]);
	}
	
	/**
	 * Creates a new {@link IsometricBackground} instance with the given
	 * {@link #getTileImages() array of images} and the two-dimensional
	 * {@link #getTiles() matrix of indices} into the array to determine which
	 * tiles get drawn, as well as the offsetTileHeight specifying how low or
	 * high to draw the tiles in relation to the tiles they cover, and the
	 * startY coordinate offset specifying an offset to add to the
	 * {@link #getY() y coordinate} when the {@link IsometricBackground} starts
	 * displaying.
	 * @param tileImages The {@link #getTileImages() array of tile images} to
	 *        use to construct this {@link IsometricBackground} instance.
	 * @param tiles The {@link #getTiles() matrix of indices} into the
	 *        {@link #getTileImages() array of tile images}.
	 * @param offsetTileHeight The offset tile height used to determine how low
	 *        or high to draw tiles for the {@link IsometricBackground}. A
	 *        positive value makes the front tiles cover more of the back tiles,
	 *        a negative value makes the front tiles cover less of the back
	 *        tiles.
	 * @param startY A y-coordinate offset added to the background
	 *        {@link #getY() y coordinate}, used if the background needs to
	 *        start rendering further down the screen than its default of 0.
	 * @see IsometricBackground
	 */
	public IsometricBackground(BufferedImage[] tileImages, int[][] tiles,
	        int offsetTileHeight, int startY) {
		super(tiles.length, tiles[0].length, tileImages[0].getWidth(),
		        tileImages[0].getHeight(), offsetTileHeight, startY);
		
		this.tileImages = tileImages;
		this.tiles = tiles;
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
	 * @see IsometricBackground
	 */
	public BufferedImage[] getTileImages() {
		return this.tileImages;
	}
	
	/**
	 * Sets the array of {@link BufferedImage} instances representing all the
	 * images that can be used as a single tile.
	 * @param tileImages The array of {@link BufferedImage} instances
	 *        representing all the images that can be used as a single tile.
	 * @see IsometricBackground
	 */
	public void setTileImages(BufferedImage[] tileImages, int offsetTileHeight) {
		this.tileImages = tileImages;
		
		this.setTileSize(tileImages[0].getWidth(), tileImages[0].getHeight(),
		        offsetTileHeight);
	}
	
	/**
	 * Gets the matrix of integers representing which tile in the
	 * {@link #getTileImages() tile image array} should be used for a particular
	 * tile in the grid.
	 * @return The matrix of integers representing which tile in the
	 *         {@link #getTileImages() tile image array} should be used for a
	 *         particular tile in the grid.
	 * @see IsometricBackground
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
	 * @see IsometricBackground
	 */
	public void setTiles(int[][] tiles) {
		this.tiles = tiles;
		
		super.setSize(tiles.length, tiles[0].length);
	}
	
}
