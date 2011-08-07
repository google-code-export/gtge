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
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BufferedImageCache;
import com.golden.gamedev.engine.FrameRateSynchronizer;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.util.BufferedImageUtil;

/**
 * In its current form, a {@link GameObject} is tied to a {@link GameEngine} instance, and one must exist in order to
 * create a {@link GameObject}. Refactoring will take place once {@link GameEngine} is treated to break this apart - a
 * {@link GameObject} may exist independently of a {@link GameEngine}, but if one is provided, it will exhibit the same
 * behavior as it currently does, which is beneficial in some cases (such as combining {@link GameObject} instances for
 * execution) but harmful in others (such as developer testing a single {@link GameObject} in isolation). <br />
 * <br />
 * Helper methods have been deleted and they will not return. Subclasses may, later on, include these helper methods if
 * they are deemed useful, but {@link Game}, {@link GameObject} and {@link GameEngine} are core classes which should be
 * as small as possible. <br />
 * <br />
 * Currently, {@link Game} is much more powerful than {@link GameObject}, but only {@link GameObject} can be used with
 * {@link GameEngine} - refactoring will fix this so that *either* object can be used with a {@link GameEngine}
 * instance, and perhaps in time {@link GameObject} will simply cease to exist (as {@link Game} may replace it).
 */
public abstract class GameObject {
	
	// REVIEW-HIGH: Make GameEngine have an interface that provides the engines via getters.
	// REVIEW-HIGH: Get rid of the dependency on GameEngine - make GameEngine provide its abilities via interfaces.
	/**
	 * The master <code>GameEngine</code> frame work.
	 */
	public final GameEngine parent;
	
	/** Graphics engine. */
	private BaseGraphics baseGraphics;
	/** I/O file engine. */
	private BaseIO baseIo;
	/** Image loader engine. */
	private BufferedImageCache bufferedImageCache;
	/** Input engine. */
	private BaseInput baseInput;
	/** Timer engine. */
	private FrameRateSynchronizer frameRateSynchronizer;
	/** Audio engine for music. */
	private BaseAudio musicEngine;
	/** Audio engine for sound. */
	private BaseAudio soundEngine;
	
	/**
	 * Whether or not this {@link GameObject} instance has finished execution.
	 */
	private boolean finish; // true, to back to game chooser
	
	/**
	 * Whether or not {@link #initResources()} has been called for this {@link GameObject} instance.
	 * {@link #initResources()} should be invoked once and only once when a {@link GameObject} instance is first
	 * {@link #start() started}.
	 */
	private boolean initialized;
	
	/**
	 * Creates new <code>GameObject</code> with specified <code>GameEngine</code> as the master engine.
	 */
	public GameObject(final GameEngine parent) {
		this.parent = parent;
		
		baseGraphics = this.parent.bsGraphics;
		baseIo = this.parent.bsIO;
		bufferedImageCache = this.parent.bsLoader;
		baseInput = this.parent.bsInput;
		frameRateSynchronizer = this.parent.bsTimer;
		musicEngine = this.parent.bsMusic;
		soundEngine = this.parent.bsSound;
	}
	
	/**
	 * Starts the game main loop, this method will not return until the game is finished playing/running. To end the
	 * game call {@linkplain #finish()} method.
	 */
	public final void start() {
		if (!initialized) {
			initResources();
			initialized = true;
		}
		
		baseInput.refresh();
		frameRateSynchronizer.beginSynchronization();
		
		executeGameLoop();
	}
	
	/**
	 * 
	 */
	private void executeGameLoop() {
		boolean firstFrameRendered = false;
		long elapsedTime = 0;
		while (continueGameExecution()) {
			if (parent.inFocus) {
				updateCallbacks(elapsedTime);
				
			}
			
			if (firstFrameRendered) {
				elapsedTime = updateTimeForFrameRendering();
			}
			
			do {
				if (!continueGameExecution()) {
					break;
				}
				
				renderFrame();
				firstFrameRendered = true;
			} while (baseGraphics.flip() == false);
		}
	}
	
	/**
	 */
	private void renderFrame() {
		final Graphics2D g = baseGraphics.getBackBuffer();
		
		render(g);
		parent.render(g);
		
		drawFPSForNonDistributableGame(g);
		
		if (!parent.inFocus) {
			parent.renderLostFocus(g);
		}
	}
	
