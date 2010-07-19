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
package com.golden.gamedev;

// JFC
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import com.golden.gamedev.game.BaseGame;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.background.Background;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * <code>Game</code> class is <b>Golden T Game Engine (GTGE) core class</b> that
 * initializes all GTGE game engines, wrap the engines up, and setup the basic
 * game frame work to be play on.
 * <p>
 * 
 * Every game is a subclass of <code>Game</code> class. And every subclass of
 * <code>Game</code> class have to do three things :
 * <ul>
 * <li>{@linkplain #initResources() initialize game variables}</li>
 * <li>{@linkplain #update(long) update the game variables}</li>
 * <li>{@linkplain #render(Graphics2D) render the game to the screen}</li>
 * </ul>
 * <p>
 * 
 * How-to-subclass <code>Game</code> class to create a new game : <br>
 * (this is the basic skeleton of every game)
 * 
 * <pre>
 * import java.awt.*;
 * import com.golden.gamedev.*;
 * 
 * public class YourGame extends Game {
 * 	
 * 	public void initResources() {
 * 		// initialize game variables
 * 	}
 * 	
 * 	public void update(long elapsedTime) {
 * 		// update the game variables
 * 	}
 * 	
 * 	public void render(Graphics2D g) {
 * 		// render the game to the screen
 * 	}
 * }
 * 
 * 
 * 
 * 
 * </pre>
 * 
 * <p>
 * 
 * And to launch/init the game use {@link com.golden.gamedev.GameLoader} class :
 * 
 * <pre>
 * import java.awt.*;
 * import com.golden.gamedev.*;
 * 
 * public class YourGame extends Game {
 * 	
 * 	public void initResources() {
 * 	}
 * 	
 * 	public void update(long elapsedTime) {
 * 	}
 * 	
 * 	public void render(Graphics2D g) {
 * 	}
 * 	
 * 	public static void main(String[] args) {
 *          &lt;b&gt;GameLoader game = new GameLoader();&lt;/b&gt;
 *          // init the game with fullscreen mode, 640x480 screen resolution
 *          game.setup(&lt;b&gt;new YourGame()&lt;/b&gt;, new Dimension(640,480), true);
 *          game.start();
 *       }
 * }
 * 
 * 
 * </pre>
 * 
 * <p>
 * 
 * There are two main tasks of <code>Game</code> class that we need to know : <br>
 * <ul>
 * <li>Game class initializes all GTGE game engines and keep the engines
 * reference (named as bsGraphics, bsInput, bsIO, etc). <br>
 * Therefore to change the default engine, do it within Game class game engine
 * initialization in {@link #initEngine()} method.</li>
 * <li>The Game class then provides a direct call to the engines commonly used
 * functions, in other word, wrapping the game engines inside the class. <br>
 * The purpose of this wrapping is to make the game coding more convenient,
 * easier, and simple. <br>
 * Therefore you can call the engines functions directly if you like to.</li>
 * </ul>
 * <p>
 * 
 * @see com.golden.gamedev.GameLoader
 * @see #initEngine()
 */
public abstract class Game extends BaseGame {
	
	/**
	 * Creates new instance of <code>Game</code> class, please <b>see note</b>
	 * below.
	 * <p>
	 * 
	 * Note: <b>Do not</b> make any overloading constructors. All that belong to
	 * constructor (this method) should be put in {@link #initResources()}
	 * method. <b>Leave this method empty and simply do not use constructor!</b>
	 * 
	 * @see #initResources()
	 * @see #update(long)
	 * @see #render(Graphics2D)
	 */
	public Game() {
	}
	
	// TODO: deprecate, but provide a replacement.
	/**
	 * Returns whether the mouse pointer is inside specified screen boundary.
	 */
	public boolean checkPosMouse(int x1, int y1, int x2, int y2) {
		return (this.getMouseX() >= x1 && this.getMouseY() >= y1
		        && this.getMouseX() <= x2 && this.getMouseY() <= y2);
	}
	
	// TODO: deprecate, but provide a replacement.
	/**
	 * Returns sprite in specified playfield that intersected with mouse
	 * pointer, or null if no sprite intersected with mouse pointer.
	 * 
	 * @param field playfield to check its intersection with mouse pointer
	 * @param pixelCheck true, checking the sprite image with pixel precision
	 */
	public Sprite checkPosMouse(PlayField field, boolean pixelCheck) {
		SpriteGroup[] groups = field.getGroups();
		int size = groups.length;
		
		for (int i = 0; i < size; i++) {
			if (groups[i].isActive()) {
				Sprite s = this.checkPosMouse(groups[i], pixelCheck);
				if (s != null) {
					return s;
				}
			}
		}
		
		return null;
	}
	
	// TODO: deprecate, but provide a replacement
	/**
	 * Returns whether the mouse pointer is inside specified sprite boundary.
	 * 
	 * @param sprite sprite to check its intersection with mouse pointer
	 * @param pixelCheck true, checking the sprite image with pixel precision
	 */
	public boolean checkPosMouse(Sprite sprite, boolean pixelCheck) {
		Background bg = sprite.getBackground();
		
		// check whether the mouse is in background clip area
		if (this.getMouseX() < bg.getClip().x
		        || this.getMouseY() < bg.getClip().y
		        || this.getMouseX() > bg.getClip().x + bg.getClip().width
		        || this.getMouseY() > bg.getClip().y + bg.getClip().height) {
			return false;
		}
		
		double mosx = this.getMouseX() + bg.getX() - bg.getClip().x;
		double mosy = this.getMouseY() + bg.getY() - bg.getClip().y;
		
		if (pixelCheck) {
			try {
				return ((sprite.getImage().getRGB((int) (mosx - sprite.getX()),
				        (int) (mosy - sprite.getY())) & 0xFF000000) != 0x00);
			}
			catch (Exception e) {
				return false;
			}
			
		}
		else {
			return (mosx >= sprite.getX() && mosy >= sprite.getY()
			        && mosx <= sprite.getX() + sprite.getWidth() && mosy <= sprite
			        .getY()
			        + sprite.getHeight());
		}
	}
	
	// TODO: deprecate, but provide a replacement
	/**
	 * Returns sprite in specified sprite group that intersected with mouse
	 * pointer, or null if no sprite intersected with mouse pointer.
	 * 
	 * @param group playfield to check its intersection with mouse pointer
	 * @param pixelCheck true, checking the sprite image with pixel precision
	 */
	public Sprite checkPosMouse(SpriteGroup group, boolean pixelCheck) {
		Sprite[] sprites = group.getSprites();
		int size = group.getSize();
		
		for (int i = 0; i < size; i++) {
			if (sprites[i].isActive()
			        && this.checkPosMouse(sprites[i], pixelCheck)) {
				return sprites[i];
			}
		}
		
		return null;
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isMousePressed(int)
	 * bsInput.isMousePressed(java.awt.event.MouseEvent.BUTTON1)}.
	 * @deprecated Use bsInput.isMousePressed(MouseEvent.BUTTON1) directly. This
	 *             method is deprecated and will be removed in GTGE 0.2.5.
	 */
	public boolean click() {
		return this.bsInput.isMousePressed(MouseEvent.BUTTON1);
	}
	
	// TODO: deprecate, but provide a replacement.
	/**
	 * Draws game frame-per-second (FPS) to specified location.
	 */
	public void drawFPS(Graphics2D g, int x, int y) {
		this.fontManager.getFont("FPS Font").drawString(g,
		        "FPS = " + this.getCurrentFPS() + "/" + this.getFPS(), x, y);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#getCurrentFPS()
	 * bsTimer.getCurrentFPS()}.
	 * @deprecated Use bsTimer.getCurrentFPS() directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public int getCurrentFPS() {
		return this.bsTimer.getCurrentFPS();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#getFPS()}.
	 * @deprecated Use bsTimer.getFPS() directly. This method is deprecated and
	 *             will be removed in GTGE 0.2.5 with no direct replacement.
	 */
	public int getFPS() {
		return this.bsTimer.getFPS();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
	 * bsGraphics.getSize().height}.
	 * @deprecated Use bsGraphics.getSize().height directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public int getHeight() {
		return this.bsGraphics.getSize().height;
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImage(String)
	 * bsLoader.getImage(String)}
	 * @deprecated Use bsLoader.getImage(String) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public BufferedImage getImage(String imagefile) {
		return this.bsLoader.getImage(imagefile);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImage(String, boolean)
	 * bsLoader.getImage(String, boolean)}.
	 * @deprecated Use bsLoader.getImage(String, boolean) directly. This method
	 *             is deprecated and will be removed in GTGE 0.2.5.
	 */
	public BufferedImage getImage(String imagefile, boolean useMask) {
		return this.bsLoader.getImage(imagefile, useMask);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImages(String, int, int)
	 * bsLoader.getImages(String, int, int)}.
	 * @deprecated Use bsLoader.getImages(String, int, int) directly. This
	 *             method is deprecated and will be removed in GTGE 0.2.5.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row) {
		return this.bsLoader.getImages(imagefile, col, row);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImages(String, int, int, boolean)
	 * bsLoader.getImages(String, int, int, boolean)}.
	 * @deprecated Use bsLoader.getImages(String, int, int, boolean) directly.
	 *             This method is deprecated and will be removed in GTGE 0.2.5.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask) {
		return this.bsLoader.getImages(imagefile, col, row, useMask);
	}
	
	// TODO: deprecate with replacement.
	/**
	 * Returns stripped images with cropped sequence.
	 * <p>
	 * 
	 * First the image is stripped by column and row, and then the images is
	 * arranged with specified series sequence order. The images then stored
	 * into cache ({@linkplain com.golden.gamedev.engine.BaseLoader bsLoader}
	 * with key as followed: start sequence + the image file + end sequence.
	 * <p>
	 * 
	 * For example:
	 * 
	 * <pre>
	 *   int start = 2, end = 4;
	 *   BufferedImage[] image = getImages(&quot;imagestrip.png&quot;, 6, 1, true, start, end);
	 * </pre>
	 * 
	 * Notice that the first image is start from 0 (zero).
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask, int start, int end) {
		String mapping = start + imagefile + end;
		BufferedImage[] image = this.bsLoader.getStoredImages(mapping);
		
		if (image == null) {
			BufferedImage[] src = this.getImages(imagefile, col, row, useMask);
			int count = end - start + 1;
			image = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				image[i] = src[start + i];
			}
			this.bsLoader.storeImages(mapping, image);
		}
		
		return image;
	}
	
	// TODO: deprecate with replacement.
	/**
	 * Returns stripped images with specified sequence.
	 * <p>
	 * 
	 * First the image is stripped by column and row, and then the images is
	 * arranged with specified sequence order. The images then stored into cache
	 * ({@linkplain com.golden.gamedev.engine.BaseLoader bsLoader}) with key as
	 * followed: the image file + sequence + digit.
	 * <p>
	 * 
	 * For example:
	 * 
	 * <pre>
	 * 
	 * 
	 * 
	 * // we want the images sequence is as followed
	 * String sequence = &quot;020120&quot;;
	 * BufferedImage[] image = getImages(&quot;imagestrip.png&quot;, 3, 1, true, sequence, 1);
	 * // this is plain same like above code except we use 2 digits here
	 * // 2 digits is used for image strip larger than 10
	 * String sequence = &quot;000200010200&quot;;
	 * BufferedImage[] image = getImages(&quot;imagestrip.png&quot;, 20, 1, true, sequence, 1);
	 * </pre>
	 * 
	 * Notice that the first image is start from 0 (zero).
	 * <p>
	 * 
	 * This is used to make custom animation (012321).
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask, String sequence, int digit) {
		String mapping = imagefile + sequence + digit;
		BufferedImage[] image = this.bsLoader.getStoredImages(mapping);
		
		if (image == null) {
			BufferedImage[] src = this.getImages(imagefile, col, row, useMask);
			int count = sequence.length() / digit;
			image = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				image[i] = src[Integer.parseInt(sequence.substring(i * digit,
				        ((i + 1) * digit)))];
			}
			this.bsLoader.storeImages(mapping, image);
		}
		
		return image;
	}
	
	// TODO: deprecate with replacement
	/**
	 * Same as {@linkplain #getImages(String, int, int, int, int)
	 * getImages(imagefile, col, row, useMask, start, end)} with mask color is
	 * turned on by default.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, int start, int end) {
		return this.getImages(imagefile, col, row, true, start, end);
	}
	
	// TODO: deprecate with replacement
	/**
	 * Same as {@linkplain #getImages(String, int, int, boolean, String, int)
	 * getImages(imagefile, col, row, useMask, sequence, digit)} with mask color
	 * is turned on by default.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, String sequence, int digit) {
		return this.getImages(imagefile, col, row, true, sequence, digit);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#getMouseX()
	 * bsInput.getMouseX()}.
	 * @deprecated Use bsInput.getMouseX() directly. This method is deprecated
	 *             and will be removed in GTGE 0.2.5.
	 */
	public int getMouseX() {
		return this.bsInput.getMouseX();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#getMouseY()
	 * bsInput.getMouseY()}.
	 * @deprecated Use bsInput.getMouseY() directly. This method is deprecated
	 *             and will be removed in GTGE 0.2.5.
	 */
	public int getMouseY() {
		return this.bsInput.getMouseY();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.util.Utility#getRandom(int, int)
	 * Utility.getRandom(int, int)}
	 * @deprecated Use Utility.getRandom(int, int) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public int getRandom(int low, int hi) {
		return Utility.getRandom(low, hi);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
	 * bsGraphics.getSize().width}.
	 * @deprecated Use bsGraphics.getSize().width directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public int getWidth() {
		return this.bsGraphics.getSize().width;
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#setMouseVisible(boolean)
	 * bsInput.setMouseVisible(false)}.
	 * @deprecated Use bsInput.setMouseVisible(false) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public void hideCursor() {
		this.bsInput.setMouseVisible(false);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyDown(int)
	 * bsInput.isKeyDown(int)}.
	 * @deprecated Use bsInput.isKeyDown(int) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public boolean keyDown(int keyCode) {
		return this.bsInput.isKeyDown(keyCode);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyPressed(int)
	 * bsInput.isKeyPressed(int)}.
	 * @deprecated Use bsInput.isKeyPressed(int) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public boolean keyPressed(int keyCode) {
		return this.bsInput.isKeyPressed(keyCode);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseAudio#play(String)
	 * bsMusic.play(String)}.
	 * 
	 * @see com.golden.gamedev.engine.BaseAudio#setBaseRenderer(com.golden.gamedev.engine.BaseAudioRenderer)
	 * @see com.golden.gamedev.engine.audio
	 * @deprecated Use bsMusic.play(String) directly. This method is deprecated
	 *             and will be removed in GTGE 0.2.5.
	 */
	public int playMusic(String audiofile) {
		return this.bsMusic.play(audiofile);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseAudio#play(String)
	 * bsSound.play(String)}.
	 * 
	 * @see com.golden.gamedev.engine.BaseAudio#setBaseRenderer(com.golden.gamedev.engine.BaseAudioRenderer)
	 * @see com.golden.gamedev.engine.audio
	 * @deprecated Use bsSound.play(String) directly. This method is deprecated
	 *             and will be removed in GTGE 0.2.5.
	 */
	public int playSound(String audiofile) {
		return this.bsSound.play(audiofile);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isMousePressed(int)
	 * bsInput.isMousePressed(java.awt.event.MouseEvent.BUTTON3)}.
	 * @deprecated Use bsInput.isMousePressed(MouseEvent.BUTTON3) directly. This
	 *             method is deprecated and will be removed in GTGE 0.2.5.
	 */
	public boolean rightClick() {
		return this.bsInput.isMousePressed(MouseEvent.BUTTON3);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#setFPS(int)
	 * bsTimer.setFPS(int)}.
	 * @deprecated Use bsTimer.setFPS(int) directly. This method is deprecated
	 *             and will be removed in GTGE 0.2.5.
	 */
	public void setFPS(int fps) {
		this.bsTimer.setFPS(fps);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#setMaskColor(Color)
	 * bsLoader.setMaskColor(java.awt.Color)}.
	 * @deprecated Use bsLoader.setMaskColor(Color) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public void setMaskColor(Color c) {
		this.bsLoader.setMaskColor(c);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#setMouseVisible(boolean)
	 * bsInput.setMouseVisible(true)}.
	 * @deprecated Use bsInput.setMouseVisible(true) directly. This method is
	 *             deprecated and will be removed in GTGE 0.2.5.
	 */
	public void showCursor() {
		this.bsInput.setMouseVisible(true);
	}
	
	/**
	 * Returns a new created buffered image which the current game state is
	 * rendered into it.
	 * @deprecated This method is deprecated and will be removed in 0.2.5. Use
	 *             {@link ImageUtil#createImage(Renderable, int, int)} instead
	 *             of this method.
	 */
	public BufferedImage takeScreenShot() {
		return ImageUtil.createImage(this, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Captures current game screen into specified file.
	 * 
	 * @see #takeScreenShot()
	 * @deprecated This method is deprecated and will be removed in 0.2.5. Use
	 *             {@link ImageUtil#createImage(Renderable, int, int)} to create
	 *             a screenshot and
	 *             {@link ImageUtil#saveImage(BufferedImage, File)} to save it
	 *             directly.
	 */
	public void takeScreenShot(File f) {
		ImageUtil.saveImage(ImageUtil.createImage(this, this.getWidth(), this
		        .getHeight()), f);
	}
	
}
