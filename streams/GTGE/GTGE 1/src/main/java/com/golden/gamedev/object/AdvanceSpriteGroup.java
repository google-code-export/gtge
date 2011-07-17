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
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Comparator;
import java.util.List;

// REVIEW-HIGH: This shouldn't be a subclass, but rather an implementation of SpriteGroup.
/**
 * Subclass of <code>SpriteGroup</code> that designed to update and render visible on the screen sprites only.
 * <p>
 * 
 * In standard sprite group, all registered sprites in the group is updated, rendered, and check for collision in every
 * game loop. If the game has many sprites and many of them are not visible, it is not efficient to update, render, and
 * check for collision all of the sprites.
 * <p>
 * 
 * <code>AdvanceSpriteGroup</code> is designed to optimize the sprite updating and rendering by updating and rendering
 * sprites that only visible on screen, sprites that outside the game view area are not checked.
 * <p>
 * 
 * The main operation is storing sprites that {@linkplain com.golden.gamedev.object.Sprite#isOnScreen() visible on
 * screen} into an inner sprite group, and the inner sprite group that will be update, render, and check for collision.
 */
public class AdvanceSpriteGroup extends BasicSpriteGroup {
	
	/**
	 * Inner sprite group that hold on screen sprites (inside view area sprites) of this group.
	 */
	protected BasicSpriteGroup ONSCREEN_GROUP;
	
	private final Insets offset;
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ***************************** CONSTRUCTOR *******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Creates new <code>AdvanceSpriteGroup</code> with specified name, and specified screen offset on each side.
	 */
	public AdvanceSpriteGroup(final String name, final int topOffset, final int leftOffset, final int bottomOffset,
			final int rightOffset) {
		super(name);
		
		offset = new Insets(topOffset, leftOffset, bottomOffset, rightOffset);
		
		ONSCREEN_GROUP = new BasicSpriteGroup(name + " #ONSCREEN");
		// this group has done the scanning
		// on_screen_group no need to do the scanning anymore
		ONSCREEN_GROUP.getScanFrequence().setActive(false);
	}
	
	/**
	 * Creates new <code>AdvanceSpriteGroup</code> with specified name, and specified screen offset.
	 */
	public AdvanceSpriteGroup(final String name, final int screenOffset) {
		this(name, screenOffset, screenOffset, screenOffset, screenOffset);
	}
	
	/**
	 * Creates new <code>AdvanceSpriteGroup</code> with specified name without screen offset (0, 0, 0, 0).
	 */
	public AdvanceSpriteGroup(final String name) {
		this(name, 0);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************* UPDATE THIS GROUP *****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	@Override
	public void update(final long elapsedTime) {
		// clear previous on screen sprites
		ONSCREEN_GROUP.clear();
		
		// scanning on screen sprites
		for (Sprite sprite : getGroupSprites()) {
			if (sprite.isActive() && sprite.isOnScreen(offset.left, offset.top, offset.right, offset.bottom)) {
				ONSCREEN_GROUP.add(sprite);
			}
		}
		
		// update only on screen sprites
		ONSCREEN_GROUP.update(elapsedTime);
		
		// schedule to scan inactive sprite
		// since we override update(), we must schedule this manually
		// or inactive sprites in this group will never be thrown !!
		if (getScanFrequence().action(elapsedTime)) {
			removeInactiveSprites();
		}
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ******************** RENDER TO GRAPHICS CONTEXT *************************
	 */
	/**
	 * *************************************************************************
	 */
	
	@Override
	public void render(final Graphics2D g) {
		// render only on screen sprites
		ONSCREEN_GROUP.render(g);
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************** GROUP PROPERTIES *****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	@Override
	public void setBackground(final Background backgr) {
		super.setBackground(backgr);
		
		ONSCREEN_GROUP.setBackground(backgr);
	}
	
	@Override
	public void setComparator(final Comparator<Sprite> c) {
		super.setComparator(c);
		
		ONSCREEN_GROUP.setComparator(c);
	}
	
	/**
	 * Returns screen offset of this group. Sprites that outside of screen bounds that still in this offset still
	 * categorized as on screen sprites.
	 */
	public Insets getScreenOffset() {
		return offset;
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************* REMOVAL OPERATION *****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	@Override
	public Sprite remove(final int index) {
		final Sprite s = super.remove(index);
		
		if (s != null) {
			ONSCREEN_GROUP.remove(s);
		}
		
		return s;
	}
	
	@Override
	public boolean remove(final Sprite s) {
		ONSCREEN_GROUP.remove(s);
		
		return super.remove(s);
	}
	
	@Override
	public void clear() {
		super.clear();
		
		ONSCREEN_GROUP.clear();
	}
	
	@Override
	public void reset() {
		super.reset();
		
		ONSCREEN_GROUP.reset();
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************* ON-SCREEN SPRITES *****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns all <i>on-screen sprites</i> (active, inactive, and also <b>null</b> sprite) in this group.
	 * 
	 * @see #getSize()
	 */
	@Override
	public List<Sprite> getSprites() {
		return ONSCREEN_GROUP.getSprites();
	}
	
	/**
	 * Returns total <b>non-null</b> <i>on-screen sprites</i> (active + inactive sprites) in this group.
	 */
	@Override
	public int getSize() {
		return ONSCREEN_GROUP.getSize();
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *************************** SPRITES GETTER ******************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns all sprites (active, inactive, and also <b>null</b> sprite) in this group.
	 * 
	 * @see #getGroupSize()
	 */
	public List<Sprite> getGroupSprites() {
		return super.getSprites();
	}
	
	/**
	 * Returns total <b>non-null</b> sprites (active + inactive) in this group.
	 */
	public int getGroupSize() {
		return super.getSize();
	}
	
}
