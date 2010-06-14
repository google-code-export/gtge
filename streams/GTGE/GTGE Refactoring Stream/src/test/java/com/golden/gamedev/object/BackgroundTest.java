/**
 * 
 */
package com.golden.gamedev.object;

import java.awt.Rectangle;

import com.golden.gamedev.object.collision.CollisionRect;

import junit.framework.TestCase;

/**
 * The {@link BackgroundTest} class provides a {@link TestCase} extension to
 * test the functionality of the {@link Background} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see Background
 * 
 */
public class BackgroundTest extends TestCase {
	
	/**
	 * The {@link Background} instance under test.
	 */
	private Background background;
	
	/**
	 * Creates a new {@link BackgroundTest} instance with the given name.
	 * @param name The {@link String} name of this {@link BackgroundTest}
	 *        instance.
	 */
	public BackgroundTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		background = new MockBackground();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#getDefaultBackground()}.
	 */
	public final void testGetDefaultBackground() {
		Background defaultBackground = Background.getDefaultBackground();
		assertNotNull(defaultBackground);
		assertTrue(
		        "The default background must always refer to the same instance.",
		        defaultBackground == Background.getDefaultBackground());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#Background(int, int)}.
	 */
	public final void testBackgroundIntInt() {
		background = new Background(100, 100);
		assertNotNull(background);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(100, background.getHeight());
		assertEquals(100, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#Background()}
	 * .
	 */
	public final void testBackground() {
		assertNotNull(background);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#getX()}.
	 */
	public final void testGetX() {
		assertEquals(0, background.getX(), 0);
		background.setX(0);
		// X is constrained - background is at maximum possible value for
		// background width.
		assertEquals(0, background.getX(), 0);
		background.setWidth(Background.screen.width + 50);
		background.setX(50);
		assertEquals(50, background.getX(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#getY()}.
	 */
	public final void testGetY() {
		assertEquals(0, background.getY(), 0);
		background.setY(0);
		// Y is constrained - background is at maximum possible value for
		// background height.
		assertEquals(0, background.getY(), 0);
		background.setHeight(Background.screen.height + 50);
		background.setY(50);
		assertEquals(50, background.getY(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#getWidth()}.
	 */
	public final void testGetWidth() {
		assertEquals(Background.screen.getWidth(), background.getWidth(), 0);
		background.setWidth(50);
		assertEquals(50, background.getWidth(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#getHeight()}.
	 */
	public final void testGetHeight() {
		assertEquals(Background.screen.getHeight(), background.getHeight(), 0);
		background.setHeight(50);
		assertEquals(50, background.getHeight(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setSize(int, int)}.
	 */
	public final void testSetSize() {
		assertEquals(Background.screen.getHeight(), background.getHeight(), 0);
		assertEquals(Background.screen.getWidth(), background.getWidth(), 0);
		background.setSize(100, 150);
		assertEquals(150, background.getHeight(), 0);
		assertEquals(100, background.getWidth(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setLocation(double, double)}.
	 */
	public final void testSetLocation() {
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		
		// Since the clip is the same as the background size, bounds force 0, 0
		background.setLocation(100, 150);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		
		// Now the background has some wiggle room...
		background.setWidth(1000);
		background.setHeight(1000);
		background.setLocation(100, 150);
		assertEquals(100, background.getX(), 0);
		assertEquals(150, background.getY(), 0);
		
		// Setting these to negative values should snap them back to 0.
		background.setLocation(0, 0);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#move(double, double)}.
	 */
	public final void testMove() {
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		background.move(100, 150);
		// Since the clip is the same as the background size, bounds force 0, 0
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		
		// Now the background has some wiggle room...
		background.setWidth(1000);
		background.setHeight(1000);
		background.move(100, 150);
		assertEquals(100, background.getX(), 0);
		assertEquals(150, background.getY(), 0);
		
		// Negative values are permitted.
		background.move(-50, -50);
		assertEquals(50, background.getX(), 0);
		assertEquals(100, background.getY(), 0);
		
		// However, the negative bound is still 0, 0.
		background.move(-150, -150);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setToCenter(int, int, int, int)}
	 * .
	 */
	public final void testSetToCenterIntIntIntInt() {
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		// This won't do anything.
		background.setToCenter(0, 0, 50, 50);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		
		background.setHeight(1000);
		background.setWidth(1000);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(1000, background.getHeight());
		assertEquals(1000, background.getWidth());
		
		background.setToCenter(700, 700, 50, 50);
		assertEquals(360, background.getX(), 0);
		assertEquals(485, background.getY(), 0);
		assertEquals(1000, background.getHeight());
		assertEquals(1000, background.getWidth());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setToCenter(com.golden.gamedev.object.Sprite)}
	 * .
	 */
	public final void testSetToCenterSprite() {
		Sprite center = new Sprite(0, 0);
		center.setWidth(50);
		center.setHeight(50);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		// This won't do anything.
		background.setToCenter(center);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		
		background.setHeight(1000);
		background.setWidth(1000);
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(1000, background.getHeight());
		assertEquals(1000, background.getWidth());
		
		center.setX(700);
		center.setY(700);
		background.setToCenter(center);
		assertEquals(360, background.getX(), 0);
		assertEquals(485, background.getY(), 0);
		assertEquals(1000, background.getHeight());
		assertEquals(1000, background.getWidth());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setClip(int, int, int, int)}.
	 */
	public final void testSetClipIntIntIntInt() {
		Rectangle clip = background.getClip();
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		background.setClip(300, 300, 50, 50);
		
		clip = background.getClip();
		assertEquals(300, clip.getX(), 0);
		assertEquals(300, clip.getY(), 0);
		assertEquals(50, clip.getHeight(), 0);
		assertEquals(50, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setClip(java.awt.Rectangle)}.
	 */
	public final void testSetClipRectangle() {
		Rectangle clip = background.getClip();
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		background.setClip(new Rectangle(300, 300, 50, 50));
		
		clip = background.getClip();
		assertEquals(300, clip.getX(), 0);
		assertEquals(300, clip.getY(), 0);
		assertEquals(50, clip.getHeight(), 0);
		assertEquals(50, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#getClip()}.
	 */
	public final void testGetClip() {
		Rectangle clip = background.getClip();
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		background.setClip(new Rectangle(300, 300, 50, 50));
		
		clip = background.getClip();
		assertEquals(300, clip.getX(), 0);
		assertEquals(300, clip.getY(), 0);
		assertEquals(50, clip.getHeight(), 0);
		assertEquals(50, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#setX(double)}
	 * .
	 */
	public final void testSetX() {
		assertEquals(0, background.getX(), 0);
		background.setX(0);
		// X is constrained - background is at maximum possible value for
		// background width.
		assertEquals(0, background.getX(), 0);
		background.setWidth(Background.screen.width + 50);
		background.setX(50);
		assertEquals(50, background.getX(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#setY(double)}
	 * .
	 */
	public final void testSetY() {
		assertEquals(0, background.getY(), 0);
		background.setY(0);
		// Y is constrained - background is at maximum possible value for
		// background height.
		assertEquals(0, background.getY(), 0);
		background.setHeight(Background.screen.height + 50);
		background.setY(50);
		assertEquals(50, background.getY(), 0);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.object.Background#update(long)}
	 * .
	 */
	public final void testUpdate() {
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		Rectangle clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
		
		background.update(1000);
		// Absolutely nothing has changed.
		
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		clip = background.getClip();
		assertNotNull(clip);
		assertEquals(0, clip.getX(), 0);
		assertEquals(0, clip.getY(), 0);
		assertEquals(Background.screen.height, clip.getHeight(), 0);
		assertEquals(Background.screen.width, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#render(java.awt.Graphics2D)}.
	 */
	public final void testRenderGraphics2D() {
		MockGraphics2D g = new MockGraphics2D();
		background.render(g);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(0, g.lastDestinationRectangleFirstX);
		assertEquals(0, g.lastDestinationRectangleFirstY);
		assertEquals(Background.screen.width, g.lastDestinationRectangleSecondX);
		assertEquals(Background.screen.height,
		        g.lastDestinationRectangleSecondY);
		assertEquals(0, g.lastSourceRectangleFirstX);
		assertEquals(0, g.lastSourceRectangleFirstY);
		assertEquals(Background.screen.width, g.lastSourceRectangleSecondX);
		assertEquals(Background.screen.height, g.lastSourceRectangleSecondY);
		
		// Shrink the width and height.
		background.setWidth(Background.screen.width - 10);
		background.setHeight(Background.screen.height - 10);
		
		background.render(g);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(0, g.lastDestinationRectangleFirstX);
		assertEquals(0, g.lastDestinationRectangleFirstY);
		assertEquals(Background.screen.width - 10,
		        g.lastDestinationRectangleSecondX);
		assertEquals(Background.screen.height - 10,
		        g.lastDestinationRectangleSecondY);
		assertEquals(0, g.lastSourceRectangleFirstX);
		assertEquals(0, g.lastSourceRectangleFirstY);
		assertEquals(Background.screen.width - 10, g.lastSourceRectangleSecondX);
		assertEquals(Background.screen.height - 10,
		        g.lastSourceRectangleSecondY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#render(java.awt.Graphics2D, int, int, int, int, int, int)}
	 * .
	 */
	public final void testRenderGraphics2DIntIntIntIntIntInt() {
		MockGraphics2D g = new MockGraphics2D();
		background.render(g, 10, 20, 30, 40, 50, 60);
		assertNull(g.lastDrawnImageObserver);
		assertEquals(30, g.lastDestinationRectangleFirstX);
		assertEquals(40, g.lastDestinationRectangleFirstY);
		assertEquals(50, g.lastDestinationRectangleSecondX);
		assertEquals(60, g.lastDestinationRectangleSecondY);
		assertEquals(10, g.lastSourceRectangleFirstX);
		assertEquals(20, g.lastSourceRectangleFirstY);
		assertEquals(50, g.lastSourceRectangleSecondX);
		assertEquals(60, g.lastSourceRectangleSecondY);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#intersects(com.golden.gamedev.object.collision.CollisionShape)}
	 * .
	 */
	public final void testIntersects() {
		CollisionRect rect = new CollisionRect();
		rect.setBounds(1000 + Background.screen.getWidth(),
		        1000 + Background.screen.getHeight(), 50, 50);
		assertFalse(rect.intersects(background));
		assertFalse(background.intersects(rect));
		assertTrue(rect.intersects(rect));
		assertTrue(background.intersects(background));
		
		rect = new CollisionRect(background);
		assertTrue(rect.intersects(background));
		assertTrue(background.intersects(rect));
		assertTrue(rect.intersects(rect));
		assertTrue(background.intersects(background));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setBounds(double, double, int, int)}
	 * .
	 */
	public final void testSetBounds() {
		assertEquals(0, background.getX(), 0);
		assertEquals(0, background.getY(), 0);
		assertEquals(Background.screen.height, background.getHeight());
		assertEquals(Background.screen.width, background.getWidth());
		background.setBounds(50, 50, 50, 50);
		
		assertEquals(50, background.getX(), 0);
		assertEquals(50, background.getY(), 0);
		assertEquals(50, background.getHeight());
		assertEquals(50, background.getWidth());
		Rectangle clip = background.getClip();
		assertEquals(50, clip.getX(), 0);
		assertEquals(50, clip.getY(), 0);
		assertEquals(50, clip.getHeight(), 0);
		assertEquals(50, clip.getWidth(), 0);
		
		background.setBounds(50, 50, 100, 100);
		assertEquals(50, background.getX(), 0);
		assertEquals(50, background.getY(), 0);
		assertEquals(100, background.getHeight());
		assertEquals(100, background.getWidth());
		clip = background.getClip();
		assertEquals(50, clip.getX(), 0);
		assertEquals(50, clip.getY(), 0);
		assertEquals(100, clip.getHeight(), 0);
		assertEquals(100, clip.getWidth(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setHeight(int)}.
	 */
	public final void testSetHeight() {
		assertEquals(Background.screen.getHeight(), background.getHeight(), 0);
		background.setHeight(50);
		assertEquals(50, background.getHeight(), 0);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.Background#setWidth(int)}.
	 */
	public final void testSetWidth() {
		assertEquals(Background.screen.getWidth(), background.getWidth(), 0);
		background.setWidth(50);
		assertEquals(50, background.getWidth(), 0);
	}
	
}
