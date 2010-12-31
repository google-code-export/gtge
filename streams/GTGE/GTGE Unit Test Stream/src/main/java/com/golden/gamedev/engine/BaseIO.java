/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.golden.gamedev.engine;

// JFC
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.golden.gamedev.engine.resource.ClassBasedResourceLoader;
import com.golden.gamedev.engine.resource.ClassLoaderBasedResourceLoader;
import com.golden.gamedev.engine.resource.FileResourceLoader;
import com.golden.gamedev.engine.resource.ResourceLoader;
import com.golden.gamedev.engine.resource.SystemResourceLoader;
import com.golden.gamedev.engine.resource.UnsupportedResourceLoader;

// TODO: isolate and deprecate once internal modifications are completed.
/**
 * Class to get external resources object, such as <code>java.io.File</code>,
 * <code>java.io.InputStream</code>, and <code>java.net.URL</code>.
 * <p>
 * 
 * There are four types mode of how <code>BaseIO</code> getting the external
 * resources object : <br>
 * <ul>
 * <li>{@link #CLASS_URL}</li>
 * <li>{@link #WORKING_DIRECTORY}</li>
 * <li>{@link #SYSTEM_LOADER}, and</li>
 * <li>{@link #CLASS_LOADER}</li>
 * </ul>
 * <p>
 * 
 * By default <code>BaseIO</code> class is using <code>CLASS_URL</code>.
 * 
 * @deprecated The {@link BaseIO} class is now deprecated in favor of using
 *             specific {@link ResourceLoader} instances found in the
 *             com.golden.gamedev.engine.resource package. The reasons
 *             {@link BaseIO} should no longer be used are as follows:
 *             <ol>
 *             <li>{@link BaseIO} switched between a number of modes, but did
 *             not check user inputs properly, leading to confusing errors at
 *             runtime.</li> <li>{@link BaseIO} ignored source exceptions and
 *             instead used its own method,
 *             {@link #getException(String, int, String)}, to construct an
 *             {@link Exception} that lost the information contained within the
 *             source {@link Exception}, making debugging more difficult.</li>
 *             <li>{@link BaseIO} exposed several internal details that were not
 *             needed (for example: {@link #getModeString(int)}) </li> <li>The
 *             methods {@link #getFile(String)}, {@link #setFile(String)},
 *             {@link #getURL(String)} and {@link #getStream(String)} all
 *             contain a side-effect that causes the {@link #getMode() mode} to
 *             be changed internally if an error is encountered with the current
 *             mode, leading to possibly unpredictable code.</li>
 *             </ol>
 * 
 *             The most common {@link ResourceLoader} that should be used is the
 *             {@link ClassBasedResourceLoader}, which is now used internally in
 *             GTGE in place of {@link BaseIO}. However, the other three modes
 *             continue to be supported outside of the {@link BaseIO} class, but
 *             without side-effects and they properly throw any
 *             {@link Exception} instances encountered for easier debugging.
 */
public class BaseIO implements ResourceLoader {
	
	/**
	 * ************************* IO MODE CONSTANTS *****************************
	 */
	
	/**
	 * IO mode constant for class url.
	 */
	public static final int CLASS_URL = 1;
	
	/**
	 * IO mode constant for working directory.
	 */
	public static final int WORKING_DIRECTORY = 2;
	
	/**
	 * IO mode constant for class loader.
	 */
	public static final int CLASS_LOADER = 3;
	
	/**
	 * IO mode constant for system loader.
	 */
	public static final int SYSTEM_LOADER = 4;
	
	/**
	 * A {@link Map} of {@link Integer} keys to {@link ResourceLoader} instance
	 * values.
	 */
	Map modeToResourceLoaderMap;
	
