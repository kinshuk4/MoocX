package org.mk.training.jdbc.local;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import java.util.logging.Level;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.security.auth.Subject;
import org.mk.training.jdbc.BaseWrapperManagedConnectionFactory;

public class LocalManagedConnectionFactory extends BaseWrapperManagedConnectionFactory {

    static final long serialVersionUID = 4698955390505160469L;
    private String driverClass;
    private transient Driver driver;
    private String connectionURL;
    protected String connectionProperties;

    public LocalManagedConnectionFactory() {
        System.out.println("ManagedConnectionFactory()");
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getURL() {
        return connectionURL;
    }

    public void setURL(final String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public synchronized void setDriverClass(final String driverClass) {
        this.driverClass = driverClass;
        driver = null;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        System.out.println("LocalManagedConnectionFactory().setConnectionProperties():: " + connectionProperties);
        this.connectionProperties = connectionProperties;
        connectionProps.clear();
        if (connectionProperties != null) {
            connectionProperties = connectionProperties.replaceAll("\\\\",
                    "\\\\\\\\");
            InputStream is = new ByteArrayInputStream(connectionProperties.getBytes());
            try {
                connectionProps.load(is);
            } catch (IOException ioe) {
                throw new RuntimeException(
                        "Could not load connection properties", ioe);
            }
        }
    }

    public ManagedConnection createManagedConnection(Subject subject,
            ConnectionRequestInfo cri) throws javax.resource.ResourceException {
        Properties props = getConnectionProperties(subject, cri);
        Properties cprops = (Properties) props.clone();
        try {
            String url = getURL();
            Driver d = getDriver(url);
            Connection con = d.connect(url, props);
            if (con == null) {
                throw new ResourceException(
                        "Wrong driver class for this connection URL:" + url);
            }
            ManagedConnection mcon=new LocalManagedConnection(this, con, props,
                    transactionIsolation, preparedStatementCacheSize);
            System.out.println(mcon);
            return mcon;
        } catch (Exception e) {
            throw new ResourceException("Could not create connection", e);
        }
    }

    public ManagedConnection matchManagedConnections(final Set mcs,
            final Subject subject, final ConnectionRequestInfo cri)
            throws ResourceException {
        Properties newProps = getConnectionProperties(subject, cri);
        for (Iterator i = mcs.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o instanceof LocalManagedConnection) {
                LocalManagedConnection mc = (LocalManagedConnection) o;
                // First check the properties
                if (mc.getProperties().equals(newProps)) {
                    // Next check to see if we are validating on
                    // matchManagedConnections
                    if ((getValidateOnMatch() && mc.checkValid())
                            || !getValidateOnMatch()) {
                        return mc;
                    }
                }
            }
        }
        return null;
    }

    public int hashCode() {
        int result = 17;
        result = result * 37
                + ((connectionURL == null) ? 0 : connectionURL.hashCode());
        result = result * 37
                + ((driverClass == null) ? 0 : driverClass.hashCode());
        result = result * 37 + ((userName == null) ? 0 : userName.hashCode());
        result = result * 37 + ((password == null) ? 0 : password.hashCode());
        result = result * 37 + transactionIsolation;
        return result;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        LocalManagedConnectionFactory otherMcf = (LocalManagedConnectionFactory) other;
        return this.connectionURL.equals(otherMcf.connectionURL)
                && this.driverClass.equals(otherMcf.driverClass)
                && ((this.userName == null) ? otherMcf.userName == null
                : this.userName.equals(otherMcf.userName))
                && ((this.password == null) ? otherMcf.password == null
                : this.password.equals(otherMcf.password))
                && this.transactionIsolation == otherMcf.transactionIsolation;

    }

    protected synchronized Driver getDriver(final String url)
            throws ResourceException {
        boolean trace = log.isLoggable(Level.ALL);
        // don't bother if it is loaded already
        if (driver != null) {
            return driver;
        }
        if (driverClass == null) {
            throw new ResourceException("No Driver class specified!");
        }
        try {
            Class clazz = Class.forName(driverClass, true, Thread.currentThread().getContextClassLoader());
            driver = (Driver) clazz.newInstance();
            DriverManager.registerDriver(driver);
            return driver;
        } catch (Exception e) {
            throw new ResourceException("Failed to register driver for: "
                    + driverClass, e);
        }
    }

    protected String internalGetConnectionURL() {
        return connectionURL;
    }

    @Override
    public String toString() {
        return "connectionURL:" + connectionURL + " userName" + userName + " password:" + password
                + "\n driverClass:" + driverClass + " connectionProperties:" + connectionProperties + super.toString();
    }
}
