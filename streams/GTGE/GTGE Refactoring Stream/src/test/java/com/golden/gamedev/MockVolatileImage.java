/**
 * 
 */
package com.golden.gamedev;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.VolatileImage;

/**
 * The {@link MockVolatileImage} class provides a mock {@link VolatileImage}
 * implementation for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see VolatileImage
 * 
 */
public final class MockVolatileImage extends VolatileImage {
	
	/**
	 * Creates a new {@link MockVolatileImage} instance.
	 */
	public MockVolatileImage() {
	}
	
	public boolean contentsLost() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Graphics2D createGraphics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ImageCapabilities getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public BufferedImage getSnapshot() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int validate(GraphicsConfiguration gc) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getHeight(ImageObserver observer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Object getProperty(String name, ImageObserver observer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getWidth(ImageObserver observer) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
