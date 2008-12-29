/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Color;

import com.golden.mock.java.awt.MockGraphics2D;

import junit.framework.TestCase;

/**
 * @author MetroidFan2002
 * 
 */
public class ColorBackgroundTest extends TestCase {
	
	private ColorBackground background;
	
	protected void setUp() throws Exception {
		background = new ColorBackground(null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public void testRenderGraphics2DIntIntIntIntIntInt() {
		background = new ColorBackground(Color.RED);
		background.render(new MockGraphics2D(), 1, 2, 3, 4, 5, 6);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#ColorBackground(java.awt.Color, int, int)}
	 * .
	 */
	public void testColorBackgroundColorIntInt() {
		background = new ColorBackground(Color.BLACK, 30, 50);
		assertNotNull(background);
		assertEquals(Color.BLACK, background.getColor());
		assertEquals(30, background.getWidth());
		assertEquals(50, background.getHeight());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#ColorBackground(java.awt.Color)}
	 * .
	 */
	public void testColorBackgroundColor() {
		assertNotNull(background);
		background = new ColorBackground(Color.BLUE);
		assertNotNull(background);
		assertEquals(Color.BLUE, background.getColor());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#getColor()}.
	 */
	public void testGetColor() {
		assertNull(background.getColor());
		background.setColor(Color.GREEN);
		assertEquals(Color.GREEN, background.getColor());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#setColor(java.awt.Color)}
	 * .
	 */
	public void testSetColor() {
		background.setColor(Color.GREEN);
		assertEquals(Color.GREEN, background.getColor());
	}
	
	public ColorBackground getBackground() {
		return background;
	}
	
	public void setBackground(ColorBackground background) {
		this.background = background;
	}
	
}
