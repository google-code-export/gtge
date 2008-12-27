/**
 * 
 */
package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

/**
 * @author MetroidFan2002
 * 
 */
public class AdvanceSpriteTest extends TestCase {
	
	private AdvanceSprite sprite;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		sprite = new AdvanceSprite();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getImage()}.
	 */
	public void testGetImage() {
		try {
			sprite.getImage();
			fail("Expected NPE for null image array");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
		sprite.setImages(new BufferedImage[] {new BufferedImage(3, 4, BufferedImage.TYPE_INT_RGB)});
		assertNotNull(sprite.getImage());
		sprite.setAnimationFrame(new int[] {0});
		assertNotNull(sprite.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setImages(java.awt.image.BufferedImage[])}
	 * .
	 */
	public void testSetImages() {
		assertNull(sprite.getImages());
		sprite.setImages(new BufferedImage[] {
			new BufferedImage(3, 4, BufferedImage.TYPE_INT_RGB)
		});
		assertNotNull(sprite.getImages());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(java.awt.image.BufferedImage[], double, double)}
	 * .
	 */
	public void testAdvanceSpriteBufferedImageArrayDoubleDouble() {
		sprite = new AdvanceSprite(null, 4, 5);
		assertNotNull(sprite);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(java.awt.image.BufferedImage[])}
	 * .
	 */
	public void testAdvanceSpriteBufferedImageArray() {
		sprite = new AdvanceSprite(null);
		assertNotNull(sprite);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(double, double)}
	 * .
	 */
	public void testAdvanceSpriteDoubleDouble() {
		sprite = new AdvanceSprite(5, 6);
		assertNotNull(sprite);
		assertEquals(5, (int) sprite.getX());
		assertEquals(6, (int) sprite.getY());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite()}.
	 */
	public void testAdvanceSprite() {
		assertNotNull(sprite);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setAnimationFrame(int[])}
	 * .
	 */
	public void testSetAnimationFrameIntArray() {
		int[] array = new int[3];
		sprite.setAnimationFrame(array);
		assertNotNull(sprite.getAnimationFrame());
		assertEquals(2, sprite.getFinishAnimationFrame());
		sprite.setAnimationFrame(null);
		assertEquals(0, sprite.getFinishAnimationFrame());
		sprite.setImages(new BufferedImage[] {
		        new BufferedImage(2, 3, BufferedImage.TYPE_INT_RGB),
		        new BufferedImage(2, 3, BufferedImage.TYPE_INT_RGB)
		});
		sprite.setAnimationFrame(null);
		assertEquals(1, sprite.getFinishAnimationFrame());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getAnimationFrame()}
	 * .
	 */
	public void testGetAnimationFrame() {
		assertNull(sprite.getAnimationFrame());
		sprite.setAnimationFrame(new int[3]);
		assertNotNull(sprite.getAnimationFrame());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#animationChanged(int, int, int, int)}
	 * .
	 */
	public void testAnimationChanged() {
		sprite.animationChanged(4, 3, 4, 3);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setDirection(int)}.
	 */
	public void testSetDirection() {
		sprite.setDirection(4);
		assertEquals(4, sprite.getDirection());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getDirection()}.
	 */
	public void testGetDirection() {
		assertEquals(-1, sprite.getDirection());
		sprite.setDirection(4);
		assertEquals(4, sprite.getDirection());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setStatus(int)}.
	 */
	public void testSetStatus() {
		sprite.setStatus(4);
		assertEquals(4, sprite.getStatus());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getStatus()}.
	 */
	public void testGetStatus() {
		assertEquals(-1, sprite.getStatus());
		sprite.setStatus(4);
		assertEquals(4, sprite.getStatus());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setAnimation(int, int)}
	 * .
	 */
	public void testSetAnimation() {
		sprite.setAnimation(3, 4);
		// This call will not call animation changed.  Included for code coverage.
		sprite.setAnimation(3, 4);
	}
	
	public AdvanceSprite getSprite() {
		return sprite;
	}
	
	public void setSprite(AdvanceSprite sprite) {
		this.sprite = sprite;
	}
	
}
