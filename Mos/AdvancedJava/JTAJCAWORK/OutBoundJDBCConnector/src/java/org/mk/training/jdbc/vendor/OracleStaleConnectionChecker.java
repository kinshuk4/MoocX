package org.mk.training.jdbc.vendor;

import java.sql.SQLException;

import org.mk.training.jdbc.StaleConnectionChecker;

public class OracleStaleConnectionChecker implements StaleConnectionChecker {

	public boolean isStaleConnection(SQLException e) {

		final int error_code = Math.abs(e.getErrorCode());

		if ((error_code == 1014) || (error_code == 1033)
				|| (error_code == 1034)) {
			return true;
		}

		return false;
	}

}
