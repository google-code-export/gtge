package com.golden.gamedev.engine;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public interface BaseIO {
	
	/** ************************* IO MODE CONSTANTS ***************************** */
	
	public static final String UNKNOWN_MODE = "[UNKNOWN-MODE]";
	
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
	public URL getURL(String path, int mode);
	
	/**
	 * Returns URL from specified path with this {@link DefaultBaseIO} default mode.
	 * @param path The path to retrieve the URL from.
	 * @return The {@link URL} of the given path.
	 * @see #getMode()
	 */
	public URL getURL(String path);
	
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
	public InputStream getStream(String path, int mode);
	
	/**
	 * Returns input stream from specified path with this <code>BaseIO</code>
	 * default mode.
	 * @param path The path to retrieve an {@link InputStream} from.
	 * @return The {@link InputStream}.
	 * @see #getMode()
	 */
	public InputStream getStream(String path);
	
	/** ************************************************************************* */
	/** ***************************** INPUT FILE ******************************** */
	/** ************************************************************************* */
	
	public File getFile(String path, int mode, boolean usePathAsResourceName);
	
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
	public File getFile(String path, int mode);
	
	public File getFile(String path, boolean usePathAsResourceName);
	
	/**
	 * Returns file from specified path with this <code>BaseIO</code> default
	 * mode.
	 * <p>
	 * 
	 * File object usually used only for writing to disk.
	 * <p>
	 * 
	 * <b>Caution:</b> always try to avoid using <code>java.io.File</code>
	 * object (this method), because <code>java.io.File</code> is system
	 * dependent and not working inside jar file, use <code>java.net.URL</code>
	 * OR <code>java.io.InputStream</code> instead.
	 * <p>
	 * @param path The path to retrieve an {@link File} from.
	 * @return The {@link File}.
	 * @see #getURL(String)
	 * @see #getStream(String)
	 * @see #setFile(String)
	 * @see #getMode()
	 */
	public File getFile(String path);
	
	/**
	 * Returns the root path of this {@link DefaultBaseIO} if using specified mode. The
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
	public String getRootPath(int mode);
	
	/**
	 * Returns the official statement of specified IO mode, or
	 * <code>{@link #UNKNOWN_MODE}</code> if the IO mode is undefined.
	 * @param mode The mode to get a string representation for.
	 * @return The {@link String} representation for the given mode.
	 * @see #getMode()
	 */
	public String getModeString(int mode);
	
	/**
	 * Returns the default IO mode used for getting the resources.
	 * @return The default IO mode.
	 * @see #setMode(int)
	 * @see #getModeString(int)
	 */
	public int getMode();
	
	/**
	 * Sets the default IO mode used for getting the resources.
	 * @param mode The new default io mode.
	 * @see #getMode()
	 * @see #CLASS_URL
	 * @see #WORKING_DIRECTORY
	 * @see #CLASS_LOADER
	 * @see #SYSTEM_LOADER
	 */
	public void setMode(int mode);
	
}
