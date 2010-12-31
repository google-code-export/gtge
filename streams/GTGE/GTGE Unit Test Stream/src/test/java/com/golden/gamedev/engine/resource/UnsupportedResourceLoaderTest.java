package com.golden.gamedev.engine.resource;

import junit.framework.TestCase;

/**
 * The {@link UnsupportedResourceLoaderTest} class provides a {@link TestCase}
 * to test the functionality of the {@link UnsupportedResourceLoader} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see UnsupportedResourceLoader
 * @see TestCase
 * 
 */
public final class UnsupportedResourceLoaderTest extends TestCase {
	
	/**
	 * The {@link UnsupportedResourceLoader} instance under test.
	 */
	private UnsupportedResourceLoader loader;
	
	protected void setUp() throws Exception {
		loader = new UnsupportedResourceLoader();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.UnsupportedResourceLoader#UnsupportedResourceLoader()}
	 * .
	 */
	public final void testUnsupportedResourceLoader() {
		assertNotNull(loader);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.UnsupportedResourceLoader#getURL(java.lang.String)}
	 * .
	 */
	public final void testGetURL() {
		try {
			loader.getURL("blah");
			fail("An UnsupportedOperationException was expected - getURL(String) is not supported!");
		}
		catch (UnsupportedOperationException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.UnsupportedResourceLoader#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStream() {
		try {
			loader.getInputStream("blah");
			fail("An UnsupportedOperationException was expected - getInputStream(String) is not supported!");
		}
		catch (UnsupportedOperationException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.UnsupportedResourceLoader#getFile(java.lang.String)}
	 * .
	 */
	public final void testGetFile() {
		try {
			loader.getFile("blah");
			fail("An UnsupportedOperationException was expected - getFile(String) is not supported!");
		}
		catch (UnsupportedOperationException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.resource.UnsupportedResourceLoader#createFile(java.lang.String)}
	 * .
	 */
	public final void testCreateFile() {
		try {
			loader.createFile("blah");
			fail("An UnsupportedOperationException was expected - createFile(String) is not supported!");
		}
		catch (UnsupportedOperationException e) {
			// Intentionally blank
		}
	}
	
}
