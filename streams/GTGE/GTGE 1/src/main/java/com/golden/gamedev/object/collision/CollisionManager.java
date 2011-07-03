package com.golden.gamedev.object.collision;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

/**
 * The {@link CollisionManager} interface specifies an {@link Object} that is able to check one {@link SpriteGroup}
 * instance against another {@link SpriteGroup} and notify registered {@link CollisionListener} instances if they
 * collided. <br />
 * <br />
 * The process of registering {@link CollisionListener} instances is implementation-dependent.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 1.0
 * 
 */
public interface CollisionManager {
	
	/**
	 * The {@link CollisionSide} enumeration defines an enumeration with all four types of exclusive collisions possible
	 * in a two-dimensional environment.
	 * 
	 * @author MetroidFan2002
	 * @version 1.0
	 * @since 1.0
	 * 
	 */
	public enum CollisionSide {
		
		/**
		 * Indicates that the first {@link Sprite} collided with the second {@link Sprite} at the second {@link Sprite}
		 * instance's left side.
		 */
		LEFT,
		/**
		 * Indicates that the first {@link Sprite} collided with the second {@link Sprite} at the second {@link Sprite}
		 * instance's right side.
		 */
		RIGHT,

		/**
		 * Indicates that the first {@link Sprite} collided with the second {@link Sprite} at the second {@link Sprite}
		 * instance's top side.
		 */
		TOP,

		/**
		 * Indicates that the first {@link Sprite} collided with the second {@link Sprite} at the second {@link Sprite}
		 * instance's bottom side.
		 */
		BOTTOM
	}
	
	/**
	 * The {@link CollisionDetails} class provides an {@link Object} that contains details of a collision via its stored
	 * {@link CollisionSide} instances.
	 * 
	 * @author MetroidFan2002
	 * @version 1.0
	 * @since 1.0
	 * 
	 */
	public static final class CollisionDetails {
		
		/**
		 * The possibly-null {@link List} of {@link CollisionSide} instances representing the details of the collision
		 * that occurred.
		 */
		private final List<CollisionSide> collisionSides;
		
		/**
		 * Creates a new {@link CollisionDetails} instance.
		 * 
		 * @param collisionSides
		 *            The possibly-null {@link List} of {@link CollisionSide} instances representing the details of the
		 *            collision that occurred.
		 */
		public CollisionDetails(List<CollisionSide> collisionSides) {
			super();
			this.collisionSides = collisionSides;
		}
		
		/**
		 * Gets whether or not this {@link CollisionDetails} instance contains the given {@link CollisionSide}.
		 * 
		 * @param side
		 *            The {@link CollisionSide} instance to check for, which may be null.
		 * @return True if this {@link CollisionDetails} instance contains the given {@link CollisionSide}, false
		 *         otherwise.
		 */
		public boolean containsSide(final CollisionSide side) {
			return collisionSides == null ? false : collisionSides.contains(side);
		}
		
		/**
		 * Gets an unmodifiable {@link List} containing the {@link CollisionSide} instances contained via this
		 * {@link CollisionDetails} instance.
		 * 
		 * @return An unmodifiable {@link List} containing the {@link CollisionSide} instances contained via this
		 *         {@link CollisionDetails} instance.
		 */
		public List<CollisionSide> getCollisionSides() {
			if (collisionSides == null) {
				return Collections.emptyList();
			}
			return Collections.unmodifiableList(collisionSides);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((collisionSides == null) ? 0 : collisionSides.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof CollisionDetails)) {
				return false;
			}
			CollisionDetails other = (CollisionDetails) obj;
			if (collisionSides == null) {
				if (other.collisionSides != null)
					return false;
			} else if (!collisionSides.equals(other.collisionSides))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			ToStringBuilder builder = new ToStringBuilder(this);
			builder.append("collisionSides", collisionSides);
			return builder.toString();
		}
	}
	
	// REVIEW-HIGH: This interface needs some work internally - the collision managers that check sprites individually
	// should report collision details as well if possible.
	/**
	 * The {@link CollisionListener} interface specifies an {@link Object} that listens for collisions that occur when
	 * the {@link CollisionManager#checkCollision()} method of the {@link CollisionManager} interface is invoked on a
	 * {@link CollisionManager} instance that has added this {@link CollisionListener} instance as a listener. <br />
	 * <br />
	 * The process of registering {@link CollisionListener} instances inside of a {@link CollisionManager} instance is
	 * implementation-dependent.
	 * 
	 * @author MetroidFan2002
	 * @version 1.0
	 * @since 1.0
	 * 
	 */
	public interface CollisionListener {
		
		/**
		 * Event callback which is fired when a collision between the given {@link SpriteGroup} instances has occurred.
		 * Typically, at least one of the two {@link SpriteGroup} instances received via this callback are non-null, but
		 * this is not an absolute requirement.
		 * 
		 * @param first
		 *            The possibly-null {@link SpriteGroup} representing the first {@link SpriteGroup} that collided
		 *            with the second.
		 * @param second
		 *            The possibly-null {@link SpriteGroup} representing the second {@link SpriteGroup} that collided
		 *            with the first.
		 * @param details
		 *            The possibly-null {@link CollisionDetails} instance that provides extra details about the
		 *            collision between the two groups.
		 */
		void collisionOccurred(SpriteGroup first, SpriteGroup second, final CollisionDetails details);
		
		/**
		 * Event callback on a specific {@link Sprite} to {@link Sprite} collision. Typically, at least one of the two
		 * {@link Sprite} instances received via this callback are non-null, but this is not an absolute requirement.
		 * 
		 * @param first
		 *            The possibly-null {@link Sprite} representing the first {@link Sprite} that collided with the
		 *            second.
		 * @param second
		 *            The possibly-null {@link Sprite} representing the second {@link Sprite} that collided with the
		 *            first.
		 */
		void collisionOccurred(final Sprite first, final Sprite second);
	}
	
	/**
	 * Checks for a collision between the first and second {@link SpriteGroup} instances, notifying all registered
	 * {@link CollisionListener listeners} if a collision occurred. <br />
	 * <br />
	 * Typically, at least one of the given {@link SpriteGroup} instances is non-null, but this is not an absolute
	 * requirement. {@link CollisionManager} instances may throw an {@link IllegalArgumentException} if a null argument
	 * is provided and the {@link CollisionManager} cannot handle the null argument.
	 * 
	 * @param first
	 *            The possibly-null first {@link SpriteGroup} instance to check against the second {@link SpriteGroup}
	 *            for a collision.
	 * @param second
	 *            The possibly-null second {@link SpriteGroup} instance to check against the first {@link SpriteGroup}
	 *            for a collision.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the {@link CollisionManager} instance encounters an
	 *             unsupported null argument.
	 */
	void checkCollision(final SpriteGroup first, final SpriteGroup second);
}