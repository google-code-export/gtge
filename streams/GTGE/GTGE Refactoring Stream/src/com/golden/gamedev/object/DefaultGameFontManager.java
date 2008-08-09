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
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.golden.gamedev.object.font.AdvanceBitmapFont;
import com.golden.gamedev.object.font.BitmapFont;
import com.golden.gamedev.object.font.SystemFont;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Simplify <code>GameFont</code> creation and also behave as the storage of
 * loaded font.
 * 
 * @see com.golden.gamedev.object.font
 */
public class DefaultGameFontManager implements GameFontManager {
	
	private Map fontBank = new HashMap(5);
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>GameFontManager</code>.
	 */
	public DefaultGameFontManager() {
	}
	
	/** ************************************************************************* */
	/** ************************ REMOVAL OPERATION ****************************** */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#clear()
     */
	public void clear() {
		this.getFontBank().clear();
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#removeFont(java.lang.Object)
     */
	public Object removeFont(Object name) {
		return this.getFontBank().remove(name);
	}
	
	/** ************************************************************************* */
	/** ************************* MANUAL OPERATION ****************************** */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.lang.String)
     */
	public GameFont getFont(String name) {
		return (GameFont) this.getFontBank().get(name);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#putFont(java.lang.String, com.golden.gamedev.object.GameFont)
     */
	public GameFont putFont(String name, GameFont font) {
		return (GameFont) this.getFontBank().put(name, font);
	}
	
	/** ************************************************************************* */
	/** *********************** CREATION OPERATION ****************************** */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.image.BufferedImage)
     */
	public GameFont getFont(BufferedImage bitmap) {
		GameFont font = (GameFont) this.getFontBank().get(bitmap);
		
		if (font == null) {
			font = new AdvanceBitmapFont(this.cutLetter(bitmap));
			this.getFontBank().put(bitmap, font);
		}
		
		return font;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.image.BufferedImage, java.lang.String)
     */
	public GameFont getFont(BufferedImage bitmap, String letterSequence) {
		GameFont font = (GameFont) this.getFontBank().get(bitmap);
		
		if (font == null) {
			font = new AdvanceBitmapFont(this.cutLetter(bitmap), letterSequence);
			this.getFontBank().put(bitmap, font);
		}
		
		return font;
	}
	
	protected BufferedImage[] cutLetter(BufferedImage bitmap) {
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
			imagefont[i] = ImageUtil.applyMask(bitmap.getSubimage(w, 1,
			        width[i], height), backgr);
			
			w += width[i];
		}
		
		return imagefont;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.image.BufferedImage[])
     */
	public GameFont getFont(BufferedImage[] bitmap) {
		GameFont font = (GameFont) this.getFontBank().get(bitmap);
		
		if (font == null) {
			font = new BitmapFont(bitmap);
			this.getFontBank().put(bitmap, font);
		}
		
		return font;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.image.BufferedImage[], java.lang.String)
     */
	public GameFont getFont(BufferedImage[] bitmap, String letterSequence) {
		GameFont font = (GameFont) this.getFontBank().get(bitmap);
		
		if (font == null) {
			font = new BitmapFont(bitmap, letterSequence);
			this.getFontBank().put(bitmap, font);
		}
		
		return font;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.Font)
     */
	public GameFont getFont(Font f) {
		return this.getFont(f, null);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.GameFontManager#getFont(java.awt.Font, java.awt.Color)
     */
	public GameFont getFont(Font f, Color col) {
		GameFont font = (GameFont) this.getFontBank().get(f);
		if (font == null) {
			font = (col == null) ? new SystemFont(f) : new SystemFont(f, col);
			this.getFontBank().put(f, font);
		}
		
		return font;
	}

	public void setFontBank(Map fontBank) {
	    this.fontBank = fontBank;
    }

	public Map getFontBank() {
	    return fontBank;
    }
	
}
