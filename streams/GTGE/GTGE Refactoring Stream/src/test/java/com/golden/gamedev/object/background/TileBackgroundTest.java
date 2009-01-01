/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import junit.framework.TestCase;

import com.golden.mock.java.awt.MockGraphics2D;

/**
 * @author MetroidFan2002
 * 
 */
public class TileBackgroundTest extends TestCase {
	
	private TileBackground background;
	
	protected void setUp() throws Exception {
		background = new TileBackground(new BufferedImage[] {
			new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB)
		}, new int[][] {
			{
				0
			}
		});
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#setSize(int, int)}
	 * .
	 */
	public void testSetSize() {
		int[][] tileArray = new int[][] {
		        {
		                1, 2, 3, 4, 5
		        }, {
		                6, 7, 8, 9, 10
		        }, {
		                11, 12, 13, 14, 15
		        }, {
		                16, 17, 18, 19, 20
		        }, {
		                21, 22, 23, 24, 25
		        }
		};
		
		background.setTiles(tileArray);
		// Shrink the tiles, but keep a subset (1-4, 6-9, 16-19, 21-24}
		background.setSize(4, 4);
		int[][] array = background.getTiles();
		assertTrue(Arrays.equals(new int[] {
		        1, 2, 3, 4
		}, array[0]));
		assertTrue(Arrays.equals(new int[] {
		        6, 7, 8, 9
		}, array[1]));
		assertTrue(Arrays.equals(new int[] {
		        11, 12, 13, 14
		}, array[2]));
		assertTrue(Arrays.equals(new int[] {
		        16, 17, 18, 19
		}, array[3]));
		
		background.setTiles(tileArray);
		// Enlarge the tiles, keep the whole array, but add a 0 to each array.
		background.setSize(6, 6);
		array = background.getTiles();
		assertTrue(Arrays.equals(new int[] {
		        1, 2, 3, 4, 5, 0
		}, array[0]));
		assertTrue(Arrays.equals(new int[] {
		        6, 7, 8, 9, 10, 0
		}, array[1]));
		assertTrue(Arrays.equals(new int[] {
		        11, 12, 13, 14, 15, 0
		}, array[2]));
		assertTrue(Arrays.equals(new int[] {
		        16, 17, 18, 19, 20, 0
		}, array[3]));
		assertTrue(Arrays.equals(new int[] {
		        21, 22, 23, 24, 25, 0
		}, array[4]));
		assertTrue(Arrays.equals(new int[] {
		        0, 0, 0, 0, 0, 0
		}, array[5]));
		
		background.setTiles(tileArray);
		// Keep the tiles exactly the same with the same size.
		background.setSize(5, 5);
		array = background.getTiles();
		assertTrue(Arrays.equals(tileArray[0], array[0]));
		assertTrue(Arrays.equals(tileArray[1], array[1]));
		assertTrue(Arrays.equals(tileArray[2], array[2]));
		assertTrue(Arrays.equals(tileArray[3], array[3]));
		assertTrue(Arrays.equals(tileArray[4], array[4]));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#renderTile(java.awt.Graphics2D, int, int, int, int)}
	 * .
	 */
	public void testRenderTile() {
		background.renderTile(new MockGraphics2D(), 0, 0, 0, 0);
		background.setTiles(new int[][] {
			{
				-1
			}
		});
		try {
			background.renderTile(new MockGraphics2D(), 0, 0, 0, 0);
			fail("Expecting ArrayIndexOutOfBoundsException - No negative indices allowed");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank.
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#TileBackground(java.awt.image.BufferedImage[], int[][])}
	 * .
	 */
	public void testTileBackgroundBufferedImageArrayIntArrayArray() {
		assertNotNull(background);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#TileBackground(java.awt.image.BufferedImage[], int, int)}
	 * .
	 */
	public void testTileBackgroundBufferedImageArrayIntInt() {
		background = new TileBackground(background.getTileImages(), 1, 1);
		assertNotNull(background);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#getTileImages()}
	 * .
	 */
	public void testGetTileImages() {
		assertNotNull(background.getTileImages());
		BufferedImage images[] = new BufferedImage[] {
		        background.getTileImages()[0], background.getTileImages()[0]
		};
		background.setTileImages(images);
		assertEquals(images, background.getTileImages());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#setTileImages(java.awt.image.BufferedImage[])}
	 * .
	 */
	public void testSetTileImages() {
		BufferedImage images[] = new BufferedImage[] {
		        background.getTileImages()[0], background.getTileImages()[0]
		};
		background.setTileImages(images);
		assertEquals(images, background.getTileImages());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#getTiles()}.
	 */
	public void testGetTiles() {
		assertNotNull(background.getTiles());
		int[][] array = new int[3][4];
		background.setTiles(array);
		assertEquals(array, background.getTiles());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.TileBackground#setTiles(int[][])}
	 * .
	 */
	public void testSetTiles() {
		int[][] array = new int[3][4];
		background.setTiles(array);
		assertEquals(array, background.getTiles());
	}
	
	public TileBackground getBackground() {
		return background;
	}
	
	public void setBackground(TileBackground background) {
		this.background = background;
	}
	
}
