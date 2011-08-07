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
package com.golden.gamedev.object.collision;

// GTGE
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

/**
 * Basic collision check, only check whether a collision occured or not.
 * <p>
 * 
 * This class does not gather any information from the collision. To get more information from the collision, such as
 * collision side, use {@link CollisionGroup} instead.
 * <p>
 * 
 * This type of collision check is the best to use for hit and destroy collision.
 * <p>
 * 
 * For example: collision between projectile and enemy ships
 * 
 * <pre>
 * Playfield playfield;
 * SpriteGroup PROJECTILE, ENEMY;
 * playfield.addCollisionGroup(PROJECTILE, ENEMY, new BasicCollisionGroup() {
 * 	
 * 	public void collided(Sprite s1, Sprite s2) {
 * 		// after enemy collided with projectile,
 * 		// the enemy explode (set to non-active)
 * 		s2.setActive(false);
 * 	}
 * });
 * </pre>
 * 
 * @see PlayField#addCollisionGroup(SpriteGroup, SpriteGroup, CollisionManager)
 */
public abstract class BasicCollisionGroup implements CollisionManager {
	
	// sprite bounding box
	/**
	 * Default collision shape used as every sprites in group 1 bounding box.
	 */
	protected final CollisionRect rect1 = new CollisionRect();
	
	/**
	 * Default collision shape used as every sprites in group 2 bounding box.
	 */
	protected final CollisionRect rect2 = new CollisionRect();
	
	/**
	 * The non-null {@link List} of {@link CollisionListener} instances to notify when a collision occurred.
	 */
	private final List<CollisionListener> listeners;
	
	/**
	 * Indicates whether this collision detection should use pixel-perfect precision or not.
	 * <p>
	 * 
	 * The usual way to turn on this variable is :
	 * 
	 * <pre>
	 * class ThisThatCollision extends BasicCollisionGroup {
	 * 	
	 * 	// class initialization
	 * 	{
	 * 		pixelPerfectCollision = true;
	 * 	}
	 * }
	 * </pre>
	 */
	public boolean pixelPerfectCollision;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>BasicCollisionGroup</code>.
	 */
	public BasicCollisionGroup(final List<? extends CollisionListener> listeners) {
		super();
		Validate.notEmpty(
				listeners,
				"The CollisionListener list may not be empty - listeners must be registered or else creating a CollisionManager instance is pointless!");
		this.listeners = new LinkedList<CollisionListener>(listeners);
	}
	
	/**
	 * Returns collision shape (bounding box) of specified sprite from group 1.
	 * <p>
	 * 
	 * In this implementation, the sprite bounding box is set as large as <code>Sprite</code> dimension:
	 * 
	 * <pre>
	 * public CollisionShape getCollisionRect1(Sprite s1) {
	 * 	rect1.setBounds(s1.getX(), s1.getY(), s1.getWidth(), s1.getHeight());
	 * 	return rect1;
	 * }
	 * </pre>
	 * 
	 * @param s1
	 *            the sprite from group 1 to be check its collision
	 * @return The collision shape of the sprite, or null to skip collision check.
	 * 
	 * @see #rect1
	 * @see #getCollisionShape2(Sprite)
	 * @see CollisionShape#intersects(CollisionShape)
	 */
	public CollisionShape getCollisionShape1(Sprite s1) {
		this.rect1.setBounds(s1.getX(), s1.getY(), s1.getWidth(), s1.getHeight());
		
		return this.rect1;
	}
	
	/**
	 * Returns collision shape (bounding box) of specified sprite from group 2.
	 * <p>
	 * 
	 * In this implementation, the sprite bounding box is set as large as <code>Sprite</code> dimension:
	 * 
	 * <pre>
	 * public CollisionShape getCollisionRect2(Sprite s2) {
	 * 	rect2.setBounds(s2.getX(), s2.getY(), s2.getWidth(), s2.getHeight());
	 * 	return rect2;
	 * }
	 * </pre>
	 * 
	 * @param s2
	 *            the sprite from group 2 to be check its collision
	 * @return The collision shape of the sprite, or null to skip collision check.
	 * 
	 * @see #rect2
	 * @see #getCollisionShape1(Sprite)
	 * @see CollisionRect#intersects(CollisionShape)
	 */
	public CollisionShape getCollisionShape2(Sprite s2) {
		this.rect2.setBounds(s2.getX(), s2.getY(), s2.getWidth(), s2.getHeight());
		
		return this.rect2;
	}
	
