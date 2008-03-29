/**
 * 
 */
package com.golden.gamedev;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.util.ImageUtil;

/**
 * The <tt>BaseGame</tt> class provides an abstract class with common
 * functionality for creating games in the GTGE framework. <br />
 * <br />
 * The main purpose of this class is to provide a superclass for both
 * {@link Game} and {@link GameObject} to extend, as they previously had much of
 * the exact same functionality. This will avoid code replication, as both will
 * rely upon the methods inherited from this class. <br />
 * <br />
 * The user of GTGE is still required to extend from either {@link GameObject}
 * or {@link Game} to have their game run as part of the GTGE framework, as
 * <tt>BaseGame</tt> is not referenced directly.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Game
 * @see GameObject
 */
public abstract class BaseGame {
	/** Graphics engine. */
	public BaseGraphics bsGraphics;
	/** I/O file engine. */
	public BaseIO bsIO;
	/** Image loader engine. */
	public BaseLoader bsLoader;
	/** Input engine. */
	public BaseInput bsInput;
	/** Timer engine. */
	public BaseTimer bsTimer;
	/** Audio engine for music. */
	public BaseAudio bsMusic;
	/** Audio engine for sound. */
	public BaseAudio bsSound;
	/** Font manager. */
	public GameFontManager fontManager;
	/**
     * Updates game variables.
     * 
     * @see #keyDown(int)
     * @see #keyPressed(int)
     */
    public abstract void update(long elapsedTime);
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyDown(int)
     * bsInput.isKeyDown(int)}.
     */
    public boolean keyDown(int keyCode) {
    	return this.bsInput.isKeyDown(keyCode);
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyPressed(int)
     * bsInput.isKeyPressed(int)}.
     */
    public boolean keyPressed(int keyCode) {
    	return this.bsInput.isKeyPressed(keyCode);
    }
	/**
     * Renders game to the screen.
     * 
     * @param g backbuffer graphics context
     */
    public abstract void render(Graphics2D g);
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
     * bsGraphics.getSize().width}.
     */
    public int getWidth() {
    	return this.bsGraphics.getSize().width;
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
     * bsGraphics.getSize().height}.
     */
    public int getHeight() {
    	return this.bsGraphics.getSize().height;
    }
	/**
     * Returns a new created buffered image which the current game state is
     * rendered into it.
     */
    public BufferedImage takeScreenShot() {
    	BufferedImage screen = ImageUtil.createImage(this.getWidth(), this
    	        .getHeight(), Transparency.OPAQUE);
    	Graphics2D g = screen.createGraphics();
    	this.render(g);
    	g.dispose();
    	
    	return screen;
    }
	/**
     * Captures current game screen into specified file.
     * 
     * @see #takeScreenShot()
     */
    public void takeScreenShot(File f) {
    	ImageUtil.saveImage(this.takeScreenShot(), f);
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseInput#setMouseVisible(boolean)
     * bsInput.setMouseVisible(true)}.
     */
    public void showCursor() {
    	this.bsInput.setMouseVisible(true);
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseLoader#setMaskColor(Color)
     * bsLoader.setMaskColor(java.awt.Color)}.
     */
    public void setMaskColor(Color c) {
    	this.bsLoader.setMaskColor(c);
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseTimer#setFPS(int)
     * bsTimer.setFPS(int)}.
     */
    public void setFPS(int fps) {
    	this.bsTimer.setFPS(fps);
    }
	/**
     * Effectively equivalent to the call
     * {@linkplain com.golden.gamedev.engine.BaseInput#isMousePressed(int)
     * bsInput.isMousePressed(java.awt.event.MouseEvent.BUTTON3)}.
     */
    public boolean rightClick() {
    	return this.bsInput.isMousePressed(MouseEvent.BUTTON3);
    }
}
