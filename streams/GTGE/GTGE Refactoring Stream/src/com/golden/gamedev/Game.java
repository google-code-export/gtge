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

import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.DefaultBaseAudio;
import com.golden.gamedev.engine.DefaultBaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.DefaultBaseLoader;
import com.golden.gamedev.engine.audio.MidiRenderer;
import com.golden.gamedev.engine.audio.WaveRenderer;
import com.golden.gamedev.engine.input.AWTInput;
import com.golden.gamedev.engine.timer.SystemTimer;
import com.golden.gamedev.funbox.ErrorNotificationDialog;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFont;
import com.golden.gamedev.object.DefaultGameFontManager;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.util.ImageUtil;

/**
 * <code>Game</code> class is <b>Golden T Game Engine (GTGE) core class</b>
 * that initializes all GTGE game engines, wrap the engines up, and setup the
 * basic game frame work to be play on.
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
 * </pre>
 * 
 * <p>
 * 
 * There are two main tasks of <code>Game</code> class that we need to know :
 * <br>
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
	
	public static final String GAME_FOCUS_MESSAGE_LINE_2 = "CLICK HERE TO GET THE FOCUS BACK";
	
	public static final String GAME_FOCUS_MESSAGE_LINE_1 = "GAME IS NOT IN FOCUS";
	
	/**
	 * The default frames per second count of a <tt>Game</tt> instance (100).
	 */
	public static final int DEFAULT_FPS = 100;
	
	/** ***************************** GAME ENGINES ****************************** */
	
	/** **************************** GAME VARIABLES ***************************** */
	
	/**
	 * Whether or not this <tt>Game</tt> instance is currently running.
	 */
	private boolean running;
	
	/**
	 * Whether or not this <tt>Game</tt> instance has ended.
	 */
	private boolean finish;
	
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
	private boolean distribute;
	
	private GameFont fpsFont;
	
	/**
	 * Whether or not this <tt>Game</tt> instance has been initialized.
	 */
	private boolean initialized;
	
	/**
	 * Whether or not this <tt>Game</tt> instance has the "focus" of the
	 * operating system (i.e. it is occupying the whole window, or the user has
	 * clicked on the window/applet containing this <tt>Game</tt> instance).
	 */
	private boolean inFocus = true;
	
	/**
	 * Whether or not this <tt>Game</tt> instance's focus message is enabled
	 * and should be displayed on the screen.
	 */
	private boolean gameFocusMessageEnabled = true;
	
	private boolean pauseOnLostFocus = false;
	
	/** ************************************************************************* */
	/** ***************************** CONSTRUCTOR ******************************* */
	/** ************************************************************************* */
	
	/**
	 * Creates new instance of <code>Game</code> class, please <b>see note</b>
	 * below.
	 * <p>
	 * 
	 * Note: <b>Do not</b> make any overloading constructors. All that belong
	 * to constructor (this method) should be put in {@link #initResources()}
	 * method. <b>Leave this method empty and simply do not use constructor!</b>
	 * 
	 * @see #initResources()
	 * @see #update(long)
	 * @see #render(Graphics2D)
	 */
	public Game() {
	}
	
	/** ************************************************************************* */
	/** *********************** START / STOP OPERATION ************************** */
	/** ************************************************************************* */
	
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
	public void stop() {
		this.setRunning(false);
	}
	
	/**
	 * End the game and back to operating system.
	 * <p>
	 * 
	 * Only call this method when the game has been finished playing. Calling
	 * this method will immediatelly makes the game to quit and the game can not
	 * be resumed/played anymore.
	 * 
	 * @see #stop()
	 */
	public void finish() {
		this.setFinish(true);
		this.stop();
	}
	
	/**
	 * Returns true, if the game has been finished playing and the game is about
	 * to return back to operating system.
	 */
	public boolean isFinish() {
		return this.finish;
	}
	
	/**
	 * Returns whether the game is currently running/playing or not. Running
	 * game means the game is in game main-loop (update and render loop).
	 * 
	 * @see #start()
	 */
	public boolean isRunning() {
		return this.running;
	}
	
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
	 * @see #initEngine()
	 * @see #distribute
	 * @see #notifyError(Throwable)
	 * @see #notifyExit()
	 */
	public final void start() {
		if (this.isRunning() || this.isFinish()) {
			return;
		}
		this.setRunning(true);
		
		if (this.isDistribute()) {
			// the game has been distributed
			// catch any unexpected/uncaught exception!
			// the logo is shown in initialize() method
			try {
				if (this.isInitialized() == false) {
					this.initialize();
					this.setInitialized(true);
				}
				
				this.runGame();
			}
			catch (Throwable e) {
				this.notifyError(e);
			}
			
		}
		else { // still in development
			if (this.isInitialized() == false) {
				this.setInitialized(true);
				this.initialize();
			}
			
			this.runGame();
		}
	}
	
	private void initialize() {
		if (this.getBsGraphics() instanceof Applet) {
			// applet game need to make sure that the applet is being focused
			// when playing the game
			// this makes the players can browse on the net while playing
			this.setPauseOnLostFocus(true);
		}
		
		// init all engines
		this.initEngine();
		
		try {
			this.getBsGraphics().getComponent().addFocusListener(
			        new FocusListener() {
				        
				        public void focusGained(FocusEvent e) {
					        Game.this.setInFocus(true);
				        }
				        
				        public void focusLost(FocusEvent e) {
					        if (Game.this.isPauseOnLostFocus()) {
						        Game.this.setInFocus(false);
					        }
				        }
			        });
		}
		catch (Exception e) {
		}
		
		if (isDistribute()) {
			// show GTGE splash screen :-)
			this.showLogo();
		}
		
		// load fps font
		try {
			URL fontURL = com.golden.gamedev.Game.class.getResource("Game.fnt");
			BufferedImage fpsImage = ImageUtil.getImage(fontURL);
			
			this.setFpsFont(this.getFontManager().getFont(fpsImage));
			this.getFontManager().removeFont(fpsImage); // unload the image
			this.getFontManager().putFont("FPS Font", this.getFpsFont());
			
			if (isDistribute()) {
				// if splash screen is shown (distribute = true)
				// fps font is not used anymore
				// remove the reference!
				// however the font still exists via fontManager.getFont("FPS
				// Font");
				this.setFpsFont(null);
			}
			
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
	}
	
	/** ************************************************************************* */
	/** ************************ GAME LOOP THREAD ******************************* */
	/** ************************************************************************* */
	
	protected void runGame() {
		executeBeforeGameLoop();
		
		executeGameLoop();
		
		executeAfterGameLoop();
	}
	
	protected void executeAfterGameLoop() {
		// stop the timer
		this.getBsTimer().stopTimer();
		this.getBsSound().stopAll();
		this.getBsMusic().stopAll();
		
		if (this.isFinish()) {
			this.getBsGraphics().cleanup();
			this.notifyExit();
		}
	}
	
	protected void executeGameLoop() {
		long elapsedTime = 0;
		while (true) {
			if (this.isInFocus()) {
				gameUpdate(elapsedTime);
				
			}
			else {
				freezeGameNotInFocus();
			}
			
			while (this.isRunning()) {
				
				// graphics operation
				Graphics2D g = this.getBsGraphics().getBackBuffer();
				
				this.render(g); // render game
				
				if (!this.isInFocus()) {
					this.renderLostFocus(g);
				}
				
				if (this.getBsGraphics().flip()) {
					break;
				}
				
			}
			if (this.isRunning()) {
				elapsedTime = this.getBsTimer().sleep();
				
				if (elapsedTime > 100) {
					// the elapsedTime can't be lower than 100 (10 fps)
					// it's a workaround so the movement is not too jumpy
					elapsedTime = 100;
				}
			}
			else {
				break;
			}
		}
	}
	
	protected void gameUpdate(long elapsedTime) {
		// update game
		this.update(elapsedTime);
		this.getBsInput().update(elapsedTime); // update input
	}
	
	protected void freezeGameNotInFocus() {
		// the game is not in focus!
		try {
			Thread.sleep(300);
		}
		catch (InterruptedException e) {
		}
	}
	
	protected void executeBeforeGameLoop() {
		// before play, runs garbage collector to clear unused memory
		System.gc();
		System.runFinalization();
		
		// start the timer
		this.getBsTimer().startTimer();
		this.getBsTimer().refresh();
	}
	
	/**
	 * Renders information when the game is not in focused.
	 * 
	 * @see #setPauseOnLostFocus(boolean)
	 */
	protected void renderLostFocus(Graphics2D g) {
		g.setFont(new Font("Dialog", Font.BOLD, 15));
		FontMetrics fm = g.getFontMetrics();
		
		int posy = (this.getHeight() / 2) - ((fm.getHeight() + 10) * (2 / 2));
		
		int x = (this.getWidth() / 2)
		        - (fm.stringWidth(GAME_FOCUS_MESSAGE_LINE_2) / 2) - 20, y = posy - 25, width = fm
		        .stringWidth(GAME_FOCUS_MESSAGE_LINE_2) + 40, height = fm
		        .getHeight()
		        + fm.getHeight() + 30;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width - 1, height - 1);
		g.setColor(Color.RED);
		g.drawRect(x, y, width - 1, height - 1);
		
		this.setGameFocusMessageEnabled(!this.isGameFocusMessageEnabled());
		
		if (this.isGameFocusMessageEnabled()) {
			try {
				// for smoooth text :)
				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
			catch (Exception e) {
			}
			
			g.setColor(Color.RED);
			g.drawString(GAME_FOCUS_MESSAGE_LINE_1, (this.getWidth() / 2)
			        - (fm.stringWidth(GAME_FOCUS_MESSAGE_LINE_1) / 2), posy);
			posy += fm.getHeight() + 10;
			g.drawString(GAME_FOCUS_MESSAGE_LINE_2, (this.getWidth() / 2)
			        - (fm.stringWidth(GAME_FOCUS_MESSAGE_LINE_2) / 2), posy);
		}
	}
	
	/**
	 * Sets whether the game is paused when the game is lost the input focus or
	 * not. By default only applet game that paused when the game is lost the
	 * focus.
	 * 
	 * @see #renderLostFocus(Graphics2D)
	 */
	public void setPauseOnLostFocus(boolean b) {
		this.pauseOnLostFocus = b;
		
		if (this.pauseOnLostFocus == false) {
			// if not paused on lost focus, make sure the game is in focus
			this.setInFocus(true);
		}
	}
	
	/**
	 * Returns whether whether the game is paused when the game is lost the
	 * input focus or not.
	 * 
	 * @see #renderLostFocus(Graphics2D)
	 */
	public boolean isPauseOnLostFocus() {
		return this.pauseOnLostFocus;
	}
	
	/** ************************************************************************* */
	/** ********************* GAME ENGINE INITIALIZATION ************************ */
	/** ************************************************************************* */
	
	/**
	 * Game engines is initialized in this method. <br>
	 * Thus modifying or changing any game engines should be done within this
	 * method.
	 * <p>
	 * 
	 * List of default game engines initialized in this method :
	 * <ul>
	 * <li> Timer Engine : uses
	 * {@link com.golden.gamedev.engine.timer.SystemTimer}</li>
	 * <li> Input Engine : uses {@link com.golden.gamedev.engine.input.AWTInput}</li>
	 * <li> Music Engine : uses
	 * {@link com.golden.gamedev.engine.audio.MidiRenderer}</li>
	 * <li> Sound Engine : uses
	 * {@link com.golden.gamedev.engine.audio.WaveRenderer}</li>
	 * <li> I/O Engine : uses {@link com.golden.gamedev.engine.DefaultBaseIO}</li>
	 * <li> Image Engine : uses
	 * {@link com.golden.gamedev.engine.DefaultBaseLoader}</li>
	 * </ul>
	 * <p>
	 * 
	 * Example how to modify or change the default game engine :
	 * 
	 * <pre>
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
		if (this.getBsTimer() == null) {
			this.setBsTimer(new SystemTimer()); // GageTimer(); // LoraxTimer();
			// //
		}
		if (this.getBsIO() == null) {
			this.setBsIO(new DefaultBaseIO(this.getClass()));
		}
		if (this.getBsLoader() == null) {
			this.setBsLoader(new DefaultBaseLoader(this.getBsIO(),
			        Color.MAGENTA));
		}
		if (this.getBsInput() == null) {
			this.setBsInput(new AWTInput(this.getBsGraphics().getComponent()));
		}
		if (this.getBsMusic() == null) {
			this.setBsMusic(new DefaultBaseAudio(this.getBsIO(),
			        new MidiRenderer()));
			this.getBsMusic().setExclusive(true);
			this.getBsMusic().setLoop(true);
		}
		if (this.getBsSound() == null) {
			this.setBsSound(new DefaultBaseAudio(this.getBsIO(),
			        new WaveRenderer()));
		}
		
		// miscellanous
		// set default fps
		this.getBsTimer().setFPS(Game.DEFAULT_FPS);
		
		// set background screen size
		Background.screen = this.getBsGraphics().getSize();
		
		// creates font manager
		if (this.getFontManager() == null) {
			this.setFontManager(new DefaultGameFontManager());
		}
		
		// locale = Locale.getDefault();
	}
	
	/** ************************************************************************* */
	/** ***************************** MAIN METHODS ****************************** */
	/** ************************************************************************* */
	
	/**
	 * Notified when the game is about to quit. By default this method is
	 * calling <code>System.exit()</code> to ensure everything is properly
	 * shut down.
	 * <p>
	 * 
	 * Override this method to create a custom exit dialog, and be sure to call
	 * <code>System.exit()</code> at the end.
	 */
	protected void notifyExit() {
		if ((this.getBsGraphics() instanceof Applet) == false) {
			// non-applet game should call System.exit(0);
			try {
				System.exit(0);
			}
			catch (Exception e) {
			}
			
		}
		else {
			// applet game should display to the user
			// that the game has been ended
			final Applet applet = (Applet) this.getBsGraphics();
			BufferedImage src = ImageUtil.createImage(this.getWidth(), this
			        .getHeight());
			Graphics2D g = src.createGraphics();
			
			try {
				// fill background
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				// play with transparency a bit
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, 0.8f));
				
				// draw in a circle only
				Shape shape = new java.awt.geom.Ellipse2D.Float(
				        this.getWidth() / 10, this.getHeight() / 10, this
				                .getWidth()
				                - (this.getWidth() / 10 * 2), this.getHeight()
				                - (this.getHeight() / 10 * 2));
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
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
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
					        + Game.GTGE_VERSION, 10, 115);
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
		new ErrorNotificationDialog(error, this.getBsGraphics(), this
		        .getClass().getName(), null);
	}
	
	/**
	 * Returns whether this game is ready to distribute or still in development
	 * stage.
	 * 
	 * @see #distribute
	 */
	public boolean isDistribute() {
		return distribute;
	}
	
	/** ************************************************************************* */
	/** ***************************** SHOW LOGO ********************************* */
	/** ************************************************************************* */
	
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
		this.hideCursor();
		SystemTimer dummyTimer = new SystemTimer();
		dummyTimer.setFPS(20);
		this.getBsInput().refresh();
		
		// loading GTGE logo for splash screen
		BufferedImage logo = null;
		try {
			URL logoURL = com.golden.gamedev.Game.class.getResource("Game.dat");
			BufferedImage orig = ImageUtil.getImage(logoURL);
			
			logo = ImageUtil.resize(orig, this.getWidth(), this.getHeight());
			
			orig.flush();
			orig = null;
		}
		catch (Exception e) {
			this.bailOut();
		}
		
		// time to show GTGE splash screen!
		// clear background with black color
		// and wait for a second
		try {
			this.clearScreen(Color.BLACK);
			Thread.sleep(1000L);
		}
		catch (InterruptedException e) {
		}
		
		// check for focus owner
		if (!this.isInFocus()) {
			while (!this.isInFocus()) {
				// the game is not in focus!
				Graphics2D g = this.getBsGraphics().getBackBuffer();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				this.renderLostFocus(g);
				this.getBsGraphics().flip();
				
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
				}
			}
			
			this.getBsInput().refresh();
			
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
				if (!this.isRunning()) {
					return;
				}
				Graphics2D g = this.getBsGraphics().getBackBuffer();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				Composite old = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, alpha));
				g.drawImage(logo, 0, 0, null);
				g.setComposite(old);
			} while (this.getBsGraphics().flip() == false);
			
			if (firstTime) {
				// workaround for OpenGL mode
				firstTime = false;
				dummyTimer.refresh();
			}
			
			long elapsedTime = dummyTimer.sleep();
			double increment = 0.00065 * elapsedTime;
			if (increment > 0.22) {
				increment = 0.22 + (increment / 6);
			}
			alpha += increment;
			
			if (this.isSkip(elapsedTime)) {
				this.clearScreen(Color.BLACK);
				logo.flush();
				logo = null;
				return;
			}
		}
		
		// show the shiny logo for 2500 ms :-)
		do {
			if (!this.isRunning()) {
				return;
			}
			Graphics2D g = this.getBsGraphics().getBackBuffer();
			
			g.drawImage(logo, 0, 0, null);
		} while (this.getBsGraphics().flip() == false);
		
		int i = 0;
		while (i++ < 50) { // 50 x 50 = 2500
			if (!this.isRunning()) {
				return;
			}
			
			try {
				Thread.sleep(50L);
			}
			catch (InterruptedException e) {
			}
			
			if (this.isSkip(50)) {
				this.clearScreen(Color.BLACK);
				logo.flush();
				logo = null;
				return;
			}
		}
		
		// gradually disappeared
		alpha = 1.0f;
		dummyTimer.refresh();
		while (alpha > 0.0f) {
			do {
				if (!this.isRunning()) {
					return;
				}
				Graphics2D g = this.getBsGraphics().getBackBuffer();
				
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				Composite old = g.getComposite();
				g.setComposite(AlphaComposite.getInstance(
				        AlphaComposite.SRC_OVER, alpha));
				g.drawImage(logo, 0, 0, null);
				g.setComposite(old);
			} while (this.getBsGraphics().flip() == false);
			
			long elapsedTime = dummyTimer.sleep();
			double decrement = 0.00055 * elapsedTime;
			if (decrement > 0.15) {
				decrement = 0.15 + ((decrement - 0.04) / 2);
			}
			alpha -= decrement;
			
			if (this.isSkip(elapsedTime)) {
				this.clearScreen(Color.BLACK);
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
			this.clearScreen(Color.BLACK);
			Thread.sleep(100L);
		}
		catch (InterruptedException e) {
		}
	}
	
	private boolean isSkip(long elapsedTime) {
		boolean skip = (this.getBsInput().getKeyPressed() != BaseInput.NO_KEY || this
		        .getBsInput().getMousePressed() != BaseInput.NO_BUTTON);
		this.getBsInput().update(elapsedTime);
		
		return skip;
	}
	
	private void clearScreen(Color col) {
		Graphics2D g = this.getBsGraphics().getBackBuffer();
		g.setColor(col);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.getBsGraphics().flip();
		
		g = this.getBsGraphics().getBackBuffer();
		g.setColor(col);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		this.getBsGraphics().flip();
	}
	
	/** ************************************************************************* */
	/** ************************* GTGE VALIDATION ******************************* */
	/** ************************************************************************* */
	
	private void bailOut() {
		try {
			URL fontURL = com.golden.gamedev.Game.class.getResource("Game.fnt");
			BufferedImage fpsImage = ImageUtil.getImage(fontURL);
			
			this.setFontManager(new DefaultGameFontManager());
			GameFont font = this.getFontManager().getFont(fpsImage);
			
			// clear background with red color
			// and write cracked version!
			Graphics2D g = this.getBsGraphics().getBackBuffer();
			
			g.setColor(Color.RED.darker());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			font.drawString(g, "THIS GAME IS USING", 10, 10);
			font.drawString(g, "GTGE CRACKED VERSION!!", 10, 30);
			font.drawString(g, "PLEASE REPORT THIS GAME TO", 10, 50);
			font.drawString(g, "WWW.GOLDENSTUDIOS.OR.ID", 10, 70);
			font.drawString(g, "THANK YOU....", 10, 105);
			
			this.getBsGraphics().flip();
			
			// wait for 8 seconds
			this.setBsInput(new AWTInput(this.getBsGraphics().getComponent()));
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
	
	/** ************************************************************************* */
	/** ***************** BELOW THIS LINE IS ENGINES UTILIZE ******************** */
	/** ***************** (PASTE INTO GAME OBJECT CLASS) ******************** */
	/** ************************************************************************* */
	
	/**
	 * Returns sprite in specified sprite group that intersected with mouse
	 * pointer, or null if no sprite intersected with mouse pointer.
	 * 
	 * @param field playfield to check its intersection with mouse pointer
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
	
	public void setFpsFont(GameFont fpsFont) {
		this.fpsFont = fpsFont;
	}
	
	public GameFont getFpsFont() {
		return fpsFont;
	}
	
	/**
	 * Sets whether or not this <tt>Game</tt> instance is currently running.
	 * It is recommended to use {@link #start()} and {@link #stop()} to make the
	 * <tt>Game</tt> instance start and stop respectively, as this method is
	 * only included to encapsulate the field. If this method needs to be used,
	 * invoke {@link #runGame()} to resume the game instead of using
	 * {@link #start()}, which executes this automatically.
	 * @param running Whether or not this <tt>Game</tt> instance is currently
	 *        running.
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Sets whether or not this <tt>Game</tt> instance has ended. Normally,
	 * this method should not be used, as {@link #finish()} should be called to
	 * end the <tt>Game</tt> instance. However, in the rare circumstances that
	 * this method is needed to be used, two methods must be overridden: the
	 * {@link #notifyExit()} method of the <tt>Game</tt> class and the
	 * {@link BaseGraphics#cleanup()} method of the {@link BaseGraphics}
	 * instance this game uses via {@link #getBsGraphics()}. Overriding the
	 * exit behavior and refusing the cleaning up of resources means that the
	 * game may launch again if both {@link #isFinish()} and
	 * {@link #isRunning()} return false, via the {@link #start()} method. If
	 * the game is to be re-initialized when {@link #start()} is called, invoke
	 * {@link #setInitialized(boolean)} with a value of false before calling
	 * {@link #start()}.
	 * @param finish Whether or not this <tt>Game</tt> instance has ended.
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	public void setDistribute(boolean distribute) {
		this.distribute = distribute;
	}
	
	/**
	 * Sets whether or not the current <tt>Game</tt> instance has been
	 * initialized. Note: this has the effect of calling
	 * {@link #initResources()} in the {@link #start()} method if this is set to
	 * true.
	 * @param initialized Whether or not this <tt>Game</tt> instance has been
	 *        initialized.
	 */
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	/**
	 * Gets whether or not the current <tt>Game</tt> instance has been
	 * initialized.
	 * @return Whether or not this <tt>Game</tt> instance has been
	 *         initialized.
	 */
	public boolean isInitialized() {
		return initialized;
	}
	
	/**
	 * Sets whether or not this <tt>Game</tt> instance has the "focus" of the
	 * operating system (i.e. it is occupying the whole window, or the user has
	 * clicked on the window/applet containing this <tt>Game</tt> instance).
	 * @param inFocus Whether or not this <tt>Game</tt> instance has the
	 *        "focus" of the operating system (i.e. it is occupying the whole
	 *        window, or the user has clicked on the window/applet containing
	 *        this <tt>Game</tt> instance).
	 */
	public void setInFocus(boolean inFocus) {
		this.inFocus = inFocus;
	}
	
	/**
	 * Gets whether or not this <tt>Game</tt> instance has the "focus" of the
	 * operating system (i.e. it is occupying the whole window, or the user has
	 * clicked on the window/applet containing this <tt>Game</tt> instance).
	 * @return Whether or not this <tt>Game</tt> instance has the "focus" of
	 *         the operating system (i.e. it is occupying the whole window, or
	 *         the user has clicked on the window/applet containing this
	 *         <tt>Game</tt> instance).
	 */
	public boolean isInFocus() {
		return inFocus;
	}
	
	/**
	 * Sets whether or not this <tt>Game</tt> instance's focus message is
	 * enabled and should be displayed on the screen. It is not recommended to
	 * use this method, as the default implementation of
	 * {@link #renderLostFocus(Graphics2D)} handles this automatically to
	 * simulate a blinking message.
	 * @param gameFocusMessageEnabled Whether or not this <tt>Game</tt>
	 *        instance's focus message is enabled and should be displayed on the
	 *        screen.
	 */
	public void setGameFocusMessageEnabled(boolean gameFocusMessageEnabled) {
		this.gameFocusMessageEnabled = gameFocusMessageEnabled;
	}
	
	/**
	 * Gets whether or not this <tt>Game</tt> instance's focus message is
	 * enabled and should be displayed on the screen.
	 * @return Whether or not this <tt>Game</tt> instance's focus message is
	 *         enabled and should be displayed on the screen.
	 */
	public boolean isGameFocusMessageEnabled() {
		return gameFocusMessageEnabled;
	}
	
}
