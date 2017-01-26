package org.mk.training.plexer;

import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;

public interface DispatcherExtended extends Dispatcher {
	void modifyInterestOps(SelectableChannel channel, int opsToSet,
			int opsToReset);

	void resumeSelection(SelectionKey key, int interestOps);
}
