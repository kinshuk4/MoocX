package org.mk.training.jdbc;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementAccess {
	/**
	 * Get the underlying statement
	 * 
	 * @return the underlying statement
	 * @throws SQLException
	 *             when already closed
	 */
	Statement getUnderlyingStatement() throws SQLException;
}
