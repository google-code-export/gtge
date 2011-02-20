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
import com.golden.gamedev.object.CollisionManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

/**
 * <p>
 * Subclass of <code>CollisionGroup</code> that calculates the precise positions
 * of the <code>Sprite</code>s at the moment of collision. It is suitable for
 * collisions that need the colliding objects to stop rather than vanish.
 * </p>
 * 
 * <p>
 * For example:
 * <ul>
 * <li>Collisions between balls that need to stop or bounce precisely off each
 * other.</li>
 * <li>An object falling to the ground and stopping.</li>
 * <li>Objects that are replaced by something else (such as an explosion
 * effect).</li> &
 * </ul>
 * </p>
 * 
 * <p>
 * This class may not work as expected with concave sprites- such as L-shapes.
 * The position of the collision will be found accurately, but the direction may
 * not be as anticipated as it is based on the <code>CollisionRect</code>s
 * rather than pixel collisions or custom <code>CollisionShape</code>s defined
 * in subclasses of <code>PreciseCollisionGroup</code> If concave sprites are
 * necessary, it might be advisable to break them into groups of smaller convex
 * <code>Sprite</code>s.
 * </p>
 * 
 * @see PlayField#addCollisionGroup(SpriteGroup, SpriteGroup, CollisionManager)
 * 
 */
public abstract class PreciseCollisionGroup extends CollisionGroup {
	
	/***************************************************************************
	 * This is used to test for non-convergence in pixel perfect collision, or
	 * when unusual <code>CollisionShape</code>s are used. The default value is
	 * 0.000001.
	 **************************************************************************/
	protected static double ITERATIVE_BAILOUT = 0.000001;
	
	/***************************************************************************
	 * This is the distance that two objects must be within to be considered
	 * adjacent. When a collision occurs, the <Sprite>s at their reverted
	 * positions are guaranteed to be at least this close. This should be larger
	 * than <code>ITERATIVE_BAILOUT</code>. The default value is 0.0001.
	 **************************************************************************/
	protected static double ADJACENCY_TOLERANCE = 0.0001;
	
	// These are used to test if there was a collision before movement
	// with the iterative method
	private CollisionShape shape3;
	private CollisionShape shape4;
	
	/***************************************************************************
	 * When set true, this <code>PreciseCollisionGroup</code> will send
	 * debugging information to the console.
	 **************************************************************************/
	protected boolean log = false;
	
	/***************************************************************************
	 * Default constructor.
	 **************************************************************************/
	public PreciseCollisionGroup() {
		// sets log to false;
		// set true for debugging
		// log=true;
	}
	