	/**
	 * Creates the {@link #modeToResourceLoaderMap} instance based on the given
	 * non-null target {@link Class} instance.
	 * @param targetClass The non-null target {@link Class} instance to use to
	 *        create the {@link #modeToResourceLoaderMap}.
	 * @return The {@link #modeToResourceLoaderMap} instance created based on
	 *         the given target {@link Class} instance.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given target {@link Class} is null.
	 */
	private static Map createModeToResourceLoaderMap(final Class targetClass) {
		HashMap map = new HashMap(4);
		map.put(new Integer(CLASS_URL), new ClassBasedResourceLoader(
		        targetClass));
		map.put(new Integer(WORKING_DIRECTORY), new FileResourceLoader());
		ClassLoader loader = targetClass.getClassLoader();
		if (loader != null) {
			map.put(new Integer(CLASS_LOADER),
			        new ClassLoaderBasedResourceLoader(targetClass
			                .getClassLoader()));
		}
		else {
			map.put(new Integer(CLASS_LOADER), new UnsupportedResourceLoader());
		}
		map.put(new Integer(SYSTEM_LOADER), new SystemResourceLoader());
		return Collections.unmodifiableMap(map);
	}
	
	/**
	 * ************************* BASE CLASS LOADER *****************************
	 */
	
	private Class base;
	private ClassLoader loader;
	private int mode;
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** CONSTRUCTOR *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Construct new <code>BaseIO</code> with specified class as the base
	 * loader, and specified IO mode (one of {@link #CLASS_URL},
	 * {@link #WORKING_DIRECTORY}, {@link #CLASS_LOADER}, or
	 * {@link #SYSTEM_LOADER}).
	 * 
	 * @param base the base class loader
	 * @param mode one of IO mode constants
	 * @see #CLASS_URL
	 * @see #WORKING_DIRECTORY
	 * @see #CLASS_LOADER
	 * @see #SYSTEM_LOADER
	 */
	public BaseIO(Class base, int mode) {
		this.mode = mode;
		initializeWithClass(base);
	}
	
	/**
	 * Initializes this {@link BaseIO} instance with regards to fields changing
	 * when the base {@link Class} is changed.
	 * @param base The non-null base target {@link Class} to use to change this
	 *        {@link BaseIO} instance.
	 * @throws NullPointerException Throws a {@link NullPointerException} if the
	 *         given {@link Class} is null.
	 */
	private void initializeWithClass(Class base) {
		this.base = base;
		this.loader = base.getClassLoader();
		this.modeToResourceLoaderMap = createModeToResourceLoaderMap(base);
	}
	
	/**
	 * Construct new <code>BaseIO</code> with specified class as the base loader
	 * using {@link #CLASS_URL} mode as the default.
	 * 
	 * @param base the base class loader
	 */
	public BaseIO(Class base) {
		this(base, BaseIO.CLASS_URL);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** INPUT URL *********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns a {@link ResourceLoader} delegate to use based on the currently
	 * selected mode.
	 */
	private ResourceLoader getDelegate(final int mode) {
		ResourceLoader loaderFromMap = (ResourceLoader) modeToResourceLoaderMap
		        .get(new Integer(mode));
		return loaderFromMap == null ? new UnsupportedResourceLoader()
		        : loaderFromMap;
	}
	
	/**
	 * Returns URL from specified path with specified mode.
	 * @param path The path to get the URL from.
	 * @param mode The mode to retrieve the URL.
	 * @return The {@link URL}.
	 * @see #CLASS_LOADER
	 * @see #CLASS_URL
	 * @see #SYSTEM_LOADER
	 * @see #WORKING_DIRECTORY
	 */
	public URL getURL(String path, int mode) {
		URL url = null;
		
		try {
			url = getDelegate(mode).getURL(path);
		}
		catch (Exception e) {
		}
		
		if (url == null) {
			throw new RuntimeException(this.getException(path, mode, "getURL"));
		}
		
		return url;
	}
	
	public URL getURL(String path) {
		URL url = null;
		
		try {
			url = this.getURL(path, this.mode);
		}
		catch (Exception e) {
		}
		
		if (url == null) {
			// smart resource locater
			int smart = 0;
			while (url == null
			        && !this.getModeString(++smart).equals("[UNKNOWN-MODE]")) {
				try {
					url = this.getURL(path, smart);
				}
				catch (Exception e) {
				}
			}
			
			if (url == null) {
				throw new RuntimeException(this.getException(path, this.mode,
				        "getURL"));
			}
			
			this.mode = smart;
		}
		
		return url;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * **************************** INPUT STREAM *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns {@link InputStream} from specified path with specified mode.
	 * @param path The path to retrieve an {@link InputStream} from.
	 * @param mode The mode to use for retrieving the {@link InputStream}.
	 * @return The {@link InputStream}.
	 * @see #CLASS_LOADER
	 * @see #CLASS_URL
	 * @see #SYSTEM_LOADER
	 * @see #WORKING_DIRECTORY
	 */
	public InputStream getStream(String path, int mode) {
		InputStream stream = null;
		
		try {
			stream = getDelegate(mode).getInputStream(path);
		}
		catch (Exception e) {
		}
		
		if (stream == null) {
			throw new RuntimeException(this.getException(path, mode,
			        "getStream"));
		}
		
		return stream;
	}
	
	/**
	 * Returns input stream from specified path with this <code>BaseIO</code>
	 * default mode.
	 * @param path The path to retrieve an {@link InputStream} from.
	 * @return The {@link InputStream}.
	 * @see #getMode()
	 */
	public InputStream getStream(String path) {
		return getInputStream(path);
	}
	
	public InputStream getInputStream(String path) {
		InputStream stream = null;
		
		try {
			stream = this.getStream(path, this.mode);
		}
		catch (Exception e) {
		}
		
		if (stream == null) {
			// smart resource locater
			int smart = 0;
			while (stream == null
			        && !this.getModeString(++smart).equals("[UNKNOWN-MODE]")) {
				try {
					stream = this.getStream(path, smart);
				}
				catch (Exception e) {
				}
			}
			
			if (stream == null) {
				throw new RuntimeException(this.getException(path, this.mode,
				        "getStream"));
			}
			
			this.mode = smart;
		}
		
		return stream;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** INPUT FILE ********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Return file from specified path with specified mode.
	 * @param path The path to retrieve an {@link File} from.
	 * @param mode The mode to use for retrieving the {@link File}.
	 * @return The {@link File}.
	 * @see #CLASS_LOADER
	 * @see #CLASS_URL
	 * @see #SYSTEM_LOADER
	 * @see #WORKING_DIRECTORY
	 */
	public File getFile(String path, int mode) {
		File file = null;
		
		try {
			file = getDelegate(mode).getFile(path);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			throw new RuntimeException(this.getException(path, mode, "getFile"));
		}
		
		return file;
	}
	
	public File getFile(String path) {
		File file = null;
		
		try {
			file = this.getFile(path, this.mode);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			// smart resource locater
			int smart = 0;
			while (file == null
			        && !this.getModeString(++smart).equals("[UNKNOWN-MODE]")) {
				try {
					file = this.getFile(path, smart);
				}
				catch (Exception e) {
				}
			}
			
			if (file == null) {
				throw new RuntimeException(this.getException(path, this.mode,
				        "getFile"));
			}
			
			this.mode = smart;
		}
		
		return file;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *************************** OUTPUT FILE *********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns file on specified path with specified mode for processing.
	 * @param path The path to retrieve a {@link File} from.
	 * @param mode The mode to use for retrieving the {@link File}.
	 * @return The {@link File}.
	 * @see #CLASS_LOADER
	 * @see #CLASS_URL
	 * @see #SYSTEM_LOADER
	 * @see #WORKING_DIRECTORY
	 */
	public File setFile(String path, int mode) {
		File file = null;
		
		try {
			file = getDelegate(mode).createFile(path);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			throw new RuntimeException(this.getException(path, mode, "setFile"));
		}
		
		return file;
	}
	
	/**
	 * Returns file on specified path with this <code>BaseIO</code> default mode
	 * for processing.
	 * @param path The path to retrieve an {@link File} from.
	 * @return The {@link File}.
	 */
	public File setFile(String path) {
		File file = null;
		
		try {
			file = this.setFile(path, this.mode);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			// smart resource locater
			int smart = 0;
			while (file == null) {
				try {
					file = this.setFile(path, ++smart);
				}
				catch (Exception e) {
				}
			}
			
			this.mode = smart;
		}
		
		return file;
	}
	
	public File createFile(final String path) {
		return setFile(path, this.mode);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *********************** IO MODE CONSTANTS *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns the root path of this {@link BaseIO} if using specified mode. The
	 * root path is the root where all the resources will be taken from.
	 * <p>
	 * 
	 * For example : <br>
	 * The root path = "c:\games\spaceinvader" <br>
	 * The resource name = "images\background.png" <br>
	 * The resource then will be taken from = <br>
	 * "c:\games\spaceinvader\images\background.png"
	 * @param mode The mode to retrieve root path for.
	 * @return The root path of the given mode.
	 */
	public String getRootPath(int mode) {
		switch (mode) {
			case CLASS_URL:
				return this.base.getResource("").toString();
				
			case WORKING_DIRECTORY:
				return System.getProperty("user.dir") + File.separator;
				
			case CLASS_LOADER:
				return this.loader.getResource("").toString();
				
			case SYSTEM_LOADER:
				return ClassLoader.getSystemResource("").toString();
		}
		
		return "[UNKNOWN-MODE]";
	}
	
	/**
	 * Returns the official statement of specified IO mode, or
	 * <code>[UNKNOWN-MODE]</code> if the IO mode is undefined.
	 * @param mode The mode to get a string representation for.
	 * @return The {@link String} representation fo the given mode.
	 * @see #getMode()
	 */
	public String getModeString(int mode) {
		switch (mode) {
			case CLASS_URL:
				return "Class-URL";
			case WORKING_DIRECTORY:
				return "Working-Directory";
			case CLASS_LOADER:
				return "Class-Loader";
			case SYSTEM_LOADER:
				return "System-Loader";
		}
		
		return "[UNKNOWN-MODE]";
	}
	
	/**
	 * Returns the default IO mode used for getting the resources.
	 * @return The default IO mode.
	 * @see #setMode(int)
	 * @see #getModeString(int)
	 */
	public int getMode() {
		return this.mode;
	}
	
	/**
	 * Sets the default IO mode used for getting the resources.
	 * @param mode The new default io mode.
	 * @see #getMode()
	 * @see #CLASS_URL
	 * @see #WORKING_DIRECTORY
	 * @see #CLASS_LOADER
	 * @see #SYSTEM_LOADER
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Returns exception string used whenever resource can not be found.
	 * @param path The path that was retrived.
	 * @param mode The io mode used during the exception occured.
	 * @param method The method the exception occured in.
	 * @return The exception description string.
	 */
	protected String getException(String path, int mode, String method) {
		return "Resource not found (" + this + "): " + this.getRootPath(mode)
		        + path;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *********************** BASE CLASS LOADER *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Sets the base class where the resources will be taken from.
	 * @param base The base {@link Class}.
	 * @see #getBase()
	 */
	public void setBase(Class base) {
		initializeWithClass(base);
	}
	
	/**
	 * Returns the base class where the resources will be taken from.
	 * @return The base {@link Class}.
	 * @see #setBase(Class)
	 */
	public Class getBase() {
		return this.base;
	}
	
	/**
	 * Returns the class loader associated with this {@link BaseIO}.
	 * @return The {@link ClassLoader}.
	 * @see #setBase(Class)
	 */
	public ClassLoader getLoader() {
		return this.loader;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + " " + "[mode="
		        + this.getModeString(this.mode) + ", baseClass=" + this.base
		        + ", classLoader=" + this.loader + "]";
	}
	
	/**
	 * Gets the {@link ResourceLoader} that was selected via this {@link BaseIO}
	 * instance, or itself if one was not selected. <br />
	 * <br />
	 * This method is a temporary stopgap that will allow the use of custom
	 * {@link BaseIO} extensions with GTGE 0.2.4 but will be removed when
	 * {@link BaseIO} is removed in a later release.
	 * 
	 * @return The {@link ResourceLoader} that was selected via this
	 *         {@link BaseIO} instance, or itself if one was not selected.
	 */
	public ResourceLoader getSelectedResourceLoader() {
		ResourceLoader loaderFromMap = (ResourceLoader) modeToResourceLoaderMap
		        .get(new Integer(mode));
		return loaderFromMap == null ? this : loaderFromMap;
	}
	
}
