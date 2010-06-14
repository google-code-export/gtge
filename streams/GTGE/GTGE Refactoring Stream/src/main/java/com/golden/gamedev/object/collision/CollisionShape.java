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

import java.io.Serializable;

import com.golden.gamedev.Positionable;

/**
 * <code>CollisionShape</code> interface represents a form of geometric shape
 * that behave as sprite collision area. <code>CollisionShape</code> is able to
 * determine whether its area is intersected with another collision shape area.
 * <p>
 * 
 * This interface that play the role to determine whether two sprites are
 * collided to each other or not using <code>CollisionShape</code>
 * {@linkplain #intersects(CollisionShape)} method. <br />
 * <br />
 * As of GTGE 0.2.4, {@link CollisionShape} instances are {@link Positionable} -
 * they were always able to be positioned via
 * {@link #setBounds(double, double, int, int)} or
 * {@link #setLocation(double, double)}, but now an individual X or Y coordinate
 * may be set directly. <br />
 * <br />
 * {@link CollisionShape} instances should be easily able to be serialized to
 * and retrieved from a byte-stream and hence they were made
 * {@link Serializable} as of 0.2.4 as well.
 * 
 * @version 2.0
 */
public interface CollisionShape extends Dimensionable, Serializable {
	
	/**
	 * Returns whether this collision shape intersects with other collision
	 * shape area.
	 */
	public boolean intersects(CollisionShape shape);
	
	/**
	 * Moves this collision shape to specified location.
	 */
	public void setLocation(double x, double y);
	
	/**
	 * Moves this collision shape by specified delta.
	 */
	public void move(double dx, double dy);
	
	/**
	 * Sets the boundary of this colllision shape to specified boundary.
	 */
	public void setBounds(double x1, double y1, int w1, int h1);
	
	/**
	 * Sets the width of this {@link CollisionShape} instance.
	 * @param width The width of this {@link CollisionShape} instance.
	 * @since 0.2.4
	 */
	void setWidth(final int width);
	
	/**
	 * Sets the height of this {@link CollisionShape} instance.
	 * @param height The height of this {@link CollisionShape} instance.
	 * @since 0.2.4
	 */
	void setHeight(final int height);
}
