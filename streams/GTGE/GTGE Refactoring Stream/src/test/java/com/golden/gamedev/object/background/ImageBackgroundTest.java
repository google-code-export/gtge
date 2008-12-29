/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.image.BufferedImage;

import com.golden.mock.java.awt.MockGraphics2D;

import junit.framework.TestCase;

/**
 * @author MetroidFan2002
 * 
 */
public class ImageBackgroundTest extends TestCase {
	
	private ImageBackground background;
	
	protected void setUp() throws Exception {
		background = new ImageBackground(new BufferedImage(2, 4,
		        BufferedImage.TYPE_INT_RGB));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public void testRenderGraphics2DIntIntIntIntIntInt() {
		background.render(new MockGraphics2D(), 1, 2, 3, 4, 5, 6);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#ImageBackground(java.awt.image.BufferedImage, int, int)}
	 * .
	 */
	public void testImageBackgroundBufferedImageIntInt() {
		BufferedImage image = new BufferedImage(3, 4,
		        BufferedImage.TYPE_INT_RGB);
		background = new ImageBackground(image, 50, 60);
		assertNotNull(background);
		assertEquals(image, background.getImage());
		assertEquals(50, background.getWidth());
		assertEquals(60, background.getHeight());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#ImageBackground(java.awt.image.BufferedImage)}
	 * .
	 */
	public void testImageBackgroundBufferedImage() {
		assertNotNull(background);
		BufferedImage image = new BufferedImage(3, 4,
		        BufferedImage.TYPE_INT_RGB);
		background = new ImageBackground(image);
		assertNotNull(background);
		assertEquals(image, background.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#getImage()}.
	 */
	public void testGetImage() {
		assertNotNull(background.getImage());
		BufferedImage image = new BufferedImage(3, 4,
		        BufferedImage.TYPE_INT_RGB);
		background.setImage(image);
		assertEquals(image, background.getImage());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.background.ImageBackground#setImage(java.awt.image.BufferedImage)}
	 * .
	 */
	public void testSetImage() {
		BufferedImage image = new BufferedImage(3, 4,
		        BufferedImage.TYPE_INT_RGB);
		background.setImage(image);
		assertEquals(image, background.getImage());
	}
	
	public ImageBackground getBackground() {
		return background;
	}
	
	public void setBackground(ImageBackground background) {
		this.background = background;
	}
	
}
