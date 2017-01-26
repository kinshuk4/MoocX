package org.mk.training.mail.incoming;

import javax.mail.Message;

public interface MailListener {
	public void onMessage(Message msg);
}