	/**
	 * Performs collision check between Sprite <code>s1</code> and Sprite
	 * <code>s2</code>, and returns true if the sprites (<code>shape1</code>,
	 * <code>shape2</code>) collided.
	 * <p>
	 * 
	 * The revert positions are set precisely by this method.
	 * 
	 * @param firstSprite sprite from group 1
	 * @param secondSprite sprite from group 2
	 * @param firstCollisionShape bounding box of sprite 1
	 * @param secondCollisionShape bounding box of sprite 2
	 * @return <code>true</code> if the sprites is collided one another.
	 */
	public boolean isCollide(Sprite firstSprite, Sprite secondSprite, CollisionShape firstCollisionShape, CollisionShape secondCollisionShape) {
		
		// if (shape1.intersects(shape2)) {
		if ((this.pixelPerfectCollision && CollisionManager.isPixelCollide(
		        firstSprite.getX(), firstSprite.getY(), firstSprite.getImage(),
		        secondSprite.getX(), secondSprite.getY(),
		        secondSprite.getImage()))
		        || (!this.pixelPerfectCollision && firstCollisionShape
		                .intersects(secondCollisionShape))) {
			// basic check to see if collision occurred
			this.sprite1 = firstSprite;
			this.sprite2 = secondSprite;
			this.collisionSide = 0;
			
			// set up collision variables
			
			// this gets the speed
			double firstSpriteSpeedXAxis = firstSprite.getX()
			        - firstSprite.getOldX();
			double firstSpriteSpeedYAxis = firstSprite.getY()
			        - firstSprite.getOldY();
			double secondSpriteSpeedXAxis = secondSprite.getX()
			        - secondSprite.getOldX();
			double secondSpriteSpeedYAxis = secondSprite.getY()
			        - secondSprite.getOldY();
			// now get the bounds for the CollisionShapes
			double x1 = firstCollisionShape.getX() - firstSpriteSpeedXAxis;
			double y1 = firstCollisionShape.getY() - firstSpriteSpeedYAxis;
			double x2 = secondCollisionShape.getX() - secondSpriteSpeedXAxis;
			double y2 = secondCollisionShape.getY() - secondSpriteSpeedYAxis;
			double firstCollisionShapeWidth = firstCollisionShape.getWidth();
			double firstCollisionShapeHeight = firstCollisionShape.getHeight();
			double secondCollisionShapeWidth = secondCollisionShape.getWidth();
			double secondCollisionShapeHeight = secondCollisionShape
			        .getHeight();
			
			if (this.log) {
				System.out.print("Collision (" + firstSprite.getX() + ","
				        + firstSprite.getY() + "),(" + x1 + "," + y1 + ")-->");
			}
			
			// check for collision at old location
			
			if (this.checkCollisionHelper(firstSprite, secondSprite, x1, y1,
			        x2, y2, true)) {// collision
				return handleCollisionWithSpriteCollisionShapes(firstSprite,
				        secondSprite, firstCollisionShape, secondCollisionShape);
				
			} // if overlap
			
			else { // no collision at old location
			
				double tHoriz = 999999.0, tVert = 999999.0; // garbage values
				// that
				// should not be
				// achieved
				int xCollision = -1, yCollision = -1;
				
				if (firstSpriteSpeedXAxis > secondSpriteSpeedXAxis) {// left-to-right
					                                                 // on X
					if (this.log) {
						System.out.print("dx1>dx2-->");
					}
					tHoriz = (x2 - x1 - firstCollisionShapeWidth)
					        / (firstSpriteSpeedXAxis - secondSpriteSpeedXAxis);
					xCollision = CollisionGroup.RIGHT_LEFT_COLLISION;
				}
				else if (secondSpriteSpeedXAxis > firstSpriteSpeedXAxis) { // right-to-left
					                                                       // on
					// X
					if (this.log) {
						System.out.print("dx1<dx2-->");
					}
					tHoriz = (x1 - x2 - secondCollisionShapeWidth)
					        / (secondSpriteSpeedXAxis - firstSpriteSpeedXAxis);
					xCollision = CollisionGroup.LEFT_RIGHT_COLLISION;
				}
				
				if (firstSpriteSpeedYAxis > secondSpriteSpeedYAxis) {// bottom-to-top
					                                                 // on Y
					if (this.log) {
						System.out.print("dy1>dy2-->");
					}
					tVert = (y2 - y1 - firstCollisionShapeHeight)
					        / (firstSpriteSpeedYAxis - secondSpriteSpeedYAxis);
					yCollision = CollisionGroup.BOTTOM_TOP_COLLISION;
				}
				else if (secondSpriteSpeedYAxis > firstSpriteSpeedYAxis) { // top-to-bottom
					                                                       // on
					// Y
					if (this.log) {
						System.out.print("dy1<dy2-->");
					}
					tVert = (y1 - y2 - secondCollisionShapeHeight)
					        / (secondSpriteSpeedYAxis - firstSpriteSpeedYAxis);
					yCollision = CollisionGroup.TOP_BOTTOM_COLLISION;
				}
				
				// completely stationary case should have been dealt with above
				
				double finalT;
				
				if (tHoriz <= tVert) {// X collision happens first
					if (this.log) {
						System.out.print("X " + tHoriz + "-->");
					}
					
					this.collisionSide = xCollision;
					// check to see if this is actual collision or too early.
					// may happen when (for example):
					// ***
					// ^
					// |
					// sss
					// sss
					// sss
					// or similar. If not adjacent at this earliest position,
					// assume other is correct.
					
					if (this.checkAdjacencyHelper(firstSprite, secondSprite, x1
					        + tHoriz * firstSpriteSpeedXAxis, y1 + tHoriz
					        * firstSpriteSpeedYAxis, x2 + tHoriz
					        * secondSpriteSpeedXAxis, y2 + tHoriz
					        * secondSpriteSpeedYAxis, firstSpriteSpeedXAxis,
					        firstSpriteSpeedYAxis, secondSpriteSpeedXAxis,
					        secondSpriteSpeedYAxis, false)) {
						// Yes- X collision is the first real collision
						if (this.log) {
							System.out.print("X " + tHoriz + "-->");
						}
						finalT = tHoriz;
					}
					else {
						// No- X collision is not an actual collision, so use Y
						// collision.
						if (this.log) {
							System.out.print("Y " + tVert + "-->");
						}
						this.collisionSide = yCollision;
						finalT = tVert;
					}
				}
				else {// Y collision happens first
					if (this.log) {
						System.out.print("Y " + tVert + "-->");
					}
					
					this.collisionSide = yCollision;
					// similar check here
					if (this.checkAdjacencyHelper(firstSprite, secondSprite, x1
					        + tVert * firstSpriteSpeedXAxis, y1 + tVert
					        * firstSpriteSpeedYAxis, x2 + tVert
					        * secondSpriteSpeedXAxis, y2 + tVert
					        * secondSpriteSpeedYAxis, firstSpriteSpeedXAxis,
					        firstSpriteSpeedYAxis, secondSpriteSpeedXAxis,
					        secondSpriteSpeedYAxis, false)) {
						// Yes- Y collision is the first real collision
						if (this.log) {
							System.out.print("Y " + tVert + "-->");
						}
						finalT = tVert;
					}
					else {
						// No- Y collision is not an actual collision, so use X
						// collision.
						if (this.log) {
							System.out.print("X " + tHoriz + "-->");
						}
						this.collisionSide = xCollision;
						finalT = tHoriz;
					}
				}
				
				// set revert positions
				// these may be changed later but are
				// correct for simple cases
				
				// these are for the CollisionRect
				this.collisionX1 = x1 + finalT * firstSpriteSpeedXAxis;
				this.collisionY1 = y1 + finalT * firstSpriteSpeedYAxis;
				this.collisionX2 = x2 + finalT * secondSpriteSpeedXAxis;
				this.collisionY2 = y2 + finalT * secondSpriteSpeedYAxis;
				
				// this is sufficient for non-pixel perfect collisions with
				// bounding rectangles
				
				if (this.checkCollisionHelper(firstSprite, secondSprite,
				        this.collisionX1, this.collisionY1, this.collisionX2,
				        this.collisionY2, true)) {
					// still a collision- this occurs if a non-rectangular
					// CollisionShape exists
					// larger than its height and width would suggest.
					
					if (this.log) {
						System.out.print("Iterate (1)-->");
					}
					
					if (this.iterativeMethod(firstSprite, secondSprite, 0.0,
					        finalT, x1, y1, x2, y2, firstSpriteSpeedXAxis,
					        firstSpriteSpeedYAxis, secondSpriteSpeedXAxis,
					        secondSpriteSpeedYAxis)) {
						// collision occurred- collision positions set in
						// iterativeMethod()
						// correct them because these are for the rect, not the
						// sprite
						this.collisionX1 = this.collisionX1 - x1
						        + firstSprite.getOldX();
						this.collisionY1 = this.collisionY1 - y1
						        + firstSprite.getOldY();
						this.collisionX2 = this.collisionX2 - x2
						        + secondSprite.getOldX();
						this.collisionY2 = this.collisionY2 - y2
						        + secondSprite.getOldY();
						
						if (this.log) {
							System.out.println("true: " + this.collisionSide
							        + " (" + this.collisionX1 + ","
							        + this.collisionY1 + ")");
						}
						return true;
					}
					else {
						// no actual collision (due to custom collisionShapes)
						if (this.log) {
							System.out.println("false");
						}
						return false;
					}
				}
				
				else if (this.checkAdjacencyHelper(firstSprite, secondSprite,
				        this.collisionX1, this.collisionY1, this.collisionX2,
				        this.collisionY2, firstSpriteSpeedXAxis,
				        firstSpriteSpeedYAxis, secondSpriteSpeedXAxis,
				        secondSpriteSpeedYAxis, true)) {
					// this occurs when regular bounding boxes are used. Nothing
					// more needs to be done.
					// need to correct collision?? positions because these are
					// for the rect, not the sprite
					this.collisionX1 = this.collisionX1 - x1
					        + firstSprite.getOldX();
					this.collisionY1 = this.collisionY1 - y1
					        + firstSprite.getOldY();
					this.collisionX2 = this.collisionX2 - x2
					        + secondSprite.getOldX();
					this.collisionY2 = this.collisionY2 - y2
					        + secondSprite.getOldY();
					
					if (this.log) {
						System.out.println("true: " + this.collisionSide + " ("
						        + this.collisionX1 + "," + this.collisionY1
						        + ")");
					}
					
					return true;
					
				}
				
				else {
					// this occurs when the bouding shape is smaller than its
					// width and height
					// would suggest,
					// or with pixel perfect collision.
					
					if (this.log) {
						System.out.print("Iterate (2)-->");
					}
					
					if (this.iterativeMethod(firstSprite, secondSprite, finalT,
					        1.0, x1, y1, x2, y2, firstSpriteSpeedXAxis,
					        firstSpriteSpeedYAxis, secondSpriteSpeedXAxis,
					        secondSpriteSpeedYAxis)) {
						// collision occurred- collision positions set in
						// iterativeMethod()
						// correct them because these are for the rect, not the
						// sprite
						this.collisionX1 = this.collisionX1 - x1
						        + firstSprite.getOldX();
						this.collisionY1 = this.collisionY1 - y1
						        + firstSprite.getOldY();
						this.collisionX2 = this.collisionX2 - x2
						        + secondSprite.getOldX();
						this.collisionY2 = this.collisionY2 - y2
						        + secondSprite.getOldY();
						
						if (this.log) {
							System.out.println("true: " + this.collisionSide
							        + " (" + this.collisionX1 + ","
							        + this.collisionY1 + ")");
						}
						return true;
					}
					else {
						// no actual collision due to small collisionShape or
						// transparent pixels
						if (this.log) {
							System.out.println("false");
						}
						return false;
					}
				}
				
			}// no overlap
		} // no collision
		return false;
	} // end of method
	
