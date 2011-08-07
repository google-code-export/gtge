package com.golden.gamedev.object;

import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.List;

import com.golden.gamedev.ActiveHolder;
import com.golden.gamedev.object.background.BackgroundHolder;

/**
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public interface SpriteGroup extends ActiveHolder, BackgroundHolder {
	
	// REVIEW-HIGH: Method must be kept (subclass uses it)
	/**
	 * Inserts sprite at the bottom (last index) of this group.
	 * <p>
	 * 
	 * Sprite at the last index (index = {@linkplain #getSize() size}-1) is rendered on top of other sprites (last
	 * rendered).
	 * 
	 * @see #add(int, Sprite)
	 */
	void add(final Sprite member);
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Removes specified sprite from this group.
	 * <p>
	 * 
	 * This method has a big performance hit, <b>avoid</b> using this method in tight-loop (main-loop). <br>
	 * The standard way to remove a sprite from its group is by setting sprite active state to false
	 * {@link com.golden.gamedev.object.Sprite#setActive(boolean) Sprite.setActive(false)}.
	 * <p>
	 * 
	 * SpriteGroup is designed to remove any inactive sprites automatically after a period, use directly sprite removal
	 * method only for specific purpose (if you really know what you are doing).
	 * 
	 * @return true, if specified sprite is successfuly removed from the group, or false if the sprite is not belong to
	 *         this group.
	 * @see com.golden.gamedev.object.Sprite#setActive(boolean)
	 * @see #getScanFrequence()
	 */
	boolean remove(final Sprite s);
	
	// REVIEW-HIGH: Method must be kept (subclass uses it).
	/**
	 * Removes all members from this group, thus makes this group empty.
	 * <p>
	 * 
	 * For example: <br>
	 * Destroying all enemies when player got a bomb.
	 * 
	 * <pre>
	 * ENEMY_GROUP.clear();
	 * </pre>
	 * 
	 * <p>
	 * 
	 * This method simply set group size to nil. The sprites reference is actually removed when
	 * {@link #removeInactiveSprites()} is scheduled.
	 * <p>
	 * 
	 * To remove all sprites and also its reference immediately, use {@link #reset()} instead.
	 * 
	 * @see #reset()
	 */
	void clear();
	
	// REVIEW-HIGH: This method must be kept for now (AdvanceSpriteGroup extends it).
	/**
	 * Updates all active sprites in this group, and check the schedule for removing inactive sprites.
	 * 
	 * @see #getScanFrequence()
	 */
	void update(final long elapsedTime);
	
	// REVIEW-HIGH: This method must be kept (subclass uses it).
	/**
	 * Renders all active sprites in this group. If this group is associated with a comparator, the group sprites is
	 * sort against the comparator first before rendered.
	 * 
	 * @see #setComparator(Comparator)
	 */
	void render(final Graphics2D g);
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	// REVIEW-HIGH: Switch to using a list of Sprites.
	/**
	 * Returns all sprites (active, inactive, and also <b>null</b> sprite) in this group.
	 * <p>
	 * 
	 * How to iterate all sprites :
	 * 
	 * <pre>
	 * SpriteGroup GROUP;
	 * Sprite[] sprites = GROUP.getSprites();
	 * int size = GROUP.getSize();
	 * // iterate the sprite one by one
	 * for (int i = 0; i &lt; size; i++) {
	 * 	// remember the sprite array consists inactive sprites too
	 * 	// you need to check sprite active state before process the sprite
	 * 	if (sprites[i].isActive()) {
	 * 		// now, what do you want with this active sprite?
	 * 		// move it to (0, 0)
	 * 		sprites[i].setLocation(0, 0);
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @see #getSize()
	 */
	List<Sprite> getSprites();
	
	/**
	 * @param sprites
	 *            the sprites to set
	 */
	void setSprites(List<Sprite> sprites);
	
	/**
	 * Returns total active and inactive sprites (<b>non-null</b> sprites) in this group.
	 * 
	 * @see #getSprites()
	 */
	int getSize();
	
	/**
	 * {@inheritDoc} In addition, {@link SpriteGroup} instances will set the {@link Sprite#setBackground(Background)
	 * background} of all {@link Sprite} instances contained in the current {@link #getSprites() sprite list} to this
	 * {@link Background} instance. If the {@link Background} instance is null, a substitute {@link Background} may be
	 * substituted instead.
	 */
	void setBackground(final Background backgr);
}