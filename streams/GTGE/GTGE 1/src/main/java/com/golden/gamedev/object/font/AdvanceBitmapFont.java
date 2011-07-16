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
package com.golden.gamedev.object.font;

// JFC
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import org.apache.commons.lang.Validate;

import com.golden.gamedev.util.BufferedImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Game font that use images for the letter, each images can have different width but must have same height.
 * <p>
 * 
 * <code>AdvanceBitmapFont</code> takes up two parameters, the array of images font and the sequence of the images, for
 * example if the images font array sequence is ordered as follow: "ABCDEFGHIJKLMNOPQRSTUVWXYZ", specify the parameter
 * letter sequence as is.
 * <p>
 * 
 * If the images font have same width, use the standard {@link com.golden.gamedev.object.font.BitmapFont}.
 */
public final class AdvanceBitmapFont extends BitmapFont {
	
	private int[] w;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>AdvanceBitmapFont</code> with specified images font and letter sequence.
	 * 
	 * @param imagefont
	 *            the images font, all images must have same height
	 * @param letterSequence
	 *            the order sequence of the images font
	 */
	public AdvanceBitmapFont(BufferedImage[] imagefont, String letterSequence) {
		super(imagefont, letterSequence);
		
		this.w = new int[imagefont.length];
		
		for (int i = 0; i < imagefont.length; i++) {
			this.w[i] = imagefont[i].getWidth();
		}
	}
	
	/**
	 * Creates new <code>AdvanceBitmapFont</code> with specified images font and default letter sequence :
	 * 
	 * <pre>
	 *         ! &quot; # $ % &amp; ' ( ) * + , - . / 0 1 2 3
	 *       4 5 6 7 8 9 : ; &lt; = &gt; ? @ A B C D E F G
	 *       H I J K L M N O P Q R S T U V W X Y Z [
	 *       \ ] &circ; _ ` a b c d e f g h i j k l m n o
	 *       p q r s t u v w x y z { | } &tilde;
	 * </pre>
	 * 
	 * @param imagefont
	 *            the images font, all images must have same height
	 */
	public AdvanceBitmapFont(BufferedImage[] imagefont) {
		this(imagefont, " !\"#$%&'()*+,-./0123" + "456789:;<=>?@ABCDEFG" + "HIJKLMNOPQRSTUVWXYZ["
				+ "\\]^_`abcdefghijklmno" + "pqrstuvwxyz{|}~");
	}
	
	// REVIEW-HIGH: Document this constructor.
	public AdvanceBitmapFont(BufferedImage image) {
		this(cutLetter(image));
	}
	
	// REVIEW-HIGH: Document this constructor.
	public AdvanceBitmapFont(BufferedImage image, String letterSequence) {
		this(cutLetter(image), letterSequence);
	}
	
	public void setImageFont(BufferedImage[] imagefont, String letterSequence) {
		super.setImageFont(imagefont, letterSequence);
		
		this.w = new int[imagefont.length];
		
		for (int i = 0; i < imagefont.length; i++) {
			this.w[i] = imagefont[i].getWidth();
		}
	}
	
	public int getWidth(char c) {
		return this.w[this.charIndex[(int) c]];
	}
	
	public int getWidth(String st) {
		int len = st.length();
		int width = 0;
		
		for (int i = 0; i < len; i++) {
			width += this.getWidth(st.charAt(i));
		}
		
		return width;
	}
	
	// REVIEW-MEDIUM: - This method needs to be moved inside the AdvanceBitmapFont class because it is applicable for
	// its
	// construction only.
	public static BufferedImage[] cutLetter(BufferedImage bitmap) {
		int delimiter = bitmap.getRGB(0, 0); // pixel <0,0> : delimiter
		Integer[] width = new Integer[100]; // assumption : 100 letter
		int ctr = 0;
		int last = 0; // last width point
		
		for (int i = 1; i < bitmap.getWidth(); i++) {
			if (bitmap.getRGB(i, 0) == delimiter) {
				// found delimiter
				width[ctr++] = i - last;
				last = i;
				
				if (ctr >= width.length) {
					width = Utility.expand(width, 50, true);
				}
			}
		}
		
		// create bitmap font
		BufferedImage[] imagefont = new BufferedImage[ctr];
		Color backgr = new Color(bitmap.getRGB(1, 0));
		int height = bitmap.getHeight() - 1;
		int w = 0;
		for (int i = 0; i < imagefont.length; i++) {
			imagefont[i] = BufferedImageUtil.applyMask(bitmap.getSubimage(w, 1, width[i], height), backgr);
			
			w += width[i];
		}
		
		return imagefont;
	}
	