	/**
	 * Handles the case of a detected collision with either the provided
	 * {@link CollisionShape} instances or (in the case of pixel-perfect
	 * collision detection) that a pixel-perfect collision was detected.
	 * 
	 * @param firstSprite The first {@link Sprite} to be handled when a
	 *        collision is detected.
	 * @param secondSprite The second {@link Sprite} to be handled when a
	 *        collision is detected.
	 * @param firstCollisionShape The first {@link CollisionShape} indicating
	 *        the bounds of collision of the first {@link Sprite}.
	 * @param secondCollisionShape The second {@link CollisionShape} indicating
	 *        the bounds of collision of the second {@link Sprite}.
	 * @return True if the sprites were considered to have collided, false
	 *         otherwise - checking the {@link #getCollisionSide() collision
	 *         side} when true is returned will return the side of the collision
	 *         relative to the first {@link Sprite}.
	 */
	private boolean handleCollisionWithSpriteCollisionShapes(Sprite firstSprite, Sprite secondSprite, CollisionShape firstCollisionShape, CollisionShape secondCollisionShape) {
		if (this.log) {
			System.out.print("Overlap->");
		}
		
		this.collisionSide = 0;
		
		final double firstSpriteSpeedXAxis = firstSprite.getX()
		        - firstSprite.getOldX();
		final double firstSpriteSpeedYAxis = firstSprite.getY()
		        - firstSprite.getOldY();
		final double secondSpriteSpeedXAxis = secondSprite.getX()
		        - secondSprite.getOldX();
		final double secondSpriteSpeedYAxis = secondSprite.getY()
		        - secondSprite.getOldY();
		
		if (objectsAreStationary(firstSpriteSpeedXAxis, firstSpriteSpeedYAxis,
		        secondSpriteSpeedXAxis, secondSpriteSpeedYAxis)) {
			if (this.log) {
				System.out.println("Both stationary");
			}
			// Since they did not move, a collision is not detected - only
			// active collisions are considered.
			return false;
		}
		else {
			// find distances to move (based on default collision
			// shapes)
			// this might need to be changed to use the iterative method
			// if this behaviour should respect pixel perfection
			final double distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite = firstCollisionShape
			        .getX()
			        - secondCollisionShape.getX()
			        + firstCollisionShape.getWidth();
			
			final double distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite = secondCollisionShape
			        .getX()
			        - firstCollisionShape.getX()
			        + secondCollisionShape.getWidth();
			
			final double distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite = firstCollisionShape
			        .getY()
			        - secondCollisionShape.getY()
			        + firstCollisionShape.getHeight();
			
			final double distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite = secondCollisionShape
			        .getY()
			        - firstCollisionShape.getY()
			        + secondCollisionShape.getHeight();
			
			final double minimumDistanceBetweenSpriteSides = Math
			        .min(Math
			                .min(distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite,
			                        distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite),
			                Math.min(
			                        distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite,
			                        distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite));
			
			Sprite spriteWhichInitiatedCollision = secondSprite;
			
			// The collided sprite is the sprite which has the greater sum of
			// the squares of the speeds between the two sprites. If they are
			// equal, the second
			// sprite will be assumed to be the one that initiated the collision
			if (Math.pow(firstSpriteSpeedXAxis, 2)
			        + Math.pow(firstSpriteSpeedYAxis, 2) > Math.pow(
			        secondSpriteSpeedXAxis, 2)
			        + Math.pow(secondSpriteSpeedYAxis, 2)) {
				spriteWhichInitiatedCollision = firstSprite;
			}
			
			if (this.log) {
				System.out.print(spriteWhichInitiatedCollision + "-->");
			}
			
			if (spriteWhichInitiatedCollision == firstSprite) {
				adjustCollisionFieldsWhenFirstSpriteInitiatesCollision(
				        firstSprite,
				        secondSprite,
				        distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite,
				        distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite,
				        distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite,
				        distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite,
				        minimumDistanceBetweenSpriteSides);
			}
			else {
				adjustCollisionFieldsWhenSecondSpriteInitiatesCollision(
				        firstSprite,
				        secondSprite,
				        distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite,
				        distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite,
				        distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite,
				        distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite,
				        minimumDistanceBetweenSpriteSides);
			}
			if (this.log) {
				System.out.println("Corrected");
			}
			return true;
			
		}
	}
	
