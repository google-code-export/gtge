/**
 * 
 */
package com.golden.gamedev.object;

import java.awt.Insets;
import java.util.Arrays;

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
		validateConstructorDefaults();
		group.update(10);
		assertEquals(10, group.getScanFrequence().getCurrentTick());
		assertTrue(group.getScanFrequence().isActive());
		validateOffsets(new Insets(0, 0, 0, 0));
		assertEquals(0, group.getSize());
		assertEquals(20, group.getSprites().length);
		assertEquals(null, group.getSprites()[0]);
		assertTrue(group.isActive());
		
		// TODO: this test seems really weak.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#render(java.awt.Graphics2D)}
	 * .
	 */
	public final void testRender() {
		MockGraphics2D g = new MockGraphics2D();
		group.render(g);
		
		// TODO: this test seems really weak.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#setBackground(com.golden.gamedev.object.background.Background)}
	 * .
	 */
	public final void testSetBackground() {
		MockBackground background = new MockBackground();
		group.setBackground(background);
		assertTrue(background == group.getBackground());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#setComparator(java.util.Comparator)}
	 * .
	 */
	public final void testSetComparator() {
		// TODO: must test sorting with real test.
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
		group = new AdvanceSpriteGroup("Group", 1, 2, 3, 4);
		validateConstructorDefaults();
		validateOffsets(new Insets(1, 2, 3, 4));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#AdvanceSpriteGroup(java.lang.String, int)}
	 * .
	 */
	public final void testAdvanceSpriteGroupStringInt() {
		group = new AdvanceSpriteGroup("Group", 0);
		assertNotNull(group);
		validateConstructorDefaults();
		validateOffsets(new Insets(0, 0, 0, 0));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#AdvanceSpriteGroup(java.lang.String)}
	 * .
	 */
	public final void testAdvanceSpriteGroupString() {
		assertNotNull(group);
		validateConstructorDefaults();
		validateOffsets(new Insets(0, 0, 0, 0));
	}
	
	/**
	 * Validates that the {@link #group} has the expected values shared by all
	 * constructors.
	 */
	private void validateConstructorDefaults() {
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
		assertEquals(0, group.getSize());
		assertEquals(20, group.getSprites().length);
		assertEquals(null, group.getSprites()[0]);
		assertTrue(group.isActive());
	}
	
	/**
	 * Validates the group's {@link AdvanceSpriteGroup#getScreenOffset() offset}
	 * against the specified non-null expected results.
	 * @param expectedResults The non-null {@link Insets} instance specifying
	 *        the expected results to compare against.
	 */
	private void validateOffsets(final Insets expectedResults) {
		Insets offset = group.getScreenOffset();
		assertNotNull(offset);
		assertEquals(expectedResults.bottom, offset.bottom);
		assertEquals(expectedResults.top, offset.top);
		assertEquals(expectedResults.left, offset.left);
		assertEquals(expectedResults.right, offset.right);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getScreenOffset()}.
	 */
	public final void testGetScreenOffset() {
		validateOffsets(new Insets(0, 0, 0, 0));
		group = new AdvanceSpriteGroup("blah", 1, 2, 3, 4);
		validateOffsets(new Insets(1, 2, 3, 4));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getGroupSprites()}.
	 */
	public final void testGetGroupSprites() {
		Sprite activeSprite = new Sprite();
		assertTrue(activeSprite.isActive());
		Sprite inactiveSprite = new Sprite();
		inactiveSprite.setActive(false);
		assertFalse(inactiveSprite.isActive());
		group.add(activeSprite);
		group.add(inactiveSprite);
		assertEquals(Arrays.asList(new Sprite[] {
		        activeSprite, inactiveSprite
		}), Arrays.asList(new Sprite[] {
		        group.getGroupSprites()[0], group.getGroupSprites()[1]
		}));
		assertNull(group.getGroupSprites()[2]);
		group.remove(inactiveSprite);
		assertEquals(Arrays.asList(new Sprite[] {
			activeSprite
		}), Arrays.asList(new Sprite[] {
			group.getGroupSprites()[0]
		}));
		assertNull(group.getGroupSprites()[1]);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.AdvanceSpriteGroup#getGroupSize()}.
	 */
	public final void testGetGroupSize() {
		Sprite sprite = new Sprite();
		assertTrue(sprite.isActive());
		group.add(sprite);
		assertEquals(1, group.getGroupSize());
		sprite.setActive(false);
		assertEquals(1, group.getGroupSize());
	}
	
}
