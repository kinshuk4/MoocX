package com.grapevineim.xmpp.ra.outbound.jee;

import com.grapevineim.xmpp.ra.ResourceAdapterImpl;
import com.grapevineim.xmpp.ra.outbound.XmppManagedConnectionFactory;
import java.io.PrintWriter;
import java.io.Serializable;

import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.resource.spi.work.WorkManager;
import org.mk.training.util.ExecutorWorkManagerDelegator;

public class XmppJEEManagedConnectionFactory extends XmppManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation,
        Serializable {

    private static final long serialVersionUID = -1;
    private static final Logger LOG = Logger.getLogger(XmppJEEManagedConnectionFactory.class.getName());
    private ResourceAdapter ra;
    private PrintWriter out;
    private WorkManager workmanager;

    public XmppJEEManagedConnectionFactory() {
    }

    public ResourceAdapter getResourceAdapter() {
        return ra;
    }

    public void setResourceAdapter(ResourceAdapter ra)
            throws ResourceException {
        this.ra = ra;
        workmanager = ((ResourceAdapterImpl) this.ra).getWorkManager();
        System.out.println("before::");
        this.setDispatchThreadExecutor(new ExecutorWorkManagerDelegator(workmanager));
        System.out.println("after::");
    }
}
