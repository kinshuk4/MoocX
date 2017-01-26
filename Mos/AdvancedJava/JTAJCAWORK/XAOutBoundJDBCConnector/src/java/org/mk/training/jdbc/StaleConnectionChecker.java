package org.mk.training.jdbc;

import java.sql.SQLException;

public interface StaleConnectionChecker {

	/**
	 * Is the connection stale
	 * 
	 * @param e
	 *            the <code>java.sql.SQLException</code>
	 * @return is the connection stale
	 */
	public boolean isStaleConnection(SQLException e);

}
