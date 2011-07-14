package com.golden.gamedev.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import org.junit.Test;

// REVIEW-HIGH: Finish this test!
/**
 * The {@link BufferedImageUtilTest} class provides a test case to test the {@link BufferedImageUtil} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * @see BufferedImageUtil
 * 
 */
public class BufferedImageUtilTest {
	
	/**
	 * Test method for the private constructor of the {@link BufferedImageUtil} class.
	 * 
	 * @throws Throwable
	 */
	@Test(expected = UnsupportedOperationException.class)
	public final void testBufferedImageUtil() throws Throwable {
		@SuppressWarnings("unchecked")
		Constructor<BufferedImageUtil> constructor = (Constructor<BufferedImageUtil>) BufferedImageUtil.class
				.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		try {
			constructor.newInstance((Object[]) null);
		} catch (Exception e) {
			throw e.getCause();
		}
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#createImage(int, int, int)}.
	 */
	@Test
	public final void testCreateImage() {
		BufferedImage image = BufferedImageUtil.createImage(640, 480, Transparency.BITMASK);
		assertNotNull("Expected a non-null image", image);
		assertEquals("Expected the width to be 640", 640, image.getWidth());
		assertEquals("Expected the height to be 480", 480, image.getHeight());
		assertEquals("Expected the transparency to be Bitmask", Transparency.BITMASK, image.getTransparency());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#applyMask(BufferedImage, java.awt.Color)}.
	 */
	@Test
	public final void testApplyMask() {
		BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_4BYTE_ABGR);
		final Color[][] defaultColors = { { Color.RED, Color.YELLOW }, { Color.GREEN, Color.BLUE } };
		applyColors(image, defaultColors);
		equalsForPixels(image, defaultColors);
		
		image = BufferedImageUtil.applyMask(image, Color.YELLOW);
		final Color[][] expectedColors1 = { { Color.RED, new Color(Color.YELLOW.getRGB() & 0x00ffffff, true) },
				{ Color.GREEN, Color.BLUE } };
		equalsForPixels(image, expectedColors1);
		
		image = BufferedImageUtil.applyMask(image, Color.BLUE);
		final Color[][] expectedColors2 = { { Color.RED, new Color(Color.YELLOW.getRGB() & 0x00ffffff, true) },
				{ Color.GREEN, new Color(Color.BLUE.getRGB() & 0x00ffffff, true) } };
		equalsForPixels(image, expectedColors2);
		
	}
	
	private static void applyColors(final BufferedImage image, final Color[][] colors) {
		for (int rowIndex = 0; rowIndex < colors[0].length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < colors[rowIndex].length; columnIndex++) {
				image.setRGB(columnIndex, rowIndex, colors[rowIndex][columnIndex].getRGB());
			}
		}
	}
	
	private static void equalsForPixels(final BufferedImage image, final Color[][] colors) {
		for (int rowIndex = 0; rowIndex < colors[0].length; rowIndex++) {
			for (int columnIndex = 0; columnIndex < colors[rowIndex].length; columnIndex++) {
				assertEquals("Expected the given color for index " + rowIndex + ", " + columnIndex + ", was "
						+ new Color(image.getRGB(columnIndex, rowIndex)) + " but expected "
						+ colors[rowIndex][columnIndex], colors[rowIndex][columnIndex].getRGB(),
						image.getRGB(columnIndex, rowIndex));
			}
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.util.BufferedImageUtil#splitImages(java.awt.image.BufferedImage, int, int)}.
	 */
	@Test
	public final void testSplitImages() {
		BufferedImage source = new BufferedImage(2, 2, BufferedImage.TYPE_3BYTE_BGR);
		BufferedImage[] array = BufferedImageUtil.splitImages(source, 2, 2);
		assertEquals("Expected an array with a length of 4", 4, array.length);
		array = BufferedImageUtil.splitImages(source, 1, 1);
		assertEquals("Expected an array with a length of 1", 1, array.length);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#rotate(java.awt.image.BufferedImage, int)}.
	 */
	@Test
	public final void testRotate() {
		BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_4BYTE_ABGR);
		final Color[][] defaultColors = { { Color.RED, Color.YELLOW }, { Color.GREEN, Color.BLUE } };
		applyColors(image, defaultColors);
		equalsForPixels(image, defaultColors);
		
		image = BufferedImageUtil.rotate(image, 45);
		final Color[][] expectedColors = { { new Color(255, 30, 0), new Color(0, 0, 0, 0) },
				{ new Color(214, 205, 39), new Color(0, 0, 0, 0) }, { new Color(214, 205, 39), new Color(0, 0, 0, 0) } };
		equalsForPixels(image, expectedColors);
		assertEquals("Expected the height to be 3", 3, image.getHeight());
		assertEquals("Expected the width to be 2", 2, image.getWidth());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#resize(java.awt.image.BufferedImage, int, int)}.
	 */
	@Test
	public final void testResize() {
		// TODO: Implement
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#flipHorizontal(java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testFlipHorizontal() {
		// TODO: Implement
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.util.BufferedImageUtil#flipVertical(java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testFlipVertical() {
		// TODO: Implement
	}
	
}
