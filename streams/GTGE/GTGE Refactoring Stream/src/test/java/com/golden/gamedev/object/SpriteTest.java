/**
 * 
 */
package com.golden.gamedev.object;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.background.BoundedBackground;

import junit.framework.TestCase;

/**
 * The {@link SpriteTest} class provides a {@link TestCase} to test the
 * functionality of the {@link Sprite} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * 
 */
public class SpriteTest extends TestCase {
	
	/**
	 * The {@link Sprite} instance under test.
	 */
	private Sprite sprite;
	
	/**
	 * Creates a new {@link SpriteTest} instance with the given name.
	 * @param name The {@link String} specifying the name of this
	 *        {@link SpriteTest} instance.
	 */
	public SpriteTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		sprite = new Sprite();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#Sprite(java.awt.image.BufferedImage, double, double)}
	 * .
	 */
	public final void testSpriteBufferedImageDoubleDouble() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#Sprite(java.awt.image.BufferedImage)}
	 * .
	 */
	public final void testSpriteBufferedImage() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#Sprite(double, double)}.
	 */
	public final void testSpriteDoubleDouble() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#Sprite()}.
	 */
	public final void testSprite() {
		assertNotNull(sprite);
		assertEquals(0, sprite.getX(), 0);
		assertEquals(0, sprite.getOldX(), 0);
		assertEquals(0, sprite.getY(), 0);
		assertEquals(0, sprite.getOldY(), 0);
		assertNull(sprite.getImage());
		assertEquals(0, sprite.getHeight());
		assertEquals(0, sprite.getWidth());
		assertEquals(BoundedBackground.getDefaultBackground(), sprite
		        .getBackground());
		assertEquals(0, sprite.getHorizontalSpeed(), 0);
		assertEquals(0, sprite.getVerticalSpeed(), 0);
		assertNotNull(sprite.getDefaultCollisionShape());
		assertEquals(0, sprite.getID());
		assertNull(sprite.getDataID());
		assertEquals(0, sprite.getLayer());
		assertTrue(sprite.isActive());
		assertFalse(sprite.isImmutable());
		assertTrue(sprite.isVisible());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setBackground(com.golden.gamedev.object.Background)}
	 * .
	 */
	public final void testSetBackground() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getBackground()}.
	 */
	public final void testGetBackground() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getImage()}.
	 */
	public final void testGetImage() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setImage(java.awt.image.BufferedImage)}
	 * .
	 */
	public final void testSetImage() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getWidth()}.
	 */
	public final void testGetWidth() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getHeight()}.
	 */
	public final void testGetHeight() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#getDefaultCollisionShape()}.
	 */
	public final void testGetDefaultCollisionShape() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#moveTo(long, double, double, double)}
	 * .
	 */
	public final void testMoveTo() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setLocation(double, double)}.
	 */
	public final void testSetLocation() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#move(double, double)}.
	 */
	public final void testMove() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#moveX(double)}.
	 */
	public final void testMoveX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#moveY(double)}.
	 */
	public final void testMoveY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setX(double)}.
	 */
	public final void testSetX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setY(double)}.
	 */
	public final void testSetY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setWidth(int)}.
	 */
	public final void testSetWidth() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setHeight(int)}.
	 */
	public final void testSetHeight() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#forceX(double)}.
	 */
	public final void testForceX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#forceY(double)}.
	 */
	public final void testForceY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getX()}.
	 */
	public final void testGetX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getY()}.
	 */
	public final void testGetY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getOldX()}.
	 */
	public final void testGetOldX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getOldY()}.
	 */
	public final void testGetOldY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setSpeed(double, double)}.
	 */
	public final void testSetSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setHorizontalSpeed(double)}.
	 */
	public final void testSetHorizontalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setVerticalSpeed(double)}.
	 */
	public final void testSetVerticalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setMovement(double, double)}.
	 */
	public final void testSetMovement() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#addHorizontalSpeed(long, double, double)}
	 * .
	 */
	public final void testAddHorizontalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#addVerticalSpeed(long, double, double)}
	 * .
	 */
	public final void testAddVerticalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#getHorizontalSpeed()}.
	 */
	public final void testGetHorizontalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#getVerticalSpeed()}.
	 */
	public final void testGetVerticalSpeed() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getScreenX()}.
	 */
	public final void testGetScreenX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getScreenY()}.
	 */
	public final void testGetScreenY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getCenterX()}.
	 */
	public final void testGetCenterX() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getCenterY()}.
	 */
	public final void testGetCenterY() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#isOnScreen(int, int, int, int)}.
	 */
	public final void testIsOnScreenIntIntIntInt() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#isOnScreen()}.
	 */
	public final void testIsOnScreen() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#update(long)}.
	 */
	public final void testUpdate() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#render(java.awt.Graphics2D)}.
	 */
	public final void testRenderGraphics2D() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#render(java.awt.Graphics2D, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntInt() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getID()}.
	 */
	public final void testGetID() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setID(int)}.
	 */
	public final void testSetID() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getDataID()}.
	 */
	public final void testGetDataID() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setDataID(java.lang.Object)}.
	 */
	public final void testSetDataID() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#getLayer()}.
	 */
	public final void testGetLayer() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#setLayer(int)}.
	 */
	public final void testSetLayer() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#isActive()}.
	 */
	public final void testIsActive() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setActive(boolean)}.
	 */
	public final void testSetActive() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#isImmutable()}.
	 */
	public final void testIsImmutable() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setImmutable(boolean)}.
	 */
	public final void testSetImmutable() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#getDistance(com.golden.gamedev.object.Sprite)}
	 * .
	 */
	public final void testGetDistance() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#intersects(com.golden.gamedev.object.collision.CollisionShape)}
	 * .
	 */
	public final void testIntersects() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setBounds(double, double, int, int)}
	 * .
	 */
	public final void testSetBounds() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Sprite#isVisible()}.
	 */
	public final void testIsVisible() {
		assertTrue(sprite.isVisible());
		MockGraphics2D g = new MockGraphics2D();
		BufferedImage image = new BufferedImage(640, 480,
		        BufferedImage.TYPE_INT_ARGB);
		sprite.setImage(image);
		
		// Because an image is now set, the sprite will render.
		sprite.render(g);
		
		// If the image is the same, the sprite rendered properly.
		assertTrue(image == g.lastDrawnImage);
		
		// Sprite doesn't use an image observer
		assertNull(g.lastDrawnImageObserver);
		
		// The sprite position of the default sprite should be 0,0 (see
		// testSprite()).
		assertEquals(0, g.lastDrawnX);
		assertEquals(0, g.lastDrawnY);
		
		// Changing to invisible - forcing no rendering.
		sprite.setVisible(false);
		assertFalse(sprite.isVisible());
		
		// This should now not throw a null pointer exception because it should
		// not attempt to render.
		sprite.render(null);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Sprite#setVisible(boolean)}.
	 */
	public final void testSetVisible() {
		assertTrue(sprite.isVisible());
		MockGraphics2D g = new MockGraphics2D();
		BufferedImage image = new BufferedImage(640, 480,
		        BufferedImage.TYPE_INT_ARGB);
		sprite.setImage(image);
		
		// Because an image is now set, the sprite will render.
		sprite.render(g);
		
		// If the image is the same, the sprite rendered properly.
		assertTrue(image == g.lastDrawnImage);
		
		// Sprite doesn't use an image observer
		assertNull(g.lastDrawnImageObserver);
		
		// The sprite position of the default sprite should be 0,0 (see
		// testSprite()).
		assertEquals(0, g.lastDrawnX);
		assertEquals(0, g.lastDrawnY);
		
		// Changing to invisible - forcing no rendering.
		sprite.setVisible(false);
		assertFalse(sprite.isVisible());
		
		// This should now not throw a null pointer exception because it should
		// not attempt to render.
		sprite.render(null);
		
		// Changing it back should make it render again.
		g.lastDrawnImage = null;
		g.lastDrawnX = -5;
		g.lastDrawnY = -5;
		sprite.setVisible(true);
		sprite.render(g);
		
		// If the image is the same, the sprite rendered properly.
		assertTrue(image == g.lastDrawnImage);
		
		// Sprite doesn't use an image observer
		assertNull(g.lastDrawnImageObserver);
		
		// The sprite position of the default sprite should be 0,0 (see
		// testSprite()).
		assertEquals(0, g.lastDrawnX);
		assertEquals(0, g.lastDrawnY);
	}
	
}