	/**
	 * @param firstSprite
	 * @param secondSprite
	 * @param distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite
	 * @param distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite
	 * @param distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite
	 * @param distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite
	 * @param minimumDistanceBetweenSpriteSides
	 */
	private void adjustCollisionFieldsWhenSecondSpriteInitiatesCollision(Sprite firstSprite, Sprite secondSprite, final double distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite, final double distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite, final double distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite, final double distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite, final double minimumDistanceBetweenSpriteSides) {
		this.collisionX1 = firstSprite.getX();
		this.collisionY1 = firstSprite.getY();
		if (minimumDistanceBetweenSpriteSides == distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite) {
			this.collisionX2 = secondSprite.getX()
			        - distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite;
			this.collisionY2 = secondSprite.getY();
			this.collisionSide = CollisionGroup.LEFT_RIGHT_COLLISION;
		}
		else if (minimumDistanceBetweenSpriteSides == distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite) {
			this.collisionX2 = secondSprite.getX()
			        + distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite;
			this.collisionY2 = secondSprite.getY();
			this.collisionSide = CollisionGroup.RIGHT_LEFT_COLLISION;
		}
		else if (minimumDistanceBetweenSpriteSides == distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite) {
			this.collisionX2 = secondSprite.getX();
			this.collisionY2 = secondSprite.getY()
			        - distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite;
			this.collisionSide = CollisionGroup.TOP_BOTTOM_COLLISION;
		}
		else {
			this.collisionX2 = secondSprite.getX();
			this.collisionY2 = secondSprite.getY()
			        + distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite;
			this.collisionSide = CollisionGroup.BOTTOM_TOP_COLLISION;
		}
	}
	
