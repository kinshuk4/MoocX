/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.ra.outbound;

import samples.connectors.mailconnector.api.ConnectionSpecImpl;
import java.io.*;
import java.security.Principal;
import java.util.*;
import java.util.logging.*;
import javax.resource.Referenceable;
import javax.resource.cci.*;
import javax.resource.spi.*;
import javax.resource.ResourceException;
import javax.naming.Reference;

import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import samples.connectors.mailconnector.api.*;

/**
 * This implementation class provides an interface for getting a connection 
 * to a Mail Server. It looks up a ConnectionFactory instance in the JNDI 
 * namespace and uses it to get Mail Server connections. 
 */
public class JavaMailConnectionFactoryImpl implements
        JavaMailConnectionFactory, Serializable, Referenceable {

    static Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");
    private ManagedConnectionFactory mcf;
    private ConnectionManager cm;
    // Local variables
    private Reference reference;
    private transient PrintWriter out;
    private transient int milliseconds;

    /**
     * JavaMailConnectionFactoryImpl constructor (no arguments).
     */
    public JavaMailConnectionFactoryImpl() {
    }

    /**
     * JavaMailConnectionFactoryImpl constructor.
     *
     * @param mcf  the ManagedConnectionFactory that created this 
     *             JavaMailConnectionFactory instance
     * @param cm   the ConnectionManager
     */
    public JavaMailConnectionFactoryImpl(ManagedConnectionFactory mcf,
            ConnectionManager cm) {
        logger.info(" 3. JavaMailConnectionFactoryImpl::Constructor");
        this.mcf = mcf;
        if (cm == null) {
            this.cm = new ConnectionManagerImpl();
        } else {
            this.cm = cm;
        }
    }

    /**
     * Gets a connection to the Mail Server.
     * Passes along mail server and user info.
     *
     * @return Connection	Connection instance
     */
    public JavaMailConnection createConnection()
            throws ResourceException {
        JavaMailConnection con = null;

        logger.info(" 3.- JMCFI::createConnection -- Client requested a connection. Get it from Connection Manager");

        // Use the default values of the Managed connection factory
        if (cm != null) {
            con = (JavaMailConnection) cm.allocateConnection(mcf, null);
        } else {
            con = (JavaMailConnection) mcf.createManagedConnection(null, null);
        }
        logger.info(" 6.- JMCFI::createConnection -- Returning Connection to Client");

        return con;
    }

    /**
     * Gets a connection to a Mail Server instance. A component should use 
     * the getConnection variant with a javax.resource.cci.ConnectionSpec 
     * parameter if it needs to pass any resource-adapter-specific security 
     * information and connection parameters.
     *
     * @param properties   connection parameters and security information 
     *                     specified as ConnectionSpec instance
     * @return a JavaMailConnection instance
     */
    public JavaMailConnection createConnection(ConnectionSpec properties)
            throws ResourceException {
        JavaMailConnection con = null;
        logger.info(" 3.- JMCFI::createConnection -- Client requested a connection. Get it from Connection Manager");
        final ConnectionRequestInfoImpl info = (ConnectionRequestInfoImpl) properties;
        if (cm != null) {
            con = (JavaMailConnection) cm.allocateConnection(mcf, info);
        } else {
            con = (JavaMailConnection) this.mcf.createManagedConnection(new Subject(true, Collections.singleton(new Principal() {

                public String getName() {
                    return info.getUserName();
                }
            }), Collections.EMPTY_SET, Collections.singleton(new PasswordCredential(info.getUserName(), info.getPassword().toCharArray()))), info).getConnection(new Subject(true, Collections.singleton(new Principal() {

                public String getName() {
                    return info.getUserName();
                }
            }), Collections.EMPTY_SET, Collections.singleton(new PasswordCredential(info.getUserName(), info.getPassword().toCharArray()))), info);
        }
        logger.info(" 6.- JMCFI::createConnection -- Returning Connection to user");
        return con;
    }

    /**
     * Sets the log writer for the ConnectionFactory instance. 
     * The log writer is a character output stream to which all logging and 
     * tracing messages for the Connectionfactory instance will be printed.
     *
     * @param out log writer associated with the ConnectionFactory
     */
    public void setLogWriter(PrintWriter out)
            throws ResourceException {
        this.out = out;
    }

    /**
     * Gets the log writer for the ConnectionFactory instance.
     *
     * @return PrintWriter  log writer associated with the ConnectionFactory
     */
    public PrintWriter getLogWriter()
            throws ResourceException {
        return out;
    }

    /**
     * Sets the maximum time in milliseconds that this connection factory will 
     * wait while attempting to connect to a Mail Server. A value of zero 
     * specifies that the timeout is the default system timeout if there is 
     * one; otherwise it specifies that there is no timeout. When a 
     * ConnectionFactory object is created, the timeout is initially zero.
     *
     * @param milliseconds  connection establishment timeout in milliseconds
     */
    public void setTimeout(int milliseconds)
            throws ResourceException {
        this.milliseconds = milliseconds;
    }

    /**
     * Gets the maximum time in milliseconds that this connection factory can 
     * wait while attempting to connect to a Mail Server.
     *
     * @return  connection establishment timeout in milliseconds
     */
    public int getTimeout()
            throws ResourceException {
        return milliseconds;
    }

    /**
     * This method is declared in the javax.resource.Referenceable interface 
     * and should be implemented in order to support JNDI registration.
     *
     * @param reference  a Reference instance
     */
    public void setReference(Reference reference) {
        logger.info("In JavaMailConnectionFactoryImpl::setReference");
        this.reference = reference;
    }

    /**
     * This method is declared in the javax.naming.Referenceable interface 
     * and should be implemented in order to support JNDI registration.
     *
     * @return  a Reference instance
     */
    public Reference getReference() {
        logger.info("In JavaMailConnectionFactoryImpl::getReference");
        return reference;
    }
}
