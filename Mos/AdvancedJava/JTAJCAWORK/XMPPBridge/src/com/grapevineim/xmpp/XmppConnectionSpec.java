package com.grapevineim.xmpp;

import java.io.Serializable;

import javax.resource.cci.ConnectionSpec;

public abstract class XmppConnectionSpec implements ConnectionSpec,
        Serializable {

    private String host;
    private Integer port;
    private String domain;
    private String username;
    private String password;

    public XmppConnectionSpec() {
    }

    public XmppConnectionSpec(String host, Integer port, String domain, String username, String password) {
        this.host = host;
        this.port = port;
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof XmppConnectionSpec) {
                XmppConnectionSpec other = (XmppConnectionSpec) o;
                return (username.equals(other.username)
                        && host.equals(other.host)
                        && port.equals(other.port)
                        && domain.equals(other.domain));
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = "".hashCode();
        hashCode += username.hashCode();
        hashCode += host.hashCode();
        hashCode += port.hashCode();
        hashCode += domain.hashCode();
        return hashCode;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer(80);
        buffer.append(username).append(", ");
        buffer.append(host).append(", ");
        buffer.append(port).append(", ");
        buffer.append(domain);
        return buffer.toString();
    }
}
