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
 */
public class DefaultBaseIO implements BaseIO {
	
	public static final String DESCRIPTION_SYSTEM_LOADER = "System-Loader";

	public static final String DESCRIPTION_CLASS_LOADER = "Class-Loader";

	public static final String DESCRIPTION_WORKING_DIRECTORY = "Working-Directory";

	public static final String DESCRIPTION_CLASS_URL = "Class-URL";

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
	
	/** ************************* BASE CLASS LOADER ***************************** */
	
	private Class base;
	private ClassLoader loader;
	private int mode;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
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
	public DefaultBaseIO(Class base, int mode) {
		this.setBase(base);
		this.setLoader(base.getClassLoader());
		this.setMode(mode);
	}
	
	/**
	 * Construct new <code>BaseIO</code> with specified class as the base
	 * loader using {@link #CLASS_URL} mode as the default.
	 * 
	 * @param base the base class loader
	 */
	public DefaultBaseIO(Class base) {
		this(base, DefaultBaseIO.CLASS_URL);
	}
	
	/** ************************************************************************* */
	/** ***************************** INPUT URL ********************************* */
	/** ************************************************************************* */
	
	public URL getURL(String path, int mode) {
		URL url = null;
		
		try {
			switch (mode) {
				case CLASS_URL:
					url = this.getBase().getResource(path);
					break;
				
				case WORKING_DIRECTORY:
					File f = new File(path);
					if (f.exists()) {
						url = f.toURI().toURL();
					}
					break;
				
				case CLASS_LOADER:
					url = this.getLoader().getResource(path);
					break;
				
				case SYSTEM_LOADER:
					url = ClassLoader.getSystemResource(path);
					break;
			}
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
			url = this.getURL(path, this.getMode());
		}
		catch (Exception e) {
		}
		
		if (url == null) {
			// smart resource locater
			int smart = 0;
			while (url == null
			        && !this.getModeString(++smart).equals(UNKNOWN_MODE)) {
				try {
					url = this.getURL(path, smart);
				}
				catch (Exception e) {
				}
			}
			
			if (url == null) {
				throw new RuntimeException(this.getException(path, this
				        .getMode(), "getURL"));
			}
			
			this.setMode(smart);
		}
		
		return url;
	}
	
	/** ************************************************************************* */
	/** **************************** INPUT STREAM ******************************* */
	/** ************************************************************************* */
	
	public InputStream getStream(String path, int mode) {
		InputStream stream = null;
		
		try {
			switch (mode) {
				case CLASS_URL:
					stream = this.getBase().getResourceAsStream(path);
					break;
				
				case WORKING_DIRECTORY:
					stream = new File(path).toURI().toURL().openStream();
					break;
				
				case CLASS_LOADER:
					stream = this.getLoader().getResourceAsStream(path);
					break;
				
				case SYSTEM_LOADER:
					stream = ClassLoader.getSystemResourceAsStream(path);
					break;
			}
		}
		catch (Exception e) {
		}
		
		if (stream == null) {
			throw new RuntimeException(this.getException(path, mode,
			        "getStream"));
		}
		
		return stream;
	}
	
	public InputStream getStream(String path) {
		InputStream stream = null;
		
		try {
			stream = this.getStream(path, this.getMode());
		}
		catch (Exception e) {
		}
		
		if (stream == null) {
			// smart resource locater
			int smart = 0;
			while (stream == null
			        && !this.getModeString(++smart).equals(UNKNOWN_MODE)) {
				try {
					stream = this.getStream(path, smart);
				}
				catch (Exception e) {
				}
			}
			
			if (stream == null) {
				throw new RuntimeException(this.getException(path, this
				        .getMode(), "getStream"));
			}
			
			this.setMode(smart);
		}
		
		return stream;
	}
	
	/** ************************************************************************* */
	/** ***************************** INPUT FILE ******************************** */
	
