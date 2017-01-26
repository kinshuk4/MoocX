package org.mk.training.jdbc;

import java.sql.SQLException;

public class NullExceptionSorter implements ExceptionSorter {
	public boolean isExceptionFatal(SQLException e) {
		return false;
	}
}
