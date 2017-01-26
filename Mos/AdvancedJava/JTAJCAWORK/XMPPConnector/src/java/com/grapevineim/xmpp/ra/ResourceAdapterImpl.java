/****************************************************************************** 
 * $Id$
 * 
 * Copyright 2008 GrapevineIM (http://www.grapevine.im)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions 
 * and limitations under the License. 
 * 
 ******************************************************************************/
package com.grapevineim.xmpp.ra;

import java.io.Serializable;
import java.util.HashMap;

import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.grapevineim.xmpp.XmppMessageListener;
import com.grapevineim.xmpp.ra.inbound.ActivationSpecImpl;
import com.grapevineim.xmpp.ra.inbound.XmppMessageListenerImpl;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 
 * @author alex
 * 
 */
public class ResourceAdapterImpl implements ResourceAdapter, Serializable {

    private static final long serialVersionUID = -1;
    private static final Logger LOG = Logger.getLogger(ResourceAdapterImpl.class.getName());
    private final Map<MessageEndpointFactory, XmppMessageListener> messageListeners = new ConcurrentHashMap<MessageEndpointFactory, XmppMessageListener>();
    private WorkManager workManager = null;

    public void start(BootstrapContext ctx)
            throws ResourceAdapterInternalException {
        LOG.info("[XMPPRA.start()] Entered");
        // get the work manager
        this.workManager = ctx.getWorkManager();

    }

    public void stop() {
    }

    public void endpointActivation(
            MessageEndpointFactory messageEndpointFactory, ActivationSpec spec)
            throws NotSupportedException {
        LOG.info("[RA.endpointActivation()] Entered");

        try {
            XmppMessageListener l = new XmppMessageListenerImpl(workManager,
                    messageEndpointFactory, (ActivationSpecImpl) spec);
                messageListeners.put(messageEndpointFactory, l);
        } catch (Exception ex) {
            LOG.severe("[RA.endpointActivation()] An Exception was caught while activating the endpoint");
            LOG.severe("[RA.endpointActivation()] Please check the server logs for details");
            throw new NotSupportedException("Activation failed", ex);
        }
    }

    public void endpointDeactivation(
            MessageEndpointFactory messageEndpointFactory, ActivationSpec spec) {
        LOG.info("[RA.endpointdeactivation()] Entered");
        try {
            XmppMessageListener l = messageListeners.remove(messageEndpointFactory);
            ((XmppMessageListenerImpl) l).cleanup();
        } catch (Exception ex) {
            LOG.severe("[RA.endpointActivation()] An Exception was caught while deactivating the endpoint");
        }
    }

    public WorkManager getWorkManager() {
        return this.workManager;
    }

    public XAResource[] getXAResources(ActivationSpec[] specs)
            throws ResourceException {
        return null;
    }
}