	public File getFile(String path, int mode, boolean usePathAsResourceName) {
		String resourceString = usePathAsResourceName ? path : "";
		String pathExtension = usePathAsResourceName ? "" : File.separator
		        + path;
		
		File file = null;
		
		try {
			switch (mode) {
				case CLASS_URL:
					file = new File(this.getBase().getResource(resourceString)
					        .getFile().replaceAll("%20", " ")
					        + pathExtension);
					break;
				
				case WORKING_DIRECTORY:
					file = new File(path);
					break;
				
				case CLASS_LOADER:
					file = new File(this.getLoader()
					        .getResource(resourceString).getFile().replaceAll(
					                "%20", " ")
					        + pathExtension);
					break;
				
				case SYSTEM_LOADER:
					file = new File(ClassLoader.getSystemResource(
					        resourceString).getFile().replaceAll("%20", " ")
					        + pathExtension);
					break;
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (file == null) {
			throw new RuntimeException(this.getException(path, mode, "getFile"));
		}
		
		return file;
	}
	
	public File getFile(String path, int mode) {
		File file = null;
		
		try {
			file = getFile(path, mode, true);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			throw new RuntimeException(this.getException(path, mode, "getFile"));
		}
		
		return file;
	}
	
	public File getFile(String path, boolean usePathAsResourceName) {
		File file = null;
		
		try {
			file = this.getFile(path, this.getMode(), usePathAsResourceName);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			// smart resource locater
			int smart = 0;
			while (file == null
			        && !this.getModeString(++smart).equals(UNKNOWN_MODE)) {
				try {
					file = this.getFile(path, smart, usePathAsResourceName);
				}
				catch (Exception e) {
				}
			}
			
			if (file == null) {
				throw new RuntimeException(this.getException(path, this
				        .getMode(), "getFile"));
			}
			
			this.setMode(smart);
		}
		
		return file;
	}
	
	public File getFile(String path) {
		return getFile(path, true);
	}
	
	/** ************************************************************************* */
	/** *************************** OUTPUT FILE ********************************* */
	/** ************************************************************************* */
	
	/**
	 * Returns file on specified path with specified mode for processing.
	 * @param path The path to retrieve a {@link File} from.
	 * @param mode The mode to use for retrieving the {@link File}.
	 * @return The {@link File}.
	 * @see #CLASS_LOADER
	 * @see #CLASS_URL
	 * @see #SYSTEM_LOADER
	 * @see #WORKING_DIRECTORY
	 * @deprecated "setFile" is deprecated because it is not a setter method.
	 *             Replaced by {@link #getFile(String, int, boolean)} with the
	 *             third argument set to false.
	 */
	public File setFile(String path, int mode) {
		File file = null;
		
		try {
			file = getFile(path, mode, false);
		}
		catch (Exception e) {
		}
		
		if (file == null) {
			throw new RuntimeException(this.getException(path, mode, "setFile"));
		}
		
		return file;
	}
	
	/**
	 * Returns file on specified path with this <code>BaseIO</code> default
	 * mode for processing.
	 * @param path The path to retrieve an {@link File} from.
	 * @return The {@link File}.
	 * @deprecated "setFile" is deprecated because it is not a setter method.
	 *             Replaced by {@link #getFile(String, boolean)} with the second
	 *             argument set to false.
	 */
	public File setFile(String path) {
		return getFile(path, false);
	}
	
	/** ************************************************************************* */
	/** *********************** IO MODE CONSTANTS ******************************* */
	/** ************************************************************************* */
	
	public String getRootPath(int mode) {
		switch (mode) {
			case CLASS_URL:
				return this.getBase().getResource("").toString();
				
			case WORKING_DIRECTORY:
				return System.getProperty("user.dir") + File.separator;
				
			case CLASS_LOADER:
				return this.getLoader().getResource("").toString();
				
			case SYSTEM_LOADER:
				return ClassLoader.getSystemResource("").toString();
		}
		
		return UNKNOWN_MODE;
	}
	
	public String getModeString(int mode) {
		switch (mode) {
			case CLASS_URL:
				return DESCRIPTION_CLASS_URL;
			case WORKING_DIRECTORY:
				return DESCRIPTION_WORKING_DIRECTORY;
			case CLASS_LOADER:
				return DESCRIPTION_CLASS_LOADER;
			case SYSTEM_LOADER:
				return DESCRIPTION_SYSTEM_LOADER;
		}
		
		return UNKNOWN_MODE;
	}
	
	public int getMode() {
		return this.mode;
	}
	
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
	
	/** ************************************************************************* */
	/** *********************** BASE CLASS LOADER ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Sets the base class where the resources will be taken from.
	 * @param base The base {@link Class}.
	 * @see #getBase()
	 */
	public void setBase(Class base) {
		this.base = base;
		this.setLoader(base.getClassLoader());
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
	 * Returns the class loader associated with this {@link DefaultBaseIO}.
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
		        + this.getModeString(this.getMode()) + ", baseClass="
		        + this.getBase() + ", classLoader=" + this.getLoader() + "]";
	}
	
	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}
	
}
