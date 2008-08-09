/**
 * 
 */
package com.golden.gamedev;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.engine.BaseGraphics;
import com.golden.gamedev.engine.BaseIO;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.BaseLoader;
import com.golden.gamedev.engine.BaseTimer;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.GameFontManager;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.util.ImageUtil;
import com.golden.gamedev.util.Utility;

/**
 * The <tt>BaseGame</tt> class provides an abstract class with common
 * functionality for creating games via the GTGE framework. <br />
 * <br />
 * The main purpose of this class is to provide a superclass for both
 * {@link Game} and {@link GameObject} to extend, as they previously had much of
 * the exact same functionality. This will avoid code replication, as both will
 * rely upon the methods inherited from this class. <br />
 * <br />
 * The user of the GTGE framework is still required to extend from either
 * {@link GameObject} or {@link Game} to have their game run as part of the GTGE
 * framework, as <tt>BaseGame</tt> is not referenced directly in the GTGE
 * framework.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Game
 * @see GameObject
 */
public abstract class BaseGame {
	
	/** Graphics engine. */
	private BaseGraphics bsGraphics;
	
	/** Input engine. */
	private BaseInput bsInput;
	
	/** I/O file engine. */
	private BaseIO bsIO;
	
	/** Image loader engine. */
	private BaseLoader bsLoader;
	
	/** Audio engine for music. */
	private BaseAudio bsMusic;
	
	/** Audio engine for sound. */
	private BaseAudio bsSound;
	
	/** Timer engine. */
	private BaseTimer bsTimer;
	
	/** Font manager. */
	private GameFontManager fontManager;
	
	/**
	 * Returns whether the mouse pointer is inside specified screen boundary.
	 */
	public boolean checkPosMouse(int x1, int y1, int x2, int y2) {
		return (this.getMouseX() >= x1 && this.getMouseY() >= y1
		        && this.getMouseX() <= x2 && this.getMouseY() <= y2);
	}
	
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
	 */
	public boolean click() {
		return this.getBsInput().isMousePressed(MouseEvent.BUTTON1);
	}
	
