package org.mk.training.jdbc;

import java.sql.SQLException;

public class StaleConnectionException extends SQLException {

	/** The serialVersionUID */
	private static final long serialVersionUID = -2789276182969659546L;

	public StaleConnectionException(SQLException e) {
		super(e.getMessage(), e.getSQLState(), e.getErrorCode());

	}

	public StaleConnectionException() {
		super();
	}

	public StaleConnectionException(String reason, String SQLState,
			int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public StaleConnectionException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public StaleConnectionException(String reason) {
		super(reason);

	}

}
