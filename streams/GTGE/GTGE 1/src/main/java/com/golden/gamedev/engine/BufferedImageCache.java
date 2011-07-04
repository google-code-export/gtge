package com.golden.gamedev.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.commons.lang.StringUtils;

/**
 * The {@link BufferedImageCache} interface specifies an {@link Object} that is able to load and store
 * {@link BufferedImage} data for fast retrieval. <br />
 * <br />
 * Two types of {@link BufferedImage} data are stored in a {@link BufferedImageCache} - singular {@link BufferedImage}
 * instances, and {@link BufferedImage} arrays representing multiple smaller images stored as one larger image in a
 * single file. <br />
 * <br />
 * Typically, this cache is used to load images for linked parts of levels that may not be immediately accessible by the
 * player, but may be immediately referenced by some action (such as walking through a door). Typically, storing the
 * images in memory in a cache when loading related levels is faster than retrieving them from secondary storage on
 * demand, but memory is consumed and manual management of the cache is required if it is used in order to allow memory
 * to be reclaimed as needed.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public interface BufferedImageCache {
	
	/**
	 * Gets the given {@link BufferedImage} from the cache under the given file name if it is already present,
	 * otherwise, loads it into the cache using the masking color specified and returns it. Note that if the image file
	 * path already exists in the cache, the masking color will be ignored - the image will be returned with the
	 * original masking color specified when it was stored into the cache.
	 * 
	 * @param fileName
	 *            The non-{@link StringUtils#isBlank(String) blank} {@link String} representing the image file name to
	 *            be loaded.
	 * @param maskColor
	 *            The possibly-null {@link Color} representing the masking color to use. If null, the masking color will
	 *            be ignored.
	 * @return A non-null {@link BufferedImage} instance from the given {@link String} file name.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given {@link String} file name is
	 *             {@link StringUtils#isBlank(String) blank}.
	 */
	BufferedImage getImage(final String fileName, Color maskColor);
	
	/**
	 * Gets the given {@link BufferedImage} array from the cache under the given file name if it is already present,
	 * otherwise, loads it into the cache using the masking color specified and returns it. Note that if the image file
	 * path already exists in the cache, the masking color will be ignored - the image will be returned with the
	 * original masking color specified when it was stored into the cache. <br />
	 * <br />
	 * The array returned consists of individual images stored in a larger image, starting from the top left image, and
	 * ending with the bottom right image.
	 * 
	 * @param imagefile
	 *            The non-{@link StringUtils#isBlank(String) blank} {@link String} representing the image file name to
	 *            be loaded.
	 * @param col
	 *            The number of columns in the {@link BufferedImage} to be returned to separate out individual images.
	 * @param row
	 *            The number of rows in the {@link BufferedImage} to be returned to separate out individual images.
	 * @param maskColor
	 *            The possibly-null {@link Color} representing the masking color to use. If null, the masking color will
	 *            be ignored.
	 * @return A non-null {@link BufferedImage} array from the given {@link String} file name.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given {@link String} file name is
	 *             {@link StringUtils#isBlank(String) blank}.
	 * 
	 */
	BufferedImage[] getImages(final String imagefile, final int col, final int row, Color maskColor);
	
	/**
	 * Removes the given {@link BufferedImage} instance from the cache.
	 * 
	 * @param image
	 *            The {@link BufferedImage} instance to remove from cache.
	 * @return True if the image was removed, false if the image did not exist in the cache in order to be removed.
	 */
	boolean removeImage(final BufferedImage image);
	
	/**
	 * Removes the specified {@link BufferedImage} array from the cache.
	 * 
	 * @param images
	 *            The {@link BufferedImage} array to remove from cache.
	 * @return True if the image array was removed, false if the image did not exist in the cache in order to be
	 *         removed.
	 */
	boolean removeImages(final BufferedImage[] images);
	
	/**
	 * Removes the {@link BufferedImage} instance with the specified filename from the cache.
	 * 
	 * @param fileName
	 *            The file name of the image to remove.
	 * @return Null if the given file name was not mapped, otherwise, the image that was removed will be returned.
	 */
	BufferedImage removeImage(final String fileName);
	
	/**
	 * Removes the {@link BufferedImage} array with the specified filename from the cache.
	 * 
	 * @param fileName
	 *            The file name of the image to remove.
	 * @return Null if the given file name was not mapped, otherwise, the image array that was removed will be returned.
	 */
	BufferedImage[] removeImages(final String fileName);
	
	/**
	 * Clears all mappings maintained by this {@link BufferedImageCache} instance.
	 */
	void clearCache();
	
	/**
	 * Stores the given {@link BufferedImage} instance into the cache with the specified file name key.
	 * 
	 * @param fileName
	 *            The file name to use to store the image.
	 * @param image
	 *            The image to store.
	 */
	void storeImage(final String fileName, final BufferedImage image);
	
	/**
	 * Stores the given {@link BufferedImage} array into the cache with the specified file name key.
	 * 
	 * @param fileName
	 *            The file name to use to store the image.
	 * @param image
	 *            The array to store.
	 */
	void storeImages(final String fileName, final BufferedImage[] images);
	
	/**
	 * Gets the given {@link BufferedImage} instance from the cache with the specified file name key.
	 * 
	 * @param fileName
	 *            The file name to use to retrieve the image.
	 * @param image
	 *            The image stored under the given file name key (which may be null if no image was stored).
	 */
	BufferedImage getStoredImage(final String fileName);
	
	/**
	 * Gets the given {@link BufferedImage} array from the cache with the specified file name key.
	 * 
	 * @param fileName
	 *            The file name to use to retrieve the image.
	 * @param image
	 *            The array stored under the given file name key (which may be null if no array was stored).
	 */
	BufferedImage[] getStoredImages(final String fileName);
}