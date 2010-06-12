/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Color;
import java.awt.Rectangle;

import junit.framework.TestCase;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.MockGraphics2D;

/**
 * The {@link ColorBackgroundTest} class provides a {@link TestCase} that tests
 * the behavior of the {@link ColorBackground} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * 
 */
public class ColorBackgroundTest extends TestCase {
	
	/**
	 * The {@link ColorBackground} instance under test.
	 */
	private ColorBackground background;
	
	/**
	 * Creates a new {@link ColorBackgroundTest} instance with the given name.
	 * @param name The {@link String} name of this {@link ColorBackgroundTest}
	 *        instance.
	 */
	public ColorBackgroundTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		this.background = new ColorBackground(null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntIntIntIntIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		g.setColor(Color.RED);
		background.setColor(Color.BLUE);
		background.render(g, 1, 2, 50, 100, 150, 200);
		assertEquals(50, g.lastFillX);
		assertEquals(100, g.lastFillY);
		assertEquals(150, g.lastFillWidth);
		assertEquals(200, g.lastFillHeight);
		assertTrue(Color.RED == g.getColor());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#ColorBackground(java.awt.Color, int, int)}
	 * .
	 */
	public final void testColorBackgroundColorIntInt() {
		background = new ColorBackground(null, 100, 100);
		assertNotNull(background);
		assertNull(background.getColor());
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(100, background.getHeight());
		assertEquals(100, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		
		background = new ColorBackground(Color.BLUE, 100, 100);
		assertNotNull(background);
		assertTrue(Color.BLUE == background.getColor());
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(100, background.getHeight());
		assertEquals(100, background.getWidth());
		clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#ColorBackground(java.awt.Color)}
	 * .
	 */
	public final void testColorBackgroundColor() {
		assertNotNull(background);
		assertNull(background.getColor());
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		
		background = new ColorBackground(Color.RED);
		assertTrue(Color.RED == background.getColor());
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#getColor()}.
	 */
	public final void testGetColor() {
		assertNull(background.getColor());
		background.setColor(Color.RED);
		assertTrue(Color.RED == background.getColor());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ColorBackground#setColor(java.awt.Color)}
	 * .
	 */
	public final void testSetColor() {
		assertNull(background.getColor());
		background.setColor(Color.RED);
		assertTrue(Color.RED == background.getColor());
		background.setColor(Color.BLUE);
		assertTrue(Color.BLUE == background.getColor());
	}
}
