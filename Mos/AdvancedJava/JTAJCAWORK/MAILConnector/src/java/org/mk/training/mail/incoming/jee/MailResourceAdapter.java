package org.mk.training.mail.incoming.jee;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;
import org.mk.training.util.WorkManagerDelegatingExecutor;

public class MailResourceAdapter implements ResourceAdapter {

    private BootstrapContext ctx;
    /** The activations by activation spec */
    private Map activations = new ConcurrentHashMap();

    public MailResourceAdapter() {
        System.out.println("MailResourceAdapter()");
    }

    /**
     * Get the work manager
     *
     * @return the work manager
     */
    public WorkManager getWorkManager() {
        return ctx.getWorkManager();
    }

    // --- Begin ResourceAdapter interface methods
    public void start(BootstrapContext ctx)
            throws ResourceAdapterInternalException {

        System.out.println("MailResourceAdapter.start(BootstrapContext ctx)");
        this.ctx = ctx;
    }

    public void stop() {
        //newMsgsWorker.release();
    }

    public void endpointActivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) throws ResourceException {
        System.out.println("MailResourceAdapter.endpointActivation(MessageEndpointFactory"
                + " endpointFactory,ActivationSpec spec) :: "
                + " :: " + endpointFactory + " :: " + spec);
        MailActivationSpec mailSpec = (MailActivationSpec) spec;
        System.out.println("mailSpec::::" + mailSpec);
        System.out.println("mailSpec.getPort()" + mailSpec.getPort() + "mailSpec.isSecureExchange()" + mailSpec.isSecureExchange());
        mailSpec.setSessionProperties(MailActivationSpec.getExchangeProperties(mailSpec.getPort(),
                mailSpec.isSecureExchange(), mailSpec.getStoreProtocol()));
        System.out.println("mailSpec.getSessionProperties():" + mailSpec.getSessionProperties());
        JEEENVMailActivator activator = new JEEENVMailActivator(mailSpec, endpointFactory);
        mailSpec.setActivator(activator);
        WorkManagerDelegatingExecutor executor = new WorkManagerDelegatingExecutor(this.getWorkManager(), activator);
        mailSpec.setListenerThreadExecutor(executor);
        mailSpec.setDispatchThreadExecutor(executor);
        System.out.println("Set Executors" + mailSpec.getDispatchThreadExecutor() + mailSpec.getListenerThreadExecutor());
        System.out.println("Set activator");
        activations.put(spec, activator);
        mailSpec.setupIncomingMail();
    }

    public void endpointDeactivation(MessageEndpointFactory endpointFactory,
            ActivationSpec spec) {
        System.out.println("MailResourceAdapter.endpointDeactivation(MessageEndpointFactory"
                + " endpointFactory,ActivationSpec spec) :: "
                + " :: " + endpointFactory + " :: " + spec);

        JEEENVMailActivator activation = (JEEENVMailActivator) activations.remove(spec);
        if (activation != null) {
            activation.release();
        }

    }

    public XAResource[] getXAResources(ActivationSpec[] specs)
            throws ResourceException {
        return new XAResource[0];
    }
    // -    -- End ResourceAdapter interface methods
}
