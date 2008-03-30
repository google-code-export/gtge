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
import java.awt.image.BufferedImage;
import java.util.Comparator;

import com.golden.gamedev.object.collision.CollisionRect;
import com.golden.gamedev.object.collision.CollisionShape;

/**
 * <code>Sprite</code> is the object in game that has graphical look and has
 * its own behaviour.
 * <p>
 * 
 * Every sprite is lived in a background, by default sprite is attached to
 * {@linkplain Background#getDefaultBackground default background}, always
 * remember to set the sprite to the game background or use {@link SpriteGroup}
 * class in {@link PlayField} to take care the sprite background set
 * automatically.
 * <p>
 * 
 * Sprite is located somewhere in the background, to set sprite location simply
 * use {@linkplain #setLocation(double, double)}. <br>
 * And to move the sprite use either by moving the sprite directly by using
 * {@linkplain #move(double, double)} or give speed to the sprite by using
 * {@linkplain #setSpeed(double, double)}.
 * <p>
 * 
 * In conjunction with sprite group/playfield, every sprite has active state,
 * this active state that determine whether the sprite is alive or not. Thus to
 * remove a sprite from playfield, simply set the sprite active state to false
 * by using {@linkplain #setActive(boolean) setActive(false)}.
 * <p>
 * 
 * To create sprite behaviour, always use {@link Timer} class utility in order
 * to make the sprite behaviour independent of frame rate.
 * 
 * @see com.golden.gamedev.object.SpriteGroup
 * @see com.golden.gamedev.object.PlayField
 * @see com.golden.gamedev.object.Timer
 */
public class Sprite implements java.io.Serializable, BaseSprite {
	
	// /////// optimization /////////
	// private final Rectangle collisionOffset = new Rectangle(0,0,0,0); //
	// offset collision
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4499098097309229784L;
	
	/** ************************** SPRITE BACKGROUND **************************** */
	
	private Background background;
	
	/** *************************** SPRITE POSITION ***************************** */
	
	private double x, y;
	private double horizontalSpeed, verticalSpeed; // in pixels per millisecond
	private double oldX, oldY; // old position before this sprite moves
	        
	// ///////// optimization ///////////
	private static double screenX, screenY; // screen position = x-background.x
	        
	/** **************************** SPRITE IMAGES ****************************** */
	
	private transient BufferedImage image;
	
	/**
	 * The width of this sprite.
	 */
	protected int width;
	
	/**
	 * The height of this sprite.
	 */
	protected int height;
	
	/**
	 * Default collision shape used in {@link #getDefaultCollisionShape()}, can
	 * be used in along with collision manager.
	 */
	protected CollisionShape defaultCollisionShape = null;
	
	/** **************************** SPRITE FLAGS ******************************* */
	
	private int id; // to differentiate a sprite with another
	private Object dataID;
	
	private int layer; // for layering purpose only
	
	private boolean active = true;
	private boolean immutable; // immutable sprite won't be disposed/thrown
	
	// from its group
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>Sprite</code> with specified image and location.
	 */
	public Sprite(BufferedImage image, double x, double y) {
		// init variables
		this.x = this.oldX = x;
		this.y = this.oldY = y;
		
		// sprite image
		if (image != null) {
			this.image = image;
			this.width = image.getWidth();
			this.height = image.getHeight();
		}
		
		this.background = Background.getDefaultBackground();
	}
	
	/**
	 * Creates new <code>Sprite</code> with specified image and located at (0,
	 * 0).
	 * <p>
	 * 
	 * @see #setLocation(double, double)
	 */
	public Sprite(BufferedImage image) {
		this(image, 0, 0);
	}
	
	/**
	 * Creates new <code>Sprite</code> with specified position and null image.
	 * <p>
	 * 
	 * <b>Note: the image must be set before rendering by using
	 * {@link #setImage(BufferedImage)}.</b>
	 * 
	 * @see #setImage(BufferedImage)
	 */
	public Sprite(double x, double y) {
		this(null, x, y);
	}
	
	/**
	 * Creates new <code>Sprite</code> with null image and located at (0, 0).
	 * <p>
	 * 
	 * <b>Note: the image must be set before rendering by using
	 * {@link #setImage(BufferedImage)}.</b>
	 * 
	 * @see #setImage(BufferedImage)
	 * @see #setLocation(double, double)
	 */
	public Sprite() {
		this(0, 0);
	}
	
