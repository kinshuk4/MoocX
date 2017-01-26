/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.api;

import javax.resource.cci.ConnectionSpec;

/**
 * This implementation class is used by an application component to pass
 * connection-specific info/properties to the getConnection method in the
 * JavaMailConnectionFactoryImpl class.
 * This class is implemented as a JavaBeans component.
 */
public abstract class ConnectionSpecImpl implements ConnectionSpec {

    protected String userName;
    protected String password;
    protected String serverName;
    protected String protocol = "smtp";
    protected boolean secure;
    protected String port;
    protected String fromAddress;

    /**
     * ConnectionSpecImpl constructor (no arguments).
     */
    public ConnectionSpecImpl() {
        this(null, null, null, "smtp", true, null, null);
    }

    public ConnectionSpecImpl(String userName, String password, String serverName, String protocol, boolean secure, String port, String fromAddress) {
        this.userName = userName;
        this.password = password;
        this.serverName = serverName;
        if (protocol != null) {
            this.protocol = protocol;
        }
        this.secure = secure;
        this.port = port;
        this.fromAddress = fromAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        if (protocol != null) {
            this.protocol = protocol;
        }
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    @Override
    public String toString() {
        return "userName:" + userName + " password:" + password + " serverName:" + serverName
                + " port:" + port + " protocol:" + protocol + " fromAddress:" + fromAddress;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof ConnectionSpecImpl) {
            ConnectionSpecImpl other = (ConnectionSpecImpl) obj;
            if(this==other) equal=true;
            else equal = (this.userName).equals(other.userName)
                    && (this.password).equals(other.password)
                    && (this.serverName).equals(other.serverName)
                    && (this.protocol).equals(other.protocol);
        }
        else equal=false;
        System.out.println("ConnectionSpecImpl.equals():"+equal);
        return equal;
    }

    @Override
    public int hashCode() {
        int hashcode = new String("").hashCode();

        if (userName != null) {
            hashcode += userName.hashCode();
        }

        if (password != null) {
            hashcode += password.hashCode();
        }

        if (serverName != null) {
            hashcode += serverName.hashCode();
        }

        if (protocol != null) {
            hashcode += protocol.hashCode();
        }
        return hashcode;
    }
}
