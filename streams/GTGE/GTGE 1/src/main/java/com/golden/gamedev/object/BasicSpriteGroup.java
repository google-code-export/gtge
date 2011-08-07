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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Group of sprites with common behaviour, for example PLAYER_GROUP, ENEMY_GROUP, etc. This class maintain a growable
 * sprite list (array of sprites). Each time a sprite is added into this group, this group automatically adjust the size
 * of its sprites array.
 * <p>
 * 
 * <code>SpriteGroup</code> is used to store a list of sprites and also manage the sprites updating, rendering, and
 * collision check.
 * <p>
 * 
 * For example how to create and use sprite group :
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * SpriteGroup ENEMY_GROUP;
 * 
 * public void initResources() {
 * 	// creates the enemy sprites
 * 	Sprite asteroid = new Sprite();
 * 	Sprite asteroid2 = new Sprite();
 * 	// creates and add the sprites into enemy group
 * 	ENEMY_GROUP = new SpriteGroup(&quot;Enemy&quot;);
 * 	ENEMY_GROUP.add(asteroid);
 * 	ENEMY_GROUP.add(asteroid2);
 * }
 * 
 * public void update(long elapsedTime) {
 * 	// update all enemies at once
 * 	ENEMY_GROUP.update(elapsedTime);
 * }
 * 
 * public void render(Graphics2D g) {
 * 	// render all enemies at once to the screen
 * 	ENEMY_GROUP.render(g);
 * }
 * </pre>
 * 
 * @see com.golden.gamedev.object.PlayField
 * @see com.golden.gamedev.object.collision.CollisionGroup
 */
public class BasicSpriteGroup implements SpriteGroup {
	
	// removes inactive sprites every 15 seconds
	private Timer scanFrequence = new Timer(15000);
	
	private boolean active = true;
	
	private Background background = Background.getDefaultBackground();
	
	private Comparator<Sprite> comparator; // comparator for sorting sprite
	
	/**
	 * The non-null {@link List} of {@link Sprite} instances associated with this {@link BasicSpriteGroup} instance.
	 */
	private List<Sprite> sprites = new ArrayList<Sprite>(); // member of this group
	
