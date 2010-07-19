/**
 * 
 */
package com.golden.gamedev.game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import com.golden.gamedev.object.GameFont;

/**
 * The {@link ChildGameLoop} class provides an implementation of the
 * {@link GameLoop} for a child {@link BaseGame} instance.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see GameLoop
 * 
 */
final class ChildGameLoop implements GameLoop {
	
	/**
	 * The non-null {@link BaseGame} instance that this {@link NormalGameLoop}
	 * will provide a {@link GameLoop} implementation for.
	 */
	private BaseGame game;
	
	/**
	 * Creates a new {@link NormalGameLoop} instance with the given non-null
	 * {@link BaseGame} instance.
	 * @param game The non-null {@link BaseGame} instance to use to create this
	 *        {@link NormalGameLoop} instance.
	 */
	public ChildGameLoop(final BaseGame game) {
		super();
		this.game = game;
	}
	
	public void executeLoop() {
		// grabbing engines from master engine
		BaseGame.transferEngines(this.game.getParent(), this.game);
		GameFont fpsFont = this.game.getFontManager().getFont(
		        BaseGame.FPS_FONT_NAME);
		if (!this.game.initialized) {
			this.game.initResources();
			this.game.initialized = true;
		}
		
		this.game.finish = false;
		
		// start game loop!
		// before play, clear memory (runs garbage collector)
		System.gc();
		System.runFinalization();
		
		this.game.getBaseInput().reset();
		this.game.getBaseTimer().reset();
		
		long elapsedTime = 0;
		out: while (true) {
			if (this.game.getParent().isInFocus()) {
				// update game
				this.game.update(elapsedTime);
				this.game.getParent().update(elapsedTime); // update common
														   // variables
				this.game.getBaseInput().update(elapsedTime);
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
				if (this.game.finish || !this.game.getParent().isRunning()) {
					// if finish, quit this game
					break out;
				}
				
				// graphics operation
				Graphics2D g = this.game.getBaseGraphics().getBackBuffer();
				
				this.game.render(g); // render game
				this.game.getParent().render(g); // render global game
				
				if (this.game.getParent() instanceof BaseGame
				        && ((BaseGame) this.game.getParent()).isDistribute()) {
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
					
					fpsFont.drawString(g, "FPS = "
					        + this.game.getBaseTimer().getCurrentFPS() + "/"
					        + this.game.getBaseTimer().getFPS(), 9, this.game
					        .getBaseGraphics().getSize().height - 21);
					
					fpsFont.drawString(g, "GTGE", this.game.getBaseGraphics()
					        .getSize().width - 65, 9);
				}
				
				if (!this.game.getParent().isInFocus()) {
					this.game.getParent().renderLostFocus(g);
				}
				
			} while (this.game.getBaseGraphics().flip() == false);
			
			elapsedTime = this.game.getBaseTimer().sleep();
			
			if (elapsedTime > 100) {
				// can't lower than 10 fps (1000/100)
				elapsedTime = 100;
			}
		}
	}
	
}
