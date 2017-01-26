package org.mk.training.jdbc.xa;

import java.sql.SQLException;
import java.util.Properties;

import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;


import org.mk.training.jdbc.BaseWrapperManagedConnection;

public class XAManagedConnection extends BaseWrapperManagedConnection implements
        XAResource, LocalTransaction {

    protected final XAConnection xaConnection;
    protected final XAResource xaResource;
    protected Xid currentXid;

    public XAManagedConnection(XAManagedConnectionFactory mcf,
            XAConnection xaConnection, Properties props,
            int transactionIsolation, int psCacheSize) throws SQLException {
        super(mcf, xaConnection.getConnection(), props, transactionIsolation,
                psCacheSize);
        System.out.println("XAManagedConnection(mcf, con, props, transactionIsolation, psCacheSize)");
        this.xaConnection = xaConnection;
        xaConnection.addConnectionEventListener(new javax.sql.ConnectionEventListener() {

            public void connectionClosed(javax.sql.ConnectionEvent ce) {
                // only we can do this, ignore
                System.out.println("XAListener.connectionClosed()::" + ce);
            }

            public void connectionErrorOccurred(
                    javax.sql.ConnectionEvent ce) {
                System.out.println("XAListener.connectionErrorOccurred()::" + ce.getSQLException());
                SQLException ex = ce.getSQLException();
                broadcastConnectionError(ex);
            }
        });
        this.xaResource = xaConnection.getXAResource();
    }

    public void begin() throws ResourceException {
        System.out.println("XAManagedConnection.begin()::LocalTransaction:: ");
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

    public void commit() throws ResourceException {
        System.out.println("XAManagedConnection.commit()::LocalTransaction:: ");
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
        System.out.println("XAManagedConnection.rollback()::LocalTransaction:: ");
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

    protected void broadcastConnectionError(SQLException e) {
        super.broadcastConnectionError(e);
    }

    public LocalTransaction getLocalTransaction() throws ResourceException {
        return this;
    }

    public XAResource getXAResource() throws ResourceException {
        return this;
    }

    public void destroy() throws ResourceException {
        System.out.println("XAManagedConnection.destroy()");
        try {
            super.destroy();
        } finally {
            try {
                xaConnection.close();
            } catch (SQLException e) {
                checkException(e);
            }
        }
    }

    public void start(Xid xid, int flags) throws XAException {
        System.out.println("XAManagedConnection.start(Xid xid, int flags)::XAResource:: "
                + xid + " flags :: " + flags);
        try {
            checkState();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            xaResource.start(xid, flags);

        } catch (XAException e) {
            // JBAS-3336 Connections that fail in enlistment should not be
            // returned
            // to the pool
            if (isFailedXA(e.errorCode)) {
                broadcastConnectionError(e);
            }

            throw e;
        }

        synchronized (stateLock) {
            currentXid = xid;
            inManagedTransaction = true;
        }
    }

    public void end(Xid xid, int flags) throws XAException {
        System.out.println("XAManagedConnection.end(Xid xid, int flags)::XAResource:: "
                + xid + "flags :: " + flags);
        try {
            xaResource.end(xid, flags);
        } catch (XAException e) {
            //Some drivers cant handle this flag.
            if (XAResource.TMSUSPEND != flags) {
                broadcastConnectionError(e);
                throw e;
            }
        }
        // we want to allow ending transactions that are not the current
        // one. When one does this, inManagedTransaction is still true.
        synchronized (stateLock) {
            if (currentXid != null && currentXid.equals(xid)) {
                inManagedTransaction = false;
                currentXid = null;
            }
        }
    }

    public int prepare(Xid xid) throws XAException {
        int rv = xaResource.prepare(xid);
        System.out.println("XAManagedConnection.prepare(Xid xid, int flags)::XAR"
                + "esources:: " + xid + " returnValue:: " + rv);
        return rv;

    }

    public void commit(Xid xid, boolean onePhase) throws XAException {
        System.out.println("XAManagedConnection.commit(Xid xid, int flags)::XAR"
                + "esource:: " + xid + " onePhase:: " + onePhase);

        xaResource.commit(xid, onePhase);
    }

    public void rollback(Xid xid) throws XAException {
        System.out.println("XAManagedConnection.rollback(Xid xid, int flags)::XAR"
                + "esource:: " + xid);

        xaResource.rollback(xid);
    }

    public void forget(Xid xid) throws XAException {
        xaResource.forget(xid);
    }

    public Xid[] recover(int flag) throws XAException {
        return xaResource.recover(flag);
    }

    public boolean isSameRM(XAResource other) throws XAException {
        Boolean overrideValue = ((XAManagedConnectionFactory) mcf).getIsSameRMOverrideValue();
        if (overrideValue != null) {
            return overrideValue.booleanValue();
        }
        // compare apples to apples
        boolean issamerm = (other instanceof XAManagedConnection) ? xaResource.isSameRM(((XAManagedConnection) other).xaResource)
                : xaResource.isSameRM(other);
        System.out.println("issamerm:" + issamerm);
        return issamerm;
    }

    public int getTransactionTimeout() throws XAException {
        return xaResource.getTransactionTimeout();
    }

    public boolean setTransactionTimeout(int seconds) throws XAException {
        return xaResource.setTransactionTimeout(seconds);
    }

    private boolean isFailedXA(int errorCode) {

        return (errorCode == XAException.XAER_RMERR || errorCode == XAException.XAER_RMFAIL);
    }
}
