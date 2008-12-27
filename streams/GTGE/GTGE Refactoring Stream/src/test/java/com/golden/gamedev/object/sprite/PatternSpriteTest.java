/**
 * 
 */
package com.golden.gamedev.object.sprite;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import sun.print.PeekGraphics;

import com.golden.gamedev.object.Sprite;
import com.golden.mock.java.awt.MockGraphics2D;

import junit.framework.TestCase;


/**
 * The {@link PatternSpriteTest} is a {@link TestCase} that tests the behavior of the {@link PatternSprite} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see PatternSprite
 *
 */
public class PatternSpriteTest extends TestCase {
	
	/**
	 * The {@link PatternSprite} instance under test.
	 */
	private PatternSprite sprite;
	
	protected void setUp() throws Exception {
		Sprite tempSprite = new Sprite(new BufferedImage(32, 42, BufferedImage.TYPE_INT_RGB));
		sprite = new PatternSprite(tempSprite);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#render(java.awt.Graphics2D, int, int)}.
	 */
	public void testRenderGraphics2DIntInt() {
		try {
			sprite.render(null, 1, 2);
			fail("Expected null pointer exception - graphics context is null.");
		}
		catch(NullPointerException e) {
			//Intentionally Blank
		}
		sprite.render(new MockGraphics2D(), 50, 50);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#PatternSprite(com.golden.gamedev.object.Sprite, double, double)}.
	 */
	public void testPatternSpriteSpriteDoubleDouble() {
		sprite = new PatternSprite(sprite.getPattern(), 10, 15);
		assertNotNull(sprite);
		assertEquals(10, (int)sprite.getX());
		assertEquals(15, (int)sprite.getY());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#PatternSprite(com.golden.gamedev.object.Sprite)}.
	 */
	public void testPatternSpriteSprite() {
		assertNotNull(sprite);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#getPattern()}.
	 */
	public void testGetPattern() {
		assertNotNull(sprite.getPattern());
		sprite.setPattern(null);
		assertNull(sprite.getPattern());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#setPattern(com.golden.gamedev.object.Sprite)}.
	 */
	public void testSetPattern() {
		sprite.setPattern(null);
		assertNull(sprite.getPattern());
	}

	
    public PatternSprite getSprite() {
    	return sprite;
    }

	
    public void setSprite(PatternSprite sprite) {
    	this.sprite = sprite;
    }
	
}
