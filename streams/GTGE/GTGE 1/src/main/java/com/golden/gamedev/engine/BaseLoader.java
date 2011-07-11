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
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.golden.gamedev.util.BufferedImageUtil;

/**
 * The {@link BaseLoader} class provides the default implementation of the {@link BufferedImageCache} interface that
 * uses a {@link BaseIO} instance in combination with {@link BufferedImageUtil} to retrieve images when needed to store into the
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
		Validate.isTrue(StringUtils.isNotBlank(imagefile), "The file name may not be blank!");
		BufferedImage image = imageStore.get(imagefile);
		
		if (image == null) {
			final URL url = base.getURL(imagefile);
			
			try {
				image = (maskColor != null) ? BufferedImageUtil.applyMask(ImageIO.read(url), maskColor) : ImageIO.read(url);
			} catch (IOException e) {
				throw new RuntimeException("Unexpected IOException occurred when retrieving the image for URL " + url,
						e);
			}
			
			imageStore.put(imagefile, image);
		}
		
		return image;
	}
	
	@Override
	public BufferedImage[] getImages(final String imagefile, final int col, final int row, Color maskColor) {
		Validate.isTrue(StringUtils.isNotBlank(imagefile), "The file name may not be blank!");
		Validate.isTrue(col > 0, "The columns to cut the image with must be greater than zero!");
		Validate.isTrue(row > 0, "The rows to cut the image with must be greater than zero!");
		
		BufferedImage[] image = arrayStore.get(imagefile);
		
		if (image == null) {
			final URL url = base.getURL(imagefile);
			
			try {
				image = (maskColor != null) ? BufferedImageUtil.splitImages(BufferedImageUtil.applyMask(ImageIO.read(url), maskColor), col, row)
						: BufferedImageUtil.splitImages(ImageIO.read(url), col, row);
			} catch (IOException e) {
				throw new RuntimeException("Unexpected IOException occurred when retrieving the image for URL " + url,
						e);
			}
			
			arrayStore.put(imagefile, image);
		}
		
		return image;
	}
	
	@Override
	public boolean removeImage(final BufferedImage image) {
		return removeIfMatched(image, imageStore.values().iterator());
	}
	
	@Override
	public boolean removeImages(final BufferedImage[] images) {
		return removeIfMatched(images, arrayStore.values().iterator());
	}
	
	/**
	 * Removes the given {@link Object} from the given {@link Iterator} if it is matched via
	 * {@link Object#equals(Object) equals} once, returning whether or not an {@link Object} is removed.
	 * 
	 * @param toCheck
	 *            The possibly-null {@link Object} to check for a match.
	 * @param iterator
	 *            The non-null {@link Iterator} to use to remove the {@link Object} if a match is found.
	 * @return True if the {@link Object} was removed from the {@link Iterator} once, false otherwise.
	 */
	private static boolean removeIfMatched(final Object toCheck, final Iterator<?> iterator) {
		while (iterator.hasNext()) {
			if (ObjectUtils.equals(iterator.next(), toCheck)) {
				iterator.remove();
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