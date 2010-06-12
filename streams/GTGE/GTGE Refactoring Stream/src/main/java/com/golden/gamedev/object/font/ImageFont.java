/**
 * 
 */
package com.golden.gamedev.object.font;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.golden.gamedev.object.GameFont;

/**
 * The {@link ImageFont} class provides a simple {@link GameFont} implementation
 * that uses a backing {@link Map} of {@link Character} keys to {@link Image}
 * values to use to draw characters on the given {@link Graphics2D} instance.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see GameFont
 * 
 */
public final class ImageFont implements GameFont {
	
	/**
	 * The default character sequence that is used when a new {@link ImageFont}
	 * is constructed via {@link ImageFont#ImageFont(Image[])}.
	 * @see ImageFont#ImageFont(Image[])
	 */
	public static final String DEFAULT_CHARACTER_SEQUENCE = " !\"#$%&'()*+,-./0123"
	        + "456789:;<=>?@ABCDEFG"
	        + "HIJKLMNOPQRSTUVWXYZ["
	        + "\\]^_`abcdefghijklmno" + "pqrstuvwxyz{|}~";
	
	/**
	 * The non-null, but possibly {@link Map#isEmpty() empty} {@link Map} of
	 * {@link Character} keys to {@link Image} values.
	 */
	private Map characterToImageMap = new HashMap();
	
	/**
	 * Internal flag set if all images are the same height, provides
	 * optimization for the {@link #getHeight(String)} method invocation.
	 */
	private boolean sameHeightOptimization = false;
	
	/**
	 * Creates a new {@link ImageFont} instance with no {@link Character
	 * characters} {@link #isAvailable(char) available} to be drawn.
	 */
	public ImageFont() {
		super();
	}
	
	/**
	 * Creates a new {@link ImageFont} instance with the given non-null
	 * {@link Map} of {@link Character} keys to {@link Image} values.
	 * @param characterToImageMap The non-null {@link Map} of {@link Character}
	 *        keys to {@link Image} values.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if
	 *         {@link #setCharacterToImageMap(Map)} would throw an
	 *         {@link IllegalArgumentException}
	 * @see #setCharacterToImageMap(Map)
	 */
	public ImageFont(final Map characterToImageMap) {
		super();
		this.setCharacterToImageMap(characterToImageMap);
	}
	
	/**
	 * Creates new {@link ImageFont} instance with the given {@link Image} array
	 * that is expected to be in the following
	 * {@link #DEFAULT_CHARACTER_SEQUENCE default character sequence}: <br />
	 * <br />
	 * 
	 * <pre>
	 *         ! &quot; # $ % &amp; ' ( ) * + , - . / 0 1 2 3
	 *       4 5 6 7 8 9 : ; &lt; = &gt; ? @ A B C D E F G
	 *       H I J K L M N O P Q R S T U V W X Y Z [
	 *       \ ] &circ; _ ' a b c d e f g h i j k l m n o
	 *       p q r s t u v w x y z { | } &tilde;
	 * </pre>
	 * 
	 * @param images The non-null array of images that may have less characters
	 *        than the {@link #DEFAULT_CHARACTER_SEQUENCE default character
	 *        sequence}, but will be assumed to contain the characters in the
	 *        order of the character sequence.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given {@link Image} array
	 *         is null, or it had more characters than the length of the
	 *         {@link #DEFAULT_CHARACTER_SEQUENCE default character sequence}.
	 */
	public ImageFont(Image[] images) {
		this(images, DEFAULT_CHARACTER_SEQUENCE);
	}
	
	/**
	 * Creates a new {@link ImageFont} instance with the given
	 * {@link CharSequence} to be mapped to the given {@link Image} array.
	 * @param images The non-null array of non-null {@link Image} instances to
	 *        be mapped as values to the given {@link Character} keys contained
	 *        in the given {@link CharSequence}.
	 * @param characters The non-null {@link CharSequence} containing
	 *        {@link Character} instances to be mapped to the given
	 *        {@link Image} array.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if
	 *         {@link #addCharacter(Character, Image)} would throw an
	 *         {@link IllegalArgumentException}, or if the given values are null
	 *         or the {@link Image} array has a length that exceeds the given
	 *         {@link CharSequence}.
	 * @see #addCharacter(Character, Image)
	 */
	public ImageFont(Image[] images, CharSequence characters) {
		super();
		if (characters == null) {
			throw new IllegalArgumentException(
			        "The character array may not be null.");
		}
		if (images == null) {
			throw new IllegalArgumentException(
			        "The images array may not be null.");
		}
		if (images.length > characters.length()) {
			throw new IllegalArgumentException(
			        "The images array must have the same length as the images array.");
		}
		for (int index = 0; index < images.length; index++) {
			// NOTE: for a 1.5 implementation, let autoboxing do its stuff -
			// this is a candidate for rewiring.
			this.addCharacter(new Character(characters.charAt(index)),
			        images[index]);
		}
	}
	
