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

import com.golden.gamedev.object.Sprite;

/**
 * The <tt>CollisionShape</tt> interface represents a "bounding box" that
 * behaves as a collision area for a two-dimensional rectangular object, such as
 * a {@link Sprite}. <br />
 * <br />
 * <tt>CollisionShape</tt> instances are required to have a position, height
 * and width, the ability to set their bounds in numerous ways, and most
 * importantly, the ability to detect whether or not an
 * {@link #intersects(CollisionShape) intersection} occurred with another
 * <tt>CollisionShape</tt> instance. The {@link #intersects(CollisionShape)}
 * method is a quick way to see if a <tt>CollisionShape</tt> instance has
 * collided with another instance of <tt>CollisionShape</tt>, however, it is
 * not necessarily perfect, as the <tt>CollisionShape</tt> instance may
 * encompass more area than the underlying object represented with its
 * <tt>CollisionShape</tt> instance.
 * 
 * @since 0.2.3
 * @version 1.1
 */
public interface CollisionShape {
	
	/**
	 * Returns true if this <tt>CollisionShape</tt> instance intersects with
	 * the given <tt>CollisionShape</tt> instance, false otherwise.
	 * @return True if this <tt>CollisionShape</tt> instance intersects with
	 *         the given <tt>CollisionShape</tt> instance, false otherwise.
	 */
	public boolean intersects(CollisionShape shape);
	
	// TODO: refactor this and get rid of the doubles, only use ints for
	// collision of pixels.
	/**
	 * Moves this <tt>CollisionShape</tt> instance to the specified position.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        <tt>CollisionShape</tt> instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        <tt>CollisionShape</tt> instance.
	 */
	public void setLocation(double x, double y);
	
	// TODO: refactor this and get rid of the doubles, only use ints for
	// collision of pixels.
	/**
	 * Moves this <tt>CollisionShape</tt> instance by the specified offsets.
	 * @param offsetX The offset to add to this <tt></tt> instance's
	 *        {@link #getX() x-coordinate position}.
	 * @param offsetY The offset to add to this <tt></tt> instance's
	 *        {@link #getY() y-coordinate position}.
	 */
	public void move(double offsetX, double offsetY);
	
	// TODO: refactor this and get rid of the doubles, only use ints for
	// collision of pixels.
	/**
	 * Sets the bounds of this <tt>CollisionShape</tt> instance to the
	 * specified parameters.
	 * @param x The {@link #getX() x-coordinate position} of this
	 *        <tt>CollisionShape</tt> instance.
	 * @param y The {@link #getY() y-coordinate position} of this
	 *        <tt>CollisionShape</tt> instance.
	 * @param width The {@link #getWidth() width} of this
	 *        <tt>CollisionShape</tt> instance.
	 * @param height The {@link #getHeight() height} of this
	 *        <tt>CollisionShape</tt> instance.
	 */
	public void setBounds(double x, double y, int width, int height);
	
	// TODO: refactor this and get rid of this method, replace with a method
	// returning an integer.
	/**
	 * Returns the <tt>x</tt> coordinate position of this
	 * <tt>CollisionShape</tt> instance.
	 * @return The <tt>x</tt> coordinate position of this
	 *         <tt>CollisionShape</tt> instance.
	 */
	public double getX();
	
	// TODO: refactor this and get rid of this method, replace with a method
	// returning an integer.
	/**
	 * Returns the <tt>y</tt> coordinate position of this
	 * <tt>CollisionShape</tt> instance.
	 * @return The <tt>y</tt> coordinate position of this
	 *         <tt>CollisionShape</tt> instance.
	 */
	public double getY();
	
	/**
	 * Returns the width of this <tt>CollisionShape</tt> instance.
	 * @return The width of this <tt>CollisionShape</tt> instance.
	 */
	public int getWidth();
	
	/**
	 * Returns the height of this <tt>CollisionShape</tt> instance.
	 * @return The height of this <tt>CollisionShape</tt> instance.
	 */
	public int getHeight();
	
}
