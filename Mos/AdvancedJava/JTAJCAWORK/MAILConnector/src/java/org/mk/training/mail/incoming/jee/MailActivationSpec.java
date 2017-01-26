package org.mk.training.mail.incoming.jee;



import java.io.Serializable;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.ResourceException;
import org.mk.training.mail.incoming.MailReceiver;

public class MailActivationSpec extends MailReceiver implements ActivationSpec, Serializable {
	/** @since 1.0 */
	private static final long serialVersionUID = -3034364895936568423L;

	/** The resource adapter */
	private transient ResourceAdapter ra;

	public MailActivationSpec() {
		System.out.println("MailActivationSpec().Constructor()");
	}

	public ResourceAdapter getResourceAdapter() {
		return ra;
	}

	public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
		this.ra = ra;
	}

	public void validate() throws InvalidPropertyException {

	}
}
