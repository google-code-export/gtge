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
package com.golden.gamedev.object;

// JFC
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.golden.gamedev.object.font.AdvanceBitmapFont;
import com.golden.gamedev.object.font.BitmapFont;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Simplify <code>GameFont</code> creation and also behave as the storage of loaded font.
 * 
 * @see com.golden.gamedev.object.font
 */
public class GameFontManager {
	
	// REVIEW-HIGH: this map needs better generic support, but the conversion to make the code compile forces the
	// Object/Object mashup.
	private final Map<Object, Object> fontBank = new HashMap<Object, Object>(5);
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** CONSTRUCTOR *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Creates new <code>GameFontManager</code>.
	 */
	public GameFontManager() {
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************ REMOVAL OPERATION ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Removed all loaded font from the storage.
	 */
	public void clear() {
		this.fontBank.clear();
	}
	
	/**
	 * Removes font with specified name from font manager storage.
	 * 
	 * @return Removed font.
	 */
	public Object removeFont(Object name) {
		return this.fontBank.remove(name);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************* MANUAL OPERATION ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns font with specified name.
	 * 
	 * @see #putFont(String, GameFont)
	 */
	public GameFont getFont(String name) {
		return (GameFont) this.fontBank.get(name);
	}
	
	/**
	 * Inserts font with specified name to font manager storage.
	 * 
	 * @see #getFont(String)
	 */
	public GameFont putFont(String name, GameFont font) {
		return (GameFont) this.fontBank.put(name, font);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *********************** CREATION OPERATION ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
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
	public GameFont getFont(BufferedImage bitmap) {
		GameFont font = (GameFont) this.fontBank.get(bitmap);
		
		if (font == null) {
			font = new AdvanceBitmapFont(this.cutLetter(bitmap));
			this.fontBank.put(bitmap, font);
		}
		
		return font;
	}
	
	/**
	 * Returns {@link com.golden.gamedev.object.font.AdvanceBitmapFont} that using standard <i>Bitmap Font Writer</i>,
	 * created by Stefan Pettersson. Bitmap Font Writer is freeware font editor, visit Bitmap Font Writer website
	 * (http://www.stefan-pettersson.nu) for updates and additional information.
	 * <p>
	 * 
	 * How to: Creating <i>Bitmap Font Writer</i> Font <br>
	 * The image size shall be cut exactly according to the font size, but leaving one pixel row above the characters. <br>
	 * This row of pixels is used to define each characters width. <br>
	 * The first pixel (0,0) will be used as the font width delimiters.
	 * 
	 * @param bitmap
	 *            the font images
	 * @param letterSequence
	 *            the letter sequence of the bitmap
	 * @return Bitmap <code>GameFont</code>.
	 */
	public GameFont getFont(BufferedImage bitmap, String letterSequence) {
		GameFont font = (GameFont) this.fontBank.get(bitmap);
		
		if (font == null) {
			font = new AdvanceBitmapFont(this.cutLetter(bitmap), letterSequence);
			this.fontBank.put(bitmap, font);
		}
		
		return font;
	}
	
	// REVIEW-MEDIUM: - This method needs to be moved inside the AdvanceBitmapFont class because it is applicable for
	// its
	// construction only.
	private BufferedImage[] cutLetter(BufferedImage bitmap) {
		int delimiter = bitmap.getRGB(0, 0); // pixel <0,0> : delimiter
		int[] width = new int[100]; // assumption : 100 letter
		int ctr = 0;
		int last = 0; // last width point
		
		for (int i = 1; i < bitmap.getWidth(); i++) {
			if (bitmap.getRGB(i, 0) == delimiter) {
				// found delimiter
				width[ctr++] = i - last;
				last = i;
				
				if (ctr >= width.length) {
					width = (int[]) Utility.expand(width, 50);
				}
			}
		}
		
		// create bitmap font
		BufferedImage[] imagefont = new BufferedImage[ctr];
		Color backgr = new Color(bitmap.getRGB(1, 0));
		int height = bitmap.getHeight() - 1;
		int w = 0;
		for (int i = 0; i < imagefont.length; i++) {
			imagefont[i] = ImageUtil.applyMask(bitmap.getSubimage(w, 1, width[i], height), backgr);
			
			w += width[i];
		}
		
		return imagefont;
	}
	
	/**
	 * Returns bitmap font with specified images following this letter sequence :
	 * 
	 * <pre>
	 *         ! &quot; # $ % &amp; ' ( ) * + , - . / 0 1 2 3
	 *       4 5 6 7 8 9 : ; &lt; = &gt; ? @ A B C D E F G
	 *       H I J K L M N O P Q R S T U V W X Y Z [
	 *       \ ] &circ; _ a b c d e f g h i j k l m n o p
	 *       q r s t u v w x y z { | } &tilde;
	 * </pre>
	 * 
	 * <p>
	 * 
	 * If requested font has not been created before, this method creates new {@link BitmapFont} and return it.
	 */
	public GameFont getFont(BufferedImage[] bitmap) {
		GameFont font = (GameFont) this.fontBank.get(bitmap);
		
		if (font == null) {
			font = new BitmapFont(bitmap);
			this.fontBank.put(bitmap, font);
		}
		
		return font;
	}
	
	/**
	 * Returns bitmap font with specified font images and letter sequence.
	 */
	public GameFont getFont(BufferedImage[] bitmap, String letterSequence) {
		GameFont font = (GameFont) this.fontBank.get(bitmap);
		
		if (font == null) {
			font = new BitmapFont(bitmap, letterSequence);
			this.fontBank.put(bitmap, font);
		}
		
		return font;
	}
	
	/**
	 * Returns font with specified system font, the color of the font is following active color of the graphics context
	 * where the font drawn.
	 */
	public GameFont getFont(Font f) {
		return this.getFont(f, null);
	}
	
	/**
	 * Returns font with specified system font and color.
	 */
	public GameFont getFont(Font f, Color col) {
		GameFont font = (GameFont) this.fontBank.get(f);
		if (font == null) {
			font = (col == null) ? new SystemFont(f) : new SystemFont(f, col);
			this.fontBank.put(f, font);
		}
		
		return font;
	}
	
	// REVIEW-MEDIUM: better documentation needed, enforce non-nullality for the URL.
	// REVIEW-HIGH: Alter the signature of this method to simply "createFont" and allow the type to be passed in as a
	// nullable integer. Specify that if it is null, it will default to a TrueType font.
	// REVIEW-HIGH: Instead of catching the exceptions, throw them - this is a deficiency in Java that GTGE won't cure,
	// and this method
	// shouldn't simply use verdana fonts as defaults as it is completely unspecified. Can be rethrown as
	// RuntimeExceptions, though.
	/**
	 * Creates java.awt.Font from specified True Type Font URL (*.ttf).
	 * 
	 * @see com.golden.gamedev.engine.BaseIO#getURL(String)
	 * @see com.golden.gamedev.object.GameFontManager#getFont(Font)
	 */
	public static Font createTrueTypeFont(URL url, int style, float size) {
		Font f = null;
		
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (IOException e) {
			System.err.println("ERROR: " + url + " is not found or can not be read");
			f = new Font("Verdana", 0, 0);
		} catch (FontFormatException e) {
			System.err.println("ERROR: " + url + " is not a valid true type font");
			f = new Font("Verdana", 0, 0);
		}
		
		return f.deriveFont(style, size);
	}
	
}
