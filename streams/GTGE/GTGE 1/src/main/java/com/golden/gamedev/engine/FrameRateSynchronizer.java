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
package com.golden.gamedev.engine;

import com.golden.gamedev.Game;

/**
 * The {@link FrameRateSynchronizer} interface provides a strategy for synchronizing a {@link Game} instance to render a
 * requested number of frames per second. This is needed because without this strategy, {@link Game} instances will
 * simply run with the computer's allowable processing speed, which can result in games rendering frames too fast for a
 * player to play them on newer machines.<br />
 * <br />
 * The {@link FrameRateSynchronizer} interface is typically used in a loop as in the following example:
 * 
 * <pre>
 *    public class LoopExample {
 *       .....
 *       public static void main(String[] args) {
 *          FrameRaterSynchronizer engine;  // Initialization not shown.
 *          // Set the desired frames per second.
 *          // This value may not be able to be achieved due to the hardware performance of the current machine.
 *          engine.setFps(50); // 50 FPS desired.
 *          // Begin synchronization for the desired framerate.
 *          engine.beginSynchronization();
 *          // Loop to render frames and react to user input.
 *          while (true) {
 *             // Do some tasks and render a single frame.
 *             
 *             // After frame rendering, call the delayForFrame() method and capture the delayTime.
 *             long delayTime = engine.delayForFrame();
 *             
 *             // This time represents the delay needed to ensure the frame gets enough clock cycles to render at the desired rate.
 *             // It is typically passed to updating code for the next frame's display.
 *          }
 *       }
 *    }
 * </pre>
 * 
 * @version 1.0
 * @since 1.0
 * @author MetroidFan2002 - Changed interface name and methods to reflect the actual intent of the interface, which is
 *         not to time operations but rather to maintain a constant, synchronized frame rate.
 * @author Paulus Tuerah - Original Author
 * 
 * @see SystemTimeFrameRateSynchronizer The default FrameRateSynchronizer implementation which uses the system time for
 *      frame rate delay.
 */
public interface FrameRateSynchronizer {
	
	/**
	 * Starts the {@link FrameRateSynchronizer} instance to synchronize with the current requested {@link #setFps(int)
	 * frame rate}. Because a constructor may provide the ability to set the requested frames per second directly, this
	 * method will not throw an exception if {@link #setFps(int)} is not invoked prior to its invocation, but
	 * unanticipated side effects may occur if the requested frames per second is not set beforehand.
	 * 
	 * @see #setFps(int) To set the requested frames per second.
	 */
	public void beginSynchronization();
	
	/**
	 * Delays the execution of the current {@link Thread} in order for a frame to be rendered in a bounded amount of
	 * time.
	 * 
	 * @return The amount of time delay that occurred, in milliseconds, in order to allow for the current frame to be
	 *         rendered for a single interval in order to support the {@link #setFps(int) requested frames per second}.
	 * @throws RuntimeException
	 *             Throws a {@link RuntimeException} if the delay cannot be executed due to an unexpected error.
	 */
	public long delayForFrame();
	
	/**
	 * Gets the number of frames actually rendered for the previous second.
	 * 
	 * @return The number of frames actually rendered for the previous second.
	 * @see #getFps() To retrieve the requested number of frames per second.
	 */
	public int getRenderedFps();
	
	/**
	 * Gets the requested number of frames per second. To retrieve the number of frames actually rendered for the
	 * previous second, use {@link #getRenderedFps()}.
	 * 
	 * @return The requested number of frames per second.
	 * @see #getRenderedFps() To retrieve the number of frames actually rendered for the previous second.
	 * @see #setFps(int) To set the requested number of frames per second.
	 */
	public int getFps();
	
	/**
	 * Sets the requested number of frames per second for this {@link FrameRateSynchronizer} instance. This value is a
	 * requested number of frames per second because due to system processing and hardware the current machine may not
	 * be capable of meeting this frame rate. The {@link FrameRateSynchronizer#beginSynchronization()} method should be
	 * invoked on a {@link FrameRateSynchronizer} instance immediately after changing the requested frame rate to ensure
	 * consistent results after this method is invoked.
	 * 
	 * @param fps
	 *            The requested number of frames per second, which must be greater than or equal to 1.
	 * @throws IllegalArgumentException
	 *             Throws an {@link IllegalArgumentException} if the requested number of frames per second is less than
	 *             or equal to 0.
	 * @see #getRenderedFps() To retrieve the number of frames actually rendered for the previous second.
	 * @see #beginSynchronization() To begin synchronization of this FrameRateSynchronizer instance after this method is
	 *      invoked.
	 */
	public void setFps(final int fps);
	
}
