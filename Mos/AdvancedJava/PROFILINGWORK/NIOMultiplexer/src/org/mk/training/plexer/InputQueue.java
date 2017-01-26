package org.mk.training.plexer;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.io.IOException;

public interface InputQueue {
	int fillFrom(ByteChannel channel) throws IOException;

	boolean isEmpty();

	int indexOf(byte b);
	
	int bufferPosition();

	ByteBuffer dequeueBytes(int count);

	void discardBytes(int count);
}
