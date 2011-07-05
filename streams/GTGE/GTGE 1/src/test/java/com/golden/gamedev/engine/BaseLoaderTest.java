package com.golden.gamedev.engine;

import static org.junit.Assert.*;

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
	@Test
	public final void testGetImages() {
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImage(java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testRemoveImageBufferedImage() {
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImages(java.awt.image.BufferedImage[])}.
	 */
	@Test
	public final void testRemoveImagesBufferedImageArray() {
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImage(java.lang.String)}.
	 */
	@Test
	public final void testRemoveImageString() {
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#removeImages(java.lang.String)}.
	 */
	@Test
	public final void testRemoveImagesString() {
		
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseLoader#clearCache()}.
	 */
	@Test
	public final void testClearCache() {
		
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#storeImage(java.lang.String, java.awt.image.BufferedImage)}.
	 */
	@Test
	public final void testStoreImage() {
		
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseLoader#storeImages(java.lang.String, java.awt.image.BufferedImage[])}.
	 */
	@Test
	public final void testStoreImages() {
		
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