	/** ************************************************************************* */
	/** *********************** SPRITE BACKGROUND ******************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setBackground(com.golden.gamedev.object.Background)
     */
	public void setBackground(Background backgr) {
		this.background = backgr;
		if (this.background == null) {
			this.background = Background.getDefaultBackground();
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getBackground()
     */
	public Background getBackground() {
		return this.background;
	}
	
	/** ************************************************************************* */
	/** ************************ IMAGE OPERATION ******************************** */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getImage()
     */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setImage(java.awt.image.BufferedImage)
     */
	public void setImage(BufferedImage image) {
		this.image = image;
		
		this.width = this.height = 0;
		if (image != null) {
			this.width = image.getWidth();
			this.height = image.getHeight();
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getWidth()
     */
	public int getWidth() {
		return this.width;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getHeight()
     */
	public int getHeight() {
		return this.height;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getDefaultCollisionShape()
     */
	public CollisionShape getDefaultCollisionShape() {
		if (this.defaultCollisionShape == null) {
			this.defaultCollisionShape = new CollisionRect();
		}
		
		this.defaultCollisionShape.setBounds(this.getX(), this.getY(), this
		        .getWidth(), this.getHeight());
		
		return this.defaultCollisionShape;
	}
	
	/** ************************************************************************* */
	/** ********************** MOVEMENT OPERATION ******************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#moveTo(long, double, double, double)
     */
	public boolean moveTo(long elapsedTime, double xs, double ys, double speed) {
		if (this.x == xs && this.y == ys) {
			return true;
		}
		
		double angle = 90 + Math
		        .toDegrees(Math.atan2(ys - this.y, xs - this.x));
		double radians = Math.toRadians(angle);
		
		double vx = Math.sin(radians) * speed * elapsedTime, vy = -Math
		        .cos(radians)
		        * speed * elapsedTime;
		
		boolean arriveX = false, arriveY = false;
		
		// checking x coordinate
		if (vx != 0) {
			if (vx > 0) {
				// moving right
				if (this.x + vx >= xs) {
					vx = xs - this.x; // snap
					arriveX = true;
				}
				
			}
			else {
				// moving left
				if (this.x + vx <= xs) {
					vx = xs - this.x; // snap
					arriveX = true;
				}
			}
			
		}
		else if (this.x == xs) {
			arriveX = true;
		}
		
		// checking y coordinate
		if (vy != 0) {
			if (vy > 0) {
				// moving down
				if (this.y + vy >= ys) {
					vy = ys - this.y; // snap
					arriveY = true;
				}
				
			}
			else {
				// moving up
				if (this.y + vy <= ys) {
					vy = ys - this.y; // snap
					arriveY = true;
				}
			}
			
		}
		else if (this.y == ys) {
			arriveY = true;
		}
		
		this.move(vx, vy);
		
		return (arriveX && arriveY);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setLocation(double, double)
     */
	public void setLocation(double xs, double ys) {
		this.oldX = this.x = xs;
		this.oldY = this.y = ys;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#move(double, double)
     */
	public void move(double dx, double dy) {
		if (dx != 0 || dy != 0) {
			this.oldX = this.x;
			this.x += dx;
			this.oldY = this.y;
			this.y += dy;
		}
		
		// if (dx != 0) {
		// oldX = x;
		// x += dx;
		// }
		//
		// if (dy != 0) {
		// oldY = y;
		// y += dy;
		// }
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#moveX(double)
     */
	public void moveX(double dx) {
		if (dx != 0) {
			this.oldX = this.x;
			this.x += dx;
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#moveY(double)
     */
	public void moveY(double dy) {
		if (dy != 0) {
			this.oldY = this.y;
			this.y += dy;
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setX(double)
     */
	public void setX(double xs) {
		this.oldX = this.x = xs;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setY(double)
     */
	public void setY(double ys) {
		this.oldY = this.y = ys;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#forceX(double)
     */
	public void forceX(double xs) {
		this.x = xs;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#forceY(double)
     */
	public void forceY(double ys) {
		this.y = ys;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getX()
     */
	public double getX() {
		return this.x;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getY()
     */
	public double getY() {
		return this.y;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getOldX()
     */
	public double getOldX() {
		return this.oldX;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getOldY()
     */
	public double getOldY() {
		return this.oldY;
	}
	
	/** ************************************************************************* */
	/** ************************* SPEED VARIABLES ******************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setSpeed(double, double)
     */
	public void setSpeed(double vx, double vy) {
		this.horizontalSpeed = vx;
		this.verticalSpeed = vy;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setHorizontalSpeed(double)
     */
	public void setHorizontalSpeed(double vx) {
		this.horizontalSpeed = vx;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setVerticalSpeed(double)
     */
	public void setVerticalSpeed(double vy) {
		this.verticalSpeed = vy;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setMovement(double, double)
     */
	public void setMovement(double speed, double angleDir) {
		// convert degrees to radians
		double radians = Math.toRadians(angleDir);
		
		this.setSpeed(Math.sin(radians) * speed, -Math.cos(radians) * speed);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#addHorizontalSpeed(long, double, double)
     */
	public void addHorizontalSpeed(long elapsedTime, double accel, double maxSpeed) {
		if (accel == 0 || elapsedTime == 0) {
			return;
		}
		
		this.horizontalSpeed += accel * elapsedTime;
		
		if (accel < 0) {
			if (this.horizontalSpeed < maxSpeed) {
				this.horizontalSpeed = maxSpeed;
			}
			
		}
		else {
			if (this.horizontalSpeed > maxSpeed) {
				this.horizontalSpeed = maxSpeed;
			}
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#addVerticalSpeed(long, double, double)
     */
	public void addVerticalSpeed(long elapsedTime, double accel, double maxSpeed) {
		if (accel == 0 || elapsedTime == 0) {
			return;
		}
		
		this.verticalSpeed += accel * elapsedTime;
		
		if (accel < 0) {
			if (this.verticalSpeed < maxSpeed) {
				this.verticalSpeed = maxSpeed;
			}
			
		}
		else {
			if (this.verticalSpeed > maxSpeed) {
				this.verticalSpeed = maxSpeed;
			}
		}
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getHorizontalSpeed()
     */
	public double getHorizontalSpeed() {
		return this.horizontalSpeed;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getVerticalSpeed()
     */
	public double getVerticalSpeed() {
		return this.verticalSpeed;
	}
	
	/** ************************************************************************* */
	/** ******************* OTHER SPRITE POSITION FUNCTIONS ********************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getScreenX()
     */
	public double getScreenX() {
		return this.x - this.background.getX() + this.background.getClip().x;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getScreenY()
     */
	public double getScreenY() {
		return this.y - this.background.getY() + this.background.getClip().y;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getCenterX()
     */
	public double getCenterX() {
		return this.x + (this.width / 2);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getCenterY()
     */
	public double getCenterY() {
		return this.y + (this.height / 2);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#isOnScreen(int, int, int, int)
     */
	public boolean isOnScreen(int leftOffset, int topOffset, int rightOffset, int bottomOffset) {
		Sprite.screenX = this.x - this.background.getX();
		Sprite.screenY = this.y - this.background.getY();
		
		return (Sprite.screenX + this.width > -leftOffset
		        && Sprite.screenY + this.height > -topOffset
		        && Sprite.screenX < this.background.getClip().width
		                + rightOffset && Sprite.screenY < this.background
		        .getClip().height
		        + bottomOffset);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#isOnScreen()
     */
	public boolean isOnScreen() {
		return this.isOnScreen(0, 0, 0, 0);
	}
	
	/** ************************************************************************* */
	/** ************************* UPDATE SPRITE ********************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#update(long)
     */
	public void update(long elapsedTime) {
		this.updateMovement(elapsedTime);
	}
	
	/**
	 * Updates sprite movement.
	 */
	protected void updateMovement(long elapsedTime) {
		this.move(this.horizontalSpeed * elapsedTime, this.verticalSpeed
		        * elapsedTime);
	}
	
	/** ************************************************************************* */
	/** ************************* RENDER SPRITE ********************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#render(java.awt.Graphics2D)
     */
	public void render(Graphics2D g) {
		Sprite.screenX = this.x - this.background.getX();
		Sprite.screenY = this.y - this.background.getY();
		
		// check whether the sprite is still on screen rendering area
		if (Sprite.screenX + this.width <= 0
		        || Sprite.screenY + this.height <= 0
		        || Sprite.screenX > this.background.getClip().width
		        || Sprite.screenY > this.background.getClip().height) {
			return;
		}
		
		Sprite.screenX += this.background.getClip().x;
		Sprite.screenY += this.background.getClip().y;
		
		this.render(g, (int) Sprite.screenX, (int) Sprite.screenY);
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#render(java.awt.Graphics2D, int, int)
     */
	public void render(Graphics2D g, int x, int y) {
		g.drawImage(this.image, x, y, null);
	}
	
	/** ************************************************************************* */
	/** ************************** SPRITE FLAGS ********************************* */
	/** ************************************************************************* */
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getID()
     */
	public int getID() {
		return this.id;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setID(int)
     */
	public void setID(int id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getDataID()
     */
	public Object getDataID() {
		return this.dataID;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setDataID(java.lang.Object)
     */
	public void setDataID(Object dataID) {
		this.dataID = dataID;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getLayer()
     */
	public int getLayer() {
		return this.layer;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setLayer(int)
     */
	public void setLayer(int i) {
		this.layer = i;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#isActive()
     */
	public boolean isActive() {
		return this.active;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setActive(boolean)
     */
	public void setActive(boolean b) {
		this.active = b;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#isImmutable()
     */
	public boolean isImmutable() {
		return this.immutable;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#setImmutable(boolean)
     */
	public void setImmutable(boolean b) {
		this.immutable = true;
	}
	
	/* (non-Javadoc)
     * @see com.golden.gamedev.object.BaseSprite#getDistance(com.golden.gamedev.object.BaseSprite)
     */
	public double getDistance(BaseSprite other) {
		return Math.sqrt(Math.pow(this.getCenterX() - other.getCenterX(), 2)
		        + Math.pow(this.getCenterY() - other.getCenterY(), 2));
	}
	
	// private static int garbagecount = 0;
	// protected void finalize() throws Throwable {
	// System.out.println("Total sprite garbaged = " + (++garbagecount) + " = "
	// + this);
	// super.finalize();
	// }
	
}
