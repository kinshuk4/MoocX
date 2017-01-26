package org.mk.training.jdbc.vendor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Logger;
import org.mk.training.jdbc.ValidConnectionChecker;

public class OracleValidConnectionChecker implements ValidConnectionChecker,
		Serializable {
	private static final long serialVersionUID = -2227528634302168877L;

	private static final Logger log = Logger.getLogger(OracleValidConnectionChecker.class.getName());

	private Method ping;

	// The timeout (apparently the timeout is ignored?)
	private static Object[] params = new Object[] { new Integer(5000) };

	public OracleValidConnectionChecker() {
		try {
			Class oracleConnection = Thread.currentThread()
					.getContextClassLoader().loadClass(
							"oracle.jdbc.driver.OracleConnection");
			ping = oracleConnection.getMethod("pingDatabase",
					new Class[] { Integer.TYPE });
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to resolve pingDatabase method:", e);
		}
	}

	public SQLException isValidConnection(Connection c) {
		try {
			Integer status = (Integer) ping.invoke(c, params);

			// Error
			if (status.intValue() < 0)
				return new SQLException("pingDatabase failed status=" + status);
		} catch (Exception e) {
			// What do we do here? Assume it is a misconfiguration
			log.warning("Unexpected error in pingDatabase"+e);
		}

		// OK
		return null;
	}
}
