package org.mk.training.plexer;

import java.nio.ByteBuffer;

public interface InputHandler {
	ByteBuffer nextMessage(ChannelFacade channelFacade);

	void handleInput(ByteBuffer message, ChannelFacade channelFacade);

	void starting(ChannelFacade channelFacade);

	void started(ChannelFacade channelFacade);

	void stopping(ChannelFacade channelFacade);

	void stopped(ChannelFacade channelFacade);
	
	void endOfInput (ChannelFacade channelFacade);
}
