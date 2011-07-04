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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.golden.gamedev.util.ImageUtil;

/**
 * The {@link BaseLoader} class provides the default implementation of the {@link BufferedImageCache} interface that
 * uses a {@link BaseIO} instance in combination with {@link ImageUtil} to retrieve images when needed to store into the
 * cache.
 * 
 * @author MetroidFan2002 (Refactoring, implemented the {@link BufferedImageCache} interface)
 * @author Paupau (Original Author)
 * @version 1.0
 * @since 1.0
 * @see BufferedImageCache
 */
public final class BaseLoader implements BufferedImageCache {
	
	/**
	 * The non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image filenames
	 * from.
	 */
	private BaseIO base;
	
	/**
	 * The {@link Map} of {@link String} file name keys to {@link BufferedImage} instances to cache.
	 */
	private Map<String, BufferedImage> imageStore = new HashMap<String, BufferedImage>();
	
	/**
	 * The {@link Map} of {@link String} file name keys to {@link BufferedImage} array instances to cache.
	 */
	private Map<String, BufferedImage[]> arrayStore = new HashMap<String, BufferedImage[]>();
	
	/**
	 * Creates a new {@link BaseLoader} instance.
	 * 
	 * @param base
	 *            The non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image
	 *            filenames from.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given {@link BaseIO} instance is null.
	 */
	public BaseLoader(final BaseIO base) {
		super();
		setBaseIO(base);
	}
	
	@Override
	public BufferedImage getImage(final String imagefile, Color maskColor) {
		BufferedImage image = imageStore.get(imagefile);
		
		if (image == null) {
			final URL url = base.getURL(imagefile);
			
			image = (maskColor != null) ? ImageUtil.getImage(url, maskColor) : ImageUtil.getImage(url);
			
			imageStore.put(imagefile, image);
		}
		
		return image;
	}
	
	@Override
	public BufferedImage[] getImages(final String imagefile, final int col, final int row, Color maskColor) {
		BufferedImage[] image = arrayStore.get(imagefile);
		
		if (image == null) {
			final URL url = base.getURL(imagefile);
			
			image = (maskColor != null) ? ImageUtil.getImages(url, col, row, maskColor) : ImageUtil.getImages(url, col,
					row);
			
			arrayStore.put(imagefile, image);
		}
		
		return image;
	}
	
	@Override
	public boolean removeImage(final BufferedImage image) {
		final Iterator<BufferedImage> it = imageStore.values().iterator();
		
		while (it.hasNext()) {
			if (it.next() == image) {
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean removeImages(final BufferedImage[] images) {
		final Iterator<BufferedImage[]> it = arrayStore.values().iterator();
		
		while (it.hasNext()) {
			if (it.next() == images) {
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public BufferedImage removeImage(final String imagefile) {
		return imageStore.remove(imagefile);
	}
	
	@Override
	public BufferedImage[] removeImages(final String imagefile) {
		return arrayStore.remove(imagefile);
	}
	
	@Override
	public void clearCache() {
		imageStore = new HashMap<String, BufferedImage>();
		arrayStore = new HashMap<String, BufferedImage[]>();
	}
	
	@Override
	public void storeImage(final String key, final BufferedImage image) {
		imageStore.put(key, image);
	}
	
	@Override
	public void storeImages(final String key, final BufferedImage[] images) {
		arrayStore.put(key, images);
	}
	
	@Override
	public BufferedImage getStoredImage(final String key) {
		return imageStore.get(key);
	}
	
	@Override
	public BufferedImage[] getStoredImages(final String key) {
		return arrayStore.get(key);
	}
	
	/**
	 * Gets the non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image
	 * filenames from.
	 * 
	 * @return The non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image
	 *         filenames from.
	 */
	public BaseIO getBaseIO() {
		return base;
	}
	
	/**
	 * Sets the non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image
	 * filenames from.
	 * 
	 * @param base
	 *            The non-null {@link BaseIO} instance to use to retrieve {@link BaseIO#getURL(String) URLs} for image
	 *            filenames from.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given {@link BaseIO} instance is null.
	 */
	public void setBaseIO(final BaseIO base) {
		Validate.notNull(base, "The BaseIO instance may not be null!");
		this.base = base;
	}
	
	@Override
	public String toString() {
		final ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("base", base);
		builder.append("imageBank", imageStore);
		builder.append("imagesBank", arrayStore);
		return builder.toString();
	}
}