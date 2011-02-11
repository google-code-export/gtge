package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import mock.java.awt.MockGraphics2D;

import junit.framework.TestCase;

/**
 * The {@link AdvanceSpriteTest} class provides a {@link TestCase} to test the
 * functionality of the {@link AdvanceSprite} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * @see AdvanceSprite
 * @see TestCase
 */
public class AdvanceSpriteTest extends TestCase {
	
	/**
	 * The {@link AdvanceSprite} instance under test.
	 */
	private AdvanceSprite sprite;
	
	protected void setUp() throws Exception {
		sprite = new AdvanceSprite();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getImage()}.
	 */
	public final void testGetImage() {
		try {
			sprite.getImage();
			fail("Expected NullPointerException - no images set!");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
		final BufferedImage image = createControlImage();
		sprite.setImage(image);
		assertEquals(image, sprite.getImage());
		
		// Testing for animation frame - good case (frame = 0)
		sprite.setAnimationFrame(new int[] {
			0
		});
		assertEquals(image, sprite.getImage());
		
		// Testing for animation frame - bad case (frame = -1)
		sprite.setAnimationFrame(-1, 3);
		try {
			sprite.getImage();
			fail("Expected ArrayIndexOutOfBoundsException - frame was negative");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank
		}
		
		// Testing for bad animation frame reference - frame restored to 0.
		sprite.setAnimationFrame(0, 0);
		sprite.setAnimationFrame(new int[] {
			-1
		});
		try {
			sprite.getImage();
			fail("Expected ArrayIndexOutOfBoundsException - animation frame was negative");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * @return
	 */
	private BufferedImage createControlImage() {
		final BufferedImage image = new BufferedImage(640, 480,
		        BufferedImage.TYPE_3BYTE_BGR);
		return image;
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#render(java.awt.Graphics2D, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		try {
			sprite.render(g, 50, 50);
			fail("Expected NullPointerException - no images set.");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
		final BufferedImage image = createControlImage();
		sprite.setImage(image);
		sprite.render(g, 50, 50);
		assertEquals(50, g.lastDrawnX);
		assertEquals(50, g.lastDrawnY);
		assertEquals(image, g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
		
		try {
			sprite.render(null, 50, 50);
			fail("Expected NullPointerException - no Graphics2D instance to draw on.");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
		
		// Test ArrayIndexOutOfBounds exception with bad frame.
		sprite.setAnimationFrame(-1, 2);
		try {
			sprite.render(g, 50, 50);
			fail("Expected ArrayIndexOutOfBoundsException - invalid frame.");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank
		}
		
		sprite.setAnimationFrame(new int[] {
			0
		});
		sprite.setAnimationFrame(-1, 2);
		try {
			sprite.render(g, 50, 50);
			fail("Expected ArrayIndexOutOfBoundsException - invalid frame.");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank
		}
		
		// Good frame, but bad animation frame.
		sprite.setAnimationFrame(0, 0);
		sprite.setAnimationFrame(new int[] {
			-1
		});
		try {
			sprite.render(g, 50, 50);
			fail("Expected ArrayIndexOutOfBoundsException - invalid animation frame.");
		}
		catch (ArrayIndexOutOfBoundsException e) {
			// Intentionally blank
		}
		
		sprite.setAnimationFrame(new int[] {
			0
		});
		sprite.render(g, 50, 50);
		assertEquals(50, g.lastDrawnX);
		assertEquals(50, g.lastDrawnY);
		assertEquals(image, g.lastDrawnImage);
		assertNull(g.lastDrawnImageObserver);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setImages(java.awt.image.BufferedImage[])}
	 * .
	 */
	public final void testSetImages() {
		final BufferedImage image = createControlImage();
		final BufferedImage[] images = new BufferedImage[] {
			image
		};
		sprite.setImages(images);
		assertEquals(images, sprite.getImages());
		sprite.setAnimationFrame(new int[] {
		        3, 5
		});
		sprite.setImages(images);
		assertEquals(images, sprite.getImages());
		assertEquals(0, sprite.getStartAnimationFrame());
		assertEquals(1, sprite.getFinishAnimationFrame());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(java.awt.image.BufferedImage[], double, double)}
	 * .
	 */
	public final void testAdvanceSpriteBufferedImageArrayDoubleDouble() {
		final BufferedImage[] array = new BufferedImage[] {
			createControlImage()
		};
		sprite = new AdvanceSprite(array, 20, 30);
		assertEquals(array, sprite.getImages());
		assertEquals(20, sprite.getX(), 0.000001);
		assertEquals(30, sprite.getY(), 0.000001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(java.awt.image.BufferedImage[])}
	 * .
	 */
	public final void testAdvanceSpriteBufferedImageArray() {
		final BufferedImage[] array = new BufferedImage[] {
			createControlImage()
		};
		sprite = new AdvanceSprite(array);
		assertEquals(array, sprite.getImages());
		assertEquals(0, sprite.getX(), 0.000001);
		assertEquals(0, sprite.getY(), 0.000001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite(double, double)}
	 * .
	 */
	public final void testAdvanceSpriteDoubleDouble() {
		sprite = new AdvanceSprite(10, 20);
		assertNotNull(sprite);
		assertNull(sprite.getImages());
		assertEquals(10, sprite.getX(), 0.000001);
		assertEquals(20, sprite.getY(), 0.000001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#AdvanceSprite()}.
	 */
	public final void testAdvanceSprite() {
		assertNotNull(sprite);
		assertNull(sprite.getImages());
		assertEquals(0, sprite.getX(), 0.000001);
		assertEquals(0, sprite.getY(), 0.000001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setAnimationFrame(int[])}
	 * .
	 */
	public final void testSetAnimationFrameIntArray() {
		int[] animation = new int[] {
			0
		};
		sprite.setAnimationFrame(animation);
		assertEquals(animation, sprite.getAnimationFrame());
		assertEquals(0, sprite.getStartAnimationFrame());
		assertEquals(0, sprite.getFinishAnimationFrame());
		sprite.setAnimationFrame(animation);
		assertEquals(animation, sprite.getAnimationFrame());
		assertEquals(0, sprite.getStartAnimationFrame());
		assertEquals(0, sprite.getFinishAnimationFrame());
		
		animation = new int[] {
			1
		};
		try {
			sprite.setAnimationFrame(null);
			fail("Expected NullPointerException - no images set yet.");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
		
		sprite.setImage(createControlImage());
		sprite.setAnimationFrame(animation);
		assertEquals(animation, sprite.getAnimationFrame());
		assertEquals(0, sprite.getStartAnimationFrame());
		assertEquals(0, sprite.getFinishAnimationFrame());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getAnimationFrame()}
	 * .
	 */
	public final void testGetAnimationFrame() {
		assertNull(sprite.getAnimationFrame());
		final int[] animation = new int[] {
			0
		};
		sprite.setAnimationFrame(animation);
		assertEquals(animation, sprite.getAnimationFrame());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setDirection(int)}.
	 */
	public final void testSetDirection() {
		sprite.setDirection(1);
		assertEquals(1, sprite.getDirection());
		sprite.setDirection(1);
		assertEquals(1, sprite.getDirection());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getDirection()}.
	 */
	public final void testGetDirection() {
		assertEquals(-1, sprite.getDirection());
		sprite.setDirection(1);
		assertEquals(1, sprite.getDirection());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setStatus(int)}.
	 */
	public final void testSetStatus() {
		sprite.setStatus(1);
		assertEquals(1, sprite.getStatus());
		sprite.setStatus(1);
		assertEquals(1, sprite.getStatus());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#getStatus()}.
	 */
	public final void testGetStatus() {
		assertEquals(-1, sprite.getStatus());
		sprite.setStatus(1);
		assertEquals(1, sprite.getStatus());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.AdvanceSprite#setAnimation(int, int)}
	 * .
	 */
	public final void testSetAnimation() {
		sprite.setAnimation(2, 4);
		assertEquals(2, sprite.getStatus());
		assertEquals(4, sprite.getDirection());
		sprite.setAnimation(2, 4);
		assertEquals(2, sprite.getStatus());
		assertEquals(4, sprite.getDirection());
		sprite.setAnimation(2, 3);
		assertEquals(2, sprite.getStatus());
		assertEquals(3, sprite.getDirection());
	}
	
}
