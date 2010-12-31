package com.golden.gamedev.engine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@link MockBaseInput} class provides a {@link BaseInput} implementation
 * to be used for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see BaseInput
 * 
 */
public final class MockBaseInput implements BaseInput {
	
	/**
	 * A non-null {@link Map} of {@link Integer} keys to {@link Boolean} values
	 * representing whether or not keys are down.
	 */
	private Map keyDownMap = new HashMap();
	
	/**
	 * A non-null {@link Map} of {@link Integer} keys to {@link Boolean} values
	 * representing whether or not keys are pressed.
	 */
	private Map keyPressedMap = new LinkedHashMap();
	
	/**
	 * Creates a new {@link MockBaseInput} instance.
	 */
	public MockBaseInput() {
		super();
	}
	
	public void update(long elapsedTime) {
		throw new UnsupportedOperationException("Not currently implemented");
		
	}
	
	public void refresh() {
		clearDownKeys();
		clearPressedKeys();
	}
	
	public void cleanup() {
		throw new UnsupportedOperationException("Not currently implemented");
		
	}
	
	public void mouseMove(int x, int y) {
		throw new UnsupportedOperationException("Not currently implemented");
		
	}
	
	public boolean isMouseExists() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMouseX() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMouseY() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMouseDX() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMouseDY() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMouseReleased() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isMouseReleased(int button) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getMousePressed() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isMousePressed(int button) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isMouseDown(int button) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public void setMouseVisible(boolean visible) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isMouseVisible() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getKeyReleased() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isKeyReleased(int keyCode) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public int getKeyPressed() {
		Iterator keyIterator = keyPressedMap.keySet().iterator();
		if (keyIterator.hasNext()) {
			return ((Integer) keyIterator.next()).intValue();
		}
		return BaseInput.NO_KEY;
	}
	
	public boolean isKeyPressed(int keyCode) {
		Object result = keyPressedMap.get(new Integer(keyCode));
		return Boolean.TRUE.equals(result);
	}
	
	public boolean isKeyDown(int keyCode) {
		return Boolean.TRUE.equals(keyDownMap.get(new Integer(keyCode)));
	}
	
	public int getKeyTyped() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public boolean isKeyTyped(int keyCode) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public long getRepeatDelay() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public void setRepeatDelay(long delay) {
		throw new UnsupportedOperationException("Not currently implemented");
		
	}
	
	public long getRepeatRate() {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	public void setRepeatRate(long rate) {
		throw new UnsupportedOperationException("Not currently implemented");
	}
	
	/**
	 * Places the given integer as a key recognized as being down.
	 * @param keyCode The key code recognized as being down.
	 * @see #isKeyDown(int)
	 */
	public void placeKeyDown(final int keyCode) {
		keyDownMap.put(new Integer(keyCode), Boolean.TRUE);
	}
	
	/**
	 * Clears the currently recognized key codes of keys being down.
	 */
	public void clearDownKeys() {
		keyDownMap = new HashMap();
	}
	
	/**
	 * Places the given integer as a key recognized as being pressed.
	 * @param keyCode The key code recognized as being pressed.
	 * @see #isKeyPressed(int)
	 */
	public void placeKeyPressed(final int keyCode) {
		keyPressedMap.put(new Integer(keyCode), Boolean.TRUE);
	}
	
	/**
	 * Clears the currently recognized key codes of keys being pressed.
	 */
	public void clearPressedKeys() {
		keyPressedMap = new LinkedHashMap();
	}
}
