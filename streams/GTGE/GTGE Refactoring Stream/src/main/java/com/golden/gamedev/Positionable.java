package com.golden.gamedev;

/**
 * The {@link Positionable} interface specifies an {@link Object} that has both
 * an X and a Y coordinate value that may be changed or retrieved as a double. <br />
 * <br /> {@link Positionable} instances do not need to be {@link Renderable}, but
 * they often are.<br />
 * Double precision is used for the coordinate position values because although
 * these {@link Positionable} instances are expected, if {@link Renderable}, to
 * be {@link Renderable#render(java.awt.Graphics2D) rendered} to a pixel grid
 * (made up of whole integer pixel values), speed of {@link Positionable}
 * instances may influence their position. To account for speed, it is easier to
 * keep track of the position as a double and cast to int for
 * {@link Renderable#render(java.awt.Graphics2D) rendering} rather than keeping
 * track of overhead from integer division/multiplication and storing the
 * position values as integer values.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Renderable#render(java.awt.Graphics2D)
 * 
 */
public interface Positionable {
	
	/**
	 * Gets the X-coordinate of this {@link Positionable} instance, as a double.
	 * @return The X-coordinate of this {@link Positionable} instance, as a
	 *         double.
	 */
	double getX();
	
	/**
	 * Sets the X-coordinate of this {@link Positionable} instance, as a double.
	 * @param x The X-coordinate of this {@link Positionable} instance, as a
	 *        double.
	 */
	void setX(double x);
	
	/**
	 * Gets the Y-coordinate of this {@link Positionable} instance, as a double.
	 * @return The Y-coordinate of this {@link Positionable} instance, as a
	 *         double.
	 */
	double getY();
	
	/**
	 * Sets the Y-coordinate of this {@link Positionable} instance, as a double.
	 * @param y The Y-coordinate of this {@link Positionable} instance, as a
	 *        double.
	 */
	void setY(double y);
}
