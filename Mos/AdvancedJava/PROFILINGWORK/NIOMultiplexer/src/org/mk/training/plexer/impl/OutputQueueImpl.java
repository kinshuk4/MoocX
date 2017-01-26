package org.mk.training.plexer.impl;

import org.mk.training.plexer.BufferFactory;
import org.mk.training.plexer.ChannelFacade;
import org.mk.training.plexer.OutputQueue;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;
import java.io.IOException;

class OutputQueueImpl implements OutputQueue {
	private final BufferFactory bufferFactory;
	private final ChannelFacade facade;
	private final LinkedList<ByteBuffer> queue;
	private ByteBuffer active = null;
	public OutputQueueImpl(BufferFactory bufferFactory, ChannelFacade adapter) {
		this.bufferFactory = bufferFactory;
		this.facade = adapter;
		queue = new LinkedList<ByteBuffer>();
	}
	public synchronized boolean isEmpty() {
		return (active == null) && (queue.size() == 0);
	}
	public synchronized int drainTo(ByteChannel channel) throws IOException {
		int bytesWritten = 0;

		while (true) {
			if (active == null) {
				if (queue.size() == 0)
					break;

				active = queue.removeFirst();
				active.flip();
			}

			int rc = channel.write(active);
			bytesWritten += rc;

			if (!active.hasRemaining()) {
				bufferFactory.returnBuffer(active);
				active = null;
			}

			if (rc == 0)
				break;
		}

		return bytesWritten;
	}

	// -- not needed by framework

	public synchronized boolean enqueue(ByteBuffer byteBuffer) {
		System.out.println("OutputQueue.enqueue::");
		if (byteBuffer.remaining() == 0) {
			return false;
		}
		if (queue.size() > 0) {
			ByteBuffer tail = queue.getLast();

			if (tail.hasRemaining()) {
				topUpBuffer(tail, byteBuffer);
			}
		}
		while (byteBuffer.hasRemaining()) {
			ByteBuffer newBuf = bufferFactory.newBuffer();
			topUpBuffer(newBuf, byteBuffer);
			queue.addLast(newBuf);
		}
		facade.modifyInterestOps(SelectionKey.OP_WRITE, 0);
		return true;
	}

	private void topUpBuffer(ByteBuffer dest, ByteBuffer src) {
		if (src.remaining() <= dest.remaining()) {
			dest.put(src);
		} else {
			// TODO: make this more efficient with buffer slice?
			while (dest.hasRemaining()) {
				dest.put(src.get());
			}
		}
	}
}