	/**
	 * @param firstSprite
	 * @param secondSprite
	 * @param distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite
	 * @param distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite
	 * @param distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite
	 * @param distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite
	 * @param minimumDistanceBetweenSpriteSides
	 */
	private void adjustCollisionFieldsWhenFirstSpriteInitiatesCollision(Sprite firstSprite, Sprite secondSprite, final double distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite, final double distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite, final double distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite, final double distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite, final double minimumDistanceBetweenSpriteSides) {
		this.collisionX2 = secondSprite.getX();
		this.collisionY2 = secondSprite.getY();
		if (minimumDistanceBetweenSpriteSides == distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite) {
			this.collisionX1 = firstSprite.getX()
			        - distanceBetweenRightSideOfFirstSpriteAndLeftSideOfSecondSprite;
			this.collisionY1 = firstSprite.getY();
			this.collisionSide = CollisionGroup.RIGHT_LEFT_COLLISION;
		}
		else if (minimumDistanceBetweenSpriteSides == distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite) {
			this.collisionX1 = firstSprite.getX()
			        + distanceBetweenLeftSideOfFirstSpriteAndRightSideOfSecondSprite;
			this.collisionY1 = firstSprite.getY();
			this.collisionSide = CollisionGroup.LEFT_RIGHT_COLLISION;
		}
		else if (minimumDistanceBetweenSpriteSides == distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite) {
			this.collisionX1 = firstSprite.getX();
			this.collisionY1 = firstSprite.getY()
			        - distanceBetweenBottomSideOfFirstSpriteAndTopSideOfSecondSprite;
			this.collisionSide = CollisionGroup.BOTTOM_TOP_COLLISION;
		}
		else {
			this.collisionX1 = firstSprite.getX();
			this.collisionY1 = firstSprite.getY()
			        + distanceBetweenTopSideOfFirstSpriteAndBottomSideOfSecondSprite;
			this.collisionSide = CollisionGroup.TOP_BOTTOM_COLLISION;
		}
	}
	
