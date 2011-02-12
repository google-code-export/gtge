package com.golden.gamedev.object.sprite;

import mock.java.awt.MockGraphics2D;
import junit.framework.TestCase;

/**
 * The {@link InvisibleSpriteTest} class provides a {@link TestCase} extension
 * to test the behavior of the {@link InvisibleSprite} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * @see InvisibleSprite
 * @see TestCase
 * 
 */
public class InvisibleSpriteTest extends TestCase {
	
	/**
	 * The {@link InvisibleSprite} instance under test.
	 */
	private InvisibleSprite sprite;
	
	protected void setUp() throws Exception {
		sprite = new InvisibleSprite(10, 20, 30, 40);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#render(java.awt.Graphics2D)}
	 * .
	 */
	public final void testRenderGraphics2D() {
		MockGraphics2D g = new MockGraphics2D();
		sprite.render(g);
		assertNull(g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(0, g.lastDrawnX);
		assertEquals(0, g.lastDrawnY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#render(java.awt.Graphics2D, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		sprite.render(g, 100, 200);
		assertNull(g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(0, g.lastDrawnX);
		assertEquals(0, g.lastDrawnY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite(double, double, int, int)}
	 * .
	 */
	public final void testInvisibleSprite() {
		assertNotNull(sprite);
		assertEquals(10, sprite.getX(), 0.000001);
		assertEquals(20, sprite.getY(), 0.000001);
		assertEquals(30, sprite.getWidth(), 0.000001);
		assertEquals(40, sprite.getHeight(), 0.000001);
	}
	
}
