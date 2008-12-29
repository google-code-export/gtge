/**
 * 
 */
package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.object.Sprite;


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
	 * Test method for {@link com.golden.gamedev.object.sprite.PatternSprite#getImage()}.
	 */
	public void testGetImage() {
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
		Sprite pattern = new Sprite(image);
		sprite = new PatternSprite(pattern);
		assertEquals(image, sprite.getImage());
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
