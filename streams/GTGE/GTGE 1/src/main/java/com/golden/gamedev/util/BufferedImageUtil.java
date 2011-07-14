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
package com.golden.gamedev.util;

// JFC
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.apache.commons.lang.Validate;

/**
 * Utility class for creating, loading, and manipulating image.
 */
public final class BufferedImageUtil {
	
	/**
	 * The {@link AffineTransformOp} instance to use to vertically flip a {@link BufferedImage} instance.
	 */
	private static final AffineTransformOp VERTICAL_IMAGE_FLIP_OPERATION = new AffineTransformOp(
			AffineTransform.getScaleInstance(1, -1), AffineTransformOp.TYPE_BICUBIC);
	
	/**
	 * The {@link AffineTransformOp} instance to use to horizontally flip a {@link BufferedImage} instance.
	 */
	private static final AffineTransformOp HORIZONTAL_IMAGE_FLIP_OPERATION = new AffineTransformOp(
			AffineTransform.getScaleInstance(-1, 1), AffineTransformOp.TYPE_BICUBIC);
	
	/**
	 * This constructor would creates a new {@link BufferedImageUtil} instance, but as it's a utility class, it throws a
	 * new {@link UnsupportedOperationException} to prevent this.
	 * 
	 * @throws UnsupportedOperationException
	 *             Throws an {@link UnsupportedOperationException} to prevent instantiation of the
	 *             {@link BufferedImageUtil} class.
	 */
	private BufferedImageUtil() {
		throw new UnsupportedOperationException("The ImageUtil class may not be instantiated!");
	}
	
