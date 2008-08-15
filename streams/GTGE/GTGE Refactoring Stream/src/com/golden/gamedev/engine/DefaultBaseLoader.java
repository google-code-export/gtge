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
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.golden.gamedev.util.ImageUtil;

/**
 * Class for loading and masking images, and also behave as storage of the
 * loaded images.
 * <p>
 * 
 * Supported image format: png (*.png), gif (*.gif), and jpeg (*.jpg).
 * <p>
 * 
 * <code>BaseLoader</code> class is using functions from
 * {@link com.golden.gamedev.util.ImageUtil} class for loading and masking
 * images in convenient way.
 * <p>
 * 
 * This class is using {@link DefaultBaseIO} to get the external resources.
 * 
 * @see com.golden.gamedev.util.ImageUtil
 */
public class DefaultBaseLoader implements BaseLoader {
	
	/** ************************** LOADER PROPERTIES **************************** */
	
	// Base IO to get external resources
	private BaseIO base;
	
	// masking color
	private Color maskColor;
	
	/** **************************** IMAGE STORAGE ****************************** */
	
	// store single image
	private Map imageBank;
	
	// store multiple images
	private Map imagesBank;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Constructs new <code>BaseLoader</code> with specified I/O loader, and
	 * masking color.
	 * <p>
	 * 
	 * Masking color is the color of the images that will be converted to
	 * transparent.
	 * 
	 * @param base I/O resource loader
	 * @param maskColor the mask color
	 */
	public DefaultBaseLoader(BaseIO base, Color maskColor) {
		this.setBaseIO(base);
		this.maskColor = maskColor;
		
		this.setImageBank(new HashMap(5));
		this.setImagesBank(new HashMap(30));
	}
	
	/** ************************************************************************* */
	/** *********************** INSERTION OPERATION ***************************** */
	/** ************************************************************************* */
	
	public BufferedImage getImage(String imagefile, boolean useMask) {
		BufferedImage image = (BufferedImage) this.getImageBank().get(imagefile);
		
		if (image == null) {
			URL url = this.getBaseIO().getURL(imagefile);
			
			image = (useMask) ? ImageUtil.getImage(url, this.maskColor)
			        : ImageUtil.getImage(url);
			
			this.getImageBank().put(imagefile, image);
		}
		
		return image;
	}
	
	public BufferedImage getImage(String imagefile) {
		return this.getImage(imagefile, true);
	}
	
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask) {
		BufferedImage[] image = (BufferedImage[]) this.getImagesBank()
		        .get(imagefile);
		
		if (image == null) {
			URL url = this.getBaseIO().getURL(imagefile);
			
			image = (useMask) ? ImageUtil.getImages(url, col, row,
			        this.maskColor) : ImageUtil.getImages(url, col, row);
			
			this.getImagesBank().put(imagefile, image);
		}
		
		return image;
	}
	
	public BufferedImage[] getImages(String imagefile, int col, int row) {
		return this.getImages(imagefile, col, row, true);
	}
	
	/** ************************************************************************* */
	/** ************************ REMOVAL OPERATION ****************************** */
	/** ************************************************************************* */
	
	public boolean removeImage(BufferedImage image) {
		Iterator it = this.getImageBank().values().iterator();
		
		while (it.hasNext()) {
			if (it.next() == image) {
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public boolean removeImages(BufferedImage[] images) {
		Iterator it = this.getImagesBank().values().iterator();
		
		while (it.hasNext()) {
			if (it.next() == images) {
				it.remove();
				return true;
			}
		}
		
		return false;
	}
	
	public BufferedImage removeImage(String imagefile) {
		return (BufferedImage) this.getImageBank().remove(imagefile);
	}
	
	public BufferedImage[] removeImages(String imagefile) {
		return (BufferedImage[]) this.getImagesBank().remove(imagefile);
	}
	
	public void clearCache() {
		this.getImageBank().clear();
		this.getImagesBank().clear();
	}
	
	/** ************************************************************************* */
	/** ************************* CUSTOM OPERATION ****************************** */
	/** ************************************************************************* */
	
	public void storeImage(String key, BufferedImage image) {
		if (this.getImageBank().get(key) != null) {
			throw new ArrayStoreException("Key -> " + key + " is bounded to "
			        + this.getImageBank().get(key));
		}
		
		this.getImageBank().put(key, image);
	}
	
	public void storeImages(String key, BufferedImage[] images) {
		if (this.getImagesBank().get(key) != null) {
			throw new ArrayStoreException("Key -> " + key + " is bounded to "
			        + this.getImagesBank().get(key));
		}
		
		this.getImagesBank().put(key, images);
	}
	
	public BufferedImage getStoredImage(String key) {
		return (BufferedImage) this.getImageBank().get(key);
	}
	
	public BufferedImage[] getStoredImages(String key) {
		return (BufferedImage[]) this.getImagesBank().get(key);
	}
	
	/** ************************************************************************* */
	/** ********************** BASE LOADER PROPERTIES *************************** */
	/** ************************************************************************* */
	
	/**
	 * Returns {@link DefaultBaseIO} associated with this image loader.
	 * @return The {@link DefaultBaseIO} used by the loader.
	 * @see #setBaseIO(DefaultBaseIO)
	 */
	public BaseIO getBaseIO() {
		return this.base;
	}
	
	/**
	 * Sets {@link DefaultBaseIO} where the image resources is loaded from.
	 * @param base The new {@link DefaultBaseIO} used by the loader.
	 */
	public void setBaseIO(BaseIO base) {
		this.base = base;
	}
	
	public Color getMaskColor() {
		return this.maskColor;
	}
	
	public void setMaskColor(Color c) {
		this.maskColor = c;
	}
	
	public String toString() {
		StringBuffer imageKey = new StringBuffer(), imagesKey = new StringBuffer();
		
		Iterator imageIt = this.getImageBank().keySet().iterator(), imagesIt = this.getImagesBank()
		        .keySet().iterator();
		
		imageKey.append("\"");
		while (imageIt.hasNext()) {
			imageKey.append(imageIt.next());
			
			if (imageIt.hasNext()) {
				imageKey.append(",");
			}
		}
		imageKey.append("\"");
		
		imagesKey.append("\"");
		while (imagesIt.hasNext()) {
			String key = (String) imagesIt.next();
			BufferedImage[] image = (BufferedImage[]) this.getImagesBank().get(key);
			int len = (image == null) ? -1 : image.length;
			imagesKey.append(key).append("(").append(len).append(")");
			
			if (imagesIt.hasNext()) {
				imagesKey.append(",");
			}
		}
		imagesKey.append("\"");
		
		return super.toString() + " " + "[maskColor=" + this.maskColor
		        + ", BaseIO=" + this.getBaseIO() + ", imageLoaded=" + imageKey
		        + ", imagesLoaded=" + imagesKey + "]";
	}

	public void setImageBank(Map imageBank) {
	    this.imageBank = imageBank;
    }

	public Map getImageBank() {
	    return imageBank;
    }

	public void setImagesBank(Map imagesBank) {
	    this.imagesBank = imagesBank;
    }

	public Map getImagesBank() {
	    return imagesBank;
    }
	
}
