package com.golden.gamedev;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;

import mock.java.lang.MockSecurityManager;

import junit.framework.TestCase;

import com.golden.gamedev.engine.graphics.WindowedMode;

/**
 * The {@link GameTest} class provides a {@link TestCase} extension to test the
 * functionality of the {@link Game} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * @see Game
 * @see TestCase
 * 
 */
public class GameTest extends TestCase {
	
	private MockGame game;
	
	protected void setUp() throws Exception {
		game = new MockGame();
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#Game()}.
	 */
	public final void testGame() {
		assertNotNull(game);
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#stop()}.
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public final void testStop() throws InterruptedException,
	        InvocationTargetException {
		assertFalse(game.isRunning());
		game.bsGraphics = new WindowedMode(new Dimension(5, 5), false);
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		// Pause this thread for a bit to allow the game to start.
		Thread.sleep(100);
		assertTrue(game.isRunning());
		game.stop();
		assertFalse(game.isRunning());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#finish()}.
	 * @throws InterruptedException
	 */
	public final void testFinish() throws InterruptedException {
		assertFalse(game.isRunning());
		assertFalse(game.isFinish());
		game.bsGraphics = new WindowedMode(new Dimension(5, 5), false);
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		// Pause this thread for a bit to allow the game to start.
		Thread.sleep(100);
		assertTrue(game.isRunning());
		assertFalse(game.isFinish());
		game.finish();
		assertTrue(game.isFinish());
		assertFalse(game.isRunning());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#isFinish()}.
	 */
	public final void testIsFinish() {
		// Testing of the isFinish method is really done in testFinish().
		assertFalse(game.isFinish());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#isRunning()}.
	 */
	public final void testIsRunning() {
		// Both testStop() and testFinish() provide a better test of the
		// isRunning method.
		assertFalse(game.isRunning());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#start()}.
	 */
	public final void testStart() throws Exception {
		game.finish();
		assertTrue(game.isFinish());
		game.start();
		assertTrue(game.isFinish());
		game = new MockGame();
		assertFalse(game.isRunning());
		assertFalse(game.isFinish());
		game.bsGraphics = new WindowedMode(new Dimension(5, 5), false);
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		// Pause this thread for a bit to allow the game to start.
		Thread.sleep(100);
		assertTrue(game.isRunning());
		assertFalse(game.isFinish());
		game.start();
		game.stop();
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		Thread.sleep(100);
		game.stop();
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		Thread.sleep(100);
		game.finish();
		assertTrue(game.isFinish());
		assertFalse(game.isRunning());
		// Make the default quitting of the game throw an exception, which is
		// caught and then ignored.
		// This should be thrown in the final release candidate - don't sqeltch
		// exceptions!
		SecurityManager manager = System.getSecurityManager();
		System.setSecurityManager(new MockSecurityManager());
		game = new MockGame();
		assertFalse(game.isRunning());
		assertFalse(game.isFinish());
		game.bsGraphics = new WindowedMode(new Dimension(5, 5), false);
		new Thread(new Runnable() {
			
			public void run() {
				game.start();
			}
		}).start();
		// Pause this thread for a bit to allow the game to start.
		Thread.sleep(100);
		assertTrue(game.isRunning());
		assertFalse(game.isFinish());
		game.finish();
		System.setSecurityManager(manager);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#setPauseOnLostFocus(boolean)}.
	 */
	public final void testSetPauseOnLostFocus() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#isPauseOnLostFocus()}.
	 */
	public final void testIsPauseOnLostFocus() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#initResources()}.
	 */
	public final void testInitResources() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#update(long)}.
	 */
	public final void testUpdate() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#render(java.awt.Graphics2D)}.
	 */
	public final void testRender() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#isDistribute()}.
	 */
	public final void testIsDistribute() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#showLogo()}.
	 */
	public final void testShowLogo() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getRandom(int, int)}.
	 */
	public final void testGetRandom() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getWidth()}.
	 */
	public final void testGetWidth() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getHeight()}.
	 */
	public final void testGetHeight() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#takeScreenShot()}.
	 */
	public final void testTakeScreenShot() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#takeScreenShot(java.io.File)}.
	 */
	public final void testTakeScreenShotFile() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#playMusic(java.lang.String)}.
	 */
	public final void testPlayMusic() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#playSound(java.lang.String)}.
	 */
	public final void testPlaySound() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#setFPS(int)}.
	 */
	public final void testSetFPS() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getCurrentFPS()}.
	 */
	public final void testGetCurrentFPS() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getFPS()}.
	 */
	public final void testGetFPS() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#drawFPS(java.awt.Graphics2D, int, int)}.
	 */
	public final void testDrawFPS() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getMouseX()}.
	 */
	public final void testGetMouseX() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getMouseY()}.
	 */
	public final void testGetMouseY() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#checkPosMouse(int, int, int, int)}.
	 */
	public final void testCheckPosMouseIntIntIntInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#checkPosMouse(com.golden.gamedev.object.Sprite, boolean)}
	 * .
	 */
	public final void testCheckPosMouseSpriteBoolean() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#checkPosMouse(com.golden.gamedev.object.SpriteGroup, boolean)}
	 * .
	 */
	public final void testCheckPosMouseSpriteGroupBoolean() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#checkPosMouse(com.golden.gamedev.object.PlayField, boolean)}
	 * .
	 */
	public final void testCheckPosMousePlayFieldBoolean() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#click()}.
	 */
	public final void testClick() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#rightClick()}.
	 */
	public final void testRightClick() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#keyDown(int)}.
	 */
	public final void testKeyDown() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#keyPressed(int)}.
	 */
	public final void testKeyPressed() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#hideCursor()}.
	 */
	public final void testHideCursor() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#showCursor()}.
	 */
	public final void testShowCursor() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#setMaskColor(java.awt.Color)}.
	 */
	public final void testSetMaskColor() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImage(java.lang.String, boolean)}.
	 */
	public final void testGetImageStringBoolean() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImage(java.lang.String)}.
	 */
	public final void testGetImageString() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int, boolean)}
	 * .
	 */
	public final void testGetImagesStringIntIntBoolean() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int)}.
	 */
	public final void testGetImagesStringIntInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int, boolean, java.lang.String, int)}
	 * .
	 */
	public final void testGetImagesStringIntIntBooleanStringInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int, java.lang.String, int)}
	 * .
	 */
	public final void testGetImagesStringIntIntStringInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int, boolean, int, int)}
	 * .
	 */
	public final void testGetImagesStringIntIntBooleanIntInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#getImages(java.lang.String, int, int, int, int)}
	 * .
	 */
	public final void testGetImagesStringIntIntIntInt() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.Game#getResourceLoader()}.
	 */
	public final void testGetResourceLoader() {
		// TODO: implement test.
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.Game#setResourceLoader(com.golden.gamedev.engine.resource.ResourceLoader)}
	 * .
	 */
	public final void testSetResourceLoader() {
		// TODO: implement test.
	}
	
}