	public final int drawString(final Graphics2D g, final String s, final int x, final int y) {
		int resultingXCoordinate = x;
		
		for (int index = 0; index < s.length(); index++) {
			char character = s.charAt(index);
			g.drawImage(ImageFont.retrieveNonNullImageForCharacter(
			        new Character(character), this.characterToImageMap),
			        resultingXCoordinate, y, null);
			resultingXCoordinate += this.getWidth(character);
		}
		
		return resultingXCoordinate;
	}
	
	public final int drawString(final Graphics2D g, final String s, final int alignment, final int x, final int y, final int width) {
		if (alignment == GameFont.LEFT) {
			return this.drawString(g, s, x, y);
			
		}
		else if (alignment == GameFont.CENTER) {
			return this.drawString(g, s, x + (width / 2)
			        - (this.getWidth(s) / 2), y);
			
		}
		else if (alignment == GameFont.RIGHT) {
			return this.drawString(g, s, x + width - this.getWidth(s), y);
			
		}
		else if (alignment == GameFont.JUSTIFY) {
			// calculate left width
			int leftWidth = width - this.getWidth(s);
			if (leftWidth <= 0) {
				// no width left, use standard draw string
				return this.drawString(g, s, x, y);
			}
			
			return this.drawJustifiedSpaceSeparatedSubstrings(g, s, x, y, this
			        .calculateWidthOfJustifiedSpaceInPixels(s, leftWidth));
		}
		
		return 0;
	}
	
	public final int drawText(final Graphics2D g, final String text, final int alignment, final int x, final int y, final int width, final int vspace, final int firstIndent) {
		boolean firstLine = true;
		int curpos = 0;
		int startpos = 0;
		int endpos = 0;
		int currentWidthForText = firstIndent;
		int currentYCoordinate = y;
		String lastSubstringDrawn = null;
		for (int index = 0; index < text.length(); index++) {
			char character = text.charAt(index);
			currentWidthForText += this.getWidth(character);
			if (character == ' ') { // space
				endpos = curpos - 1;
			}
			
			if (currentWidthForText >= width) {
				if (firstLine) {
					lastSubstringDrawn = text.substring(startpos, endpos);
					// draw first line with indentation
					this
					        .drawString(g, lastSubstringDrawn, alignment,
					                (alignment == GameFont.RIGHT) ? x : x
					                        + firstIndent, currentYCoordinate,
					                width - firstIndent);
					firstLine = false;
				}
				else {
					lastSubstringDrawn = text.substring(startpos, endpos);
					this.drawString(g, lastSubstringDrawn, alignment, x,
					        currentYCoordinate, width);
				}
				
				currentYCoordinate += this.getHeight(lastSubstringDrawn)
				        + vspace;
				
				currentWidthForText = 0;
				curpos = endpos + 1;
				startpos = curpos;
			}
		}
		
		if (firstLine) {
			// only one line
			this.drawString(g, text.substring(startpos, curpos), alignment,
			        (alignment == GameFont.RIGHT) ? x : x + firstIndent,
			        currentYCoordinate, width - firstIndent);
			
		}
		else if (currentWidthForText != 0) {
			// draw last line
			this.drawString(g, text.substring(startpos, curpos), alignment,
			// (alignment == RIGHT) ? RIGHT : LEFT,
			        x, currentYCoordinate, width);
			
		}
		
		if (lastSubstringDrawn == null) {
			return currentYCoordinate;
		}
		
		return currentYCoordinate + this.getHeight(lastSubstringDrawn);
	}
	
	public final int getHeight() {
		return this.characterToImageMap.isEmpty() ? 0
		        : ((Image) this.characterToImageMap.values().iterator().next())
		                .getHeight(null);
	}
	
