package org.mk.training.mail.inflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;



public class MailResourceAdapter implements ResourceAdapter {
	private BootstrapContext ctx;

	/** The activations by activation spec */
	private Map activations = new ConcurrentHashMap();

	/** */
	private NewMsgsWorker newMsgsWorker;

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
		WorkManager mgr = ctx.getWorkManager();
		newMsgsWorker = new NewMsgsWorker(mgr);
		try {
			mgr.scheduleWork(newMsgsWorker); 
		} catch (WorkException e) {
			throw new ResourceAdapterInternalException(e);
		}
	}

	public void stop() {

		newMsgsWorker.release();
	}

	public void endpointActivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) throws ResourceException {
		System.out
				.println("MailResourceAdapter.endpointActivation(MessageEndpointFactory"
						+ " endpointFactory,ActivationSpec spec) :: "
						+ " :: "+endpointFactory +" :: "+ spec);
		MailActivationSpec mailSpec = (MailActivationSpec) spec;
		MailActivation activation = new MailActivation(this, endpointFactory,
				mailSpec);
		try {
			newMsgsWorker.watch(activation);
		} catch (InterruptedException e) {
			throw new ResourceException("Failed to schedule new msg check", e);
		}
		activations.put(spec, activation);
	}

	public void endpointDeactivation(MessageEndpointFactory endpointFactory,
			ActivationSpec spec) {
		System.out
		.println("MailResourceAdapter.endpointDeactivation(MessageEndpointFactory"
				+ " endpointFactory,ActivationSpec spec) :: "
				+ " :: "+endpointFactory +" :: "+ spec);

		MailActivation activation = (MailActivation) activations.remove(spec);
		if (activation != null)
			activation.release();
		
	}

	public XAResource[] getXAResources(ActivationSpec[] specs)
			throws ResourceException {
		return new XAResource[0];
	}
	// --- End ResourceAdapter interface methods

}
