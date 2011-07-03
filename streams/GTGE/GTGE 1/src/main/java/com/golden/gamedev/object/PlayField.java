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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.golden.gamedev.object.collision.CollisionManager;
import com.golden.gamedev.util.Utility;

/**
 * <code>PlayField</code> class is the game arena where all the game objects are put on. This class manages all objects
 * in the game, such as sprite, background, sprite group, and collision check.
 * <p>
 * 
 * <code>PlayField</code> simplify sprite updating and rendering. <br>
 * By calling {@link #update(long)} all sprites within this playfield will be updated and collision will be check. <br>
 * By calling {@link #render(Graphics2D)} all sprites will be rendered to the screen.
 * 
 * @see com.golden.gamedev.object.SpriteGroup
 * @see com.golden.gamedev.object.CollisionManager
 */
public final class PlayField {
	
	/**
	 * A key denoting a registered {@link CollisionManager} for two {@link SpriteGroup} instances.
	 * 
	 * @author MetroidFan2002
	 * @version 1.0
	 * @since 1.0
	 * 
	 */
	private static final class SpriteGroupCollisionManagerKey {
		
		/**
		 * The first (possibly null) {@link SpriteGroup} instance registered via this
		 * {@link SpriteGroupCollisionManagerKey key}.
		 */
		private final SpriteGroup first;
		
		/**
		 * The second (possibly null) {@link SpriteGroup} instance registered via this
		 * {@link SpriteGroupCollisionManagerKey key}.
		 */
		private final SpriteGroup second;
		
