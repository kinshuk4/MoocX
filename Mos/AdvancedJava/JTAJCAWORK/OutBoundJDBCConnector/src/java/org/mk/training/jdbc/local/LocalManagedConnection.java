package org.mk.training.jdbc.local;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;
import javax.transaction.xa.XAResource;

import org.mk.training.jdbc.BaseWrapperManagedConnection;

public class LocalManagedConnection extends BaseWrapperManagedConnection
        implements LocalTransaction {

    public LocalManagedConnection(final LocalManagedConnectionFactory mcf,
            final Connection con, final Properties props,
            final int transactionIsolation, final int psCacheSize)
            throws SQLException {
        super(mcf, con, props, transactionIsolation, psCacheSize);
        System.out.println("LocalManagedConnection(mcf, con, props, transactionIsolation, psCacheSize)");
    }

    public LocalTransaction getLocalTransaction() throws ResourceException {
        System.out.println("LocalManagedConnection.getLocalTransaction()");
        return this;
    }

    public XAResource getXAResource() throws ResourceException {
        throw new ResourceException("Local tx only!");
    }

    public void commit() throws ResourceException {
        System.out.println("LocalManagedConnection.commit()");
        synchronized (stateLock) {
            if (inManagedTransaction) {
                inManagedTransaction = false;
            }
        }
        try {
            con.commit();
        } catch (SQLException e) {
            checkException(e);
        }
    }

    public void rollback() throws ResourceException {
        System.out.println("LocalManagedConnection.rollback()");
        synchronized (stateLock) {
            if (inManagedTransaction) {
                inManagedTransaction = false;
            }
        }
        try {
            con.rollback();
        } catch (SQLException e) {
            try {
                checkException(e);
            } catch (Exception e2) {
            }
        }
    }

    public void begin() throws ResourceException {
        System.out.println("LocalManagedConnection.begin()");
        System.out.println("This method ensures that managed transaction is true and app"
                + "components cannot overrule that");
        synchronized (stateLock) {
            if (inManagedTransaction == false) {
                try {
                    if (underlyingAutoCommit) {
                        underlyingAutoCommit = false;
                        con.setAutoCommit(false);
                    }
                    checkState();
                    inManagedTransaction = true;
                } catch (SQLException e) {
                    checkException(e);
                }
            } else {
                throw new ResourceException(
                        "Trying to begin a nested local tx");
            }
        }
    }

    @Override
    public String toString() {
        return "Physical Connection:"+con+" props:"+props+super.toString();
    }
}
