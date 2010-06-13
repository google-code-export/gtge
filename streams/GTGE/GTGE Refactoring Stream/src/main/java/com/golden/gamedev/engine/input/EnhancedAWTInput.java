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
package com.golden.gamedev.engine.input;

// JFC
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.BitSet;

public class EnhancedAWTInput extends AWTInput {
	
	private BitSet keyDown = new BitSet();
	
	public EnhancedAWTInput(Component comp) {
		super(comp);
	}
	
	protected InputListener createInputListener() {
		return new EnhancedInputListener();
	}
	
	/**
	 * @deprecated This method is deprecated in favor of {@link #reset()} and
	 *             will be removed in 0.2.5.
	 */
	public void refresh() {
		super.refresh();
		
		this.keyDown.clear();
	}
	
	public void reset() {
		refresh();
	}
	
	/**
	 * @deprecated The method is deprecated with no direct replacement and will
	 *             be removed in 0.2.5 - {@link #getKeyDown()} should not be
	 *             invoked, only the individual query methods (i.e.
	 *             {@link #isKeyDown(int)}) should be used.
	 */
	public boolean[] getKeyDown() {
		throw new UnsupportedOperationException(
		        "Enhanced AWT Input use BitSet boolean, "
		                + "use getKeyDownBitSet() instead.");
	}
	
	/**
	 * @deprecated The method is deprecated with no direct replacement and will
	 *             be removed in 0.2.5 - {@link #getKeyDownBitSet()} should not
	 *             be invoked, only the individual query methods (i.e.
	 *             {@link #isKeyDown(int)}) should be used.
	 */
	public BitSet getKeyDownBitSet() {
		return this.keyDown;
	}
	
	public boolean isKeyDown(int keyCode) {
		return this.keyDown.get(keyCode);
	}
	
	protected class EnhancedInputListener extends InputListener {
		
		public void keyPressed(KeyEvent e) {
			if (!EnhancedAWTInput.this.keyDown.get(e.getKeyCode())) {
				EnhancedAWTInput.this.keyDown.set(e.getKeyCode());
				
				EnhancedAWTInput.this.keyPressed[EnhancedAWTInput.this.pressedKey] = e
				        .getKeyCode();
				EnhancedAWTInput.this.pressedKey++;
			}
			
			e.consume();
		}
		
		public void keyReleased(KeyEvent e) {
			EnhancedAWTInput.this.keyDown.clear(e.getKeyCode());
			
			EnhancedAWTInput.this.keyReleased[EnhancedAWTInput.this.releasedKey] = e
			        .getKeyCode();
			EnhancedAWTInput.this.releasedKey++;
			
			e.consume();
		}
		
	}
	
}
