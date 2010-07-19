/**
 * 
 */
package com.golden.gamedev.game;

/**
 * The {@link GameLoop} interface defines a contract for how a {@link BaseGame}
 * instance's {@link Game#start() start} method is implemented.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see BaseGame#start()
 * 
 */
interface GameLoop {
	
	void executeLoop();
	
}
