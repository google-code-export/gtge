package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import com.golden.gamedev.Game;

/**
 * The {@link ClassLoaderBasedResourceLoaderTest} class provides a
 * {@link TestCase} to test the functionality of the
 * {@link ClassLoaderBasedResourceLoader} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ClassLoaderBasedResourceLoader
 * @see TestCase
 * 
 */
public class ClassLoaderBasedResourceLoaderTest extends TestCase {
	
	/**
	 * The {@link ClassLoaderBasedResourceLoader} instance under test.
	 */
	private ClassLoaderBasedResourceLoader loader;
	
	protected void setUp() throws Exception {
		loader = new ClassLoaderBasedResourceLoader(Game.class.getClassLoader());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader#ClassLoaderBasedResourceLoader(java.lang.ClassLoader)}
	 * .
	 */
	public final void testClassLoaderBasedResourceLoader() {
		assertNotNull(loader);
		try {
			loader = new ClassLoaderBasedResourceLoader(null);
			fail("Expected NullPointerException - must have a class loader!");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader#getURL(java.lang.String)}
	 * .
	 */
	public final void testGetURL() {
		URL url = this.loader.getURL("com/golden/gamedev/Game.dat");
		assertNotNull(url);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStream() {
		InputStream stream = this.loader
		        .getInputStream("com/golden/gamedev/Game.dat");
		assertNotNull(stream);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader#getFile(java.lang.String)}
	 * .
	 */
	public final void testGetFile() {
		File file = this.loader.getFile("com/golden/gamedev/Game.dat");
		assertNotNull(file);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader#createFile(java.lang.String)}
	 * .
	 */
	public final void testCreateFile() {
		File fileToCompare = new File(Game.class.getClassLoader()
		        .getResource("").getFile().replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.loader.createFile("blah");
		assertEquals(fileToCompare, file);
	}
	
}
