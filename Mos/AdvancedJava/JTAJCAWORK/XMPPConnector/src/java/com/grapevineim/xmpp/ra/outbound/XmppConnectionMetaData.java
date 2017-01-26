package com.grapevineim.xmpp.ra.outbound;

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.cci.ConnectionMetaData;

/**
 * This class provides information about an EIS instance connected through a
 * Connection instance.
 */

public class XmppConnectionMetaData implements ConnectionMetaData, Serializable {

	private static final long serialVersionUID = -1;
	private String userName = "undefined";
	private String EISProductName = "undefined";
	private String EISProductVersion = "undefined";

	public XmppConnectionMetaData() {
	}

	public XmppConnectionMetaData(String userName, String EISProductName,
			String EISProductVersion) {
		this.userName = userName;
		this.EISProductName = EISProductName;
		this.EISProductVersion = EISProductVersion;
	}

	public String getEISProductName() throws ResourceException {
		return EISProductName;
	}

	public String getEISProductVersion() {
		return EISProductVersion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEISProductName(String EISProductName) {
		this.EISProductName = EISProductName;
	}

	public void setEISProductVersion(String EISProductVersion) {
		this.EISProductVersion = EISProductVersion;
	}
}
