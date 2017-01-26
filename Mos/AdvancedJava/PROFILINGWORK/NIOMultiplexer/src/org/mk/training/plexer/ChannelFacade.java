package org.mk.training.plexer;

public interface ChannelFacade {
	InputQueue inputQueue();

	OutputQueue outputQueue();

	void setHandler(InputHandler handler);

	int getInterestOps();

	void modifyInterestOps(int opsToSet, int opsToReset);
}
