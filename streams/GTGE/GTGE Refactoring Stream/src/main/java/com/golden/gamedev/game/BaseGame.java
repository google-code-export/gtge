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
package com.golden.gamedev.game;

// JFC
import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import com.golden.gamedev.Renderable;
import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.engine.audio.MidiRenderer;
import com.golden.gamedev.engine.audio.WaveRenderer;
import com.golden.gamedev.engine.input.AWTInput;
import com.golden.gamedev.engine.timer.SystemTimer;
import com.golden.gamedev.funbox.ErrorNotificationDialog;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.background.BoundedBackground;
import com.golden.gamedev.util.ImageUtil;

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
public abstract class BaseGame implements Game {
	
	/**
	 * The {@link String} that the {@link GameFont} to use to draw the FPS is
	 * stored under when this {@link Game} is {@link #start() started}.
	 */
	public static final String FPS_FONT_NAME = "FPS Font";
	
	/**
	 * Current GTGE version.
	 */
	public static final String GTGE_VERSION = "0.2.4";
	
	private static final int DEFAULT_FPS = 100;
	
	/**
	 * ***************************** GAME ENGINES ******************************
	 */
	
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
	 * **************************** GAME VARIABLES *****************************
	 */
	
	boolean running; // true, indicates the game is currently
	// running/playing
	boolean finish; // true, indicates the game has been ended
	// an ended game can't be played anymore
	
	/**
	 * Indicates whether this game is finished and ready to distribute or still
	 * in development stage.
	 * <p>
	 * 
	 * A distributed game (distribute = true) will catch any uncatch/unexpected
	 * game exception and send the error to {@link #notifyError(Throwable)}
	 * method.
	 * <p>
	 * 
	 * When your game is completed and it is time to distribute the game to the
	 * world, set this distribute value to true in class initialization :
	 * 
	 * <pre>
	 *    public class YourGame extends Game {
	 *       // class initialization, put it here
	 *       &lt;b&gt;{ distribute = true; }&lt;/b&gt;
	 *       // do not put it in initResources() method or other place!
	 *       public void initResources() { }
	 *       public void update(long elapsedTime) { }
	 *       public void render(Graphics2D g) { }
	 *    }
	 * </pre>
	 * 
	 * @see #notifyError(Throwable)
	 */
	protected boolean distribute;
	
	private boolean development; // to avoid developer hack 'distribute'
	// value
	boolean initialized; // true, indicates the game has been
	// initialized
	// used when the game is stopped and played again
	// to avoid multiple initialization
	private boolean inFocus = true;
	private boolean inFocusBlink;
	private boolean pauseOnLostFocus = false;
	
	/**
	 * Whether or not this {@link BaseGame} instance should invoke
	 * {@link System#exit(int) exit} when {@link #finish()} is invoked to end
	 * the {@link Game}. Defaults to true for regular {@link BaseGame}
	 * instances, but false if the {@link BaseGame} delegates to a parent
	 * {@link Game} instance.
	 */
	private boolean exitOnFinish = true;
	
	/**
	 * The (possibly null) {@link Game} representing the parent {@link Game} of
	 * this {@link BaseGame} instance - typically a {@link GameEngine} instance,
	 * but this is not required.
	 */
	private Game parent;
	
	/**
	 * The non-null {@link GameLoop} instance to use for this {@link BaseGame}
	 * instance.
	 */
	private GameLoop gameLoop;
	
	/**
	 * Creates new instance of <code>BaseGame</code> class, please <b>see
	 * note</b> below.
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
	public BaseGame() {
		super();
	}
	
	/**
	 * Creates a new {@link BaseGame} instance with the given (possibly null)
	 * {@link Game} as its parent {@link Game} instance. Typically, this
	 * {@link Game} will be an instance of the {@link GameEngine} class, but
	 * this does not have to be the case - any valid {@link Game} implementation
	 * may be set here. <br />
	 * <br />
	 * @param parent The given (possibly null) parent {@link Game} instance to
	 *        set.
	 */
	public BaseGame(final Game parent) {
		super();
		this.parent = parent;
		if (parent != null) {
			// Child games don't exit via finish().
			this.exitOnFinish = false;
		}
	}
	
