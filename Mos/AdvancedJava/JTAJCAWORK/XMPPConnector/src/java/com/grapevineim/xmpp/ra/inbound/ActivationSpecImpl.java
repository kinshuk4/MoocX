package com.grapevineim.xmpp.ra.inbound;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.grapevineim.xmpp.XmppConnectionSpec;
import java.util.logging.Logger;

public class ActivationSpecImpl extends XmppConnectionSpec implements ActivationSpec {

    private static final long serialVersionUID = -1;
    private static final Logger LOG = Logger.getLogger(ActivationSpecImpl.class.getName());
    private ResourceAdapter resourceAdapter = null;

    public ActivationSpecImpl() {
    }

    public void validate() throws InvalidPropertyException {
        checkProperty(getHost());
        checkProperty(getDomain());
        checkProperty(getUsername());
        checkProperty(getPassword());
        if (getPort() == null || getPort().intValue() < 1024) {
            LOG.severe("port is invalid");
            throw new InvalidPropertyException("port is invalid");
        }
    }

    private void checkProperty(String s) throws InvalidPropertyException {
        if (StringUtils.isBlank(s)) {
            LOG.severe(s + " is blank");
            throw new InvalidPropertyException(s + " is blank");
        }
    }

    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        this.resourceAdapter = ra;
    }

    public ResourceAdapter getResourceAdapter() {
        return resourceAdapter;
    }
}
