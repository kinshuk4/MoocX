package org.mk.training.jdbc;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.sql.DataSource;
import javax.transaction.RollbackException;

public class WrapperDataSource implements Referenceable, DataSource,
        Serializable {

    static final long serialVersionUID = 3570285419164793501L;
    private final BaseWrapperManagedConnectionFactory mcf;
    private final ConnectionManager cm;
    private Reference reference;

    public WrapperDataSource(final BaseWrapperManagedConnectionFactory mcf,
            final ConnectionManager cm) {
        this.mcf = mcf;
        this.cm = cm;
        System.out.println("WrapperDataSource");
        System.out.println("MCF Used" + mcf);
    }

    public PrintWriter getLogWriter() throws SQLException {
        // TODO: implement this javax.sql.DataSource method
        return null;
    }

    public void setLogWriter(PrintWriter param1) throws SQLException {
        // TODO: implement this javax.sql.DataSource method
    }

    public int getLoginTimeout() throws SQLException {
        // TODO: implement this javax.sql.DataSource method
        return 0;
    }

    public void setLoginTimeout(int param1) throws SQLException {
        // TODO: implement this javax.sql.DataSource method
    }

    public Connection getConnection() throws SQLException {
        System.out.println("WrapperDataSource().getConnection()");
        try {
            WrappedConnection wc = (WrappedConnection) cm.allocateConnection(
                    mcf, null);
            wc.setDataSource(this);
            return wc;
        } catch (ResourceException re) {
            throw new SQLException(re);
        }
    }

    public Connection getConnection(String user, String password)
            throws SQLException {
        ConnectionRequestInfo cri = new WrappedConnectionRequestInfo(user,
                password);
        try {
            WrappedConnection wc = (WrappedConnection) cm.allocateConnection(
                    mcf, cri);
            wc.setDataSource(this);
            return wc;
        } catch (ResourceException re) {
            throw new SQLException(re);
        }
    }

    public void setReference(final Reference reference) {
        this.reference = reference;
    }

    public Reference getReference() {
        return reference;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
           return null;
    }
}
