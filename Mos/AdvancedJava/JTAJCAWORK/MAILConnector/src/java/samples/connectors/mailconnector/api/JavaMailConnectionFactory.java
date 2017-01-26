/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */

/*
 * @(#)JavaMailMessageListener.java
 */

package samples.connectors.mailconnector.api;

import javax.mail.Message.*;
import javax.resource.*;
import javax.resource.cci.*;


/**
 * Interface for obtaining mail server connections. 
 * 
 * @author Alejandro E. Murillo
 */

public interface JavaMailConnectionFactory
{
    /**
     * Gets a connection to the Mail Server.
     * Passes along mail server and user info.
     *
     * @return Connection	Connection instance
     */

    public JavaMailConnection createConnection()
        throws ResourceException;

    /**
     * Gets a connection to a Mail Server instance. A component should use 
     * the getConnection variant with a javax.resource.cci.ConnectionSpec 
     * parameter if it needs to pass any resource-adapter-specific security 
     * information and connection parameters.
     *
     * @param properties  connection parameters and security information 
     *                    specified as ConnectionSpec instance
     * @return  a JavaMailConnection instance
     */

    public JavaMailConnection createConnection(ConnectionSpec properties)
        throws ResourceException;
}