	public final void finish() {
		this.finish = true;
		this.stop();
	}
	
	public final void start() {
		// The gameLoop is not able to be initialized in the constructor (the
		// BaseGame wasn't fully initialized), so it's set up here.
		if (gameLoop == null) {
			if (parent == null) {
				gameLoop = new NormalGameLoop(this);
			}
			else {
				gameLoop = new ChildGameLoop(this);
			}
		}
		gameLoop.executeLoop();
	}
	
	public final void stop() {
		this.running = false;
	}
	
	/**
	 * Returns true, if the game has been finished playing and the game is about
	 * to return back to operating system.
	 */
	public final boolean isFinish() {
		return this.finish;
	}
	
	void initialize() {
		if (this.bsGraphics instanceof Applet) {
			// applet game need to make sure that the applet is being focused
			// when playing the game
			// this makes the players can browse on the net while playing
			this.setPauseOnLostFocus(true);
		}
		
		// init all engines
		this.initEngine();
		
		try {
			this.bsGraphics.getComponent().addFocusListener(
			        new FocusListener() {
				        
				        public void focusGained(FocusEvent e) {
					        BaseGame.this.setInFocus(true);
				        }
				        
				        public void focusLost(FocusEvent e) {
					        if (BaseGame.this.pauseOnLostFocus) {
						        BaseGame.this.setInFocus(false);
					        }
				        }
			        });
		}
		catch (Exception e) {
		}
		
		if (this.development == false) {
			// show GTGE splash screen :-)
			this.showLogo();
		}
		
		// load fps font
		try {
			URL fontURL = BaseGame.class.getResource("Game.fnt");
			BufferedImage fpsImage = ImageUtil.getImage(fontURL);
			
			GameFont fpsFont = this.fontManager.getFont(fpsImage);
			this.fontManager.removeFont(fpsImage); // unload the image
			this.fontManager.putFont(FPS_FONT_NAME, fpsFont);
		}
		catch (Exception e) {
			// someone is trying to hack GTGE here!
			this.bailOut();
		}
		
		// before play, clear unused memory (runs garbage collector)
		System.gc();
		System.runFinalization();
		
		// init resources
		this.initResources();
		this.initialized = true;
	}
	
	private void bailOut() {
		try {
			URL fontURL = BaseGame.class.getResource("Game.fnt");
			BufferedImage fpsImage = ImageUtil.getImage(fontURL);
			
			this.fontManager = new GameFontManager();
			GameFont font = this.fontManager.getFont(fpsImage);
			
			// clear background with red color
			// and write cracked version!
			Graphics2D g = this.bsGraphics.getBackBuffer();
			
			g.setColor(Color.RED.darker());
			g.fillRect(0, 0, this.bsGraphics.getSize().width, this.bsGraphics
			        .getSize().height);
			font.drawString(g, "THIS GAME IS USING", 10, 10);
			font.drawString(g, "GTGE CRACKED VERSION!!", 10, 30);
			font.drawString(g, "PLEASE REPORT THIS GAME TO", 10, 50);
			font.drawString(g, "WWW.GOLDENSTUDIOS.OR.ID", 10, 70);
			font.drawString(g, "THANK YOU....", 10, 105);
			
			this.bsGraphics.flip();
			
			// wait for 8 seconds
			this.bsInput = new AWTInput(this.bsGraphics.getComponent());
			try {
				int i = 0;
				do {
					Thread.sleep(50L);
				} while (++i < 160 && this.isSkip(50) == false); // 160 x 50
				// = 800
			}
			catch (InterruptedException e) {
			}
			
			this.finish();
		}
		catch (Throwable e) {
			// e.printStackTrace();
		}
		
		System.out.println("THIS GAME IS USING GTGE CRACKED VERSION!!");
		System.out
		        .println("PLEASE REPORT THIS GAME TO HTTP://WWW.GOLDENSTUDIOS.OR.ID/");
		System.out.println("THANK YOU....");
		
		System.exit(-1);
	}
	
