package com.golden.gamedev.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface BaseLoader {
	
	/**
	 * Loads and returns an image from the file location. If useMask is set to
	 * true, then the default masking colour will be used. Images that have been
	 * previously loaded will return immediately from the image cache.
	 * 
	 * @param imagefile The image filename to be loaded
	 * @param useMask If true, then the image is loaded using the default
	 *        transparent color
	 * @return Requested image.
	 */
	public BufferedImage getImage(String imagefile, boolean useMask);
	
	/**
	 * Loads and returns an image with specified file using masking color. Image
	 * that have been loaded before will return immediately from cache.
	 * 
	 * @param imagefile the image filename to be loaded
	 * @return Requested image.
	 * 
	 * @see #getImage(String, boolean)
	 */
	public BufferedImage getImage(String imagefile);
	
	/**
	 * Loads and returns image strip with specified file and whether using
	 * masking color or not. Images that have been loaded before will return
	 * immediately from cache.
	 * 
	 * @param imagefile the image filename to be loaded
	 * @param col image strip column
	 * @param row image strip row
	 * @param useMask true, the image is using transparent color
	 * @return Requested image.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask);
	
	/**
	 * Loads and returns image strip with specified file using masking color.
	 * Images that have been loaded before will return immediately from cache.
	 * 
	 * @param imagefile the image filename to be loaded
	 * @param col image strip column
	 * @param row image strip row
	 * @return Requested image.
	 * 
	 * @see #getImages(String, int, int, boolean)
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row);
	
	/**
	 * Removes specified image from cache.
	 * @param image The image to remove from cache.
	 * @return If removing the image from cache worked.
	 */
	public boolean removeImage(BufferedImage image);
	
	/**
	 * Removes specified images from cache.
	 * @param images The images to remove from cache.
	 * @return If removing the images from cache worked.
	 */
	public boolean removeImages(BufferedImage[] images);
	
	/**
	 * Removes image with specified image filename from cache.
	 * @param imagefile The file name of the image to remove.
	 * @return The removed image.
	 */
	public BufferedImage removeImage(String imagefile);
	
	/**
	 * Removes images with specified image filename from cache.
	 * @param imagefile The file name of the image to remove.
	 * @return The removed images.
	 */
	public BufferedImage[] removeImages(String imagefile);
	
	/**
	 * Clear all cached images.
	 */
	public void clearCache();
	
	/**
	 * Stores image into cache with specified key.
	 * @param key The key used to store the image.
	 * @param image The image to store.
	 */
	public void storeImage(String key, BufferedImage image);
	
	/**
	 * Stores images into cache with specified key.
	 * @param key The key used to store the images.
	 * @param images The images to store.
	 */
	public void storeImages(String key, BufferedImage[] images);
	
	/**
	 * Returns cache image with specified key.
	 * @param key The key of the image wanted.
	 * @return The image with the given key or <code>null</code>.
	 */
	public BufferedImage getStoredImage(String key);
	
	/**
	 * Returns cache images with specified key.
	 * @param key The key of the images wanted.
	 * @return The images with the given key.
	 */
	public BufferedImage[] getStoredImages(String key);
	
	/**
	 * Returns image loader masking color.
	 * @return The masking color.
	 * @see #setMaskColor(Color)
	 */
	public Color getMaskColor();
	
	/**
	 * Sets image loader masking color.
	 * <p>
	 * 
	 * Masking color is the color of the images that will be converted to
	 * transparent.
	 * @param c The new masking color.
	 * @see #getMaskColor()
	 */
	public void setMaskColor(Color c);
	
}
