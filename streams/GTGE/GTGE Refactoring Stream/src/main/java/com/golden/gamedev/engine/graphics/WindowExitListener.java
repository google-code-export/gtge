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
package com.golden.gamedev.engine.graphics;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The <tt>WindowExitListener</tt> class is a {@link WindowListener} that only
 * actually listens for the
 * {@link WindowListener#windowClosing(WindowEvent) window closing event}, and
 * forces the Java Virtual Machine to quit when the close button of the object
 * being listened to is pressed by calling the {@link System#exit(int)} method
 * with an argument of 0.
 * 
 * @since 0.2.3
 * @version 1.1
 * 
 */
public final class WindowExitListener implements WindowListener {
	
	/**
	 * The <tt>WindowExitListener</tt> class' singleton instance.
	 */
	private static final WindowListener singleton = new WindowExitListener();
	
	/**
	 * Returns the <tt>WindowExitListener</tt> class' singleton instance.
	 * @return The <tt>WindowExitListener</tt> class' singleton instance.
	 */
	public static WindowListener getInstance() {
		return WindowExitListener.singleton;
	}
	
	/**
	 * Creates a new <tt>WindowExitListener</tt> instance.
	 */
	private WindowExitListener() {
		super();
	}
	
	/**
	 * Calls the {@link System#exit(int)} method with an argument of 0 to force
	 * the Java Virtual Machine to quit when the close button of the object
	 * being listened to is pressed.
	 */
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	
	public void windowOpened(WindowEvent e) {
	}
	
	public void windowClosed(WindowEvent e) {
	}
	
	public void windowIconified(WindowEvent e) {
	}
	
	public void windowDeiconified(WindowEvent e) {
	}
	
	public void windowActivated(WindowEvent e) {
	}
	
	public void windowDeactivated(WindowEvent e) {
	}
	
}
