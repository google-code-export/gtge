package com.golden.gamedev.object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Comparator;

import com.golden.gamedev.object.collision.CollisionShape;

public interface BaseSprite {
	
	/**
	 * Associates specified background with this sprite.
	 */
	public void setBackground(Background backgr);
	
	/**
	 * Returns the background where this sprite lived.
	 */
	public Background getBackground();
	
	/**
	 * Returns the image of this sprite.
	 */
	public BufferedImage getImage();
	
	/**
	 * Sets specified image as this sprite image.
	 */
	public void setImage(BufferedImage image);
	
	/**
	 * Returns the width of this sprite.
	 */
	public int getWidth();
	
	/**
	 * Returns the height of this sprite.
	 */
	public int getHeight();
	
	/**
	 * Returns default {@linkplain #defaultCollisionShape collision shape}, can
	 * be used along with collision manager.
	 */
	public CollisionShape getDefaultCollisionShape();
	
	/**
	 * Attempts to move this sprite to specified <code>xs</code>,
	 * <code>ys</code> location, if the sprite is right on specified location,
	 * this method will return true.
	 * <p>
	 * 
	 * For example :
	 * 
	 * <pre>
	 *    Sprite s;
	 *    public void update(long elapsedTime) {
	 *       // move sprite to 100, 100 with speed 0.1 pixel in a millisecond
	 *       if (s.moveTo(elapsedTime, 100, 100, 0.1) {
	 *          // sprite has arrived to 100, 100
	 *       }
	 *    }
	 * </pre>
	 */
	public boolean moveTo(long elapsedTime, double xs, double ys, double speed);
	
	/**
	 * Sets this sprite coordinate to specified location on the background.
	 */
	public void setLocation(double xs, double ys);
	
	/**
	 * Moves this sprite as far as delta x (dx) and delta y (dy).
	 */
	public void move(double dx, double dy);
	
	/**
	 * Moves sprite <code>x</code> coordinate as far as delta x (dx).
	 */
	public void moveX(double dx);
	
	/**
	 * Moves sprite <code>y</code> coordinate as far as delta y (dy).
	 */
	public void moveY(double dy);
	
	/**
	 * Sets sprite <code>x</code> coordinate.
	 */
	public void setX(double xs);
	
	/**
	 * Sets sprite <code>y</code> coordinate.
	 */
	public void setY(double ys);
	
	/**
	 * Forces sprite <code>x</code> position to specified coordinate.
	 * <p>
	 * 
	 * The difference between {@link #setX(double)} with this method : <br>
	 * <code>setX(double)</code> changes the sprite old position (oldX = xs),
	 * while using <code>forceX(double)</code> <b>the old position is n ot
	 * changed</b>.
	 * <p>
	 * 
	 * This method is used on collision check to move the sprite, but still keep
	 * the sprite old position value.
	 */
	public void forceX(double xs);
	
	/**
	 * Forces sprite <code>y</code> position to specified coordinate.
	 * <p>
	 * 
	 * The difference between {@link #setY(double)} with this method : <br>
	 * <code>setY(double)</code> changes the sprite old position (oldY = ys),
	 * while using <code>forceY(double)</code> <b>the old position is n ot
	 * changed</b>.
	 * <p>
	 * 
	 * This method is used on collision check to move the sprite, but still keep
	 * the sprite old position value.
	 */
	public void forceY(double ys);
	
	/**
	 * Returns sprite <code>x</code> coordinate.
	 */
	public double getX();
	
	/**
	 * Returns sprite <code>y</code> coordinate.
	 */
	public double getY();
	
	/**
	 * Returns sprite <code>x</code> coordinate before the sprite moving to
	 * the current position.
	 */
	public double getOldX();
	
	/**
	 * Returns sprite <code>y</code> coordinate before the sprite moving to
	 * the current position.
	 */
	public double getOldY();
	
	/**
	 * Sets the speed of this sprite, the speed is based on actual time in
	 * milliseconds, 1 means the sprite is moving as far as 1000 (1x1000ms)
	 * pixels in a second. Negative value (-1) means the sprite moving backward.
	 */
	public void setSpeed(double vx, double vy);
	
