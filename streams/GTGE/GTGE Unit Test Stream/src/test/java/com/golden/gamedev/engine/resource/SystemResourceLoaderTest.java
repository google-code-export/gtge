package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

/**
 * The {@link SystemResourceLoaderTest} class provides a {@link TestCase} to
 * test the behavior of the {@link SystemResourceLoader} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see SystemResourceLoader
 * @see TestCase
 * 
 */
public class SystemResourceLoaderTest extends TestCase {
	
	/**
	 * The {@link SystemResourceLoader} instance under test.
	 */
	private SystemResourceLoader loader;
	
	protected void setUp() throws Exception {
		loader = new SystemResourceLoader();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.SystemResourceLoader#SystemResourceLoader()}
	 * .
	 */
	public final void testSystemResourceLoader() {
		assertNotNull(loader);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.SystemResourceLoader#getURL(java.lang.String)}
	 * .
	 */
	public final void testGetURL() {
		URL url = this.loader.getURL("com/golden/gamedev/Game.dat");
		assertNotNull(url);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.SystemResourceLoader#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStream() {
		InputStream stream = this.loader
		        .getInputStream("com/golden/gamedev/Game.dat");
		assertNotNull(stream);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.SystemResourceLoader#getFile(java.lang.String)}
	 * .
	 */
	public final void testGetFile() {
		File file = this.loader.getFile("com/golden/gamedev/Game.dat");
		assertNotNull(file);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.SystemResourceLoader#createFile(java.lang.String)}
	 * .
	 */
	public final void testCreateFile() {
		File fileToCompare = new File(ClassLoader.getSystemResource("")
		        .getFile().replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.loader.createFile("blah");
		assertEquals(fileToCompare, file);
	}
	
}
