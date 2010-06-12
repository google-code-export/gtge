/**
 * 
 */
package com.golden.gamedev;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MetroidFan2002
 * 
 */
public class MockBufferedImageHolder implements BufferedImageHolder {
	
	private int imageIndex = 0;
	
	private List imageList = new ArrayList();
	
	/**
	 * Creates a new {@link MockBufferedImageHolder} instance, populated with a
	 * single null {@link BufferedImage} instance as the {@link #getImage()
	 * current image}.
	 */
	public MockBufferedImageHolder() {
		super();
		imageList.add(null);
	}
	
	public BufferedImage getImage() {
		return (BufferedImage) imageList.get(imageIndex);
	}
	
	public void setImage(BufferedImage image) {
		imageList.set(imageIndex, image);
	}
	
	public void clearImages() {
		imageList = new ArrayList();
	}
	
	public void goToNextImage() {
		if (imageList.isEmpty()) {
			throw new IllegalStateException(
			        "Image list is empty, it is not possible to go to the next image.");
		}
		imageIndex = (imageIndex + 1) % imageList.size();
	}
	
	public void goToPreviousImage() {
		if (imageList.isEmpty()) {
			throw new IllegalStateException(
			        "Image list is empty, it is not possible to go to the previous image.");
		}
		imageIndex--;
		if (imageIndex < 0) {
			imageIndex = imageList.size() - 1;
		}
	}
	
}
