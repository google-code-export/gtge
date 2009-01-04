/**
 * 
 */
package com.golden.gamedev.object.background;

import com.golden.gamedev.object.Background;
import com.golden.mock.java.awt.MockGraphics2D;

import junit.framework.TestCase;

/**
 * @author MetroidFan2002
 * 
 */
public class ParallaxBackgroundTest extends TestCase {
	
	private ParallaxBackground background;
	
	protected void setUp() throws Exception {
		Background[] backgrounds = {
			new Background()
		};
		background = new ParallaxBackground(backgrounds);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#setLocation(double, double)}
	 * .
	 */
	public void testSetLocation() {
		background.setLocation(300, 200);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#update(long)}
	 * .
	 */
	public void testUpdate() {
		background.update(10);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#render(java.awt.Graphics2D)}
	 * .
	 */
	public void testRenderGraphics2D() {
		background.render(new MockGraphics2D());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#ParallaxBackground(com.golden.gamedev.object.Background[])}
	 * .
	 */
	public void testParallaxBackground() {
		assertNotNull(background);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#getParallaxBackground()}
	 * .
	 */
	public void testGetParallaxBackground() {
		assertEquals(1, background.getParallaxBackground().length);
		background.setParallaxBackground(new Background[] {
		        new Background(), new Background(800, 600)
		});
		assertEquals(2, background.getParallaxBackground().length);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ParallaxBackground#setParallaxBackground(com.golden.gamedev.object.Background[])}
	 * .
	 */
	public void testSetParallaxBackground() {
		background.setParallaxBackground(new Background[] {
		        new Background(), new Background(800, 600)
		});
		assertEquals(2, background.getParallaxBackground().length);
	}
	
	public ParallaxBackground getBackground() {
		return background;
	}
	
	public void setBackground(ParallaxBackground background) {
		this.background = background;
	}
	
}