	/**
	 * Sets horizontal speed of the sprite, the speed is based on actual time in
	 * milliseconds, 1 means the sprite is moving as far as 1000 (1x1000ms)
	 * pixels in a second to the right, while negative value (-1) means the
	 * sprite is moving to the left.
	 */
	public void setHorizontalSpeed(double vx);
	
	/**
	 * Sets vertical speed of the sprite, the speed is based on actual time in
	 * milliseconds, 1 means the sprite is moving as far as 1000 (1x1000ms)
	 * pixels in a second to the bottom, while negative value (-1) means the
	 * sprite is moving to the top.
	 */
	public void setVerticalSpeed(double vy);
	
	/**
	 * Moves sprite with specified angle, and speed.
	 * <p>
	 * 
	 * The angle is as followed:
	 * 
	 * <pre>
	 *   0�   : moving to top (12 o'clock)
	 *   90�  : moving to right (3 o'clock)
	 *   180� : moving to bottom (6 o'clock)
	 *   270� : moving to left (9 o'clock)
	 * </pre>
	 */
	public void setMovement(double speed, double angleDir);
	
	/**
	 * Accelerates sprite horizontal speed by <code>accel</code> and limit the
	 * speed to <code>maxSpeed</code>.
	 * <p>
	 * 
	 * This is used to create momentum speed (slowly increase/decrease the
	 * sprite speed).
	 * <p>
	 * 
	 * For example :
	 * 
	 * <pre>
	 * Sprite s;
	 * 
	 * public void update(long elapsedTime) {
	 * 	// accelerate sprite speed by 0.002
	 * 	// and limit the maximum speed to 4
	 * 	// moving right
	 * 	s.addHorizontalSpeed(elapsedTime, 0.002, 4);
	 * 	// moving left
	 * 	s.addHorizontalSpeed(elapsedTime, -0.002, -4);
	 * 	// momentum stop
	 * 	s.addHorizontalSpeed(elapsedTime, (s.getHorizontalSpeed() &gt; 0) ? -0.002
	 * 	        : 0.002, 0);
	 * }
	 * </pre>
	 */
	public void addHorizontalSpeed(long elapsedTime, double accel, double maxSpeed);
	
	/**
	 * Accelerates sprite vertical speed by <code>accel</code> and limit the
	 * speed to <code>maxSpeed</code>.
	 * <p>
	 * 
	 * This is used to create momentum speed (slowly increase/decrease the
	 * sprite speed).
	 * <p>
	 * 
	 * For example :
	 * 
	 * <pre>
	 * Sprite s;
	 * 
	 * public void update(long elapsedTime) {
	 * 	// accelerate sprite speed by 0.002
	 * 	// and limit the maximum speed to 4
	 * 	// moving down
	 * 	s.addVerticalSpeed(elapsedTime, 0.002, 4);
	 * 	// moving up
	 * 	s.addVerticalSpeed(elapsedTime, -0.002, -4);
	 * 	// momentum stop
	 * 	s.addVerticalSpeed(elapsedTime,
	 * 	        (s.getVerticalSpeed() &gt; 0) ? -0.002 : 0.002, 0);
	 * }
	 * </pre>
	 */
	public void addVerticalSpeed(long elapsedTime, double accel, double maxSpeed);
	
	/**
	 * Returns horizontal speed of the sprite.
	 * <p>
	 * 
	 * Positive means the sprite is moving to right, and negative means the
	 * sprite is moving to left.
	 */
	public double getHorizontalSpeed();
	
	/**
	 * Returns vertical speed of the sprite.
	 * <p>
	 * 
	 * Positive means the sprite is moving to bottom, and negative means the
	 * sprite is moving to top.
	 */
	public double getVerticalSpeed();
	
	/**
	 * Returns sprite <code>x</code> coordinate relative to screen area.
	 */
	public double getScreenX();
	
	/**
	 * Returns sprite <code>y</code> coordinate relative to screen area.
	 */
	public double getScreenY();
	
	/**
	 * Returns the center of the sprite in <code>x</code> coordinate (x +
	 * (width/2)).
	 */
	public double getCenterX();
	
	/**
	 * Returns the center of the sprite in <code>y</code> coordinate (y +
	 * (height/2)).
	 */
	public double getCenterY();
	
