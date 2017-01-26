package org.mk.training.jdbc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public class NullValidConnectionChecker implements ValidConnectionChecker,
		Serializable {
	private static final long serialVersionUID = -223752863430216887L;

	public SQLException isValidConnection(Connection c) {
		return null;
	}
}
