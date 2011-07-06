package com.golden.gamedev.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import com.golden.gamedev.Game;

/**
 * The {@link BaseLoaderTest} class provides a test case to test the behavior of the {@link BaseLoader} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * @see BaseLoader
 * 
 */
public class BaseLoaderTest {
	
	/**
	 * The {@link BaseLoader} instance under test.
	 */
	private BaseLoader loader;
	
	@Before
	public void setUp() {
		loader = new BaseLoader(new BaseIO(Game.class));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#BaseLoader(com.golden.gamedev.engine.BaseIO)}.
	 */
	@Test
	public final void testBaseLoader() {
		assertNotNull(loader);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getImage(java.lang.String, java.awt.Color)}.
	 */
	@Test
	public final void testGetImage() {
		BufferedImage image = loader.getImage("com/golden/gamedev/engine/graphics/Icon.png", null);
		assertNotNull(image);
		BufferedImage second = loader.getImage("com/golden/gamedev/engine/graphics/Icon.png", null);
		assertTrue(image == second);
		loader.clearCache();
		image = loader.getImage("com/golden/gamedev/engine/graphics/Icon.png", Color.MAGENTA);
		assertNotNull(image);
		second = loader.getImage("com/golden/gamedev/engine/graphics/Icon.png", Color.MAGENTA);
		assertTrue(image == second);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getImage(java.lang.String, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImageNullFileName() {
		loader.getImage(null, null);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getImage(java.lang.String, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImageEmptyFileName() {
		loader.getImage("", null);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getImage(java.lang.String, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImageBlankFileName() {
		loader.getImage("   ", null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImagesNullFileName() {
		loader.getImages(null, 1, 1, null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImagesEmptyFileName() {
		loader.getImages("", 1, 1, null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImagesBlankFileName() {
		loader.getImages("   ", 1, 1, null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImagesBadRow() {
		loader.getImages("hi", 1, -4, null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test
	public final void testGetImages() {
		BufferedImage[] image = loader.getImages("com/golden/gamedev/engine/graphics/Icon.png", 1, 1, null);
		assertNotNull(image);
		BufferedImage[] second = loader.getImages("com/golden/gamedev/engine/graphics/Icon.png", 1, 1, null);
		assertTrue(image == second);
		loader.clearCache();
		image = loader.getImages("com/golden/gamedev/engine/graphics/Icon.png", 1, 1, Color.MAGENTA);
		assertNotNull(image);
		second = loader.getImages("com/golden/gamedev/engine/graphics/Icon.png", 1, 1, Color.MAGENTA);
		assertTrue(image == second);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#getImages(java.lang.String, int, int, java.awt.Color)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetImagesBadColumn() {
		loader.getImages("hi", 0, 1, null);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImage(java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testRemoveImageBufferedImage() {
		BufferedImage toRemove = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
		loader.storeImage("ha", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		loader.storeImage("toRemove", toRemove);
		loader.storeImage("na", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		assertNotNull(loader.getStoredImage("ha"));
		assertNotNull(loader.getStoredImage("toRemove"));
		assertNotNull(loader.getStoredImage("na"));
		assertTrue(loader.removeImage(toRemove));
		assertFalse(loader.removeImage(new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR)));
		assertNotNull(loader.getStoredImage("ha"));
		assertNull(loader.getStoredImage("toRemove"));
		assertNotNull(loader.getStoredImage("na"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImages(java.awt.image.BufferedImage[])}.
	 */
	@Test
	public final void testRemoveImagesBufferedImageArray() {
		BufferedImage[] toRemove = new BufferedImage[5];
		loader.storeImages("ha", new BufferedImage[5]);
		loader.storeImages("toRemove", toRemove);
		loader.storeImages("na", new BufferedImage[5]);
		assertNotNull(loader.getStoredImages("ha"));
		assertNotNull(loader.getStoredImages("toRemove"));
		assertNotNull(loader.getStoredImages("na"));
		assertTrue(loader.removeImages(toRemove));
		assertFalse(loader.removeImages(new BufferedImage[3]));
		assertNotNull(loader.getStoredImages("ha"));
		assertNull(loader.getStoredImages("toRemove"));
		assertNotNull(loader.getStoredImages("na"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImage(java.lang.String)}.
	 */
	@Test
	public final void testRemoveImageString() {
		loader.storeImage("blah", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		assertNotNull(loader.getStoredImage("blah"));
		assertNotNull(loader.removeImage("blah"));
		assertNull(loader.getStoredImage("blah"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImages(java.lang.String)}.
	 */
	@Test
	public final void testRemoveImagesString() {
		loader.storeImages("blah2", new BufferedImage[5]);
		assertNotNull(loader.getStoredImages("blah2"));
		assertNotNull(loader.removeImages("blah2"));
		assertNull(loader.getStoredImages("blah2"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#clearCache()}.
	 */
	@Test
	public final void testClearCache() {
		loader.storeImage("blah", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		loader.storeImages("blah2", new BufferedImage[5]);
		assertNotNull(loader.getStoredImage("blah"));
		assertNotNull(loader.getStoredImages("blah2"));
		loader.clearCache();
		assertNull(loader.getStoredImage("blah"));
		assertNull(loader.getStoredImages("blah2"));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#storeImage(java.lang.String, java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testStoreImage() {
		assertNull(loader.getStoredImage("blah"));
		loader.storeImage("blah", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		assertNotNull(loader.getStoredImage("blah"));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#storeImages(java.lang.String, java.awt.image.BufferedImage[])}.
	 */
	@Test
	public final void testStoreImages() {
		assertNull(loader.getStoredImages("blah"));
		loader.storeImages("blah", new BufferedImage[3]);
		assertNotNull(loader.getStoredImages("blah"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getStoredImage(java.lang.String)}.
	 */
	@Test
	public final void testGetStoredImage() {
		assertNull(loader.getStoredImage("blah"));
		loader.storeImage("blah", new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR));
		assertNotNull(loader.getStoredImage("blah"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getStoredImages(java.lang.String)}.
	 */
	@Test
	public final void testGetStoredImages() {
		assertNull(loader.getStoredImages("blah"));
		loader.storeImages("blah", new BufferedImage[3]);
		assertNotNull(loader.getStoredImages("blah"));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#getBaseIO()}.
	 */
	@Test
	public final void testGetBaseIO() {
		assertNotNull(loader.getBaseIO());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#setBaseIO(com.golden.gamedev.engine.BaseIO)}.
	 */
	@Test
	public final void testSetBaseIO() {
		loader.setBaseIO(new BaseIO(Game.class));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#setBaseIO(com.golden.gamedev.engine.BaseIO)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testSetBaseIONull() {
		loader.setBaseIO(null);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#toString()}.
	 */
	@Test
	public final void testToString() {
		assertNotNull(loader.toString());
	}
	
}
