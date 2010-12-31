package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

/**
 * The {@link FileResourceLoaderTest} class provides a {@link TestCase} to test
 * the behavior of the {@link FileResourceLoader} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see FileResourceLoader
 * @see TestCase
 * 
 */
public class FileResourceLoaderTest extends TestCase {
	
	/**
	 * The {@link FileResourceLoader} instance under test.
	 */
	private FileResourceLoader loader;
	
	protected void setUp() throws Exception {
		loader = new FileResourceLoader();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.FileResourceLoader#FileResourceLoader()}
	 * .
	 */
	public final void testFileResourceLoader() {
		assertNotNull(loader);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.FileResourceLoader#getURL(java.lang.String)}
	 * .
	 */
	public final void testGetURL() {
		URL url = this.loader
		        .getURL("src/main/resources/com/golden/gamedev/Game.dat");
		assertNotNull(url);
		
		try {
			url = this.loader.getURL(null);
			fail("Expected IllegalStateException - null file does not exist.");
		}
		catch (IllegalStateException e) {
			// Intentionally blank
		}
		
		try {
			url = this.loader.getURL("blah");
			fail("Expected IllegalStateException - non-existent file does not exist.");
		}
		catch (IllegalStateException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.FileResourceLoader#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStream() {
		InputStream stream = this.loader
		        .getInputStream("src/main/resources/com/golden/gamedev/Game.dat");
		assertNotNull(stream);
		
		try {
			stream = this.loader.getInputStream(null);
			fail("Expected IllegalArgumentException - null file does not exist.");
		}
		catch (IllegalArgumentException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.FileResourceLoader#getFile(java.lang.String)}
	 * .
	 */
	public final void testGetFile() {
		File file = this.loader
		        .getFile("src/main/resources/com/golden/gamedev/Game.dat");
		assertNotNull(file);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.FileResourceLoader#createFile(java.lang.String)}
	 * .
	 */
	public final void testCreateFile() {
		File fileToCompare = new File("blah");
		File file = this.loader.createFile("blah");
		assertEquals(fileToCompare, file);
	}
	
}
