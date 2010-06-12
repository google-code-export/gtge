/**
 * 
 */
package com.golden.gamedev;

import java.awt.image.BufferedImage;

/**
 * The {@link BufferedImageHolder} interface specifies an {@link Object} that is
 * able to store and retrieve a {@link BufferedImage} instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface BufferedImageHolder {
	
	/**
	 * Gets the (possibly null) {@link BufferedImage} stored in this
	 * {@link BufferedImageHolder} instance.
	 * @return The (possibly null) {@link BufferedImage} stored in this
	 *         {@link BufferedImageHolder} instance.
	 */
	BufferedImage getImage();
	
	/**
	 * Sets the (possibly null) {@link BufferedImage} stored in this
	 * {@link BufferedImageHolder} instance.
	 * @param image The (possibly null) {@link BufferedImage} stored in this
	 *        {@link BufferedImageHolder} instance.
	 */
	void setImage(final BufferedImage image);
	
}