	// REVIEW-HIGH: Consider moving this to an explicit subclass (its render method can do this after the game draws its
	// frame).
	/**
	 * @param g
	 */
	private void drawFPSForNonDistributableGame(final Graphics2D g) {
		if (!parent.isDistribute()) {
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
			
			final GameFont fpsFont = parent.fpsFont;
			fpsFont.drawString(g,
					"FPS = " + frameRateSynchronizer.getRenderedFps() + "/" + frameRateSynchronizer.getFps(), 9,
					baseGraphics.getSize().height - 21);
			
			fpsFont.drawString(g, "GTGE", baseGraphics.getSize().width - 65, 9);
		}
	}
	
	/**
	 * @param elapsedTime
	 */
	private void updateCallbacks(final long elapsedTime) {
		update(elapsedTime);
		parent.update(elapsedTime);
		baseInput.update(elapsedTime);
	}
	
	// REVIEW-MEDIUM - Since the hack is here, why is this a long?
	/**
	 * @return
	 */
	private long updateTimeForFrameRendering() {
		long elapsedTime;
		elapsedTime = frameRateSynchronizer.delayForFrame();
		
		if (elapsedTime > 100) {
			elapsedTime = 100;
		}
		return elapsedTime;
	}
	
	/**
	 * @return
	 */
	private boolean continueGameExecution() {
		return !finish && parent.isRunning();
	}
	
	/**
	 * Finishes the execution of this {@link GameObject} instance. {@link GameObject} instances that have been finished
	 * are unable to be restarted. Note that the given {@link GameEngine} associated with this {@link GameObject}
	 * instance can be temporarily paused in order to allow for a pause and restart of a {@link GameObject} instance
	 * (via {@link GameEngine#stop()}.
	 */
	public void finish() {
		finish = true;
	}
	
	/**
	 * All game resources initialization, everything that usually goes to constructor should be put in here.
	 * <p>
	 * 
	 * This method is called only once for every newly created <code>GameObject</code> class.
	 * 
	 * @see #getImage(String)
	 * @see #getImages(String, int, int)
	 * @see #playMusic(String)
	 * @see #setMaskColor(Color)
	 * @see com.golden.gamedev.object
	 */
	public abstract void initResources();
	
	/**
	 * Updates game variables.
	 * 
	 * @see #keyDown(int)
	 * @see #keyPressed(int)
	 */
	public abstract void update(long elapsedTime);
	
	// REVIEW-HIGH: This method must be kept.
	/**
	 * Renders game to the screen.
	 * 
	 * @param g
	 *            backbuffer graphics context
	 */
	public abstract void render(Graphics2D g);
	
	/**
	 * Returns a new created buffered image which the current game state is rendered into it.
	 */
	public BufferedImage takeScreenShot() {
		final BufferedImage screen = BufferedImageUtil.createImage(baseGraphics.getSize().width,
				baseGraphics.getSize().height, Transparency.OPAQUE);
		final Graphics2D g = screen.createGraphics();
		render(g);
		g.dispose();
		
		return screen;
	}
	
	public void setBaseGraphics(BaseGraphics baseGraphics) {
		this.baseGraphics = baseGraphics;
	}
	
	public BaseGraphics getBaseGraphics() {
		return baseGraphics;
	}
	
	public void setBaseIo(BaseIO baseIo) {
		this.baseIo = baseIo;
	}
	
	public BaseIO getBaseIo() {
		return baseIo;
	}
	
	public void setBufferedImageCache(BufferedImageCache bufferedImageCache) {
		this.bufferedImageCache = bufferedImageCache;
	}
	
	public BufferedImageCache getBufferedImageCache() {
		return bufferedImageCache;
	}
	
	public void setBaseInput(BaseInput baseInput) {
		this.baseInput = baseInput;
	}
	
	public BaseInput getBaseInput() {
		return baseInput;
	}
	
	public void setFrameRateSynchronizer(FrameRateSynchronizer frameRateSynchronizer) {
		this.frameRateSynchronizer = frameRateSynchronizer;
	}
	
	public FrameRateSynchronizer getFrameRateSynchronizer() {
		return frameRateSynchronizer;
	}
	
	public void setMusicEngine(BaseAudio musicEngine) {
		this.musicEngine = musicEngine;
	}
	
	public BaseAudio getMusicEngine() {
		return musicEngine;
	}
	
	public void setSoundEngine(BaseAudio soundEngine) {
		this.soundEngine = soundEngine;
	}
	
	public BaseAudio getSoundEngine() {
		return soundEngine;
	}
}
