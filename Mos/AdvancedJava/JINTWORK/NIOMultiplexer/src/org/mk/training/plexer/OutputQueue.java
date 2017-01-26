package org.mk.training.plexer;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.io.IOException;

public interface OutputQueue {
	boolean isEmpty();

	int drainTo(ByteChannel channel) throws IOException;

	boolean enqueue(ByteBuffer byteBuffer);
}
