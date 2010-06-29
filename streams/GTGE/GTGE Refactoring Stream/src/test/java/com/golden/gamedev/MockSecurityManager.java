/**
 * 
 */
package com.golden.gamedev;

import java.security.Permission;

/**
 * The {@link MockSecurityManager} class provides a {@link SecurityManager}
 * implementation for testing purposes only.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see SecurityManager
 * 
 */
public final class MockSecurityManager extends SecurityManager {
	
	/**
	 * Whether or not to allow {@link #checkExit(int)} to proceed. Defaults to
	 * false.
	 */
	public boolean allowExit = false;
	
	/**
	 * The {@link Integer} specifying the last status code sent via the
	 * {@link #checkExit(int)} method. Defaults to null.
	 */
	public Integer exitStatusCode;
	
	/**
	 * Creates a new {@link MockSecurityManager} instance.
	 */
	public MockSecurityManager() {
		super();
	}
	
	public void checkExit(int status) {
		exitStatusCode = new Integer(status);
		if (!allowExit) {
			super.checkExit(status);
			throw new SecurityException("Unable to exit!");
		}
	}
	
	public void checkPermission(Permission perm) {
		// Nothing is true, everything is permitted.
	}
}
