package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link SystemResourceLoader} class provides an implementation of the
 * {@link ResourceLoader} interface that loads resources via the system's class
 * loader.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ResourceLoader
 * 
 */
public final class SystemResourceLoader implements ResourceLoader {
	
	/**
	 * Creates a new {@link SystemResourceLoader} instance.
	 */
	public SystemResourceLoader() {
		super();
	}
	
	public URL getURL(String path) {
		return ClassLoader.getSystemResource(path);
	}
	
	public InputStream getInputStream(String path) {
		return ClassLoader.getSystemResourceAsStream(path);
	}
	
	public File getFile(String path) {
		return new File(ClassLoader.getSystemResource(path).getFile()
		        .replaceAll("%20", " "));
	}
	
	public File createFile(String path) {
		return new File(ClassLoader.getSystemResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + path);
	}
	
}
