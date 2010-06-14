/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.object.MockGraphics2D;

/**
 * The {@link ImageBackgroundTest} class provides a {@link TestCase} to test the
 * functionality of the {@link ImageBackground} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public final class ImageBackgroundTest extends TestCase {
	
	/**
	 * The {@link ImageBackground} instance under test.
	 */
	private ImageBackground background;
	
	/**
	 * The {@link BufferedImage} instance stored into the {@link #background
	 * background instance under test} by default.
	 */
	private BufferedImage image;
	
	/**
	 * Creates a new {@link ImageBackgroundTest} instance with the given name.
	 * @param name The {@link String} name of this {@link ImageBackgroundTest}
	 *        instance.
	 */
	public ImageBackgroundTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		background = new ImageBackground(image);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntIntIntIntIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		background.render(g, 10, 20, 30, 40, 50, 60);
		assertTrue(image == g.lastDrawnImage);
		// ImageBackground doesn't use an image observer.
		assertNull(g.lastDrawnImageObserver);
		
		// Destination - where it's going to be physically drawn (x, y, x+width,
		// y+height
		assertEquals(30, g.lastDestinationRectangleFirstX);
		assertEquals(40, g.lastDestinationRectangleFirstY);
		assertEquals(80, g.lastDestinationRectangleSecondX);
		assertEquals(100, g.lastDestinationRectangleSecondY);
		
		// Source - where the background actually is (the background actually
		// starts 10 pixels off the start here, so only start drawing it from
		// its first 10 pixels, etc) - width and height calculations are ok,
		// they're allowed to exceed the actual width and height of the
		// background - same as before (x + width, y + height)
		assertEquals(10, g.lastSourceRectangleFirstX);
		assertEquals(20, g.lastSourceRectangleFirstY);
		assertEquals(60, g.lastSourceRectangleSecondX);
		assertEquals(80, g.lastSourceRectangleSecondY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#ImageBackground(java.awt.image.BufferedImage, int, int)}
	 * .
	 */
	public final void testImageBackgroundBufferedImageIntInt() {
		background = new ImageBackground(image, 320, 240);
		assertNotNull(background);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(240, background.getHeight());
		assertEquals(320, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(BoundedBackground.screen.height, clip.getHeight(), 0);
		assertEquals(BoundedBackground.screen.width, clip.getWidth(), 0);
		assertTrue(image == background.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#ImageBackground(java.awt.image.BufferedImage)}
	 * .
	 */
	public final void testImageBackgroundBufferedImage() {
		assertNotNull(background);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(600, background.getHeight());
		assertEquals(800, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(BoundedBackground.screen.height, clip.getHeight(), 0);
		assertEquals(BoundedBackground.screen.width, clip.getWidth(), 0);
		assertTrue(image == background.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#getImage()}.
	 */
	public final void testGetImage() {
		assertTrue(image == background.getImage());
		BufferedImage secondImage = new BufferedImage(640, 480,
		        BufferedImage.TYPE_INT_ARGB);
		background.setImage(secondImage);
		assertTrue(secondImage == background.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#setImage(java.awt.image.BufferedImage)}
	 * .
	 */
	public final void testSetImage() {
		BufferedImage secondImage = new BufferedImage(640, 480,
		        BufferedImage.TYPE_INT_ARGB);
		background.setImage(secondImage);
		assertTrue(secondImage == background.getImage());
	}
	
}
