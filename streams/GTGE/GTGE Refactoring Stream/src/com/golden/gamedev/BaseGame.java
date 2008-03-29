/**
 * 
 */
package com.golden.gamedev;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.object.GameFontManager;

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
}
