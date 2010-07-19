/**
 * 
 */
package com.golden.gamedev.game;

import java.awt.Graphics2D;

import com.golden.gamedev.Renderable;
import com.golden.gamedev.Updateable;
import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.object.GameFontManager;

/**
 * The {@link Game} interface defines an {@link Updateable} {@link Renderable}
 * {@link Object} that represents a game within the GTGE framework.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Object
 * @see Renderable
 * @see Updateable
 * @see BaseGame
 * 
 */
public interface Game extends Renderable, Updateable {
	
	/**
	 * All game resources initialization, everything that usually goes to
	 * constructor should be put in here.
	 * <p>
	 * 
	 * This method is called only once for every newly created <code>Game</code>
	 * class.
	 * 
	 */
	void initResources();
	
	/**
	 * End the game and return back to the calling process, exiting the game
	 * loop caused via an invocation of {@link #start()}.
	 * <p>
	 * 
	 * Only call this method when the game has been finished playing. Calling
	 * this method will immediatelly makes the game to quit and the game can not
	 * be resumed/played anymore.
	 * 
	 * @see #stop()
	 */
	void finish();
	
	/**
	 * Starts the game main loop, this method will not return until the game is
	 * finished playing/running. To stop the game use either {@link #finish()}
	 * to quit the game or {@link #stop()} to hold the game.
	 * <p>
	 * 
	 * Be sure the game {@linkplain #bsGraphics graphics engine} has been
	 * initialized (not null) before attempt to call this method.
	 * 
	 * @see #finish()
	 */
	void start();
	
	/**
	 * Stops the game from running, and to resume the game call {@link #start()}
	 * method. This method is only holding the game, to quit the game call
	 * {@link #finish()} instead. During the holding time, no action is taken,
	 * even the game rendering, therefore this method is not suitable for making
	 * game pause event.
	 * <p>
	 * 
	 * By default this stop method is only called in applet environment whenever
	 * the applet stop method is executed by the webpage.
	 * 
	 * @see #start()
	 * @see #finish()
	 */
	void stop();
	
	boolean isInFocus();
	
	void renderLostFocus(Graphics2D g);
	
	boolean isRunning();
	
	BaseGraphics getBaseGraphics();
	
	/**
	 * Sets the non-null {@link BaseGraphics} instance to use for this
	 * {@link Game} instance. This method must be invoked before
	 * {@link #start()}.
	 * @param baseGraphics The non-null {@link BaseGraphics} instance to use for
	 *        this {@link Game} instance.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the given
	 *         {@link BaseGraphics} instance is null.
	 */
	void setBaseGraphics(final BaseGraphics baseGraphics);
	
	BaseIO getBaseIO();
	
	void setBaseIO(final BaseIO baseIO);
	
	BaseLoader getBaseLoader();
	
	void setBaseLoader(final BaseLoader baseLoader);
	
	BaseInput getBaseInput();
	
	void setBaseInput(final BaseInput baseInput);
	
	BaseTimer getBaseTimer();
	
	void setBaseTimer(final BaseTimer baseTimer);
	
	BaseAudio getMusicEngine();
	
	void setMusicEngine(final BaseAudio musicEngine);
	
	BaseAudio getSoundEngine();
	
	void setSoundEngine(final BaseAudio soundEngine);
	
	GameFontManager getFontManager();
	
	void setFontManager(GameFontManager fontManager);
}
