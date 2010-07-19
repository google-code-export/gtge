/**
 * 
 */
package com.golden.gamedev.game;

/**
 * The {@link NormalGameLoop} class represents the game loop of a normal
 * (standalone or parent) {@link BaseGame} instance. <br />
 * <br />
 * Note that currently, {@link GameEngine} has a different implementation of
 * {@link BaseGame#startGameLoop()} which it uses to provide the engine support
 * - this may eventually be split from this class into a separate
 * {@link GameLoop} implementation to provide for easier to maintain code in the
 * future.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
final class NormalGameLoop implements GameLoop {
	
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
	public NormalGameLoop(final BaseGame game) {
		super();
		this.game = game;
	}
	
	public void executeLoop() {
		if (game.running || game.isFinish()) {
			return;
		}
		game.running = true;
		
		boolean development = false;
		if (!game.initialized) {
			// mark distribute state
			development = !game.distribute;
		}
		
		if (!development) {
			// the game has been distributed
			// catch any unexpected/uncaught exception!
			// the logo is shown in initialize() method
			try {
				if (!game.initialized) {
					game.initialize();
				}
				
				game.startGameLoop();
			}
			catch (Throwable e) {
				game.notifyError(e);
			}
			
		}
		else { // still in development
			if (!game.initialized) {
				game.initialize();
			}
			
			game.startGameLoop();
		}
	}
}
