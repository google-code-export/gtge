/**
 * 
 */
package com.golden.gamedev.object.sprite;

import java.awt.image.BufferedImage;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.golden.gamedev.util.ImageUtil;

/**
 * The <tt>VolatileSpriteTest</tt> class is a {@link TestCase} which verifies
 * the {@link VolatileSprite} class' behavior.
 * @author MetroidFan2002
 * @since 0.2.4
 * @version 1.0
 * @see TestCase
 * @see VolatileSprite
 * 
 */
public class VolatileSpriteTest extends TestCase {
	
	/**
	 * The {@link VolatileSprite} under test.
	 */
	private VolatileSprite spriteUnderTest;
	
	/**
	 * Creates a new <tt>VolatileSpriteTest</tt> instance.
	 */
	public VolatileSpriteTest() {
		super();
	}
	
	protected void setUp() throws Exception {
		BufferedImage image = ImageUtil.createImage(50, 50);
		BufferedImage volatileImages[] = new BufferedImage[] {
		        image, image, image
		};
		this.setSpriteUnderTest(new VolatileSprite(volatileImages, 13, 12));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.VolatileSprite#update(long)}.
	 */
	public void testUpdate() {
		// Asserts the default behavior of a volatile sprite.
		// Note that 60 milliseconds is the default time for the animation
		// timer.
		// Starts with frame 0
		this.getSpriteUnderTest().update(60);
		Assert.assertTrue(this.getSpriteUnderTest().isActive());
		Assert.assertTrue(this.getSpriteUnderTest().isAnimate());
		// At frame 1, go to frame 2.
		this.getSpriteUnderTest().update(60);
		Assert.assertTrue(this.getSpriteUnderTest().isActive());
		Assert.assertTrue(this.getSpriteUnderTest().isAnimate());
		// Frame 2 is the last frame. Attempt to update again.
		this.getSpriteUnderTest().update(60);
		Assert.assertFalse(this.getSpriteUnderTest().isAnimate());
		Assert.assertFalse(this.getSpriteUnderTest().isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.VolatileSprite#VolatileSprite(java.awt.image.BufferedImage[], double, double)}.
	 */
	public void testVolatileSprite() {
		Assert.assertNotNull(this.getSpriteUnderTest());
	}
	
	/**
	 * Gets the {@link VolatileSprite} under test.
	 * @return The {@link VolatileSprite} under test.
	 */
	public VolatileSprite getSpriteUnderTest() {
		return this.spriteUnderTest;
	}
	
	/**
	 * Sets the {@link VolatileSprite} under test.
	 * @param spriteUnderTest The {@link VolatileSprite} under test.
	 */
	public void setSpriteUnderTest(VolatileSprite spriteUnderTest) {
		this.spriteUnderTest = spriteUnderTest;
	}
	
}
