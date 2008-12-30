/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.engine.graphics.WindowedMode;
import com.golden.mock.java.awt.MockGraphics2D;

/**
 * @author MetroidFan2002
 * 
 */
public class CachedTileBackgroundTest extends TestCase {
	
	private CachedTileBackground background;
	
	protected void setUp() throws Exception {
		WindowedMode mode = new WindowedMode(new Dimension(640, 480), false);
		mode.getComponent().setVisible(false);
		background = new CachedTileBackground(mode, new BufferedImage[] {
			new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB)
		}, new int[][] {
			{
				0
			}
		});
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.CachedTileBackground#update(long)}
	 * .
	 */
	public void testUpdate() {
		background.update(10);
		background.render(new MockGraphics2D());
		background.update(10);
		background.setLocation(10, 0);
		background.update(10);
		background.render(new MockGraphics2D());
		background.setLocation(10, 10);
		background.update(10);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.CachedTileBackground#render(java.awt.Graphics2D)}
	 * .
	 */
	public void testRenderGraphics2D() {
		background.render(new MockGraphics2D());
		background.render(new MockGraphics2D());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.CachedTileBackground#CachedTileBackground(com.golden.gamedev.engine.BaseGraphics, java.awt.image.BufferedImage[], int[][])}
	 * .
	 */
	public void testCachedTileBackground() {
		assertNotNull(background);
	}
	
	public CachedTileBackground getBackground() {
		return background;
	}
	
	public void setBackground(CachedTileBackground background) {
		this.background = background;
	}
	
}
