package org.mk.echo.server;

import org.mk.training.plexer.ChannelFacade;
import org.mk.training.plexer.InputHandler;
import org.mk.training.plexer.InputQueue;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/*
 * An instance of this class will get created per every client socket
 * so you can make assumptions from that.
 */
public class ECHOHandler implements InputHandler {
	private boolean sendresponse = false;

	public ECHOHandler() {
		System.out.println("EchoHandler()");
	}

	public ByteBuffer nextMessage(ChannelFacade channelFacade) {
		System.out.println("nextMessage()");
		InputQueue inputQueue = channelFacade.inputQueue();
		int bufferposition=inputQueue.bufferPosition();
		System.out.println("bufferposition::"+bufferposition);
		if(bufferposition <= 0) return null;
		ByteBuffer buffer=inputQueue.dequeueBytes(bufferposition);
		if(buffer.isReadOnly()|| (buffer.remaining()==0)) return null;
		return buffer;

	}

	public void handleInput(ByteBuffer message, ChannelFacade channelFacade) {
		String req = new String(message.array());
		System.out.println("Message:: " + req);
		channelFacade.outputQueue().enqueue(message);
	}

	public void starting(ChannelFacade channelFacade) {

	}

	public void started(ChannelFacade channelFacade) {

	}

	public void endOfInput(ChannelFacade channelFacade) {

	}

	public void stopping(ChannelFacade channelFacade) {

	}

	public void stopped(ChannelFacade channelFacade) {

	}
}
