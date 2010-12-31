package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link UnsupportedResourceLoader} class provides an implementation of the
 * {@link ResourceLoader} interface that will never load resources.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ResourceLoader
 * 
 */
public final class UnsupportedResourceLoader implements ResourceLoader {
	
	/**
	 * Creates a new {@link UnsupportedResourceLoader} instance.
	 */
	public UnsupportedResourceLoader() {
		super();
	}
	
	public URL getURL(String path) {
		throw new UnsupportedOperationException(
		        "getURL(String) is not supported!");
	}
	
	public InputStream getInputStream(String path) {
		throw new UnsupportedOperationException(
		        "getInputStream(String) is not supported!");
	}
	
	public File getFile(String path) {
		throw new UnsupportedOperationException(
		        "getFile(String) is not supported!");
	}
	
	public File createFile(String path) {
		throw new UnsupportedOperationException(
		        "createFile(String) is not supported!");
	}
	
}
