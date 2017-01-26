/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samples.connectors.mailconnector.ra.outbound;

import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.spi.ConnectionEvent;
import samples.connectors.mailconnector.api.JavaMailConnection;

/**
 * @author veenamohitkumar
 */
public abstract class AbstractConnectionImpl implements JavaMailConnection {

    private ManagedConnectionImpl mc; // if mc is null, connection is invalid
    protected javax.mail.Transport transport; // Folder 1x1 with connection
    protected javax.mail.Session session;
    protected MailServerSession mailServerSession;
    static Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");

    /**
     * Constructor.
     *
     * @param mc  a physical connection to an underlying EIS
     * @param connectionInfo  connection-specific info/properties
     *
     */
    protected AbstractConnectionImpl(ManagedConnectionImpl mc,
            MailServerSession mailserversession) {
        this.mc = mc;
        this.transport = mailserversession.getTransport();
        this.session=mailserversession.getSession();
        this.mailServerSession=mailserversession;
        logger.info(" 5. JavaMailConnectionImpl::Constructor:"+this);
    }

    /**
     * Closes the connection.
     */
    public void close()
            throws ResourceException {
        logger.finest(" -- In JavaMailConnectionImpl:: close mc=" + mc);
        if (mc == null) {
            return;  // connection is already closed
        }
        mc.removeJavaMailConnection(this);
        mc.sendEvent(ConnectionEvent.CONNECTION_CLOSED, null, this);
        mc = null;
    }

    /**
     * Associates connection handle with new managed connection.
     *
     * @param newMc  new managed connection
     */
    public void associateConnection(ManagedConnectionImpl newMc)
            throws ResourceException {
        checkIfValid();
        mc.removeJavaMailConnection(this);
        newMc.addJavaMailConnection(this);
        mc = newMc;
    }

    /**
     * Checks the validity of the physical connection to the EIS.
     */
    void checkIfValid()
            throws ResourceException {
        logger.finest(" -- In JavaMailConnectionImpl::checkIfValid mc=" + mc);
        if (mc == null) {
            throw new ResourceException("INVALID_CONNECTION");
        }
    }

    /**
     * Sets the physical connection to the EIS as invalid.
     * The physical connection to the EIS cannot be used any more.
     */
    void invalidate() {
        logger.info(" -- In JavaMailConnectionImpl::invalidate mc=" + mc);
        mc = null;
    }
}
