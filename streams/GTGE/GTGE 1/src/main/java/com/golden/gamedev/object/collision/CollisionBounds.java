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

// JFC
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.BasicSpriteGroup;
import com.golden.gamedev.object.collision.CollisionManager.CollisionListener;
import static com.golden.gamedev.object.collision.CollisionManager.CollisionSide.*;

// REVIEW-HIGH: - CollisionBounds' boundary is EXCLUSIVE - only sprites OUTSIDE of this boundary are considered to have a collision!
/**
 * Checks collision for specified boundary.
 */
public abstract class CollisionBounds implements CollisionManager {
	
	/** ********************* COLLISION SIDE CONSTANTS ************************** */
	
	/**
	 * Indicates the sprite is collided at its left.
	 */
	public static final int LEFT_COLLISION = 1;
	
	/**
	 * Indicates the sprite is collided at its right.
	 */
	public static final int RIGHT_COLLISION = 2;
	
	/**
	 * Indicates the sprite is collided at its top.
	 */
	public static final int TOP_COLLISION = 4;
	
	/**
	 * Indicates the sprite is collided at its bottom.
	 */
	public static final int BOTTOM_COLLISION = 8;
	
	private final Rectangle boundary = new Rectangle();
	
	private int collisionSide;
	// sprite bounding box
	/**
	 * Default sprite bounding box used in {@link #getCollisionShape1(Sprite)}.
	 */
	protected final CollisionRect rect1 = new CollisionRect();
	
	/**
	 * The non-null {@link List} of {@link CollisionListener} instances to notify when a collision occurred.
	 */
	private final List<CollisionListener> listeners;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>CollisionBounds</code> with specified boundary.
	 */
	public CollisionBounds(final List<? extends CollisionListener> listeners, final int x, final int y,
			final int width, final int height) {
		super();
		Validate.notEmpty(
				listeners,
				"The CollisionListener list may not be empty - listeners must be registered or else creating a CollisionManager instance is pointless!");
		this.listeners = new LinkedList<CollisionListener>(listeners);
		boundary.setBounds(x, y, width, height);
	}
	
	/**
	 * Creates new <code>CollisionBounds</code> with specified background as the boundary.
	 */
	public CollisionBounds(final List<? extends CollisionListener> listeners, final Background backgr) {
		super();
		Validate.notEmpty(
				listeners,
				"The CollisionListener list may not be empty - listeners must be registered or else creating a CollisionManager instance is pointless!");
		this.listeners = new LinkedList<CollisionListener>(listeners);
		boundary.setBounds(0, 0, backgr.getWidth(), backgr.getHeight());
	}
	
	@Override
	public void checkCollision(final BasicSpriteGroup first, final BasicSpriteGroup second) {
		if (!first.isActive()) {
			// the group is not active, no need to check collision
			return;
		}
		
		for (Sprite sprite1 : first.getSprites()) {
			if (!sprite1.isActive()) {
				continue;
			}
			CollisionShape shape1 = sprite1.getDefaultCollisionShape();
			
			if (shape1 == null) {
				// Sprite does not wish to be considered in a collision - null collision shape retrieved.
				continue;
			}
			
			final List<CollisionSide> collisionSides = new LinkedList<CollisionSide>();
			
			if (shape1.getX() < boundary.x) {
				collisionSides.add(LEFT);
			}
			if (shape1.getY() < boundary.y) {
				collisionSides.add(TOP);
			}
			if (shape1.getX() + shape1.getWidth() > boundary.x + boundary.width) {
				collisionSides.add(RIGHT);
			}
			if (shape1.getY() + shape1.getHeight() > boundary.y + boundary.height) {
				collisionSides.add(BOTTOM);
			}
			
			if (!collisionSides.isEmpty()) {
				CollisionDetails details = new CollisionDetails(collisionSides);
				for (CollisionListener listener : listeners) {
					listener.collisionOccurred(first, null, details);
				}
			}
		}
	}
	
	/**
	 * Returns true, the sprite is collide at it <code>side</code> side.
	 */
	public boolean isCollisionSide(final int side) {
		return (collisionSide & side) != 0;
	}
	
	/**
	 * Sets the collision boundary, the sprite is bounded to this boundary.
	 */
	public void setBoundary(final int x, final int y, final int width, final int height) {
		boundary.setBounds(x, y, width, height);
	}
	
	/**
	 * Returns the boundary of the sprites.
	 */
	public Rectangle getBoundary() {
		return boundary;
	}
	
	/**
	 * Sprite <code>sprite</code> hit collision boundary, perform collided implementation.
	 */
	public abstract void collided(Sprite sprite);
	
}
