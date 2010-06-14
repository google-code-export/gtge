/**
 * 
 */
package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.BufferedImageHolder;
import com.golden.gamedev.MockBufferedImageHolder;
import com.golden.gamedev.object.MockGraphics2D;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.Background;
import com.golden.gamedev.object.background.BoundedBackground;

/**
 * The {@link PatternSpriteTest} provides a {@link TestCase} to test the
 * functionality of the {@link PatternSprite} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * 
 */
public final class PatternSpriteTest extends TestCase {
	
	/**
	 * The {@link PatternSprite} instance under test.
	 */
	private PatternSprite sprite;
	
	/**
	 * Creates a new {@link PatternSpriteTest} instance with the given name.
	 * @param name The {@link String} name of this {@link PatternSpriteTest}
	 *        instance.
	 */
	public PatternSpriteTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		sprite = new PatternSprite(new Sprite());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#render(java.awt.Graphics2D, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntInt() {
		Sprite delegateSprite = new Sprite();
		sprite = new PatternSprite(delegateSprite);
		MockGraphics2D g = new MockGraphics2D();
		sprite.render(g, 10, 10);
		assertNull(g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(10, g.lastDrawnX);
		assertEquals(10, g.lastDrawnY);
		
		BufferedImage image = new BufferedImage(640, 480,
		        BufferedImage.TYPE_INT_ARGB);
		delegateSprite.setImage(image);
		sprite.render(g, 20, 20);
		assertTrue(image == g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(20, g.lastDrawnX);
		assertEquals(20, g.lastDrawnY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#PatternSprite(BufferedImageHolder, double, double)}
	 * .
	 */
	public final void testPatternSpriteBufferedImageHolderDoubleDouble() {
		sprite = new PatternSprite(new Sprite(), 10, 10);
		assertNotNull(sprite);
		assertEquals(10, sprite.getX(), 0);
		assertEquals(10, sprite.getOldX(), 0);
		assertEquals(10, sprite.getY(), 0);
		assertEquals(10, sprite.getOldY(), 0);
		assertNull(sprite.getImage());
		assertEquals(0, sprite.getHeight());
		assertEquals(0, sprite.getWidth());
		assertEquals(BoundedBackground.getDefaultBackground(), sprite
		        .getBackground());
		assertEquals(0, sprite.getHorizontalSpeed(), 0);
		assertEquals(0, sprite.getVerticalSpeed(), 0);
		assertNotNull(sprite.getDefaultCollisionShape());
		assertEquals(0, sprite.getID());
		assertNull(sprite.getDataID());
		assertEquals(0, sprite.getLayer());
		assertTrue(sprite.isActive());
		assertFalse(sprite.isImmutable());
		assertTrue(sprite.isVisible());
		assertNotNull(sprite.getImageHolder());
		
		try {
			sprite = new PatternSprite(new MockBufferedImageHolder(), 10, 10);
			fail("Expected IllegalArgumentException - MockBufferedImageHolder is not Serializable.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#PatternSprite(BufferedImageHolder)}
	 * .
	 */
	public final void testPatternSpriteBufferedImageHolder() {
		assertNotNull(sprite);
		assertEquals(0, sprite.getX(), 0);
		assertEquals(0, sprite.getOldX(), 0);
		assertEquals(0, sprite.getY(), 0);
		assertEquals(0, sprite.getOldY(), 0);
		assertNull(sprite.getImage());
		assertEquals(0, sprite.getHeight());
		assertEquals(0, sprite.getWidth());
		assertEquals(BoundedBackground.getDefaultBackground(), sprite
		        .getBackground());
		assertEquals(0, sprite.getHorizontalSpeed(), 0);
		assertEquals(0, sprite.getVerticalSpeed(), 0);
		assertNotNull(sprite.getDefaultCollisionShape());
		assertEquals(0, sprite.getID());
		assertNull(sprite.getDataID());
		assertEquals(0, sprite.getLayer());
		assertTrue(sprite.isActive());
		assertFalse(sprite.isImmutable());
		assertTrue(sprite.isVisible());
		assertNotNull(sprite.getImageHolder());
		
		try {
			sprite = new PatternSprite(new MockBufferedImageHolder());
			fail("Expected IllegalArgumentException - MockBufferedImageHolder is not Serializable.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#getPattern()}.
	 */
	public final void testGetPattern() {
		assertNotNull(sprite.getPattern());
		Sprite pattern = new Sprite();
		sprite.setPattern(pattern);
		assertTrue(pattern == sprite.getPattern());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#setPattern(com.golden.gamedev.object.Sprite)}
	 * .
	 */
	public final void testSetPattern() {
		assertNotNull(sprite.getPattern());
		Sprite pattern = new Sprite();
		sprite.setPattern(pattern);
		assertTrue(pattern == sprite.getPattern());
		sprite.setPattern(null);
		assertNull(sprite.getPattern());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.PatternSprite#getImageHolder()}.
	 */
	public void testGetImageHolder() {
		assertNotNull(sprite.getImageHolder());
	}
	
}