	/**
	 * Draws game frame-per-second (FPS) to specified location.
	 */
	public void drawFPS(Graphics2D g, int x, int y) {
		this.getFontManager().getFont("FPS Font").drawString(g,
		        "FPS = " + this.getCurrentFPS() + "/" + this.getFPS(), x, y);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#getCurrentFPS()
	 * bsTimer.getCurrentFPS()}.
	 */
	public int getCurrentFPS() {
		return this.getBsTimer().getCurrentFPS();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
	 * bsGraphics.getSize().height}.
	 */
	public int getHeight() {
		return this.getBsGraphics().getSize().height;
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImage(String)
	 * bsLoader.getImage(String)}.
	 */
	public BufferedImage getImage(String imagefile) {
		return this.getBsLoader().getImage(imagefile);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImage(String, boolean)
	 * bsLoader.getImage(String, boolean)}.
	 */
	public BufferedImage getImage(String imagefile, boolean useMask) {
		return this.getBsLoader().getImage(imagefile, useMask);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImages(String, int, int)
	 * bsLoader.getImages(String, int, int)}.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row) {
		return this.getBsLoader().getImages(imagefile, col, row);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#getImages(String, int, int, boolean)
	 * bsLoader.getImages(String, int, int, boolean)}.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, boolean useMask) {
		return this.getBsLoader().getImages(imagefile, col, row, useMask);
	}
	
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
		BufferedImage[] image = this.getBsLoader().getStoredImages(mapping);
		
		if (image == null) {
			BufferedImage[] src = this.getImages(imagefile, col, row, useMask);
			int count = end - start + 1;
			image = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				image[i] = src[start + i];
			}
			this.getBsLoader().storeImages(mapping, image);
		}
		
		return image;
	}
	
	/**
	 * Returns stripped images with specified sequence.
	 * <p>
	 * 
	 * First the image is stripped by column and row, and then the images is
	 * arranged with specified sequence order. The images then stored into cache ({@linkplain com.golden.gamedev.engine.BaseLoader bsLoader})
	 * with key as followed: the image file + sequence + digit.
	 * <p>
	 * 
	 * For example:
	 * 
	 * <pre>
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
		BufferedImage[] image = this.getBsLoader().getStoredImages(mapping);
		
		if (image == null) {
			BufferedImage[] src = this.getImages(imagefile, col, row, useMask);
			int count = sequence.length() / digit;
			image = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				image[i] = src[Integer.parseInt(sequence.substring(i * digit,
				        ((i + 1) * digit)))];
			}
			this.getBsLoader().storeImages(mapping, image);
		}
		
		return image;
	}
	
	/**
	 * Same as {@linkplain #getImages(String, int, int, int, int)
	 * getImages(imagefile, col, row, useMask, start, end)} with mask color is
	 * turned on by default.
	 */
	public BufferedImage[] getImages(String imagefile, int col, int row, int start, int end) {
		return this.getImages(imagefile, col, row, true, start, end);
	}
	
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
	 */
	public int getMouseX() {
		return this.getBsInput().getMouseX();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#getMouseY()
	 * bsInput.getMouseY()}.
	 */
	public int getMouseY() {
		return this.getBsInput().getMouseY();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.util.Utility#getRandom(int, int)
	 * Utility.getRandom(int, int)}
	 */
	public int getRandom(int low, int hi) {
		return Utility.getRandom(low, hi);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseGraphics#getSize()
	 * bsGraphics.getSize().width}.
	 */
	public int getWidth() {
		return this.getBsGraphics().getSize().width;
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#setMouseVisible(boolean)
	 * bsInput.setMouseVisible(false)}.
	 */
	public void hideCursor() {
		this.getBsInput().setMouseVisible(false);
	}
	
	/**
	 * All game resources initialization, everything that usually goes to
	 * constructor should be put in here.
	 * <p>
	 * 
	 * This method is called only once for every newly created class to be used
	 * as a game within the GTGE framework.
	 * 
	 * @see #getImage(String)
	 * @see #getImages(String, int, int)
	 * @see #playMusic(String)
	 * @see #setMaskColor(Color)
	 * @see com.golden.gamedev.object
	 */
	public abstract void initResources();
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyDown(int)
	 * bsInput.isKeyDown(int)}.
	 */
	public boolean keyDown(int keyCode) {
		return this.getBsInput().isKeyDown(keyCode);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isKeyPressed(int)
	 * bsInput.isKeyPressed(int)}.
	 */
	public boolean keyPressed(int keyCode) {
		return this.getBsInput().isKeyPressed(keyCode);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseAudio#play(String)
	 * bsMusic.play(String)}.
	 * 
	 * @see com.golden.gamedev.engine.BaseAudio#setBaseRenderer(com.golden.gamedev.engine.BaseAudioRenderer)
	 * @see com.golden.gamedev.engine.audio
	 */
	public int playMusic(String audiofile) {
		return this.getBsMusic().play(audiofile);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseAudio#play(String)
	 * bsSound.play(String)}.
	 * 
	 * @see com.golden.gamedev.engine.BaseAudio#setBaseRenderer(com.golden.gamedev.engine.BaseAudioRenderer)
	 * @see com.golden.gamedev.engine.audio
	 */
	public int playSound(String audiofile) {
		return this.getBsSound().play(audiofile);
	}
	
	/**
	 * Renders game to the screen.
	 * 
	 * @param g backbuffer graphics context
	 */
	public abstract void render(Graphics2D g);
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#isMousePressed(int)
	 * bsInput.isMousePressed(java.awt.event.MouseEvent.BUTTON3)}.
	 */
	public boolean rightClick() {
		return this.getBsInput().isMousePressed(MouseEvent.BUTTON3);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseLoader#setMaskColor(Color)
	 * bsLoader.setMaskColor(java.awt.Color)}.
	 */
	public void setMaskColor(Color c) {
		this.getBsLoader().setMaskColor(c);
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseInput#setMouseVisible(boolean)
	 * bsInput.setMouseVisible(true)}.
	 */
	public void showCursor() {
		this.getBsInput().setMouseVisible(true);
	}
	
	/**
	 * Returns a new created buffered image which the current game state is
	 * rendered into it.
	 */
	public BufferedImage takeScreenShot() {
		BufferedImage screen = ImageUtil.createImage(this.getWidth(), this
		        .getHeight(), Transparency.OPAQUE);
		Graphics2D g = screen.createGraphics();
		this.render(g);
		g.dispose();
		
		return screen;
	}
	
	/**
	 * Captures current game screen into specified file.
	 * 
	 * @see #takeScreenShot()
	 */
	public void takeScreenShot(File f) {
		ImageUtil.saveImage(this.takeScreenShot(), f);
	}
	
	/**
	 * Updates game variables.
	 * 
	 * @see #keyDown(int)
	 * @see #keyPressed(int)
	 */
	public abstract void update(long elapsedTime);
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#getFPS()}.
	 */
	public int getFPS() {
		return this.getBsTimer().getFPS();
	}
	
	/**
	 * Effectively equivalent to the call
	 * {@linkplain com.golden.gamedev.engine.BaseTimer#setFPS(int)
	 * bsTimer.setFPS(int)}.
	 */
	public void setFPS(int fps) {
		this.getBsTimer().setFPS(fps);
	}
	
	public void setBsGraphics(BaseGraphics bsGraphics) {
		this.bsGraphics = bsGraphics;
	}
	
	public BaseGraphics getBsGraphics() {
		return this.bsGraphics;
	}
	
	public void setBsInput(BaseInput bsInput) {
		this.bsInput = bsInput;
	}
	
	public BaseInput getBsInput() {
		return this.bsInput;
	}
	
	public void setBsIO(BaseIO bsIO) {
		this.bsIO = bsIO;
	}
	
	public BaseIO getBsIO() {
		return this.bsIO;
	}
	
	public void setBsLoader(BaseLoader bsLoader) {
		this.bsLoader = bsLoader;
	}
	
	public BaseLoader getBsLoader() {
		return this.bsLoader;
	}
	
	public void setBsMusic(BaseAudio bsMusic) {
		this.bsMusic = bsMusic;
	}
	
	public BaseAudio getBsMusic() {
		return this.bsMusic;
	}
	
	public void setBsSound(BaseAudio bsSound) {
		this.bsSound = bsSound;
	}
	
	public BaseAudio getBsSound() {
		return this.bsSound;
	}
	
	public void setBsTimer(BaseTimer bsTimer) {
		this.bsTimer = bsTimer;
	}
	
	public BaseTimer getBsTimer() {
		return this.bsTimer;
	}
	
	public void setFontManager(GameFontManager fontManager) {
		this.fontManager = fontManager;
	}
	
	public GameFontManager getFontManager() {
		return this.fontManager;
	}
}
