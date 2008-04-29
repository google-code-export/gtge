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
import java.awt.Graphics2D;

import com.golden.gamedev.object.GameFont;

/**
 * Similar like <code>Game</code> class except this class is working under
 * <code>GameEngine</code> frame work.
 * <p>
 * 
 * <code>GameObject</code> class is plain same with <code>Game</code> class,
 * you can first create the game as <code>Game</code> class, run it, test it,
 * and then rename it to <code>GameObject</code> and attach it to
 * <code>GameEngine</code> frame work as one of game entities.
 * <p>
 * 
 * Please read {@link GameEngine} documentation for more information about how
 * to work with <code>GameObject</code> class.
 * 
 * @see com.golden.gamedev.GameEngine
 * @see com.golden.gamedev.Game
 */
public abstract class GameObject extends BaseGame {
	
	/** **************************** MASTER ENGINE ****************************** */
	
	/**
	 * The master <code>GameEngine</code> frame work.
	 */
	public final GameEngine parent;
	
	/** **************************** GAME ENGINE ******************************** */
	
	/** ************************* OTHER PROPERTIES ****************************** */
	
	private boolean finish; // true, to back to game chooser
	private boolean initialized; // true, indicates the game has been
	
	// initialized
	
	// to avoid double initialization
	// if the game is replaying
	
	/** ************************************************************************* */
	/** ************************* CONSTRUCTOR *********************************** */
	/** ************************************************************************* */
	
	/**
	 * Creates new <code>GameObject</code> with specified
	 * <code>GameEngine</code> as the master engine.
	 */
	public GameObject(GameEngine parent) {
		this.parent = parent;
		
		this.grabEngines();
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
	 * Starts the game main loop, this method will not return until the game is
	 * finished playing/running. To end the game call {@linkplain #finish()}
	 * method.
	 */
	public final void start() {
		// grabbing engines from master engine
		this.grabEngines();
		GameFont fpsFont = this.parent.fpsFont;
		if (!this.initialized) {
			this.initResources();
			this.initialized = true;
		}
		
		this.finish = false;
		
		// start game loop!
		// before play, clear memory (runs garbage collector)
		System.gc();
		System.runFinalization();
		
		this.bsInput.refresh();
		this.bsTimer.refresh();
		
		long elapsedTime = 0;
		out: while (true) {
			if (this.parent.inFocus) {
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
				
				if (!this.parent.inFocus) {
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
	 * End this game, and back to
	 * {@linkplain GameEngine#getGame(int) game object chooser}.
	 * 
	 * @see GameEngine#nextGameID
	 * @see GameEngine#nextGame
	 */
	public void finish() {
		this.finish = true;
	}
	
}
