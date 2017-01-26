/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */

package samples.connectors.mailconnector.api;

import javax.mail.*;
import javax.mail.Message.*;
import javax.mail.internet.*;


/**
 * JavaMailMessageListener interface implemented by JavaMailMessageBean.
 * 
 * @author Alejandro E. Murillo
 */

public interface JavaMailMessageListener
{
    /**
     * Message-driven bean method invoked by the EJB container.
     *
     * @param message  the incoming message
     */

    public void onMessage(javax.mail.Message message);
}
