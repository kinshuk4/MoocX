package org.mk.training.plexer;

import java.io.IOException;
import java.nio.channels.SelectableChannel;

public interface Dispatcher {
	void dispatch() throws IOException;

	void shutdown();

	ChannelFacade registerChannel(SelectableChannel channel,
			InputHandler handler) throws IOException;

	void unregisterChannel(ChannelFacade key);
}