	/**
	 * Returns whether the screen is still on background screen area in
	 * specified offset.
	 */
	public boolean isOnScreen(int leftOffset, int topOffset, int rightOffset, int bottomOffset);
	
	/**
	 * Returns whether the screen is still on background screen area.
	 */
	public boolean isOnScreen();
	
	/**
	 * Updates this sprite.
	 */
	public void update(long elapsedTime);
	
	/**
	 * Renders this sprite to specified graphics context.
	 * 
	 * @param g graphics context
	 */
	public void render(Graphics2D g);
	
	/**
	 * Renders sprite image to specified graphics context and specified
	 * location.
	 * 
	 * @param g graphics context
	 * @param x screen x-coordinate
	 * @param y screen y-coordinate
	 */
	public void render(Graphics2D g, int x, int y);
	
	/**
	 * Returns sprite ID, ID is used to mark a sprite from other sprite.
	 */
	public int getID();
	
	/**
	 * Sets sprite ID, ID is used to mark a sprite from other sprite.
	 */
	public void setID(int id);
	
	/**
	 * Returns sprite data ID, ID is used to mark a sprite from other sprite.
	 */
	public Object getDataID();
	
	/**
	 * Sets sprite data ID, ID is used to mark a sprite from other sprite.
	 */
	public void setDataID(Object dataID);
	
	/**
	 * Returns the layer of this sprite.
	 * 
	 * @see #setLayer(int)
	 */
	public int getLayer();
	
	/**
	 * Sets the layer of this sprite.
	 * <p>
	 * 
	 * Layer is used for z-order rendering. Use this along with
	 * {@link PlayField#setComparator(Comparator)} or
	 * {@link SpriteGroup#setComparator(Comparator)} for that purpose.
	 * 
	 * @see #getLayer()
	 */
	public void setLayer(int i);
	
	/**
	 * Returns active state of this sprite.
	 */
	public boolean isActive();
	
	/**
	 * Sets active state of this sprite, only active sprite will be updated and
	 * rendered and check for collision.
	 * <p>
	 * 
	 * Inactive sprite is same as dead sprite, it won't be updated nor rendered,
	 * and only wait to be disposed (if the sprite is not immutable).
	 * <p>
	 * 
	 * The proper way to remove a sprite from the game, is by setting sprite
	 * active state to false (Sprite.setActive(false)).
	 * 
	 * @see #setImmutable(boolean)
	 */
	public void setActive(boolean b);
	
	/**
	 * Returns whether this sprite is immutable or not.
	 */
	public boolean isImmutable();
	
	/**
	 * Sets immutable state of this sprite, immutable sprite means the sprite
	 * won't be removed from its group even though the sprite is not active.
	 * <p>
	 * 
	 * This state is used for optimization by reusing inactive sprite rather
	 * than making new sprite each time.
	 * <p>
	 * 
	 * Usually used for many, small, short live, and frequently used sprites
	 * such as projectile in shooter game. Thus rather than making a new sprite
	 * for every projectile that can cause performance degrade, the inactive
	 * projectiles can be reuse again and again.
	 * <p>
	 * 
	 * <b>WARNING:</b> Immutable sprite won't be disposed by Java garbage
	 * collector until the sprite is manually removed from its group using
	 * {@link com.golden.gamedev.object.SpriteGroup#removeImmutableSprites()}.
	 * 
	 * @see com.golden.gamedev.object.SpriteGroup#getInactiveSprite()
	 * @see com.golden.gamedev.object.SpriteGroup#removeImmutableSprites()
	 * @see #setActive(boolean)
	 */
	public void setImmutable(boolean b);
	
	/**
	 * Returns the distance of this sprite from the specified sprite.
	 * <p>
	 * 
	 * Used this method to check whether the specified sprite is in this sprite
	 * range area or not.
	 * <p>
	 * 
	 * This method can be used for :
	 * <ul>
	 * <li>Determining sprite attack range.</li>
	 * <li>Sprite aura that affecting surrounding unit.</li>
	 * <li>Activating this sprite to chase player whenever the player come
	 * closer to certain distance of this sprite.</li>
	 * </ul>
	 */
	public double getDistance(BaseSprite other);
	
}
