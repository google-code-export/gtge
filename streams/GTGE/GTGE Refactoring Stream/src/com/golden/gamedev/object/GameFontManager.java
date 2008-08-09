package com.golden.gamedev.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.font.BitmapFont;

public interface GameFontManager {
	
	/**
	 * Removed all loaded font from the storage.
	 */
	public void clear();
	
	/**
	 * Removes font with specified name from font manager storage.
	 * 
	 * @return Removed font.
	 */
	public Object removeFont(Object name);
	
	/**
	 * Returns font with specified name.
	 * 
	 * @see #putFont(String, GameFont)
	 */
	public GameFont getFont(String name);
	
	/**
	 * Inserts font with specified name to font manager storage.
	 * 
	 * @see #getFont(String)
	 */
	public GameFont putFont(String name, GameFont font);
	
	/**
	 * Returns default {@link com.golden.gamedev.object.font.AdvanceBitmapFont}
	 * that using standard <i>Bitmap Font Writer</i>, created by Stefan
	 * Pettersson. Bitmap Font Writer is freeware font editor, visit Bitmap Font
	 * Writer website (http://www.stefan-pettersson.nu) for updates and
	 * additional information.
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
	 * The image size shall be cut exactly according to the font size, but
	 * leaving one pixel row above the characters. <br>
	 * This row of pixels is used to define each characters width. <br>
	 * The first pixel (0,0) will be used as the font width delimiters.
	 * 
	 * @param bitmap the font images
	 * @return Bitmap <code>GameFont</code>.
	 */
	public GameFont getFont(BufferedImage bitmap);
	
	/**
	 * Returns {@link com.golden.gamedev.object.font.AdvanceBitmapFont} that
	 * using standard <i>Bitmap Font Writer</i>, created by Stefan Pettersson.
	 * Bitmap Font Writer is freeware font editor, visit Bitmap Font Writer
	 * website (http://www.stefan-pettersson.nu) for updates and additional
	 * information.
	 * <p>
	 * 
	 * How to: Creating <i>Bitmap Font Writer</i> Font <br>
	 * The image size shall be cut exactly according to the font size, but
	 * leaving one pixel row above the characters. <br>
	 * This row of pixels is used to define each characters width. <br>
	 * The first pixel (0,0) will be used as the font width delimiters.
	 * 
	 * @param bitmap the font images
	 * @param letterSequence the letter sequence of the bitmap
	 * @return Bitmap <code>GameFont</code>.
	 */
	public GameFont getFont(BufferedImage bitmap, String letterSequence);
	
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
	 * If requested font has not been created before, this method creates new
	 * {@link BitmapFont} and return it.
	 */
	public GameFont getFont(BufferedImage[] bitmap);
	
	/**
	 * Returns bitmap font with specified font images and letter sequence.
	 */
	public GameFont getFont(BufferedImage[] bitmap, String letterSequence);
	
	/**
	 * Returns font with specified system font, the color of the font is
	 * following active color of the graphics context where the font drawn.
	 */
	public GameFont getFont(Font f);
	
	/**
	 * Returns font with specified system font and color.
	 */
	public GameFont getFont(Font f, Color col);
	
}
