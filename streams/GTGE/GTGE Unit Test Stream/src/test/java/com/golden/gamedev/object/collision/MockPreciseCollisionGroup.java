package com.golden.gamedev.object.collision;

import com.golden.gamedev.object.Sprite;

/**
 * The {@link MockPreciseCollisionGroup} class provides an implementation of the
 * {@link PreciseCollisionGroup} class for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * @see PreciseCollisionGroup
 * 
 */
public final class MockPreciseCollisionGroup extends PreciseCollisionGroup {
	
	/**
	 * The first {@link Sprite} sent into the {@link #collided(Sprite, Sprite)}
	 * method.
	 */
	public Sprite sprite1;
	
	/**
	 * The second {@link Sprite} sent into the {@link #collided(Sprite, Sprite)}
	 * method.
	 */
	public Sprite sprite2;
	
	/**
	 * The possibly-null {@link CollisionShape} instance to use to return via
	 * {@link #getCollisionShape1(Sprite)} - if null, the default (super)
	 * implementation will be used.
	 */
	public CollisionShape collisionShape1;
	
	/**
	 * The possibly-null {@link CollisionShape} instance to use to return via
	 * {@link #getCollisionShape2(Sprite)} - if null, the default (super)
	 * implementation will be used.
	 */
	public CollisionShape collisionShape2;
	
	/**
	 * Creates a new {@link MockPreciseCollisionGroup} instance.
	 */
	public MockPreciseCollisionGroup() {
		super();
	}
	
	public void collided(Sprite s1, Sprite s2) {
		this.sprite1 = s1;
		this.sprite2 = s2;
	}
	
	public CollisionShape getCollisionShape1(Sprite s1) {
		return collisionShape1 == null ? super.getCollisionShape1(s1)
		        : collisionShape1;
	}
	
	public CollisionShape getCollisionShape2(Sprite s2) {
		return collisionShape2 == null ? super.getCollisionShape1(s2)
		        : collisionShape2;
	}
	
}