	// REVIEW-HIGH: This constructor must be kept for now (subclass uses it).
	/**
	 * Creates a new sprite group, with specified name. Name is used for group identifier only.
	 */
	public BasicSpriteGroup() {
		super();
	}
	
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
	@Override
	public final void add(final Sprite member) {
		member.setBackground(background);
		sprites.add(member);
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Removes sprite at specified index from this group.
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
	 * @see com.golden.gamedev.object.Sprite#setActive(boolean)
	 * @see #getScanFrequence()
	 */
	public Sprite remove(final int index) {
		return sprites.remove(index);
	}
	
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
	@Override
	public boolean remove(final Sprite s) {
		return sprites.remove(s);
	}
	
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
	@Override
	public void clear() {
		sprites = new ArrayList<Sprite>();
	}
	
	// REVIEW-HIGH: This method must be kept for now (AdvanceSpriteGroup extends it).
	/**
	 * Updates all active sprites in this group, and check the schedule for removing inactive sprites.
	 * 
	 * @see #getScanFrequence()
	 */
	@Override
	public void update(final long elapsedTime) {
		for (Sprite sprite : sprites) {
			if (sprite.isActive()) {
				sprite.update(elapsedTime);
			}
		}
		
		if (scanFrequence.action(elapsedTime)) {
			// remove all inactive sprites
			removeInactiveSprites();
		}
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Throws any inactive sprites from this group, this method won't remove immutable sprites, to remove all inactive
	 * sprites even though the inactive sprites are immutable use {@link #removeImmutableSprites()}.
	 * <p>
	 * 
	 * This method is automatically called every time {@linkplain #getScanFrequence() timer for scanning inactive
	 * sprite} is scheduled.
	 * 
	 * @see #getScanFrequence()
	 * @see #removeImmutableSprites()
	 * @see com.golden.gamedev.object.Sprite#setImmutable(boolean)
	 */
	public final void removeInactiveSprites() {
		for (Iterator<Sprite> iterator = sprites.iterator(); iterator.hasNext();) {
			Sprite sprite = iterator.next();
			if (!sprite.isActive() && !sprite.isImmutable()) {
				iterator.remove();
			}
		}
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it).
	/**
	 * Renders all active sprites in this group. If this group is associated with a comparator, the group sprites is
	 * sort against the comparator first before rendered.
	 * 
	 * @see #setComparator(Comparator)
	 */
	@Override
	public void render(final Graphics2D g) {
		if (comparator != null) {
			Collections.sort(sprites, comparator);
		}
		
		for (Sprite sprite : sprites) {
			if (sprite.isActive()) {
				sprite.render(g);
			}
		}
	}
	
	/**
	 * Returns the background of this group.
	 * 
	 * @see #setBackground(Background)
	 */
	@Override
	public final Background getBackground() {
		return background;
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Associates specified background with this sprite group, the background will be used by all sprites in this group.
	 * 
	 * @see #getBackground()
	 */
	@Override
	public void setBackground(final Background backgr) {
		background = (Background) ObjectUtils.defaultIfNull(backgr, Background.getDefaultBackground());
		
		// force all sprites to use a same background
		for (Sprite sprite : sprites) {
			sprite.setBackground(background);
		}
	}
	
	/**
	 * Returns active state of this group.
	 * 
	 * @see #setActive(boolean)
	 */
	@Override
	public final boolean isActive() {
		return active;
	}
	
	/**
	 * Sets active state of this group, inactive group won't be updated, rendered, and won't be checked for collision.
	 * <p>
	 * 
	 * @see #isActive()
	 */
	@Override
	public final void setActive(final boolean b) {
		active = b;
	}
	
	/**
	 * Returns comparator used for sorting sprites in this group.
	 * 
	 * @see #setComparator(Comparator)
	 */
	public final Comparator<Sprite> getComparator() {
		return comparator;
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Sets comparator used for sorting sprites in this group. Specify null comparator for unsort order (the first
	 * sprite in the array will be rendered at the back of other sprite).
	 * <p>
	 * 
	 * The comparator is used by {@link java.util.Arrays#sort(java.lang.Object[], int, int, java.util.Comparator)} to
	 * sort the sprites before rendering. For more information about how to make comparator, please read
	 * java.util.Comparator and java.util.Arrays#sort().
	 * 
	 * Example of sorting sprites based on y-axis :
	 * 
	 * <pre>
	 *    SpriteGroup ENEMY_GROUP;
	 *    ENEMY_GROUP.setComparator(
	 *       new Comparator() {
	 *          public int compare(Object o1, Object o2) {
	 *             Sprite s1 = (Sprite) o1,
	 *                    s2 = (Sprite) o2;
	 *             return (s1.getY() - s2.getY());
	 *          }
	 *       }
	 *    };
	 * </pre>
	 * 
	 * @param c
	 *            the sprite comparator, null for unsort order
	 * @see java.util.Comparator
	 * @see java.util.Arrays#sort(java.lang.Object[], int, int, java.util.Comparator)
	 */
	public void setComparator(final Comparator<Sprite> c) {
		comparator = c;
	}
	
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
	@Override
	public List<Sprite> getSprites() {
		return sprites;
	}
	
	/**
	 * @param sprites
	 *            the sprites to set
	 */
	@Override
	public void setSprites(List<Sprite> sprites) {
		Validate.notNull(sprites, "The List of Sprites may not be null!");
		this.sprites = sprites;
		setBackground(background);
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it).
	/**
	 * Returns total active and inactive sprites (<b>non-null</b> sprites) in this group.
	 * 
	 * @see #getSprites()
	 */
	@Override
	public int getSize() {
		return sprites.size();
	}
	
	// REVIEW-HIGH: This method must be kept (subclass uses it)
	/**
	 * Schedule timer for {@linkplain #removeInactiveSprites() removing inactive sprites}.
	 * <p>
	 * 
	 * For example to set this group to scan inactive sprite every 30 seconds (the default is 15 seconds) :
	 * 
	 * <pre>
	 * SpriteGroup PLAYER_GROUP;
	 * PLAYER_GROUP.getScanFrequence().setDelay(30000); // 30 x 1000 ms
	 * </pre>
	 * 
	 * @see #removeInactiveSprites()
	 */
	public final Timer getScanFrequence() {
		return scanFrequence;
	}
	
	// REVIEW-LOW: Document this.
	/**
	 * @param scanFrequence
	 *            the scanFrequence to set
	 */
	public void setScanFrequence(Timer scanFrequence) {
		Validate.notNull(scanFrequence, "The given timer may not be null!");
		this.scanFrequence = scanFrequence;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Number of sprites", getSize())
				.append("Sprites in group", getSprites()).toString();
	}
	
}
