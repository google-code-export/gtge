package com.golden.gamedev.object.collision;

import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import com.golden.gamedev.object.Sprite;

/**
 * The {@link PreciseCollisionGroupTest} class provides a {@link TestCase} to
 * test the functionality of the {@link PreciseCollisionGroup} class.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * 
 */
public class PreciseCollisionGroupTest extends TestCase {
	
	/**
	 * The {@link MockPreciseCollisionGroup} instance under test.
	 */
	private MockPreciseCollisionGroup group;
	
	protected void setUp() throws Exception {
		group = new MockPreciseCollisionGroup();
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.collision.PreciseCollisionGroup#isCollide(com.golden.gamedev.object.Sprite, com.golden.gamedev.object.Sprite, com.golden.gamedev.object.collision.CollisionShape, com.golden.gamedev.object.collision.CollisionShape)}
	 * .
	 */
	public final void testIsCollide() {
		assertFalse(group.log);
		spriteOneTests();
		spriteTwoTests();
		group = new MockPreciseCollisionGroup();
		group.log = true;
		spriteOneTests();
		spriteTwoTests();
	}
	
	/**
     * 
     */
	private void spriteOneTests() {
		// Stationary tests
		Sprite sprite1 = new Sprite();
		Sprite sprite2 = new Sprite();
		sprite1.setLocation(100, 100);
		sprite1.setImage(new BufferedImage(640, 480,
		        BufferedImage.TYPE_3BYTE_BGR));
		sprite2.setLocation(100, 100);
		sprite2.setImage(sprite1.getImage());
		assertFalse(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		// Collisions based on collision shapes - no pixel perfect collision
		// detection yet.
		// Movement for sprite 1 causes a collision.
		sprite1.setSpeed(2, 0);
		sprite1.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(740, group.collisionX1, 0);
		assertEquals(100, group.collisionY1, 0);
		assertEquals("Expected a left-right collision",
		        CollisionGroup.LEFT_RIGHT_COLLISION, group.collisionSide);
		
		// Let's reverse the speeds and try again.
		sprite1.setLocation(100, 100);
		sprite1.setSpeed(-2, 0);
		sprite1.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(-540, group.collisionX1, 0);
		assertEquals(100, group.collisionY1, 0);
		assertEquals("Expected a right-left collision",
		        CollisionGroup.RIGHT_LEFT_COLLISION, group.collisionSide);
		
		// Try a bottom-top collision.
		sprite1.setLocation(100, 100);
		sprite1.setSpeed(0, -2);
		sprite1.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(100, group.collisionX1, 0);
		assertEquals(-380, group.collisionY1, 0);
		assertEquals("Expected a bottom-top collision",
		        CollisionGroup.BOTTOM_TOP_COLLISION, group.collisionSide);
		
		// Top-bottom collision
		sprite1.setLocation(100, 100);
		sprite1.setSpeed(0, 2);
		sprite1.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(100, group.collisionX1, 0);
		assertEquals(580, group.collisionY1, 0);
		assertEquals("Expected a top-bottom collision",
		        CollisionGroup.TOP_BOTTOM_COLLISION, group.collisionSide);
		
		// both y speeds equal
		sprite1.setLocation(100, 100);
		sprite1.setSpeed(0, 2);
		sprite1.update(100);
		sprite2.setSpeed(0, 2);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(100, group.collisionX1, 0);
		assertEquals(580, group.collisionY1, 0);
		assertEquals("Expected a top-bottom collision",
		        CollisionGroup.TOP_BOTTOM_COLLISION, group.collisionSide);
		
		// Set the sprites apart.
		CollisionRect rect = new CollisionRect();
		rect.setBounds(2000, 2000, 0, 0);
		group.collisionShape1 = rect;
		
		assertFalse(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		group.collisionShape1 = null;
		
	}
	
	private void spriteTwoTests() {
		// Stationary tests
		Sprite sprite1 = new Sprite();
		Sprite sprite2 = new Sprite();
		sprite1.setLocation(100, 100);
		sprite1.setImage(new BufferedImage(640, 480,
		        BufferedImage.TYPE_3BYTE_BGR));
		sprite2.setLocation(100, 100);
		sprite2.setImage(sprite1.getImage());
		assertFalse(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		// Collisions based on collision shapes - no pixel perfect collision
		// detection yet.
		// Movement for sprite 2 causes a collision.
		sprite2.setSpeed(2, 0);
		sprite2.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(-140, group.collisionX2, 0);
		assertEquals(100, group.collisionY2, 0);
		assertEquals("Expected a left-right collision",
		        CollisionGroup.LEFT_RIGHT_COLLISION, group.collisionSide);
		
		// Let's reverse the speeds and try again.
		// Right-left collision
		sprite2.setLocation(100, 100);
		sprite2.setSpeed(-2, 0);
		sprite2.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(340, group.collisionX2, 0);
		assertEquals(100, group.collisionY2, 0);
		assertEquals("Expected a left-right collision",
		        CollisionGroup.RIGHT_LEFT_COLLISION, group.collisionSide);
		
		// Bottom-top collision
		sprite2.setLocation(100, 100);
		sprite2.setSpeed(0, -2);
		sprite2.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(100, group.collisionX2, 0);
		assertEquals(180, group.collisionY2, 0);
		assertEquals("Expected a top-bottom collision",
		        CollisionGroup.BOTTOM_TOP_COLLISION, group.collisionSide);
		
		// Top-bottom collision
		sprite2.setLocation(100, 100);
		sprite2.setSpeed(0, 2);
		sprite2.update(100);
		assertTrue(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		assertEquals(100, group.collisionX2, 0);
		assertEquals(20, group.collisionY2, 0);
		assertEquals("Expected a top-bottom collision",
		        CollisionGroup.TOP_BOTTOM_COLLISION, group.collisionSide);
		
		// Set the sprites apart.
		CollisionRect rect = new CollisionRect();
		rect.setBounds(2000, 2000, 0, 0);
		group.collisionShape2 = rect;
		
		assertFalse(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
		// Try setting both speeds to be equal
		sprite1.setY(100);
		sprite1.forceY(300);
		
		assertFalse(group.isCollide(sprite1, sprite2,
		        sprite1.getDefaultCollisionShape(),
		        sprite2.getDefaultCollisionShape()));
		
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.object.collision.PreciseCollisionGroup#PreciseCollisionGroup()}
	 * .
	 */
	public final void testPreciseCollisionGroup() {
		assertNotNull(group);
		assertTrue(group.isActive());
	}
	
}