	private void clearScreen() {
		Graphics2D g = this.bsGraphics.getBackBuffer();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.bsGraphics.getSize().width, this.bsGraphics
		        .getSize().height);
		this.bsGraphics.flip();
		
		g = this.bsGraphics.getBackBuffer();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.bsGraphics.getSize().width, this.bsGraphics
		        .getSize().height);
		this.bsGraphics.flip();
	}
	
	/**
	 * Game engines is initialized in this method. <br>
	 * Thus modifying or changing any game engines should be done within this
	 * method.
	 * <p>
	 * 
	 * List of default game engines initialized in this method :
	 * <ul>
	 * <li>Timer Engine : uses
	 * {@link com.golden.gamedev.engine.timer.SystemTimer}</li>
	 * <li>Input Engine : uses {@link com.golden.gamedev.engine.input.AWTInput}</li>
	 * <li>Music Engine : uses
	 * {@link com.golden.gamedev.engine.audio.MidiRenderer}</li>
	 * <li>Sound Engine : uses
	 * {@link com.golden.gamedev.engine.audio.WaveRenderer}</li>
	 * <li>I/O Engine : uses {@link com.golden.gamedev.engine.BaseIO}</li>
	 * <li>Image Engine : uses {@link com.golden.gamedev.engine.BaseLoader}</li>
	 * </ul>
	 * <p>
	 * 
	 * Example how to modify or change the default game engine :
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * protected void initEngine() {
	 * 	super.initEngine();
	 * 	// change the timer engine
	 * 	bsTimer = new GageTimer();
	 * 	// modify the music engine base renderer
	 * 	bsMusic.setBaseRenderer(new JOrbisOggRenderer());
	 * }
	 * </pre>
	 * 
	 * @see #bsGraphics
	 * @see #bsIO
	 * @see #bsLoader
	 * @see #bsInput
	 * @see #bsTimer
	 * @see #bsMusic
	 * @see #bsSound
	 * @see #fontManager
	 * @see com.golden.gamedev.engine
	 */
	protected void initEngine() {
		// game engine initilialization
		if (this.bsTimer == null) {
			this.bsTimer = new SystemTimer(); // GageTimer(); // LoraxTimer();
			// //
		}
		if (this.bsIO == null) {
			this.bsIO = new BaseIO(this.getClass());
		}
		if (this.bsLoader == null) {
			this.bsLoader = new BaseLoader(this.bsIO, Color.MAGENTA);
		}
		if (this.bsInput == null) {
			this.bsInput = new AWTInput(this.bsGraphics.getComponent());
		}
		if (this.bsMusic == null) {
			this.bsMusic = new BaseAudio(this.bsIO, new MidiRenderer());
			this.bsMusic.setExclusive(true);
			this.bsMusic.setLoop(true);
		}
		if (this.bsSound == null) {
			this.bsSound = new BaseAudio(this.bsIO, new WaveRenderer());
		}
		
		// miscellanous
		// set default fps
		this.bsTimer.setFPS(BaseGame.DEFAULT_FPS);
		
		// set background screen size
		BoundedBackground.screen = this.bsGraphics.getSize();
		
		// creates font manager
		if (this.fontManager == null) {
			this.fontManager = new GameFontManager();
		}
		
		// locale = Locale.getDefault();
	}
	
	/**
	 * Shows GTGE logo/splash screen, GTGE is freeware library, please support
	 * GTGE by showing this logo on your game, thank you.
	 * <p>
	 * 
	 * <b><u>Keep this method intact!</u></b>
	 * 
	 * @see #distribute
	 * @see #notifyError(Throwable)
	 */
	public final void showLogo() {
		this.bsInput.setMouseVisible(false);
		SystemTimer dummyTimer = new SystemTimer();
		dummyTimer.setFPS(20);
		this.bsInput.reset();
		
		// loading GTGE logo for splash screen
		BufferedImage logo = null;
		try {
			URL logoURL = BaseGame.class.getResource("Game.dat");
			BufferedImage orig = ImageUtil.getImage(logoURL);
			
			logo = ImageUtil.resize(orig, this.bsGraphics.getSize().width,
			        this.bsGraphics.getSize().height);
			
			orig.flush();
			orig = null;
		}
		catch (Exception e) {
		}
		this.bailOut();
		
		// time to show GTGE splash screen!
		// clear background with black color
		// and wait for a second
		try {
			this.clearScreen();
			Thread.sleep(1000L);
		}
		catch (InterruptedException e) {
		}
		
		// check for focus owner
		if (!this.isInFocus()) {
			while (!this.isInFocus()) {
				// the game is not in focus!
				Graphics2D g = this.bsGraphics.getBackBuffer();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.bsGraphics.getSize().width,
				        this.bsGraphics.getSize().height);
				this.renderLostFocus(g);
				this.bsGraphics.flip();
				
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
				}
			}
			
			this.bsInput.reset();
			
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			}
		}
		
		// gradually show (alpha blending)
		float alpha = 0.0f;
		dummyTimer.startTimer();
		boolean firstTime = true;
		while (alpha < 1.0f) {
			do {
				if (!this.running) {
					return;
				}
				Graphics2D g = this.bsGraphics.getBackBuffer();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.bsGraphics.getSize().width,
				        this.bsGraphics.getSize().height);
				Composite old = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, alpha));
				g.drawImage(logo, 0, 0, null);
				g.setComposite(old);
			} while (this.bsGraphics.flip() == false);
			
			if (firstTime) {
				// workaround for OpenGL mode
				firstTime = false;
				dummyTimer.reset();
			}
			
			long elapsedTime = dummyTimer.sleep();
			double increment = 0.00065 * elapsedTime;
			if (increment > 0.22) {
				increment = 0.22 + (increment / 6);
			}
			alpha += increment;
			
			if (this.isSkip(elapsedTime)) {
				this.clearScreen();
				logo.flush();
				logo = null;
				return;
			}
		}
		
		// show the shiny logo for 2500 ms :-)
		do {
			if (!this.running) {
				return;
			}
			Graphics2D g = this.bsGraphics.getBackBuffer();
			
			g.drawImage(logo, 0, 0, null);
		} while (this.bsGraphics.flip() == false);
		
		int i = 0;
		while (i++ < 50) { // 50 x 50 = 2500
			if (!this.running) {
				return;
			}
			
			try {
				Thread.sleep(50L);
			}
			catch (InterruptedException e) {
			}
			
			if (this.isSkip(50)) {
				this.clearScreen();
				logo.flush();
				logo = null;
				return;
			}
		}
		
		// gradually disappeared
		alpha = 1.0f;
		dummyTimer.reset();
		while (alpha > 0.0f) {
			do {
				if (!this.running) {
					return;
				}
				Graphics2D g = this.bsGraphics.getBackBuffer();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.bsGraphics.getSize().width,
				        this.bsGraphics.getSize().height);
				Composite old = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, alpha));
				g.drawImage(logo, 0, 0, null);
				g.setComposite(old);
			} while (this.bsGraphics.flip() == false);
			
			long elapsedTime = dummyTimer.sleep();
			double decrement = 0.00055 * elapsedTime;
			if (decrement > 0.15) {
				decrement = 0.15 + ((decrement - 0.04) / 2);
			}
			alpha -= decrement;
			
			if (this.isSkip(elapsedTime)) {
				this.clearScreen();
				logo.flush();
				logo = null;
				return;
			}
		}
		
		logo.flush();
		logo = null;
		dummyTimer.stopTimer();
		dummyTimer = null;
		
		// black wait before playing
		try {
			this.clearScreen();
			Thread.sleep(100L);
		}
		catch (InterruptedException e) {
		}
	}
	
	/**
	 * Returns whether this game is ready to distribute or still in development
	 * stage.
	 * 
	 * @see #distribute
	 */
	public final boolean isDistribute() {
		return (this.development == false);
	}
	
	/**
	 * Returns whether whether the game is paused when the game is lost the
	 * input focus or not.
	 * 
	 * @see #renderLostFocus(Graphics2D)
	 */
	public final boolean isPauseOnLostFocus() {
		return this.pauseOnLostFocus;
	}
	
	/**
	 * Returns whether the game is currently running/playing or not. Running
	 * game means the game is in game main-loop (update and render loop).
	 * 
	 * @see #start()
	 */
	public final boolean isRunning() {
		return this.running;
	}
	
	private boolean isSkip(long elapsedTime) {
		boolean skip = (this.bsInput.getKeyPressed() != BaseInput.NO_KEY || this.bsInput
		        .getMousePressed() != BaseInput.NO_BUTTON);
		this.bsInput.update(elapsedTime);
		
		return skip;
	}
	
	/**
	 * Notified of any unexpected or uncatch error thrown by the game when the
	 * game is ready to distribute ({@link #distribute} = true).
	 * <p>
	 * 
	 * By default this method creates an
	 * {@link com.golden.gamedev.funbox.ErrorNotificationDialog} to show the
	 * error to the user.
	 * <p>
	 * 
	 * Override this method to make a custom error dialog, or simply use the
	 * {@linkplain com.golden.gamedev.funbox.ErrorNotificationDialog} with your
	 * email address provided so the user can directly send the exception to
	 * your email.
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
	 * protected void notifyError(Throwable error) {
	 * 	new ErrorNotificationDialog(error, bsGraphics, &quot;Game Title v1.0&quot;, // the game title
	 * 	        &quot;yourmail@address.com&quot;); // your email
	 * }
	 * </pre>
	 * 
	 * @see #distribute
	 * @see com.golden.gamedev.funbox.ErrorNotificationDialog
	 */
	protected void notifyError(Throwable error) {
		new ErrorNotificationDialog(error, this.bsGraphics, this.getClass()
		        .getName(), null);
	}
	
	/**
	 * Notified when the game is about to quit. By default this method is
	 * calling <code>System.exit()</code> to ensure everything is properly shut
	 * down.
	 * <p>
	 * 
	 * Override this method to create a custom exit dialog, and be sure to call
	 * <code>System.exit()</code> at the end.
	 */
	protected void notifyExit() {
		if ((this.bsGraphics instanceof Applet) == false) {
			// non-applet game should call System.exit(0) if exitOnFinish is
			// true.
			if (exitOnFinish) {
				try {
					System.exit(0);
				}
				catch (Exception e) {
				}
			}
		}
		else {
			// applet game should display to the user
			// that the game has been ended
			final Applet applet = (Applet) this.bsGraphics;
			BufferedImage src = ImageUtil.createImage(
			        this.bsGraphics.getSize().width,
			        this.bsGraphics.getSize().height);
			Graphics2D g = src.createGraphics();
			
			try {
				// fill background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.bsGraphics.getSize().width,
				        this.bsGraphics.getSize().height);
				
				// play with transparency a bit
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, 0.8f));
				
				// draw in a circle only
				Shape shape = new java.awt.geom.Ellipse2D.Float(this.bsGraphics
				        .getSize().width / 10,
				        this.bsGraphics.getSize().height / 10, this.bsGraphics
				                .getSize().width
				                - (this.bsGraphics.getSize().width / 10 * 2),
				        this.bsGraphics.getSize().height
				                - (this.bsGraphics.getSize().height / 10 * 2));
				g.setClip(shape);
				
				// draw the game unto this image
				if (this instanceof GameEngine) {
					((GameEngine) this).getCurrentGame().render(g);
				}
				this.render(g);
				
				g.dispose();
			}
			catch (Exception e) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.bsGraphics.getSize().width,
				        this.bsGraphics.getSize().height);
				g.dispose();
			}
			
			// make it as gray
			BufferedImage converted = null;
			try {
				// technique #1
				// ColorSpace gray = ColorSpace.getInstance(ColorSpace.CS_GRAY);
				// converted = new ColorConvertOp(gray, null).filter(src, null);
				
				// technique #2
				BufferedImage image = new BufferedImage(src.getWidth(), src
				        .getHeight(), BufferedImage.TYPE_BYTE_GRAY);
				Graphics gfx = image.getGraphics();
				gfx.drawImage(src, 0, 0, null);
				gfx.dispose();
				converted = image;
				
				// technique #3
				// ImageFilter filter = new GrayFilter(true, 75);
				// ImageProducer producer = new
				// FilteredImageSource(colorImage.getSource(), filter);
				// Image mage = this.createImage(producer);
				
			}
			catch (Throwable e) {
			}
			final BufferedImage image = (converted != null) ? converted : src;
			
			applet.removeAll();
			applet.setIgnoreRepaint(false);
			
			Canvas canvas = new Canvas() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 8493852179266447783L;
				
				public void paint(Graphics g1) {
					Graphics2D g = (Graphics2D) g1;
					
					// draw game image
					g.drawImage(image, 0, 0, null);
					
					// draw text
					g.setColor(Color.YELLOW);
					g.setFont(new Font("Verdana", Font.BOLD, 12));
					g.drawString("Game has been ended", 10, 25);
					g.drawString("Thank you for playing!", 10, 45);
					g.drawString("Visit http://www.goldenstudios.or.id/", 10,
					        75);
					g.drawString("For free game engine!", 10, 95);
					g.drawString("This game is developed with GTGE v"
					        + BaseGame.GTGE_VERSION, 10, 115);
				}
			};
			canvas.setSize(applet.getSize());
			canvas.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					try {
						applet.getAppletContext().showDocument(
						        new URL("http://goldenstudios.or.id/"));
					}
					catch (Exception excp) {
					}
				}
			});
			
			applet.add(canvas);
			applet.repaint();
			canvas.repaint();
		}
	}
	
	/**
	 * Renders information when the game is not in focused. Subclasses may
	 * override this method to render whatever they wish when the game is not in
	 * focus, but this method provides a sensible default rendering. Any data
	 * rendered here will render on top of the previous invocation of
	 * {@link Renderable#render(Graphics2D)}, in other words, the sequence
	 * {@link Renderable#render(Graphics2D)}, followed by
	 * {@link #renderLostFocus(Graphics2D)}, will occur if the current
	 * {@link BaseGame} instance is not in focus (otherwise the
	 * {@link #renderLostFocus(Graphics2D)} method is not invoked and only
	 * {@link Renderable#render(Graphics2D)} will be invoked).
	 * 
	 * @see #setPauseOnLostFocus(boolean)
	 */
	public void renderLostFocus(Graphics2D g) {
		String st1 = "GAME IS NOT IN FOCUSED", st2 = "CLICK HERE TO GET THE FOCUS BACK";
		
		g.setFont(new Font("Dialog", Font.BOLD, 15));
		FontMetrics fm = g.getFontMetrics();
		
		int posy = (this.bsGraphics.getSize().height / 2)
		        - ((fm.getHeight() + 10) * (2 / 2));
		
		int x = (this.bsGraphics.getSize().width / 2)
		        - (fm.stringWidth(st2) / 2) - 20, y = posy - 25, width = fm
		        .stringWidth(st2) + 40, height = fm.getHeight()
		        + fm.getHeight() + 30;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width - 1, height - 1);
		g.setColor(Color.RED);
		g.drawRect(x, y, width - 1, height - 1);
		
		this.inFocusBlink = !this.inFocusBlink;
		
		if (!this.inFocusBlink) {
			try {
				// for smoooth text :)
				((Graphics2D) g).setRenderingHint(
				        RenderingHints.KEY_TEXT_ANTIALIASING,
				        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			catch (Exception e) {
			}
			
			g.setColor(Color.RED);
			g.drawString(st1, (this.bsGraphics.getSize().width / 2)
			        - (fm.stringWidth(st1) / 2), posy);
			posy += fm.getHeight() + 10;
			g.drawString(st2, (this.bsGraphics.getSize().width / 2)
			        - (fm.stringWidth(st2) / 2), posy);
		}
	}
	
	/**
	 * Sets whether the game is paused when the game is lost the input focus or
	 * not. By default only applet game that paused when the game is lost the
	 * focus.
	 * 
	 * @see #renderLostFocus(Graphics2D)
	 */
	public final void setPauseOnLostFocus(boolean pauseOnLostFocus) {
		this.pauseOnLostFocus = pauseOnLostFocus;
		
		if (this.pauseOnLostFocus == false) {
			// if not paused on lost focus, make sure the game is in focus
			this.setInFocus(true);
		}
	}
	
	void startGameLoop() {
		// before play, runs garbage collector to clear unused memory
		System.gc();
		System.runFinalization();
		
		// start the timer
		this.bsTimer.startTimer();
		this.bsTimer.reset();
		
		long elapsedTime = 0;
		out: while (true) {
			if (this.isInFocus()) {
				// update game
				this.update(elapsedTime);
				this.bsInput.update(elapsedTime); // update input
				
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
				if (!this.running) {
					// if not running, quit this game
					break out;
				}
				
				// graphics operation
				Graphics2D g = this.bsGraphics.getBackBuffer();
				
				this.render(g); // render game
				
				if (!this.isInFocus()) {
					this.renderLostFocus(g);
				}
				
			} while (this.bsGraphics.flip() == false);
			
			elapsedTime = this.bsTimer.sleep();
			
			if (elapsedTime > 100) {
				// the elapsedTime can't be lower than 100 (10 fps)
				// it's a workaround so the movement is not too jumpy
				elapsedTime = 100;
			}
		}
		
		// stop the timer
		this.bsTimer.stopTimer();
		this.bsSound.stopAll();
		this.bsMusic.stopAll();
		
		if (this.finish) {
			this.bsGraphics.cleanup();
			this.notifyExit();
		}
	}
	
	public final void setInFocus(boolean inFocus) {
		this.inFocus = inFocus;
	}
	
	public final boolean isInFocus() {
		return inFocus;
	}
	
	/**
	 * Sets whether or not this {@link BaseGame} instance should invoke
	 * {@link System#exit(int) exit} when {@link #finish()} is invoked to end
	 * the {@link Game}. Defaults to true.
	 * 
	 * @param exitOnFinish True if this {@link BaseGame} instance should invoke
	 *        {@link System#exit(int) exit} when {@link #finish()} is invoked to
	 *        end the {@link Game}, false otherwise.
	 */
	public final void setExitOnFinish(boolean exitOnFinish) {
		this.exitOnFinish = exitOnFinish;
	}
	
	public final BaseGraphics getBaseGraphics() {
		return bsGraphics;
	}
	
	public final void setBaseGraphics(final BaseGraphics baseGraphics) {
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
	 * Transfers engines from the given non-null source {@link Game} instance to
	 * the given non-null target {@link Game} instance.
	 * @param source The non-null source {@link Game} instance to retreive the
	 *        engines from.
	 * @param target The non-null target {@link Game} instance to set the
	 *        engines into.
	 * @throws NullPointerException Throws a {@link NullPointerException} if
	 *         either {@link Game} instance is null.
	 * @throws IllegalArgumentException Throws an
	 *         {@link IllegalArgumentException} if the copying of an engine
	 *         results in an {@link IllegalArgumentException}.
	 */
	static void transferEngines(Game source, Game target) {
		target.setBaseGraphics(source.getBaseGraphics());
		target.setBaseIO(source.getBaseIO());
		target.setBaseLoader(source.getBaseLoader());
		target.setBaseInput(source.getBaseInput());
		target.setBaseTimer(source.getBaseTimer());
		target.setMusicEngine(source.getMusicEngine());
		target.setSoundEngine(source.getSoundEngine());
		target.setFontManager(source.getFontManager());
	}
	
	public final void setRunning(boolean running) {
		this.running = running;
	}
	
	public Game getParent() {
		return parent;
	}
}
