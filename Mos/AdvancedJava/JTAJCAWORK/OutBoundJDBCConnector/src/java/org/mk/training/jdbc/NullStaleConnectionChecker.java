package org.mk.training.jdbc;

import java.sql.SQLException;

public class NullStaleConnectionChecker implements StaleConnectionChecker {

	public boolean isStaleConnection(SQLException e) {
		return false;
	}

}
