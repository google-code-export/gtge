/**
 * 
 */
package com.golden.gamedev;

/**
 * The {@link Refreshable} interface is a backwards-compatible interface
 * specifying the behavior of the {@link Resettable} interface, but with a
 * differently named method, {@link #refresh()}.
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @deprecated Deprecated in favor of {@link Resettable} and will be removed in
 *             0.2.5
 */
public interface Refreshable extends Resettable {
	
	/**
	 * @deprecated This method is deprecated and will be renamed in 0.2.5 to
	 *             {@link #reset()} via the super interface {@link Resettable}.
	 * @see Resettable
	 * @see Resettable#reset()
	 */
	void refresh();
}
