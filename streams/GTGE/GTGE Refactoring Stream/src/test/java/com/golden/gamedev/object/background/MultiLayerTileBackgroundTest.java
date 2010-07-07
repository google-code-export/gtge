/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.object.MockGraphics2D;
import com.golden.gamedev.util.ImageUtil;

/** The {@link MultiLayerTileBackgroundTest} class provides a {@link TestCase} to test the
 * functionality of the {@link MultiLayerTileBackground} class.
 * 
 * @author Will Morrison
 * @version 1.0
 * @since 0.2.4
 * 
 */
public class MultiLayerTileBackgroundTest extends TestCase {

	private MultiLayerTileBackground multiLayerBG;
	private BufferedImage[] tileImgs;
	private int[][][] layers;
	private int tileW, tileH;

	/**
	 * @param name
	 */
	public MultiLayerTileBackgroundTest(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		BufferedImage imgBG = new BufferedImage(800, 600,
				BufferedImage.TYPE_INT_ARGB);
		tileW = 32;
		tileH = 32;
		int cols = imgBG.getWidth() / tileW;
		int rows = imgBG.getHeight() / tileH;
		tileImgs = ImageUtil.splitImages(imgBG, cols, rows);
		layers = new int[40][30][]; // 40 x 30 x ? tiling
		int layerDepth;
		for (int i = 0; i < layers.length; i++)
			for (int j = 0; j < layers[0].length; j++) {
				layerDepth = j % 3 + 1;
				layers[i][j] = new int[layerDepth];
				for (int k = 0; k < layerDepth; k++)
					layers[i][j][k] = k;
			}
		multiLayerBG = new MultiLayerTileBackground(tileImgs, layers, tileW,
				tileH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.MultiLayerTileBackground#setSize(int, int)}
	 * .
	 */
	public void testSetSize() {
		System.out.println("SET SIZE");
		// 32x32 tiles in size
		multiLayerBG.setSize(32, 32);
		assertTrue(multiLayerBG.getTotalHorizontalTiles() == 32);
		assertTrue(multiLayerBG.getTotalVerticalTiles() == 32);
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.MultiLayerTileBackground#renderTile(java.awt.Graphics2D, int, int, int, int)}
	 * .
	 */
	public void testRenderTile() {
		System.out.println("RENDER TILE");
		MockGraphics2D g = new MockGraphics2D();
		this.multiLayerBG.renderTile(g, 0, 0, 0, 0);
		int[] layer = multiLayerBG.getTiles()[0][0];
		// highest image at tile position (0, 0) should be last drawn
		assertTrue(g.lastDrawnImage == multiLayerBG.getTileImages()[layer[layer.length - 1]]);
		// no image observer
		assertNull(g.lastDrawnImageObserver);
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.MultiLayerTileBackground#MultiLayerTileBackground(java.awt.image.BufferedImage[], int[][][], int, int)}
	 * .
	 */
	public void testMultiLayerTileBackground() {
		System.out.println("TESTING CONSTRUCTOR");
		int width = layers[0].length * tileW;
		int height = layers.length * tileH;
		multiLayerBG = new MultiLayerTileBackground(tileImgs, layers, tileW,
				tileH);
		assertNotNull(multiLayerBG);
		assertEquals(0, multiLayerBG.getX(), 0);
		assertEquals(0, multiLayerBG.getY(), 0);
		System.out.println(multiLayerBG.getWidth() + " " + width);
		assertTrue(multiLayerBG.getWidth() == width);
		assertTrue(multiLayerBG.getHeight() == height);

		Rectangle clip = multiLayerBG.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(BoundedBackground.screen.height, clip.getHeight(), 0);
		assertEquals(BoundedBackground.screen.width, clip.getWidth(), 0);

		// ensure construction values are values the map returns
		assertEquals(0, multiLayerBG.getTileWidth(), tileW);
		assertEquals(0, multiLayerBG.getTileHeight(), tileH);
		assertTrue(layers == multiLayerBG.getTiles());
		assertTrue(tileImgs == multiLayerBG.getTileImages());
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.abstraction.AbstractTileBackground#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public void testRenderGraphics2DIntIntIntIntIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		Rectangle clip = new Rectangle(0, 0, 640, 480);
		this.multiLayerBG.setClip(clip);
		this.multiLayerBG.render(g, 0, 0, 0, 0, 640, 480);
		assertTrue(g.lastDrawnX == clip.x + clip.width
				+ multiLayerBG.getOffsetX() - multiLayerBG.getTileWidth());
		assertTrue(g.lastDrawnY == clip.y + clip.height
				+ multiLayerBG.getOffsetY() - multiLayerBG.getTileHeight());
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.abstraction.AbstractTileBackground#getTileWidth()}
	 * .
	 */
	public void testGetTileWidth() {
		assertTrue(this.tileW == multiLayerBG.getTileWidth());
	}

	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.abstraction.AbstractTileBackground#getTileHeight()}
	 * .
	 */
	public void testGetTileHeight() {
		assertTrue(this.tileH == multiLayerBG.getTileHeight());
	}

}