	/**
	 * Creates a blank image compatible with rendering on the {@link GraphicsEnvironment#getLocalGraphicsEnvironment()
	 * local graphics environment} with the given width, height and {@link Transparency}.
	 * 
	 * @param width
	 *            The width of the image to be created, which must be greater than zero.
	 * @param height
	 *            The height of the image to be created, which must be greater than zero.
	 * @param transparency
	 *            The {@link Transparency} of the image to be created.
	 * @return The blank {@link BufferedImage} compatible with the current
	 *         {@link GraphicsEnvironment#getLocalGraphicsEnvironment() local graphics environment}.
	 * @throws HeadlessException
	 *             Throws a {@link HeadlessException} if the current machine is unable to display an image.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the width or height is less than or equal to zero, or
	 *             the given {@link Transparency} value is invalid.
	 * @see Transparency For the values that are supported for the transparency value.
	 */
	public static BufferedImage createImage(final int width, final int height, final int transparency) {
		Validate.isTrue(width > 0, "The width must be greater than zero!");
		Validate.isTrue(height > 0, "The height must be greater than zero!");
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, transparency);
	}
	
	/**
	 * Creates a new {@link BufferedImage} which is the same as the original {@link BufferedImage} with a mask that
	 * makes the given {@link Color} converted to a transparent value.
	 * 
	 * @param image
	 *            The non-null source {@link BufferedImage} instance to apply the mask to.
	 * @param colorToMask
	 *            The non-null {@link Color} to be masked.
	 * @return The {@link BufferedImage} instance with the given {@link Color} value converted to transparent.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if either argument is null.
	 */
	public static BufferedImage applyMask(final BufferedImage image, final Color colorToMask) {
		Validate.notNull(image, "The image may not be null!");
		Validate.notNull(colorToMask, "The color to mask may not be null!");
		final BufferedImage alpha = BufferedImageUtil.createImage(image.getWidth(), image.getHeight(),
				Transparency.BITMASK);
		
		final Graphics2D g = alpha.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		final int transparentPixelColorToMask = colorToMask.getRGB();
		for (int y = 0; y < alpha.getHeight(); y++) {
			for (int x = 0; x < alpha.getWidth(); x++) {
				final int pixelColor = alpha.getRGB(x, y);
				if (pixelColor == transparentPixelColorToMask) {
					alpha.setRGB(x, y, pixelColor & 0x00ffffff);
				}
			}
		}
		
		return alpha;
	}
	
	/**
	 * Splits a single image into an array of images based on the specified number of columns and rows.
	 * 
	 * @param image
	 *            the source image
	 * @param columns
	 *            image column
	 * @param rows
	 *            image row
	 * @return Array of images cutted by specified column and row.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the given image is null, or either the columns or the
	 *             rows specified is less than or equal to zero.
	 */
	public static BufferedImage[] splitImages(final BufferedImage image, final int columns, final int rows) {
		Validate.notNull(image, "The given image may not be null!");
		Validate.isTrue(columns > 0, "The number of columns to split the image into must be greater than zero!");
		Validate.isTrue(rows > 0, "The number of rows to split the image into must be greater than zero!");
		
		final int sourceImageWidth = image.getWidth() / columns;
		final int sourceImageHeight = image.getHeight() / rows;
		final int transparency = image.getColorModel().getTransparency();
		final BufferedImage[] returnedImages = new BufferedImage[rows * columns];
		
		int imageIndex = 0;
		for (int currentRow = 0; currentRow < rows; currentRow++) {
			for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
				final BufferedImage currentImage = BufferedImageUtil.createImage(sourceImageWidth, sourceImageHeight,
						transparency);
				
				final Graphics2D graphicsContext = currentImage.createGraphics();
				graphicsContext.drawImage(image, 0, 0, sourceImageWidth, sourceImageHeight, currentColumn
						* sourceImageWidth, currentRow * sourceImageHeight, (currentColumn + 1) * sourceImageWidth,
						(currentRow + 1) * sourceImageHeight, null);
				graphicsContext.dispose();
				
				returnedImages[imageIndex++] = currentImage;
			}
		}
		
		return returnedImages;
	}
	
	// REVIEW-HIGH: Make a utility that expressly tests this class so that it can be shown that the image rotation is
	// working properly.
	/**
	 * Rotates an image by specified angle (clockwise).
	 * <p>
	 * 
	 * For example: <br>
	 * 
	 * <pre>
	 * BufferedImage image;
	 * // rotate the image by 90 degree clockwise
	 * BufferedImage rotated = ImageUtil.rotate(image, 90);
	 * </pre>
	 * 
	 * @param src
	 *            the source image
	 * @param angle
	 *            angle rotation
	 * @return Rotated image.
	 */
	public static BufferedImage rotate(final BufferedImage src, final int angle) {
		return new AffineTransformOp(AffineTransform.getRotateInstance(Math.toRadians(angle)),
				AffineTransformOp.TYPE_BICUBIC).filter(src, null);
	}
	
	/**
	 * Resizes an image into specified size.
	 * <p>
	 * 
	 * For example: <br>
	 * 
	 * <pre>
	 * BufferedImage image;
	 * // resize the image to 200x300 size
	 * BufferedImage resized = ImageUtil.resize(image, 200, 300);
	 * // double the size of the image
	 * BufferedImage doubleResize = ImageUtil.resize(image, image.getWidth() * 2, image.getHeight() * 2);
	 * </pre>
	 * 
	 * @param src
	 *            the source image
	 * @param width
	 *            width of the resized image
	 * @param height
	 *            height of the resized image
	 * @return Resized image.
	 */
	public static BufferedImage resize(final BufferedImage src, final int width, final int height) {
		Validate.notNull(src, "The BufferedImage instance to resize may not be null!");
		Validate.isTrue(width > 0, "The width must be greater than zero!");
		Validate.isTrue(height > 0, "The height must be greater than zero!");
		final double verticalScale = (src.getHeight() / height) * (src.getHeight() < height ? -1 : 1);
		final double horizontalScale = (src.getWidth() / width) * (src.getWidth() < width ? -1 : 1);
		return new AffineTransformOp(AffineTransform.getScaleInstance(horizontalScale, verticalScale),
				AffineTransformOp.TYPE_BICUBIC).filter(src, null);
	}
	
	/**
	 * Flips an image horizontally.
	 * 
	 * @param src
	 *            the source image
	 * @return Horizontally flipped image.
	 */
	public static BufferedImage flipHorizontal(final BufferedImage src) {
		return BufferedImageUtil.HORIZONTAL_IMAGE_FLIP_OPERATION.filter(src, null);
	}
	
	/**
	 * Flips an image vertically.
	 * 
	 * @param src
	 *            the source image
	 * @return Vertically flipped image.
	 */
	public static BufferedImage flipVertical(final BufferedImage src) {
		return BufferedImageUtil.VERTICAL_IMAGE_FLIP_OPERATION.filter(src, null);
	}
	
}
