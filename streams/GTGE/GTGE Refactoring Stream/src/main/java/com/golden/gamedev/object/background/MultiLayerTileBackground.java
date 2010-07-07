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

import com.golden.gamedev.object.background.abstraction.AbstractTileBackground;

/**
 * This class represents a multiple layer tile background.
 * 
 * Instantiation of a <code>MultiLayerTileBackground</code> requires an array of
 * images used for tiles, a 3 dimensional array of integers, and the tile width
 * and tile height of tile images.
 * 
 * The 3 dimensional array of integers index tile images, and may be ragged (or
 * uneven.) The 3rd dimension represents the tiles at a specific location in the
 * background. The position of tiles within this 3rd dimension represent their
 * "height" (or z-depth) at that (x,y) location.
 * 
 * <code>MultiLayerTileBackground</code> usage example :
 * 
 * <pre>
 * MultiLayerTileBackground background;
 * BufferedImage[] tileImages;
 * int[][][] tiles = new int[40][30][]; // 40 x 30 x ? tiling
 * // fill tiles with random value
 * int layerDepth;
 * for (int i = 0; i &lt; tiles.length; i++)
 * 	for (int j = 0; j &lt; tiles[0].length; j++) {
 * 		layerDepth = getRandom(0, 3);
 * 		for (int k = 0; k &lt; layerDepth; k++)
 * 			tiles[i][j][k] = getRandom(0, tileImages.length - 1);
 * 	}
 * // create the background
 * background = new TileBackground(tileImages, tiles, tileImages[0].getWidth(),
 *         tileImages[0].getHeight());
 * </pre>
 * 
 * @see com.golden.gamedev.object.background.abstraction.AbstractTileBackground
 * 
 * @author Will Morrison
 * 
 */
public class MultiLayerTileBackground extends AbstractTileBackground {
	
	/**
	 * Default serial version ID, here to remove warnings.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The layers of this tile background
	 */
	private int[][][] tiles;
	/**
	 * Images each tile in this background index
	 */
	private transient BufferedImage[] imgs;
	
	/**
	 * Constructs a new <code>MultiLayerTileBackground</code> using the
	 * specified array of images, tiles, and tile dimensions.
	 * 
	 * Indices in the integer tile array reference tiles in the background.
	 * Tiles appearing in lower indices of the array imply lower appearing
	 * tiles. For example: tiles[0][0][0] = 2, tiles[0][0][1] = 3 implies image
	 * imgs[2] will be rendered beneath imgs[3].
	 * 
	 * @param imgs specified array of images for this background
	 * @param tiles specified array mapping to images in this background
	 * @param tileWidth tile width of a single tile
	 * @param tileHeight tile height of a single tile
	 */
	public MultiLayerTileBackground(BufferedImage[] imgs, int[][][] tiles,
	        int tileWidth, int tileHeight) {
		super(tiles[0].length, tiles.length, tileWidth, tileHeight);
		this.imgs = imgs;
		this.tiles = tiles;
	}
	
	public void renderTile(Graphics2D g, int tileX, int tileY, int x, int y) {
		int zdepth = this.tiles[tileY][tileX].length;
		for (int z = 0; z < zdepth; z++) {
			g.drawImage(this.imgs[this.tiles[tileY][tileX][z]], x, y, null);
		}
	}
	
	/**
	 * Returns the array of integers mapping to images used for this background
	 * 
	 * @return array of indices used in rendering the background
	 */
	public int[][][] getTiles() {
		return this.tiles;
	}
	
	/**
	 * Returns the array of images representing tiles for this background
	 * 
	 * @return array of images used in this background
	 */
	public BufferedImage[] getTileImages() {
		return this.imgs;
	}
	
	/**
	 * Sets the array of integers mapping to images in this background.
	 * 
	 * @param tiles array of integers used in rendering this background
	 */
	public void setTiles(int[][][] tiles) {
		this.tiles = tiles;
		super.setSize(tiles.length, tiles[0].length);
	}
	
	/**
	 * Sets the array of images representing tiles in this background
	 * 
	 * @param imgs images of tiles in this background
	 */
	public void setTileImages(BufferedImage[] imgs) {
		this.imgs = imgs;
		this.setTileSize(imgs[0].getWidth(), imgs[0].getHeight());
	}
	
	public void setSize(int horiz, int vert) {
		if (horiz != this.tiles.length || vert != this.tiles[0].length) {
			// enlarge/shrink old tiles
			int[][][] old = this.tiles;
			this.tiles = new int[horiz][vert][];
			
			int minx = Math.min(this.tiles[0].length, old[0].length);
			int miny = Math.min(this.tiles.length, old.length);
			for (int y = 0; y < miny; y++) {
				for (int x = 0; x < minx; x++) {
					this.tiles[y][x] = old[y][x];
				}
			}
		}
		
		super.setSize(horiz, vert);
	}
}
