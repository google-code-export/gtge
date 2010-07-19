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
import java.awt.Graphics2D;

/**
 * Extending <code>Game</code> class functionality to be able to handle multiple
 * game screen in order to separate game logic into separated entities. For
 * example: manage intro screen, title screen, menu screen, and main game screen
 * as separated entity.
 * <p>
 * 
 * Each game entity is an instance of the {@link com.golden.gamedev.game.Game}
 * interface (in above example: intro, title, main game screen are instances of
 * the {@link com.golden.gamedev.game.Game} interface class).
 * <p>
 * 
 * The first game to be played is the {@link com.golden.gamedev.game.Game} which
 * uses ID = 0.
 * <p>
 * 
 * <code>GameEngine</code> class also can be used to store global variables that
 * can be accessed within all game object entities.
 * <p>
 * 
 * Example how-to-use <code>GameEngine</code> class:
 * 
 * <pre>
 * import com.golden.gamedev.*;
 * 
 * public class YourGame extends GameEngine {
 * 	
 * 	public Game getGame(int GameID) {
 *       switch (GameID) {
 *          case 0: // GameID = 0 is always the first to play
 *             return new IntroMenu(this);
 *          case 1: return new ......
 *          case 2: return new ......
 *       }
 *       return null;
 *    }
 * 	
 * 	public static void main(String[] args) {
 * 		// GameEngine class creation is equal with Game class creation
 * 		GameLoader game = new GameLoader();
 * 		game.setup(new YourGame(), new Dimension(640, 480), false);
 * 		game.start();
 * 	}
 * }
 * 
 * public class IntroMenu extends GameObject { // change Game to GameObject
 * 
 * 	public Game(GameEngine parent) {
 * 		super(parent);
 * 	}
 * 	
 * 	public void update(long elapsedTime) {
 *       if (....) { // change screen
 *          // GameObject with ID = 1 will be played next
 *          parent.nextGameID = 1;
 *          finish();
 *       }
 *    }
 * }
 * 
 * 
 * 
 * 
 * 
 * </pre>
 * 
 * @deprecated Use {@link com.golden.gamedev.game.GameEngine} instead of this
 *             class. This class will be removed in GTGE 0.2.5.
 */
public abstract class GameEngine extends com.golden.gamedev.game.GameEngine {
	// Intentionally blank
}
