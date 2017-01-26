package org.mk.training.jdbc;

import javax.resource.spi.ConnectionRequestInfo;

public class WrappedConnectionRequestInfo implements ConnectionRequestInfo {
	private final String user;

	private final String password;

	public WrappedConnectionRequestInfo(final String user, final String password) {
		this.user = user;
		this.password = password;
	}

	public int hashCode() {
		return ((user == null) ? 37 : user.hashCode()) + 37
				* ((password == null) ? 37 : password.hashCode());
	}

	public boolean equals(Object other) {
		if (other == null
				|| !(other.getClass() == WrappedConnectionRequestInfo.class)) {
			return false;
		}
		WrappedConnectionRequestInfo cri = (WrappedConnectionRequestInfo) other;
		if (user == null) {
			if (cri.getUserName() != null) {
				return false;
			}
		} else {
			if (!user.equals(cri.getUserName())) {
				return false;
			}
		}
		if (password == null) {
			if (cri.getPassword() != null) {
				return false;
			}
		} else {
			if (!password.equals(cri.getPassword())) {
				return false;
			}
		}
		return true;
	}

	String getUserName() {
		return user;
	}

	String getPassword() {
		return password;
	}
}
