/**
 * 
 */
package com.golden.gamedev;

/**
 * The {@link ActionExecutor} interface specifies an {@link Object} that can
 * {@link #execute() execute} an arbitrary action. The action that is
 * {@link #execute() executed} is implementation-dependent.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * 
 */
public interface ActionExecutor {
	
	/**
	 * Executes the action of this {@link ActionExecutor} instance.
	 */
	void execute();
}
