/**
 * 
 */
package com.golden.gamedev.object.background;

import java.awt.Rectangle;
import java.io.Serializable;

import com.golden.gamedev.Renderable;
import com.golden.gamedev.Updateable;
import com.golden.gamedev.object.collision.CollisionShape;
import com.golden.gamedev.object.collision.Dimensionable;

/**
 * The {@link Background} interface specifies a {@link Renderable}
 * {@link Updateable} {@link CollisionShape} instance that represents a
 * background. Currently, all {@link Background} instances are also
 * {@link Serializable}, but this may change in the future.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Updateable
 * @see Renderable
 * @see CollisionShape
 * @see Serializable
 * 
 */
public interface Background extends Updateable, Renderable, CollisionShape,
        Serializable {
	
	void setSize(final int width, final int height);
	
	void setToCenter(final Dimensionable center);
	
	Rectangle getClip();
	
	void setClip(final Rectangle rectangle);
	
}
