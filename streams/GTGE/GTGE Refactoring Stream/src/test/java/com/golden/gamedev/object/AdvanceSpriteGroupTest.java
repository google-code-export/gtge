/**
 * 
 */
package com.golden.gamedev.object;

import java.awt.Insets;

import com.golden.gamedev.engine.timer.Timer;
import com.golden.gamedev.object.background.ImmutableBackground;

import junit.framework.TestCase;

/**
 * The {@link AdvanceSpriteGroupTest} class provides a {@link TestCase}
 * extension to test the behavior of the {@link AdvanceSpriteGroup} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see TestCase
 * @see AdvanceSpriteGroup
 * 
 */
public class AdvanceSpriteGroupTest extends TestCase {
	
	/**
	 * The {@link AdvanceSpriteGroup} instance under test.
	 */
	private AdvanceSpriteGroup group;
	
	/**
	 * Creates a new {@link AdvanceSpriteGroupTest} instance with the given
	 * name.
	 * @param name The {@link String} name of this
	 *        {@link AdvanceSpriteGroupTest} instance.
	 */
	public AdvanceSpriteGroupTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		group = new AdvanceSpriteGroup("Group");
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#remove(int)}.
	 */
	public final void testRemoveInt() {
		try {
			assertNull(group.remove(0));
			fail("Expected exception - can't remove something that doesn't exist.");
		}
		catch (Exception e) {
			// Intentionally blank
		}
		assertEquals(0, group.getGroupSize());
		Sprite sprite = new Sprite();
		group.add(sprite);
		assertEquals(1, group.getGroupSize());
		assertTrue(sprite == group.remove(0));
		assertEquals(0, group.getGroupSize());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#remove(com.golden.gamedev.object.Sprite)}
	 * .
	 */
	public final void testRemoveSprite() {
		assertEquals(0, group.getGroupSize());
		Sprite sprite = new Sprite();
		assertFalse(group.remove(sprite));
		assertEquals(0, group.getGroupSize());
		group.add(sprite);
		assertEquals(1, group.getGroupSize());
		assertTrue(group.remove(sprite));
		assertEquals(0, group.getGroupSize());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#clear()}.
	 */
	public final void testClear() {
		Sprite sprite = new Sprite();
		assertEquals(0, group.getGroupSize());
		group.add(sprite);
		assertEquals(1, group.getGroupSize());
		Insets insets = group.getScreenOffset();
		insets.left = 50;
		insets.right = 50;
		insets.top = 50;
		insets.bottom = 50;
		group.update(10);
		assertEquals(1, group.getSize());
		group.clear();
		assertEquals(0, group.getSize());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#reset()}.
	 */
	public final void testReset() {
		Sprite sprite = new Sprite();
		assertEquals(0, group.getGroupSize());
		group.add(sprite);
		assertEquals(1, group.getGroupSize());
		Insets insets = group.getScreenOffset();
		insets.left = 50;
		insets.right = 50;
		insets.top = 50;
		insets.bottom = 50;
		group.update(10);
		assertEquals(1, group.getSize());
		group.reset();
		assertEquals(0, group.getSize());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#update(long)}.
	 */
	public final void testUpdate() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#render(java.awt.Graphics2D)}
	 * .
	 */
	public final void testRender() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#setBackground(com.golden.gamedev.object.background.Background)}
	 * .
	 */
	public final void testSetBackground() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#setComparator(java.util.Comparator)}
	 * .
	 */
	public final void testSetComparator() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getSprites()}.
	 */
	public final void testGetSprites() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getSize()}.
	 */
	public final void testGetSize() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#AdvanceSpriteGroup(java.lang.String, int, int, int, int)}
	 * .
	 */
	public final void testAdvanceSpriteGroupStringIntIntIntInt() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#AdvanceSpriteGroup(java.lang.String, int)}
	 * .
	 */
	public final void testAdvanceSpriteGroupStringInt() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#AdvanceSpriteGroup(java.lang.String)}
	 * .
	 */
	public final void testAdvanceSpriteGroupString() {
		assertNotNull(group);
		assertNull(group.getActiveSprite());
		assertEquals(ImmutableBackground.INSTANCE, group.getBackground());
		assertNull(group.getComparator());
		assertEquals(20, group.getExpandFactor());
		assertEquals(0, group.getGroupSize());
		assertEquals(20, group.getGroupSprites().length);
		assertEquals(null, group.getGroupSprites()[0]);
		assertNull(group.getInactiveSprite());
		assertEquals("Group", group.getName());
		Timer timer = group.getScanFrequence();
		assertNotNull(timer);
		assertEquals(15000, timer.getDelay());
		assertEquals(0, timer.getCurrentTick());
		assertTrue(timer.isActive());
		Insets offset = group.getScreenOffset();
		assertNotNull(offset);
		assertEquals(0, offset.bottom);
		assertEquals(0, offset.top);
		assertEquals(0, offset.left);
		assertEquals(0, offset.right);
		assertEquals(0, group.getSize());
		assertEquals(20, group.getSprites().length);
		assertEquals(null, group.getSprites()[0]);
		assertTrue(group.isActive());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getScreenOffset()}.
	 */
	public final void testGetScreenOffset() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getGroupSprites()}.
	 */
	public final void testGetGroupSprites() {
		// TODO: implement this test
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getGroupSize()}.
	 */
	public final void testGetGroupSize() {
		// TODO: implement this test
	}
	
}
