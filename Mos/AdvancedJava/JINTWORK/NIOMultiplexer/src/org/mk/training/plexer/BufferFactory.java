package org.mk.training.plexer;

import java.nio.ByteBuffer;

public interface BufferFactory {
	ByteBuffer newBuffer();

	void returnBuffer(ByteBuffer buffer);
}
