package com.golden.gamedev.object.sprite;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * The <tt>InvisibleSpriteTest</tt> class is a {@link TestCase} to verify the
 * functionality of the {@link InvisibleSprite} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public class InvisibleSpriteTest extends TestCase {
	
	/**
	 * The {@link InvisibleSprite} under test.
	 */
	private InvisibleSprite spriteUnderTest;
	
	/**
	 * Creates a new <tt>InvisibleSpriteTest</tt> object.
	 */
	public InvisibleSpriteTest() {
		super();
	}
	
	protected void setUp() throws Exception {
		this.setSpriteUnderTest(new InvisibleSprite(100, 200, 100, 100));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#render(java.awt.Graphics2D)}.
	 */
	public void testRenderGraphics2D() {
		// This will not throw a null pointer exception, for either the 0.23 or
		// the refactored class,
		// because an InvisibleSprite will by default not render.
		this.getSpriteUnderTest().render(null);
		this.getSpriteUnderTest().setVisible(true);
		boolean failedNPECheck = true;
		try {
			this.getSpriteUnderTest().render(null);
		}
		catch (NullPointerException e) {
			failedNPECheck = false;
		}
		if (failedNPECheck) {
			Assert
			        .fail("Expected a NullPointerException because rendering should now be able to be invoked");
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#render(java.awt.Graphics2D, int, int)}.
	 */
	public void testRenderGraphics2DIntInt() {
		// This will not throw a null pointer exception, for either the 0.23 or
		// the refactored class,
		// because an InvisibleSprite will by default not render.
		this.getSpriteUnderTest().render(null, 100, 200);
		this.getSpriteUnderTest().setVisible(true);
		boolean failedNPECheck = true;
		try {
			this.getSpriteUnderTest().render(null, 100, 200);
		}
		catch (NullPointerException e) {
			failedNPECheck = false;
		}
		if (failedNPECheck) {
			Assert
			        .fail("Expected a NullPointerException because rendering should now be able to be invoked");
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite()}.
	 */
	public void testInvisibleSprite() {
		this.setSpriteUnderTest(new InvisibleSprite());
		Assert.assertNotNull(this.getSpriteUnderTest());
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
		Assert.assertEquals(0.0, this.getSpriteUnderTest().getX(), 0.00001);
		Assert.assertEquals(0.0, this.getSpriteUnderTest().getY(), 0.00001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite(java.awt.image.BufferedImage)}.
	 */
	public void testInvisibleSpriteBufferedImage() {
		this.setSpriteUnderTest(new InvisibleSprite(null));
		Assert.assertNotNull(this.getSpriteUnderTest());
		Assert.assertNull(this.getSpriteUnderTest().getImage());
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
		Assert.assertEquals(0.0, this.getSpriteUnderTest().getX(), 0.00001);
		Assert.assertEquals(0.0, this.getSpriteUnderTest().getY(), 0.00001);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite(java.awt.image.BufferedImage, double, double)}.
	 */
	public void testInvisibleSpriteBufferedImageDoubleDouble() {
		this.setSpriteUnderTest(new InvisibleSprite(null, 100, 200));
		Assert.assertNotNull(this.getSpriteUnderTest());
		Assert.assertNull(this.getSpriteUnderTest().getImage());
		Assert.assertEquals(100, this.getSpriteUnderTest().getX(), 0.0001);
		Assert.assertEquals(200, this.getSpriteUnderTest().getY(), 0.0001);
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite(double, double)}.
	 */
	public void testInvisibleSpriteDoubleDouble() {
		this.setSpriteUnderTest(new InvisibleSprite(100, 200));
		Assert.assertNotNull(this.getSpriteUnderTest());
		Assert.assertNull(this.getSpriteUnderTest().getImage());
		Assert.assertEquals(100, this.getSpriteUnderTest().getX(), 0.0001);
		Assert.assertEquals(200, this.getSpriteUnderTest().getY(), 0.0001);
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#InvisibleSprite(double, double, int, int)}.
	 */
	public void testInvisibleSpriteDoubleDoubleIntInt() {
		Assert.assertNotNull(this.getSpriteUnderTest());
		Assert.assertNull(this.getSpriteUnderTest().getImage());
		Assert.assertEquals(100, this.getSpriteUnderTest().getX(), 0.0001);
		Assert.assertEquals(200, this.getSpriteUnderTest().getY(), 0.0001);
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#isVisible()}.
	 */
	public void testIsVisible() {
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
		this.getSpriteUnderTest().setVisible(true);
		Assert.assertTrue(this.getSpriteUnderTest().isVisible());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.sprite.InvisibleSprite#setVisible(boolean)}.
	 */
	public void testSetVisible() {
		Assert.assertFalse(this.getSpriteUnderTest().isVisible());
		this.getSpriteUnderTest().setVisible(true);
		Assert.assertTrue(this.getSpriteUnderTest().isVisible());
	}
	
	/**
	 * Gets the {@link InvisibleSprite} under test.
	 * @return The {@link InvisibleSprite} under test.
	 */
	public InvisibleSprite getSpriteUnderTest() {
		return this.spriteUnderTest;
	}
	
	/**
	 * Sets the {@link InvisibleSprite} under test.
	 * @param spriteUnderTest The {@link InvisibleSprite} under test.
	 */
	public void setSpriteUnderTest(InvisibleSprite spriteUnderTest) {
		this.spriteUnderTest = spriteUnderTest;
	}
}