	// REVIEW-HIGH: Place this documentation somewhere appropriate in this class.
	/**
	 * Returns default {@link com.golden.gamedev.object.font.AdvanceBitmapFont} that using standard <i>Bitmap Font
	 * Writer</i>, created by Stefan Pettersson. Bitmap Font Writer is freeware font editor, visit Bitmap Font Writer
	 * website (http://www.stefan-pettersson.nu) for updates and additional information.
	 * <p>
	 * 
	 * The images should be following this letter sequence :
	 * 
	 * <pre>
	 *         ! &quot; # $ % &amp; ' ( ) * + , - . / 0 1 2 3
	 *       4 5 6 7 8 9 : ; &lt; = &gt; ? @ A B C D E F G
	 *       H I J K L M N O P Q R S T U V W X Y Z [
	 *       \ ] &circ; _ a b c d e f g h i j k l m n o p
	 *       q r s t u v w x y z { | } &tilde;
	 * </pre>
	 * 
	 * How to: Creating <i>Bitmap Font Writer</i> Font <br>
	 * The image size shall be cut exactly according to the font size, but leaving one pixel row above the characters. <br>
	 * This row of pixels is used to define each characters width. <br>
	 * The first pixel (0,0) will be used as the font width delimiters.
	 * 
	 * @param bitmap
	 *            the font images
	 * @return Bitmap <code>GameFont</code>.
	 */
	
	/**
	 * Creates a {@link BufferedImage} from the given {@link Font} and {@link Color} with the standard sequence of
	 * printable ASCII characters. The {@link BufferedImage} returned is compatible with the image format the <a
	 * href="http://www.stefan-pettersson.nu/site/bmpfont/" title="Bitmap Font Writer Link" >Bitmap Font Writer</a>
	 * application created by <a href="http://www.stefan-pettersson.nu">Stefan Pettersson</a> can use, so that it can be
	 * previewed and used externally.
	 * 
	 * @param sourceFont
	 *            The non-null source {@link Font} to use to print the characters to the {@link BufferedImage}.
	 * @param characterColor
	 *            The non-null {@link Color} representing the color that should be used to print each character.
	 * @return A {@link BufferedImage} containing an image with the standard sequence of printable ASCII characters.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if either argument is null.
	 */
	public static BufferedImage createBitmapFont(Font sourceFont, Color characterColor) {
		Validate.notNull(sourceFont, "The source font may not be null!");
		Validate.notNull(characterColor, "The character color may not be null!");
		// REVIEW-LOW: rename g to throwAwayGraphicsContext, or, nest the calls to remove g entirely.
		Graphics2D g = BufferedImageUtil.createImage(1, 1, Transparency.OPAQUE).createGraphics();
		// REVIEW-LOW: rename fm to fontMetricsForSelectedFont
		FontMetrics fm = g.getFontMetrics(sourceFont);
		g.dispose();
		
		// REVIEW-MEDIUM - The construction of this string should be done in a static initialization method and kept for
		// the lifetime of the AdvanceBitmapFont class. Also, rename the string.
		byte[] bytes = new byte[95];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (32 + i);
		}
		String st = new String(bytes); // all letters
		
		// REVIEW-LOW: separate declarations on their own line. Rename variables to avoid using comments which can be
		// expressed via their name.
		int w = fm.stringWidth(st), // image width
		h = fm.getHeight(), // and height
		// REVIEW-LOW: no need for a variable since you have the string (repeated calls to length() will be inlined).
		len = st.length(), // total letter count
		x = 0, // draw letter to <x, y>
		// REVIEW-HIGH: looks like getMaxDescent() should be used here instead!
		y = h - fm.getDescent() + 1;
		
		// REVIEW-LOW: rename to characterSpacingDelimiterColor
		// REVIEW-LOW: use the ternary operator to perform the check of green or yellow to shorten the code.
		Color delim = Color.GREEN; // delimiter at <0, 0>
		
		// to avoid the color same like font color
		if (delim.equals(characterColor)) {
			delim = Color.YELLOW;
		}
		
		// draw all letters to the bitmap
		// REVIEW-MEDIUM - this screams out as a refactorable function.
		BufferedImage bitmap = BufferedImageUtil.createImage(w, h, Transparency.BITMASK);
		g = bitmap.createGraphics();
		g.setFont(sourceFont);
		
		for (int i = 0; i < len; i++) {
			char c = st.charAt(i);
			
			// draw delimiter
			g.setColor(delim);
			g.drawLine(x, 0, x, 0);
			
			// draw letter
			g.setColor(characterColor);
			g.drawString(String.valueOf(c), x, y);
			x += fm.charWidth(c);
		}
		
		g.dispose();
		
		return bitmap;
	}
	
}