	@Override
	public void checkCollision(final SpriteGroup first, final SpriteGroup second) {
		if (!first.isActive() || !second.isActive()) {
			// one of the group is not active
			return;
		}
		
		List<Sprite> member1 = first.getSprites(), // group one members
		member2 = second.getSprites();
		
		CollisionShape shape1, shape2; // sprite collision rect
		
		for (Sprite sprite1 : member1) {
			if (!sprite1.isActive() || (shape1 = this.getCollisionShape1(sprite1)) == null) {
				// sprite do not want collision check
				continue;
			}
			
			for (Sprite sprite2 : member2) {
				if (!sprite2.isActive() || sprite1 == sprite2 || (shape2 = this.getCollisionShape2(sprite2)) == null) {
					// sprite do not want collision check
					continue;
				}
				
				if (this.isCollide(sprite1, sprite2, shape1, shape2)) {
					// fire collision event
					this.collided(sprite1, sprite2);
					
					// size1 = first.getSize();
					// size2 = second.getSize();
					
					if (!sprite1.isActive() || (shape1 = this.getCollisionShape1(sprite1)) == null) {
						// collided sprite has been dead
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Performs collision check between Sprite <code>s1</code> and Sprite <code>s2</code>, and returns true if the
	 * sprites (<code>shape1</code>, <code>shape2</code>) is collided.
	 * <p>
	 * 
	 * Note: this method do not check active state of the sprites.
	 * 
	 * @param s1
	 *            sprite from group 1
	 * @param s2
	 *            sprite from group 2
	 * @param shape1
	 *            bounding box of sprite 1
	 * @param shape2
	 *            bounding box of sprite 2
	 * @return true, if the sprites is collided one another.
	 */
	public boolean isCollide(Sprite s1, Sprite s2, CollisionShape shape1, CollisionShape shape2) {
		if (!this.pixelPerfectCollision) {
			return (shape1.intersects(shape2));
			
		} else {
			if (shape1.intersects(shape2)) {
				return BasicCollisionGroup.isPixelCollide(s1.getX(), s1.getY(), s1.getImage(), s2.getX(), s2.getY(),
						s2.getImage());
			}
			
			return false;
		}
	}
	
	/**
	 * Notified when <code>sprite1</code> from group 1 collided with <code>sprite2</code> from group 2.
	 * 
	 * @param s1
	 *            sprite from group 1
	 * @param s2
	 *            sprite from group 2
	 */
	public final void collided(Sprite s1, Sprite s2) {
		for (CollisionListener listener : listeners) {
			listener.collisionOccurred(s1, s2);
		}
	}
	
	/**
	 * Returns true whether <code>image1</code> at <code>x1</code>, <code>y1</code> collided with <code>image2</code> at
	 * <code>x2</code>, <code>y2</code>.
	 */
	public static boolean isPixelCollide(double x1, double y1, BufferedImage image1, double x2, double y2,
			BufferedImage image2) {
		// initialization
		double width1 = x1 + image1.getWidth() - 1, height1 = y1 + image1.getHeight() - 1, width2 = x2
				+ image2.getWidth() - 1, height2 = y2 + image2.getHeight() - 1;
		
		int xstart = (int) Math.max(x1, x2), ystart = (int) Math.max(y1, y2), xend = (int) Math.min(width1, width2), yend = (int) Math
				.min(height1, height2);
		
		// intersection rect
		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);
		
		for (int y = 1; y < toty - 1; y++) {
			int ny = Math.abs(ystart - (int) y1) + y;
			int ny1 = Math.abs(ystart - (int) y2) + y;
			
			for (int x = 1; x < totx - 1; x++) {
				int nx = Math.abs(xstart - (int) x1) + x;
				int nx1 = Math.abs(xstart - (int) x2) + x;
				try {
					if (((image1.getRGB(nx, ny) & 0xFF000000) != 0x00)
							&& ((image2.getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {
						// collide!!
						return true;
					}
				} catch (Exception e) {
					// System.out.println("s1 = "+nx+","+ny+" - s2 =
					// "+nx1+","+ny1);
				}
			}
		}
		
		return false;
	}
	
}
