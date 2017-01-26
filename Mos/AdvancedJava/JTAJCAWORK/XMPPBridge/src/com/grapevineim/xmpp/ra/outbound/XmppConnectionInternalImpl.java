/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grapevineim.xmpp.ra.outbound;

import com.grapevineim.xmpp.XmppConnection;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;
/**
 *
 * @author JEEonJBOSS
 */
public class XmppConnectionInternalImpl extends AbstractConnectionImpl implements XmppConnection{
    public XmppConnectionInternalImpl(ManagedConnection managedConnection)
            throws ResourceException {
        super((XmppManagedConnection) managedConnection);
        LOG.info("ConnectionHandle::::" + this);
        LOG.info("this.managedconnection::::" + this.managedconnection);
    }
}
