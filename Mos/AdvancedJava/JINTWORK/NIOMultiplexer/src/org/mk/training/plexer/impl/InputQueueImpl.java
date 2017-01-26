package org.mk.training.plexer.impl;

import org.mk.training.plexer.BufferFactory;
import org.mk.training.plexer.InputQueue;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.io.IOException;

class InputQueueImpl implements InputQueue {
	private final BufferFactory bufferFactory;
	private final ByteBuffer emptyBuffer;
	private ByteBuffer buffer = null;

	public InputQueueImpl(BufferFactory bufferFactory) {
		this.bufferFactory = bufferFactory;
		emptyBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
		
	}

	public synchronized int fillFrom(ByteChannel channel) throws IOException {
		if (buffer == null) {
			buffer = bufferFactory.newBuffer();
		}
		int totalreadcount=0;
		while(true){
			int readcount= channel.read(buffer);
			totalreadcount=totalreadcount+readcount;
			if(readcount == 0 || readcount==-1) break; 
		}
		return totalreadcount;
	}

	// -- not needed by framework

	public synchronized boolean isEmpty() {
		return (buffer == null) || (buffer.position() == 0);
	}

	public synchronized int indexOf(byte b) {
		if (buffer == null) {
			return -1;
		}
		int pos = buffer.position();
		for (int i = 0; i < pos; i++) {
			if (b == buffer.get(i)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized ByteBuffer dequeueBytes(int count) {
		//System.out.println("count::" + count);
		//System.out.println("capacity::" + buffer.capacity());
		//System.out.println("position::" + buffer.position());
		if ((buffer == null) || (buffer.position() == 0) || (count == 0)) {
			return emptyBuffer;
		}
		System.out.println("count::" + count);
		System.out.println("capacity::" + buffer.capacity());
		System.out.println("position::" + buffer.position());
		int size = Math.min(count, buffer.position());
		System.out.println("size::" + size);
		ByteBuffer result = ByteBuffer.allocate(size);
		buffer.flip();

		if (buffer.remaining() <= result.remaining()) {
			result.put(buffer);
		} else {
			while (result.hasRemaining()) {
				result.put(buffer.get());
			}
		}
		if (buffer.remaining() == 0) {
			bufferFactory.returnBuffer(buffer);
			buffer = null;
		} else {
			buffer.compact();
		}
		result.flip();
		return (result);
	}
	public void discardBytes(int count) {
		dequeueBytes(count);
	}

	public int bufferPosition() {
		if(buffer!=null){
			return buffer.position();
		}
		else return -1;
	}
}
