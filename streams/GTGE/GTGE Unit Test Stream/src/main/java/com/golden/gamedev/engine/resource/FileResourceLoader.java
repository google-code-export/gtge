package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link FileResourceLoader} class provides an implementation of the
 * {@link ResourceLoader} interface that directly uses the given {@link String}
 * path as a filename in order to load resources.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ResourceLoader
 * 
 */
public final class FileResourceLoader implements ResourceLoader {
	
	/**
	 * Creates a new {@link FileResourceLoader} instance.
	 */
	public FileResourceLoader() {
		super();
	}
	
	public URL getURL(String path) {
		try {
			File baseFile = new File(path);
			if (!baseFile.exists()) {
				throw new IllegalArgumentException(
				        "Path "
				                + path
				                + " does not exist as a created file, a file that exists must be specified!");
			}
			return new File(path).toURI().toURL();
		}
		catch (Exception e) {
			throw new IllegalStateException("Path " + path
			        + " does not exist as a file name.");
		}
	}
	
	public InputStream getInputStream(String path) {
		try {
			return getURL(path).openStream();
		}
		catch (Exception e) {
			throw new IllegalArgumentException(
			        "Invalid path for opening an InputStream: " + path);
		}
	}
	
	public File getFile(String path) {
		return new File(path);
	}
	
	public File createFile(String path) {
		return new File(path);
	}
}