		/**
		 * Creates a new {@link SpriteGroupCollisionManagerKey} instance.
		 * 
		 * @param first
		 *            The first (possibly null) {@link SpriteGroup} instance registered via this
		 *            {@link SpriteGroupCollisionManagerKey key}.
		 * @param second
		 *            The second (possibly null) {@link SpriteGroup} instance registered via this
		 *            {@link SpriteGroupCollisionManagerKey key}.
		 */
		private SpriteGroupCollisionManagerKey(SpriteGroup first, SpriteGroup second) {
			super();
			this.first = first;
			this.second = second;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result + ((second == null) ? 0 : second.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof SpriteGroupCollisionManagerKey))
				return false;
			SpriteGroupCollisionManagerKey other = (SpriteGroupCollisionManagerKey) obj;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (!first.equals(other.first))
				return false;
			if (second == null) {
				if (other.second != null)
					return false;
			} else if (!second.equals(other.second))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this);
			builder.append("first", first);
			builder.append("second", second);
			return builder.toString();
		}
		
		/**
		 * Gets whether or not this {@link SpriteGroupCollisionManagerKey} instance contains the specified
		 * {@link SpriteGroup} instance.
		 * 
		 * @param group
		 *            The possibly-null {@link SpriteGroup} instance to check for.
		 * @return True if this {@link SpriteGroupCollisionManagerKey} instance contains the specified
		 *         {@link SpriteGroup} instance, false otherwise.
		 */
		private boolean containsGroup(final SpriteGroup group) {
			if (group == null) {
				return first == null || second == null;
			} else {
				return group.equals(first) || group.equals(second);
			}
		}
	}
	
	/**
	 * ********************** PLAYFIELD PROPERTIES *****************************
	 */
	
	private SpriteGroup[] groups;
	private Background background;
	
	/**
	 * The non-null {@link Map} of {@link SpriteGroupCollisionManagerKey} instances to their associated
	 * {@link CollisionManager} instances.
	 */
	private Map<SpriteGroupCollisionManagerKey, CollisionManager> collisionManagers = new HashMap<SpriteGroupCollisionManagerKey, CollisionManager>();
	
	/**
	 * ************************** SORT RENDERING *******************************
	 */
	
	private Sprite[] cacheSprite;
	private Comparator<Sprite> comparator;
	
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
	 * Constructs new <code>PlayField</code> with specified background.
	 */
	public PlayField(final Background background) {
		this.background = background;
		
		// preserve one group for the extra group
		final SpriteGroup extra = new SpriteGroup("Extra Group");
		extra.setBackground(background);
		
		groups = new SpriteGroup[1];
		groups[0] = extra;
		
		cacheSprite = new Sprite[0];
	}
	
	/**
	 * Constructs new <code>PlayField</code> with {@link Background#getDefaultBackground() default background}.
	 */
	public PlayField() {
		this(Background.getDefaultBackground());
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ********************* SPRITE GROUP OPERATION ****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Inserts a sprite (extra sprite) directly into playfield, for example animation, explosion, etc.
	 * <p>
	 * 
	 * This method is a convenient way to add sprites directly into screen without have to creates new
	 * {@link SpriteGroup}.
	 * <p>
	 * 
	 * The sprite is inserted to 'extra group' and all sprites on extra group will always on top of other sprites.
	 */
	public void add(final Sprite extra) {
		groups[groups.length - 1].add(extra);
	}
	
	/**
	 * Inserts new <code>SpriteGroup</code> into this playfield. This method returned object reference of the inserted
	 * group.
	 * <p>
	 * 
	 * The returned group used to reduce code and simplicity. <br>
	 * For example :
	 * 
	 * <pre>
	 * 
	 * 
	 * 
	 * 
	 * Playfield playfield = new Playfield();
	 * SpriteGroup PLAYER = playfield.addGroup(new SpriteGroup(&quot;Player&quot;));
	 * </pre>
	 * 
	 * If there is no returned reference, we must set the sprite group and add it manually into playfield :
	 * 
	 * <pre>
	 * SpriteGroup PLAYER = new SpriteGroup(&quot;Player&quot;);
	 * Playfield playfield = new Playfield();
	 * playfield.addGroup(PLAYER);
	 * </pre>
	 * 
	 * @param group
	 *            sprite group to be inserted into this playfield
	 * @return Reference of the inserted sprite group.
	 */
	public SpriteGroup addGroup(final SpriteGroup group) {
		// extra group always at behind!
		final SpriteGroup extra = groups[groups.length - 1];
		SpriteGroup[] array = (SpriteGroup[]) ArrayUtils.remove(groups, (groups.length - 1));
		groups = array;
		
		groups = Utility.expand(groups, 2, true);
		group.setBackground(background);
		groups[groups.length - 2] = group;
		groups[groups.length - 1] = extra; // move extra group to
		// the last row
		
		return group;
	}
	
	/**
	 * Removes specified sprite group from this playfield.
	 * 
	 * @param group
	 *            sprite group to be removed from this playfield
	 * @return true, if the sprite group is successfuly removed.
	 */
	public boolean removeGroup(final SpriteGroup group) {
		// last group is exclusive for extra group
		// it can't be removed!
		for (int i = 0; i < groups.length - 1; i++) {
			if (groups[i] == group) {
				SpriteGroup[] array = (SpriteGroup[]) ArrayUtils.remove(groups, i);
				groups = array;
				
				// sprite group has been removed
				// therefore, any collision group registered
				// with it should be removed too!
				CollisionManager collisionGroup = this.getCollisionGroup(group);
				if (collisionGroup != null) {
					do {
						removeCollisionGroup(collisionGroup);
						collisionGroup = this.getCollisionGroup(group);
					} while (collisionGroup != null);
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns sprite group with specified name associated with this playfield.
	 */
	public SpriteGroup getGroup(final String name) {
		for (final SpriteGroup group : groups) {
			if (group.getName().equals(name)) {
				return group;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns all sprite group associated with this playfield.
	 */
	public SpriteGroup[] getGroups() {
		return groups;
	}
	
	/**
	 * Returns this playfield extra sprite group.
	 * <p>
	 * 
	 * Extra sprite group is preserve group that always in front of other groups, usually used to hold game animation
	 * such as explosion.
	 * <p>
	 * 
	 * This group also exists for convenient way to add sprite into playfield without creating sprite group.
	 * 
	 * @see #add(Sprite)
	 */
	public SpriteGroup getExtraGroup() {
		return groups[groups.length - 1];
	}
	
	/**
	 * Clears all sprites in this playfield and makes this playfield empty.
	 * <p>
	 * 
	 * This method iterates all groups in this playfield and remove all sprites inside it by calling
	 * {@link com.golden.gamedev.object.SpriteGroup#clear()}
	 */
	public void clearPlayField() {
		for (final SpriteGroup group : groups) {
			group.clear();
		}
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ****************** COLLISION GROUP OPERATION ****************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Associates specified collision group to this playfield.
	 */
	public void addCollisionGroup(final SpriteGroup group1, final SpriteGroup group2,
			final CollisionManager collisionGroup) {
		Validate.notNull(collisionGroup, "The collision manager to add may not be null!");
		collisionManagers.put(new SpriteGroupCollisionManagerKey(group1, group2), collisionGroup);
	}
	
	/**
	 * Removes specified collision group from this playfield.
	 * 
	 * @return true, if the collision group is successfully removed.
	 */
	public void removeCollisionGroup(final CollisionManager collisionGroup) {
		Validate.notNull(collisionGroup, "The collision manager to remove may not be null!");
		// Don't bother to iterate if the map doesn't contain the value.
		if (collisionManagers.containsValue(collisionGroup)) {
			for (Iterator<CollisionManager> valueIterator = collisionManagers.values().iterator(); valueIterator
					.hasNext();) {
				if (collisionGroup.equals(valueIterator.next())) {
					valueIterator.remove();
					break;
				}
			}
		}
	}
	
	/**
	 * Returns associated collision group that checking collision of <code>group1</code> and <code>group2</code>, or
	 * null if requested collision group can not be found.
	 * 
	 * @param group1
	 *            the first group of the collision group to be find
	 * @param group2
	 *            the second group of the collision group to be find
	 * @return CollisionGroup that checks group1 and group2 for collision, or null if no collision group can be found.
	 */
	public CollisionManager getCollisionGroup(final SpriteGroup group1, final SpriteGroup group2) {
		return collisionManagers.get(new SpriteGroupCollisionManagerKey(group1, group2));
	}
	
	/**
	 * Returns any collision group associated with specified sprite group.
	 */
	private CollisionManager getCollisionGroup(final SpriteGroup group) {
		for (Entry<SpriteGroupCollisionManagerKey, CollisionManager> entry : collisionManagers.entrySet()) {
			if (entry.getKey().containsGroup(group)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Returns all collision group associated with this playfield, as an
	 * {@link Collections#unmodifiableCollection(Collection) unmodifiable collection}.
	 */
	public Collection<CollisionManager> getCollisionGroups() {
		return Collections.unmodifiableCollection(collisionManagers.values());
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *********************** UPDATE PLAYFIELD ********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Updates sprites, background, and check for collisions.
	 */
	public void update(final long elapsedTime) {
		updateSpriteGroups(elapsedTime);
		updateBackground(elapsedTime);
		
		checkCollisions();
	}
	
	/**
	 * Updates playfield background.
	 */
	protected void updateBackground(final long elapsedTime) {
		background.update(elapsedTime);
	}
	
	/**
	 * Updates sprites in sprite groups on this playfield.
	 */
	protected void updateSpriteGroups(final long elapsedTime) {
		for (final SpriteGroup group : groups) {
			if (group.isActive()) {
				group.update(elapsedTime);
			}
		}
	}
	
	/**
	 * Checks for collision event.
	 */
	protected void checkCollisions() {
		for (final Entry<SpriteGroupCollisionManagerKey, CollisionManager> entry : collisionManagers.entrySet()) {
			entry.getValue().checkCollision(entry.getKey().first, entry.getKey().second);
		}
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * *********************** RENDER PLAYFIELD ********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Renders background, and sprite groups (with/without {@linkplain #setComparator(Comparator) comparator}).
	 */
	public void render(final Graphics2D g) {
		renderBackground(g);
		
		if (comparator == null) {
			this.renderSpriteGroups(g);
			
		} else {
			this.renderSpriteGroups(g, comparator);
		}
	}
	
	/**
	 * Renders background to specified graphics context.
	 */
	protected void renderBackground(final Graphics2D g) {
		background.render(g);
	}
	
	/**
	 * Renders sprite groups to specified graphics context.
	 */
	protected void renderSpriteGroups(final Graphics2D g) {
		for (final SpriteGroup group : groups) {
			if (group.isActive()) {
				group.render(g);
			}
		}
	}
	
	/**
	 * Renders all sprites using specified comparator. Only {@linkplain Sprite#isActive() active} and
	 * {@linkplain Sprite#isOnScreen() on screen} sprite is sorted and rendered.
	 * <p>
	 * 
	 * Sprites that rendered within this method is stored in cache, therefore if this method is called ONLY ONCE and
	 * then {@linkplain #setComparator(Comparator) comparator} is set to null, it is better to manually
	 * {@linkplain #clearCache() clear the cache} after it or the cache will hold the sprites forever and makes the
	 * sprite can't be disposed.
	 */
	protected void renderSpriteGroups(final Graphics2D g, final Comparator<Sprite> c) {
		int num = 0, len = cacheSprite.length;
		
		if (len == 0) {
			// sprite cache initialization
			cacheSprite = new Sprite[100];
			len = cacheSprite.length;
		}
		
		for (int i = 0; i < groups.length; i++) {
			if (!groups[i].isActive()) {
				continue;
			}
			
			final Sprite[] member = groups[i].getSprites();
			final int size = groups[i].getSize();
			
			for (int j = 0; j < size; j++) {
				if (member[j].isActive() && // only active and onscreen sprite
						member[j].isOnScreen()) { // is sorted and rendered
				
					if (num >= len) {
						// expand sprite storage
						cacheSprite = Utility.expand(cacheSprite, 20, true);
						len = cacheSprite.length;
					}
					
					cacheSprite[num++] = member[j];
				}
			}
		}
		
		// sort all active and onscreen sprites
		Arrays.sort(cacheSprite, 0, num, c);
		
		for (int i = 0; i < num; i++) {
			cacheSprite[i].render(g);
		}
	}
	
	/**
	 * Clears cache sprite.
	 * <p>
	 * 
	 * Cache sprite is used to keep sprites in sorted order (if this playfield used an external comparator). <br>
	 * When {@link #renderSpriteGroups(Graphics2D, Comparator)} called, all sprites are stored in cache and then sorted,
	 * Therefore after rendering done, the cache still remain in memory and wait to be cached again on next sort
	 * rendering.
	 * <p>
	 * 
	 * This method simply clears the cache sprite. Call this method when sort rendering is not needed anymore.
	 * 
	 * @see #renderSpriteGroups(Graphics2D, Comparator)
	 */
	public void clearCache() {
		cacheSprite = null;
		cacheSprite = new Sprite[0];
	}
	
	/**
	 * *************************************************************************
	 */
	/**
	 * ************************ OTHER FUNCTIONS ********************************
	 */
	/**
	 * *************************************************************************
	 */
	
	/**
	 * Returns background associated with this playfield.
	 */
	public Background getBackground() {
		return background;
	}
	
	/**
	 * Associates specified background to this playfield.
	 */
	public void setBackground(final Background backgr) {
		background = backgr;
		if (background == null) {
			background = Background.getDefaultBackground();
		}
		
		// force all sprites to use same background
		for (final SpriteGroup group : groups) {
			group.setBackground(backgr);
		}
	}
	
	/**
	 * Returns playfield comparator, comparator is used for sorting the sprites before rendering.
	 */
	public Comparator<Sprite> getComparator() {
		return comparator;
	}
	
	/**
	 * Sets playfield comparator, comparator is used for sorting the sprites before rendering. Specify null comparator
	 * for unsort order (the first sprite in the array will be rendered at the back of other sprite).
	 * <p>
	 * 
	 * The comparator is used in {@link java.util.Arrays#sort(java.lang.Object[], int, int, java.util.Comparator)} from
	 * the java.lang package to sort the sprites, for more information about how to make comparator, please read
	 * java.util.Comparator and java.util.Arrays#sort().
	 * 
	 * Example of sorting sprites based on y-axis :
	 * 
	 * <pre>
	 *    PlayField playfield;
	 *    playfield.setComparator(
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
	
}
