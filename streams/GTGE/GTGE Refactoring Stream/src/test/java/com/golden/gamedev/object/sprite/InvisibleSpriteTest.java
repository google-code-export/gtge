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
		this.setSpriteUnderTest(new InvisibleSprite(100, 100, 100, 100));
	}
	
	/**
	 * Tests the {@link InvisibleSprite#render(java.awt.Graphics2D)} method's
	 * functionality.
	 */
	public void testRenderGraphics2D() {
		// This will not throw a null pointer exception, for either the 0.23 or
		// the refactored class,
		// because an InvisibleSprite will by default not render.
		this.getSpriteUnderTest().render(null);
	}
	
	/**
	 * Tests the {@link InvisibleSprite#render(java.awt.Graphics2D, int, int)}
	 * method's functionality.
	 */
	public void testRenderGraphics2DIntInt() {
		// This will not throw a null pointer exception, for either the 0.23 or
		// the refactored class,
		// because an InvisibleSprite will by default not render.
		this.getSpriteUnderTest().render(null, 100, 200);
	}
	
	/**
	 * Tests the
	 * {@link InvisibleSprite#InvisibleSprite(double, double, int, int)}
	 * constructor.
	 */
	public void testInvisibleSprite() {
		Assert.assertNotNull(this.getSpriteUnderTest());
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
