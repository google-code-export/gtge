package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link ClassBasedResourceLoader} class provides a {@link ResourceLoader}
 * implementation that is based around a target {@link Class} instance in order
 * to retrieve resources.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see ResourceLoader
 * @see Class
 * 
 */
public final class ClassBasedResourceLoader implements ResourceLoader {
	
	/**
	 * The non-null {@link Class} to use to load resources with.
	 */
	private final Class targetClass;
	
	/**
	 * Creates a new {@link ClassBasedResourceLoader} instance with the given
	 * non-null {@link Class} to use to load resources with.
	 * @param targetClass The non-null {@link Class} to use to load resources
	 *        with.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link Class} is null.
	 */
	public ClassBasedResourceLoader(final Class targetClass) {
		super();
		if (targetClass == null) {
			throw new NullPointerException("The target class may not be null.");
		}
		this.targetClass = targetClass;
	}
	
	public URL getURL(String path) {
		return this.targetClass.getResource(path);
	}
	
	public InputStream getInputStream(String path) {
		return this.targetClass.getResourceAsStream(path);
	}
	
	public File getFile(String path) {
		return new File(this.targetClass.getResource(path).getFile()
		        .replaceAll("%20", " "));
	}
	
	public File createFile(String path) {
		return new File(this.targetClass.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + path);
	}
}
