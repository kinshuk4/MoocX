package org.mk.training.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ValidConnectionChecker {
	/**
	 * Checks the connection is valid
	 * 
	 * @param c
	 *            the connection
	 * @return Exception when not valid, null when valid
	 */
	SQLException isValidConnection(Connection c);
}
