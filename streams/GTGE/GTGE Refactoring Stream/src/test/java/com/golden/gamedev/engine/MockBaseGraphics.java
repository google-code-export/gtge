/**
 * 
 */
package com.golden.gamedev.engine;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * The {@link MockBaseGraphics} class provides a {@link BaseGraphics}
 * implementation for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see BaseGraphics
 * 
 */
public final class MockBaseGraphics implements BaseGraphics {
	
	/**
	 * The {@link Component} instance to be returned via {@link #getComponent()}
	 * .
	 */
	public Component component;
	
	/**
	 * The {@link Dimension} instance to be returned via {@link #getSize()}.
	 */
	public Dimension size;
	
	/**
	 * Creates a new {@link MockBaseGraphics} instance.
	 */
	public MockBaseGraphics() {
	}
	
	public void cleanup() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public boolean flip() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public Graphics2D getBackBuffer() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public Component getComponent() {
		return component;
	}
	
	public String getGraphicsDescription() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public Image getWindowIcon() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public String getWindowTitle() {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public void setWindowIcon(Image icon) {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
	
	public void setWindowTitle(String title) {
		throw new UnsupportedOperationException("To be implemented as needed.");
	}
}
