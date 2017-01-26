package org.mk.training.jsr160jmxconnector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;

public class JSR160Adapter implements ResourceAdapter {

    private static Logger logger;

    static {
        logger = LogUtils.getLogger();
    }
    private BootstrapContext ctx = null;
    private JSR160Worker worker = null;
    private String mbeanServerDomainName;
    private String jsr160URL;

    public String getJsr160URL() {
        return jsr160URL;
    }

    public void setJsr160URL(String jsr160URL) {
        logger.log(Level.FINE, "setJsr160URL:-" + jsr160URL);
        this.jsr160URL = jsr160URL;
    }

    public String getMbeanServerDomainName() {
        return mbeanServerDomainName;
    }

    public void setMbeanServerDomainName(String mbeanServerDomainName) {
        logger.log(Level.FINE, "setMbeanServerDomainName:-" + mbeanServerDomainName);
        this.mbeanServerDomainName = mbeanServerDomainName;
    }

    public void endpointActivation(MessageEndpointFactory arg0,
            ActivationSpec arg1) throws ResourceException {
        // TODO Auto-generated method stub
    }

    public void endpointDeactivation(MessageEndpointFactory arg0,
            ActivationSpec arg1) {
        // TODO Auto-generated method stub
    }

    public XAResource[] getXAResources(ActivationSpec[] arg0)
            throws ResourceException {

        return null;
    }

    public void start(BootstrapContext arg0)
            throws ResourceAdapterInternalException {
        logger.log(Level.FINE, "JSR160Adapter.start()");
        this.ctx = arg0;
        WorkManager wm = ctx.getWorkManager();
        worker = new JSR160Worker(this, wm);
        try {
            wm.scheduleWork(worker);
        } catch (WorkException e) {
            throw new ResourceAdapterInternalException(e);
        }
    }

    public void stop() {
        logger.log(Level.FINE, "JSR160Adapter.stop()");
        worker.release();

    }
}
