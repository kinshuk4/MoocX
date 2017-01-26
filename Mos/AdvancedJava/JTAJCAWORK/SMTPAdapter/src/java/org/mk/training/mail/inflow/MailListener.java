package org.mk.training.mail.inflow;

import javax.mail.Message;

public interface MailListener {
	public void onMessage(Message msg);
}