	/**
	 * Gets whether or not two objects with velocities are stationary due to
	 * their speeds.
	 * @param firstObjectSpeedXAxis The speed of the first object relative to
	 *        the X-axis (right/left speed).
	 * @param firstObjectSpeedYAxis The speed of the first object relative to
	 *        the Y-axis (up/down speed).
	 * @param secondObjectSpeedXAxis The speed of the second object relative to
	 *        the X-axis (right/left speed).
	 * @param secondObjectSpeedYAxis The speed of the second object relative to
	 *        the Y-axis (up/down speed).
	 * @return True if the given speeds represent that both objects are
	 *         stationary, false otherwise.
	 */
	private static boolean objectsAreStationary(double firstObjectSpeedXAxis, double firstObjectSpeedYAxis, double secondObjectSpeedXAxis, double secondObjectSpeedYAxis) {
		return firstObjectSpeedXAxis == 0 && firstObjectSpeedYAxis == 0
		        && secondObjectSpeedXAxis == 0 && secondObjectSpeedYAxis == 0;
	}
	
	/**
	 * 
	 * @deprecated The flag includePixelPerfect is redundant - use the alternate
	 *             version
	 *             {@link #checkCollisionHelper(Sprite, Sprite, double, double, double, double)}
	 *             without this flag (
	 *             {@link BasicCollisionGroup#pixelPerfectCollision} triggers
	 *             this condition without the redundant additional flag).
	 */
	protected boolean checkCollisionHelper(Sprite s1, Sprite s2, double x1, double y1, double x2, double y2, boolean includePixelPerfect) {
		if (!includePixelPerfect) {
			// check using normal method
			this.shape3 = this.getCollisionShape1(s1);
			this.shape3.setLocation(x1, y1);
			this.shape4 = this.getCollisionShape2(s2);
			this.shape4.setLocation(x2, y2);
			return this.shape3.intersects(this.shape4);
		}
		else {
			return checkCollisionHelper(s1, s2, x1, y1, x2, y2);
		}
	}
	
	protected boolean checkCollisionHelper(Sprite s1, Sprite s2, double x1, double y1, double x2, double y2) {
		
		if (this.pixelPerfectCollision) {
			return CollisionManager.isPixelCollide(x1, y1, s1.getImage(), x2,
			        y2, s2.getImage());
		}
		else {// check using normal method
			this.shape3 = this.getCollisionShape1(s1);
			this.shape3.setLocation(x1, y1);
			this.shape4 = this.getCollisionShape2(s2);
			this.shape4.setLocation(x2, y2);
			return this.shape3.intersects(this.shape4);
		}
	}
	
