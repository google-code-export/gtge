/**
 * 
 */
package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import com.golden.gamedev.Game;

/**
 * The {@link ClassBasedResourceLoaderTest} class provides a {@link TestCase} to
 * test the behavior of the {@link ClassBasedResourceLoader} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ClassBasedResourceLoader
 * @see TestCase
 * 
 */
public class ClassBasedResourceLoaderTest extends TestCase {
	
	/**
	 * The {@link ClassBasedResourceLoader} instance under test.
	 */
	private ClassBasedResourceLoader loader;
	
	protected void setUp() throws Exception {
		loader = new ClassBasedResourceLoader(Game.class);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassBasedResourceLoader#ClassBasedResourceLoader(java.lang.Class)}
	 * .
	 */
	public final void testClassBasedResourceLoader() {
		assertNotNull(loader);
		try {
			loader = new ClassBasedResourceLoader(null);
			fail("Expected NullPointerException - no class specified.");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassBasedResourceLoader#getURL(java.lang.String)}
	 * .
	 */
	public final void testGetURL() {
		URL url = this.loader.getURL("Game.dat");
		assertNotNull(url);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassBasedResourceLoader#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStream() {
		InputStream stream = this.loader.getInputStream("Game.dat");
		assertNotNull(stream);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassBasedResourceLoader#getFile(java.lang.String)}
	 * .
	 */
	public final void testGetFile() {
		File file = this.loader.getFile("Game.dat");
		assertNotNull(file);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassBasedResourceLoader#createFile(java.lang.String)}
	 * .
	 */
	public final void testCreateFile() {
		File fileToCompare = new File(Game.class.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.loader.createFile("blah");
		assertEquals(fileToCompare, file);
	}
	
}