	public final int getWidth(final String st) {
		int totalWidth = 0;
		for (int index = 0; index < st.length(); index++) {
			totalWidth += this.getWidth(st.charAt(index));
		}
		return totalWidth;
	}
	
	public final int getWidth(final char c) {
		Image image = (Image) this.characterToImageMap.get(new Character(c));
		return image == null ? 0 : image.getWidth(null);
	}
	
	public final boolean isAvailable(final char c) {
		return this.characterToImageMap.containsKey(new Character(c));
	}
	
	public final int getHeight(final String s) {
		if (this.sameHeightOptimization) {
			return this.getHeight();
		}
		int maximumHeight = 0;
		for (int index = 0; index < s.length(); index++) {
			maximumHeight = Math.max(maximumHeight, ImageFont
			        .retrieveNonNullImageForCharacter(
			                new Character(s.charAt(index)),
			                this.characterToImageMap).getHeight(null));
		}
		return maximumHeight;
	}
	
	/**
	 * Calculates and returns the width of a single justified space for the
	 * given {@link String}, in pixels.
	 * @param s The given non-null {@link String} to use to calculate the width
	 *        of a space for.
	 * @param leftWidth The integer specifying the left width buffer to use to
	 *        calculate how much space a space should take up in justification.
	 * @return The width of a single justified space for the given
	 *         {@link String}, in pixels.
	 */
	private int calculateWidthOfJustifiedSpaceInPixels(final String s, final int leftWidth) {
		int space = 0; // hold total space; hold space width in pixel
		int curpos = 0; // current string position
		
		// count total space
		while (curpos < s.length()) {
			if (s.charAt(curpos++) == ' ') {
				space++;
			}
		}
		
		if (space > 0) {
			// width left plus with total space
			// space width (in pixel) = width left / total space
			space = (leftWidth + (this.getWidth(' ') * space)) / space;
		}
		return space;
	}
	
	/**
	 * Draws justified-space separated substrings based on the given
	 * {@link String} and the given starting x and y coordinates to the given
	 * {@link Graphics2D} instance.
	 * 
	 * @param g The non-null {@link Graphics2D} instance to use to draw the
	 *        given {@link String}.
	 * @param s The non-null {@link String} to draw as space-separated
	 *        substrings.
	 * @param x The given starting x-coordinate position to use to draw the
	 *        {@link String}.
	 * @param y The given starting y-coordinate position to use to draw the
	 *        {@link String}.
	 * @param justifiedSpaceWidth The integer specifying the width of a
	 *        justified space {@link String}, in pixels.
	 * @return The resulting x-coordinate of the current cursor after the
	 *         drawing operation completes.
	 * @throws NullPointerException Throws a {@link NullPointerException} if any
	 *         of the given arguments are null.
	 */
	private int drawJustifiedSpaceSeparatedSubstrings(final Graphics2D g, final String s, final int x, final int y, final int justifiedSpaceWidth) {
		int curpos = 0;
		int endpos = 0;
		int resultingXCoordinate = x;
		while (curpos < s.length()) {
			endpos = s.indexOf(' ', curpos); // find space
			if (endpos == -1) {
				endpos = s.length(); // no space, draw all string directly
			}
			String substring = s.substring(curpos, endpos);
			
			this.drawString(g, substring, resultingXCoordinate, y);
			
			resultingXCoordinate += this.getWidth(substring)
			        + justifiedSpaceWidth; // increase
			// x-coordinate
			curpos = endpos + 1;
		}
		
		return resultingXCoordinate;
	}
	
	/**
	 * Retrieves a non-null {@link Image} for the given non-null
	 * {@link Character} to draw or else throws an {@link IllegalStateException}
	 * if an {@link Image} could not be retrieved for the given
	 * {@link Character} to draw.
	 * @param character The non-null {@link Character} to use to retrieve a
	 *        non-null {@link Image} to use to draw it.
	 * @param characterToImageMap The non-null (but possibly
	 *        {@link Map#isEmpty() empty} {@link Map} of {@link Character} keys
	 *        to their {@link Image} values.
	 * @return A non-null {@link Image} to use to draw the given
	 *         {@link Character}.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given {@link Map} did not
	 *         contain a non-null {@link Image} value for the given non-null
	 *         {@link Character} key.
	 */
	private static Image retrieveNonNullImageForCharacter(final Character character, final Map characterToImageMap) {
		Image image = (Image) characterToImageMap.get(character);
		if (image == null) {
			throw new IllegalArgumentException(
			        "Unable to retrieve image for character: " + character);
		}
		return image;
	}
	
