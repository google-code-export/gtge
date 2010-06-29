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

// JFC
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * <code>WindowExitListener</code> class is a dummy window listener class that
 * that forcing Java Virtual Machine to quit by calling
 * <code>System.exit()</code>.
 * <p>
 * 
 * This window listener is used by all <code>java.awt.Frame</code> class in this
 * graphics engine package.
 * </p>
 * <br />
 * <br />
 * 
 * As of 0.2.4, {@link WindowExitListener} has been altered to extend the
 * {@link WindowAdapter} class instead of implementing {@link WindowListener}
 * directly, but its functionality remains the same and is backwards-compatible
 * with the 0.2.3 version.
 */
public final class WindowExitListener extends WindowAdapter {
	
	/**
	 * The singleton instance of the {@link WindowExitListener} class to use.
	 */
	public static final WindowListener INSTANCE = new WindowExitListener();
	
	/**
	 * Returns <code>WindowExitListener</code> singleton instance.
	 * @return The singleton instance.
	 * @deprecated As of 0.2.4, this method is deprecated with no direct
	 *             replacement - use {@link #INSTANCE} directly as an
	 *             alternative to invoking this method. This method will be
	 *             removed in 0.2.5.
	 */
	public static WindowListener getInstance() {
		return WindowExitListener.INSTANCE;
	}
	
	/**
	 * Creates a new {@link WindowExitListener} instance, marked private to
	 * avoid instantiation except via the singleton {@link #INSTANCE}.
	 */
	private WindowExitListener() {
		super();
	}
	
	/**
	 * Calls <code>System.exit(0)</code> to force Java Virtual Machine to quit
	 * when the close button of the parent object is pressed.
	 */
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
