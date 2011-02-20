package com.golden.gamedev;

import java.awt.Graphics2D;

/**
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.3.0
 * 
 */
public final class MockGame extends Game {
	
	public boolean resourcesInitialized = false;
	
	public long elapsedTime;
	
	public Graphics2D graphics2D;
	
	/**
	 * Creates a new {@link MockGame} instance.
	 */
	public MockGame() {
		super();
	}
	
	public void initResources() {
		resourcesInitialized = true;
	}
	
	public void update(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public void render(Graphics2D g) {
		this.graphics2D = g;
		
	}
}
