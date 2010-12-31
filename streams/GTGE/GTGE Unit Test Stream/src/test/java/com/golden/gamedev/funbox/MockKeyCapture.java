package com.golden.gamedev.funbox;

import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.engine.MockBaseInput;

/**
 * The {@link MockKeyCapture} class provides a {@link KeyCapture} implementation
 * for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see KeyCapture
 * 
 */
public class MockKeyCapture extends KeyCapture {
	
	/**
	 * Whether or not the key was captured.
	 */
	public boolean keyCaptured = false;
	
	/**
	 * The possibly-null {@link Integer} to place into the injected
	 * {@link MockBaseInput} upon an invocation of {@link #refresh()}, if
	 * non-null.
	 */
	public Integer keyCodeToPlaceOnRefresh;
	
	/**
	 * The possibly-null {@link MockBaseInput} instance to use to place the
	 * given {@link #keyCodeToPlaceOnRefresh}.
	 */
	public MockBaseInput mockBaseInput;
	
	/**
	 * Creates a new {@link MockKeyCapture} instance with specified input, key,
	 * delay, and listener.
	 * 
	 * @param baseInput <code>BaseInput</code> associated with this key capture
	 * @param keys key code array (from <code>java.awt.event.KeyEvent</code>
	 *        class) to be captured
	 * @param delay delay for each key input in milliseconds
	 */
	public MockKeyCapture(final BaseInput baseInput, final int[] keys, int delay) {
		super(baseInput, keys, delay);
	}
	
	/**
	 * Creates a new {@link MockKeyCapture} instance with specified input, key
	 * sequence in string, delay, and listener. The string will be parsed
	 * internally before used.
	 * 
	 * @param baseInput <code>BaseInput</code> associated with this key capture
	 * @param keys string to be captured
	 * @param delay delay for each key input in milliseconds
	 */
	public MockKeyCapture(final BaseInput baseInput, final String keys,
	        final int delay) {
		super(baseInput, keys, delay);
	}
	
	public void keyCaptured() {
		keyCaptured = true;
	}
	
	public void refresh() {
		super.refresh();
		if (mockBaseInput != null && keyCodeToPlaceOnRefresh != null) {
			// Changes from a multi-code sequence to a single-code sequence just
			// to hit this code...
			setKeySequence(new int[] {
				keyCodeToPlaceOnRefresh.intValue()
			});
			mockBaseInput.placeKeyPressed(keyCodeToPlaceOnRefresh.intValue());
		}
	}
}
