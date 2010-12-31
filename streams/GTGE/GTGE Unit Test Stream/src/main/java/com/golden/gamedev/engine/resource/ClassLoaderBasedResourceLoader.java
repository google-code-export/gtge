package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link ClassLoaderBasedResourceLoader} class provides an implementation
 * of the {@link ResourceLoader} interface that loads resources relative to a
 * target {@link ClassLoader} instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ResourceLoader
 * @see ClassLoader
 * 
 */
public final class ClassLoaderBasedResourceLoader implements ResourceLoader {
	
	/**
	 * The non-null {@link ClassLoader} instance to use to load resources.
	 */
	private final ClassLoader targetClassLoader;
	
	/**
	 * Creates a new {@link ClassLoaderBasedResourceLoader} instance with the
	 * given non-null {@link ClassLoader} instance to use to load resources.
	 * @param targetClassLoader The non-null {@link ClassLoader} instance to use
	 *        to load resources.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link ClassLoader} is null.
	 */
	public ClassLoaderBasedResourceLoader(final ClassLoader targetClassLoader) {
		super();
		if (targetClassLoader == null) {
			throw new NullPointerException(
			        "The target class loader may not be null.");
		}
		this.targetClassLoader = targetClassLoader;
	}
	
	public URL getURL(String path) {
		return this.targetClassLoader.getResource(path);
	}
	
	public InputStream getInputStream(String path) {
		return this.targetClassLoader.getResourceAsStream(path);
	}
	
	public File getFile(String path) {
		return new File(this.targetClassLoader.getResource(path).getFile()
		        .replaceAll("%20", " "));
	}
	
	public File createFile(String path) {
		return new File(this.targetClassLoader.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + path);
	}
	
}