	/**
	 * Adds the given non-null {@link Character} to this {@link ImageFont}
	 * instance to be drawn with the given non-null {@link Image} instance,
	 * replacing any {@link Image} that was previously associated with the given
	 * {@link Character}.
	 * 
	 * @param character The non-null {@link Character} to add to this
	 *        {@link ImageFont} instance with the given {@link Image} to draw.
	 * @param image The non-null {@link Image} to use to draw the given non-null
	 *        {@link Character}.
	 * @return The (possibly null) previous {@link Image} associated with the
	 *         given {@link Character}.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if either of the given arguments
	 *         are null.
	 * @throws UnsupportedOperationException Throws an
	 *         {@link UnsupportedOperationException} if the
	 *         {@link Map#put(Object, Object)} operation is not supported by the
	 *         current {@link #setCharacterToImageMap(Map) character to image
	 *         map}.
	 */
	public final Image addCharacter(final Character character, final Image image) {
		if (character == null) {
			throw new IllegalArgumentException(
			        "The given character may not be null.");
		}
		if (image == null) {
			throw new IllegalArgumentException(
			        "The given image may not be null.");
		}
		if (this.characterToImageMap.isEmpty()) {
			this.sameHeightOptimization = true;
		}
		else {
			int height = this.getHeight();
			this.sameHeightOptimization = this.sameHeightOptimization
			        && height == image.getHeight(null);
		}
		return (Image) this.characterToImageMap.put(character, image);
	}
	
	/**
	 * Removes the given non-null {@link Character} instance's associated
	 * {@link Image} from the {@link Map} and returns the {@link Image} that was
	 * associated with the given {@link Character} instance.
	 * @param character The given non-null {@link Character} to use to remove an
	 *        {@link Image} from the {@link Map}.
	 * @return The {@link Image} that was associated with the given
	 *         {@link Character} instance.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given {@link Character}
	 *         is null.
	 */
	public final Image removeCharacter(final Character character) {
		if (character == null) {
			throw new IllegalArgumentException(
			        "The given character may not be null.");
		}
		Image result = (Image) this.characterToImageMap.remove(character);
		if (this.characterToImageMap.size() <= 1) {
			this.sameHeightOptimization = true;
		}
		return result;
	}
	
	/**
	 * Sets the non-null {@link Map} of {@link Character} keys to {@link Image}
	 * values to the given non-null {@link Map} instance specified.
	 * @param characterToImageMap The non-null {@link Map} of {@link Character}
	 *        keys to {@link Image} values to set.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given {@link Map} is
	 *         null, or it does not contain {@link Character} keys mapped to
	 *         {@link Image} values.
	 */
	public final void setCharacterToImageMap(final Map characterToImageMap) {
		if (characterToImageMap == null) {
			throw new IllegalArgumentException(
			        "The character to image map may be empty but never null.");
		}
		boolean sameHeightOptimization = true;
		int imageHeight = Integer.MIN_VALUE;
		for (Iterator entryIterator = characterToImageMap.entrySet().iterator(); entryIterator
		        .hasNext();) {
			Entry entry = (Entry) entryIterator.next();
			if (!(entry.getKey() instanceof Character)) {
				throw new IllegalArgumentException(
				        "The given character to image map must contain character keys.");
			}
			if (!(entry.getValue() instanceof Image)) {
				throw new IllegalArgumentException(
				        "The given character to image map must contain Image values.");
			}
			int currentImageHeight = ((Image) entry.getValue()).getHeight(null);
			sameHeightOptimization = imageHeight == Integer.MIN_VALUE
			        || imageHeight == currentImageHeight;
			imageHeight = currentImageHeight;
		}
		this.characterToImageMap = characterToImageMap;
		this.sameHeightOptimization = sameHeightOptimization;
	}
	
	/**
	 * Clears the {@link Map} of {@link Character} instance keys to
	 * {@link Image} values.
	 */
	public final void clearCharacterToImageMap() {
		// This is more efficient for larger maps - clear takes time to reset
		// all pointers.
		this.characterToImageMap = new HashMap();
		this.sameHeightOptimization = true;
	}
}
