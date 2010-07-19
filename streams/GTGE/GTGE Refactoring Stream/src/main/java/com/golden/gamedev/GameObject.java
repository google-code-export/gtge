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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.game.BaseGame;
import com.golden.gamedev.game.Game;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.Background;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * Similar like <code>Game</code> class except this class is working under
 * <code>GameEngine</code> frame work.
 * <p>
 * 
 * <code>GameObject</code> class is plain same with <code>Game</code> class, you
 * can first create the game as <code>Game</code> class, run it, test it, and
 * then rename it to <code>GameObject</code> and attach it to
 * <code>GameEngine</code> frame work as one of game entities.
 * <p>
 * 
 * Please read {@link GameEngine} documentation for more information about how
 * to work with <code>GameObject</code> class.
 * 
 * @see com.golden.gamedev.GameEngine
 * @see Game
 * @deprecated Do not use GameObject, use {@link BaseGame} instead. This class
 *             will be removed in GTGE 0.2.5.
 */
public abstract class GameObject implements Game {
	
	/**
	 * The master <code>GameEngine</code> frame work.
	 */
	public final GameEngine parent;
	
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
	
	private boolean finish; // true, to back to game chooser
	private boolean initialized; // true, indicates the game has been
	
	/**
	 * Creates new <code>GameObject</code> with specified
	 * <code>GameEngine</code> as the master engine.
	 */
	public GameObject(GameEngine parent) {
		this.parent = parent;
		
		this.grabEngines();
	}
	
	// TODO: deprecate, but provide a replacement.
	/**
	 * Returns whether the mouse pointer is inside specified screen boundary.
	 */
	public boolean checkPosMouse(int x1, int y1, int x2, int y2) {
		return (this.getMouseX() >= x1 && this.getMouseY() >= y1
		        && this.getMouseX() <= x2 && this.getMouseY() <= y2);
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
	
	public final void finish() {
		this.finish = true;
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
	 * bsLoader.getImage(String)}.
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
	 * 
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
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
	
	private void grabEngines() {
		this.bsGraphics = this.parent.bsGraphics;
		this.bsIO = this.parent.bsIO;
		this.bsLoader = this.parent.bsLoader;
		this.bsInput = this.parent.bsInput;
		this.bsTimer = this.parent.bsTimer;
		this.bsMusic = this.parent.bsMusic;
		this.bsSound = this.parent.bsSound;
		
		this.fontManager = this.parent.fontManager;
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
	
	public final void start() {
		// grabbing engines from master engine
		this.grabEngines();
		GameFont fpsFont = this.parent.fontManager
		        .getFont(BaseGame.FPS_FONT_NAME);
		if (!this.initialized) {
			this.initResources();
			this.initialized = true;
		}
		
		this.finish = false;
		
		// start game loop!
		// before play, clear memory (runs garbage collector)
		System.gc();
		System.runFinalization();
		
		this.bsInput.reset();
		this.bsTimer.reset();
		
		long elapsedTime = 0;
		out: while (true) {
			if (this.parent.isInFocus()) {
				// update game
				this.update(elapsedTime);
				this.parent.update(elapsedTime); // update common variables
				this.bsInput.update(elapsedTime);
				
			}
			else {
				// the game is not in focus!
				try {
					Thread.sleep(300);
				}
				catch (InterruptedException e) {
				}
			}
			
			do {
				if (this.finish || !this.parent.isRunning()) {
					// if finish, quit this game
					break out;
				}
				
				// graphics operation
				Graphics2D g = this.bsGraphics.getBackBuffer();
				
				this.render(g); // render game
				this.parent.render(g); // render global game
				
				if (!this.parent.isDistribute()) {
					// if the game is still under development
					// draw game FPS and other stuff
					
					// to make sure the FPS is drawn!
					// remove any clipping and alpha composite
					if (g.getClip() != null) {
						g.setClip(null);
					}
					if (g.getComposite() != null) {
						if (g.getComposite() != AlphaComposite.SrcOver) {
							g.setComposite(AlphaComposite.SrcOver);
						}
					}
					
					fpsFont.drawString(g, "FPS = " + this.getCurrentFPS() + "/"
					        + this.getFPS(), 9, this.getHeight() - 21);
					
					fpsFont.drawString(g, "GTGE", this.getWidth() - 65, 9);
				}
				
				if (!this.parent.isInFocus()) {
					this.parent.renderLostFocus(g);
				}
				
			} while (this.bsGraphics.flip() == false);
			
			elapsedTime = this.bsTimer.sleep();
			
			if (elapsedTime > 100) {
				// can't lower than 10 fps (1000/100)
				elapsedTime = 100;
			}
		}
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
	
	public final void stop() {
		finish();
	}
	
	public final BaseGraphics getBaseGraphics() {
		return bsGraphics;
	}
	
	public final void setBaseGraphics(BaseGraphics baseGraphics) {
		if (baseGraphics == null) {
			throw new IllegalArgumentException(
			        "The specified BaseGraphics instance may not be null.");
		}
		this.bsGraphics = baseGraphics;
	}
	
	public final BaseIO getBaseIO() {
		return bsIO;
	}
	
	public final void setBaseIO(final BaseIO baseIO) {
		this.bsIO = baseIO;
	}
	
	public final BaseLoader getBaseLoader() {
		return bsLoader;
	}
	
	public final void setBaseLoader(final BaseLoader baseLoader) {
		this.bsLoader = baseLoader;
	}
	
	public final BaseInput getBaseInput() {
		return bsInput;
	}
	
	public final void setBaseInput(final BaseInput baseInput) {
		this.bsInput = baseInput;
	}
	
	public final BaseTimer getBaseTimer() {
		return bsTimer;
	}
	
	public final void setBaseTimer(final BaseTimer baseTimer) {
		this.bsTimer = baseTimer;
	}
	
	public final BaseAudio getMusicEngine() {
		return bsMusic;
	}
	
	public final void setMusicEngine(final BaseAudio musicEngine) {
		this.bsMusic = musicEngine;
	}
	
	public final BaseAudio getSoundEngine() {
		return bsSound;
	}
	
	public final void setSoundEngine(final BaseAudio soundEngine) {
		this.bsSound = soundEngine;
	}
	
	public final GameFontManager getFontManager() {
		return fontManager;
	}
	
	public final void setFontManager(GameFontManager fontManager) {
		this.fontManager = fontManager;
	}
	
	/**
	 * Returns whether the game is currently running/playing or not. Running
	 * game means the game is in game main-loop (update and render loop).
	 * 
	 * @see #start()
	 */
	public final boolean isRunning() {
		return true;
	}
	
	public final boolean isInFocus() {
		return true;
	}
	
	public void renderLostFocus(Graphics2D g) {
		render(g);
	}
}
