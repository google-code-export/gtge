package com.golden.gamedev.engine.resource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * The {@link ResourceLoader} interface specifies an {@link Object} that can
 * load resources from a given {@link String} path represented as common
 * built-in Java types. <br />
 * <br />
 * Currently, {@link File}, {@link InputStream} and (the most flexible of the
 * three) {@link URL} resources are able to be loaded by the provided
 * implementations of the {@link ResourceLoader} interface. <br />
 * When possible, it is advised to use an {@link URL} to load resources, as this
 * grants the most flexibility.
 * 
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface ResourceLoader {
	
	/**
	 * Gets the {@link URL} representing the given non-null {@link String} path.
	 * @param path The non-null {@link String} path to use to load the
	 *        {@link URL}.
	 * @return The {@link URL} representing the given {@link String} path.
	 * @throws RuntimeException A {@link RuntimeException} may occur if an error
	 *         occurs when attempting to load the {@link URL} for the given
	 *         non-null {@link String} path.
	 */
	public URL getURL(final String path);
	
	/**
	 * Gets the {@link InputStream} representing the given non-null
	 * {@link String} path.
	 * @param path The non-null {@link String} path to use to load the
	 *        {@link InputStream}.
	 * @return The {@link InputStream} representing the given {@link String}
	 *         path.
	 * @throws RuntimeException A {@link RuntimeException} may occur if an error
	 *         occurs when attempting to load the {@link InputStream} for the
	 *         given non-null {@link String} path.
	 */
	public InputStream getInputStream(final String path);
	
	/**
	 * Gets the {@link File} representing the given non-null {@link String}
	 * path, based on the current configuration of this {@link ResourceLoader}
	 * instance. <br />
	 * <br />
	 * The difference between this method and {@link #createFile(String)} is
	 * that the path is used directly to retrieve the file, whereas when
	 * {@link #createFile(String)} is used, the path is typically appended to
	 * the current configuration of the current {@link ResourceLoader} instance
	 * in order to create the {@link File}. Generally, this method should be
	 * preferred over {@link #createFile(String)}.
	 * 
	 * @param path path The non-null {@link String} path to use to load the
	 *        {@link File}.
	 * @return The {@link File} representing the given non-null {@link String}
	 *         path.
	 * @throws RuntimeException A {@link RuntimeException} may occur if an error
	 *         occurs when attempting to load the {@link File} for the given
	 *         non-null {@link String} path.
	 */
	public File getFile(final String path);
	
	/**
	 * Creates and returns a new {@link File} instance based on the current
	 * configuration of this {@link ResourceLoader} instance and the given
	 * non-null {@link String} path.
	 * 
	 * Typically, {@link ResourceLoader} instances will contain a base path that
	 * they will append to the given {@link String} path to create this
	 * {@link File}, but this is not a requirement.
	 * 
	 * @param path The given non-null {@link String} path to use to create the
	 *        {@link File}.
	 * @return A new {@link File} instance based on the current configuration of
	 *         this {@link ResourceLoader} instance and the given non-null
	 *         {@link String} path.
	 * @throws RuntimeException A {@link RuntimeException} may occur if an error
	 *         occurs when attempting to create the {@link Files} for the given
	 *         non-null {@link String} path.
	 * 
	 */
	public File createFile(final String path);
}