	// This checks for adjacency
	protected boolean checkAdjacencyHelper(Sprite s1, Sprite s2, double x1, double y1, double x2, double y2, double dx1, double dy1, double dx2, double dy2, boolean includePixelPerfect) {
		
		// set up offsets for adjacency
		double dx = 0, dy = 0;
		if (dx1 - dx2 < 0) {
			dx = -PreciseCollisionGroup.ADJACENCY_TOLERANCE;
		}
		else if (dx1 - dx2 > 0) {
			dx = PreciseCollisionGroup.ADJACENCY_TOLERANCE;
		}
		if (dy1 - dy2 < 0) {
			dy = -PreciseCollisionGroup.ADJACENCY_TOLERANCE;
		}
		else if (dy1 - dy2 > 0) {
			dy = PreciseCollisionGroup.ADJACENCY_TOLERANCE;
		}
		
		if (includePixelPerfect && this.pixelPerfectCollision) {
			return CollisionManager.isPixelCollide(x1 + dx, y1 + dy,
			        s1.getImage(), x2, y2, s2.getImage());
		}
		else {// check using default collision shapes
			this.shape3 = this.getCollisionShape1(s1);
			this.shape3.setLocation(x1 + dx, y1 + dy);
			this.shape4 = this.getCollisionShape2(s2);
			this.shape4.setLocation(x2, y2);
			// if(log)
			// System.out.print(shape3.getX()+","+shape3.getWidth()+","+shape3.getY()+","+shape3.getHeight()+"-"+shape4.getX()+","+shape4.getWidth()+","+shape4.getY()+","+shape4.getHeight()+"-->");
			// if(log) System.out.print(shape3.intersects(shape4)+"-->");
			return this.shape3.intersects(this.shape4);
		}
	}
	
	// iterates to find the just pre-collision position.
	
	protected boolean iterativeMethod(Sprite s1, Sprite s2, double lowerT, double higherT, double oldX1, double oldY1, double oldX2, double oldY2, double speedX1, double speedY1, double speedX2, double speedY2) {
		// set up working t
		double workingT = (lowerT + higherT) / 2;
		
		// set up candidate positions
		double curX1, curY1, curX2, curY2;
		
		// set up fastest speed- this is used for the bailout condition.
		double maxSpeed = Math.max(
		        Math.max(Math.abs(speedX1), Math.abs(speedY1)),
		        Math.max(Math.abs(speedX2), Math.abs(speedY2)));
		
		while (true) {
			
			// find current candidate position
			curX1 = oldX1 + workingT * speedX1;
			curY1 = oldY1 + workingT * speedY1;
			curX2 = oldX2 + workingT * speedX2;
			curY2 = oldY2 + workingT * speedY2;
			
			if (this.checkCollisionHelper(s1, s2, curX1, curY1, curX2, curY2,
			        true)) {
				// collided- need a lower t.
				higherT = workingT;
				workingT = (workingT + lowerT) / 2;
				
				if ((higherT - lowerT) * maxSpeed < PreciseCollisionGroup.ITERATIVE_BAILOUT) {
					// got too small without avoiding collision
					// this should not happen- should be caught
					// in overlapping code
					System.err.println("Iterative failure-- too close");
					break;
				}
			}
			else if (this.checkAdjacencyHelper(s1, s2, curX1, curY1, curX2,
			        curY2, speedX1, speedY1, speedX2, speedY2, true)) {
				// not collided but adjacent- good enough.
				// extra check here to counter the fact that the iterative
				// method
				// may find a solution the wrong side of its starting point
				// when stationary
				this.collisionX1 = Math.abs(curX1 - oldX1) > 2 * PreciseCollisionGroup.ADJACENCY_TOLERANCE ? curX1
				        : oldX1;
				this.collisionY1 = Math.abs(curY1 - oldY1) > 2 * PreciseCollisionGroup.ADJACENCY_TOLERANCE ? curY1
				        : oldY1;
				this.collisionX2 = Math.abs(curX2 - oldX2) > 2 * PreciseCollisionGroup.ADJACENCY_TOLERANCE ? curX2
				        : oldX2;
				this.collisionY2 = Math.abs(curY2 - oldY2) > 2 * PreciseCollisionGroup.ADJACENCY_TOLERANCE ? curY2
				        : oldY2;
				
				return true;
			}
			else {
				// not adjacent- need a higher t.
				
				lowerT = workingT;
				workingT = (workingT + higherT) / 2;
				
				if ((higherT - lowerT) * maxSpeed < PreciseCollisionGroup.ITERATIVE_BAILOUT) {
					// got too large without achieving adjacency
					// this occurs when no collision actually
					// took place.
					break;
				}
			}
		}
		
		return false; // default return- no collision
		
	}
	
}
