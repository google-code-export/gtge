package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

/**
 * The {@link VolatileSpriteTest} class provides a {@link TestCase} that tests
 * the functionality of the {@link VolatileSprite} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * @see VolatileSprite
 * @see TestCase
 * 
 */
public class VolatileSpriteTest extends TestCase {
	
	/**
	 * The {@link VolatileSprite} instance under test.
	 */
	private VolatileSprite sprite;
	
	private BufferedImage[] array;
	
	protected void setUp() throws Exception {
		array = new BufferedImage[] {
		        new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR),
		        new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR)
		};
		sprite = new VolatileSprite(array, 10, 20);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.VolatileSprite#update(long)}.
	 */
	public final void testUpdate() {
		sprite.update(100);
		assertTrue(sprite.isActive());
		sprite.update(100);
		assertFalse(sprite.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.VolatileSprite#VolatileSprite(java.awt.image.BufferedImage[], double, double)}
	 * .
	 */
	public final void testVolatileSprite() {
		assertNotNull(sprite);
		assertEquals(array, sprite.getImages());
		assertEquals(10, sprite.getX(), 0.0000001);
		assertEquals(20, sprite.getY(), 0.0000001);
		assertTrue(sprite.isActive());
		assertTrue(sprite.isAnimate());
	}
	
}
