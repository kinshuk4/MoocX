
package org.mk.http.server;

import org.mk.training.plexer.ChannelFacade;
import org.mk.training.plexer.InputHandler;
import org.mk.training.plexer.InputQueue;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/*
 * An instance of this class will get created per every client socket
 * so you can make assumptions from that.
 */
public class HTTPHandler implements InputHandler
{
	public HTTPHandler(){
		System.out.println("EchoHandler()");
	}
	public ByteBuffer nextMessage (ChannelFacade channelFacade)
	{
		InputQueue inputQueue = channelFacade.inputQueue();
		int bufferposition=inputQueue.bufferPosition();
		System.out.println("bufferposition::"+bufferposition);
		if(bufferposition <= 0) return null;
		return (inputQueue.dequeueBytes (inputQueue.bufferPosition()));
		/*
		 * This also works if you want each header separated by "\n" as
		 * a separate buffer.
		 */
		/*InputQueue inputQueue = channelFacade.inputQueue();
		 int nlPos = inputQueue.indexOf ((byte) '\n');
		 if (nlPos == -1) return (null);
		 return (inputQueue.dequeueBytes (nlPos));
		 */

		
	} 

	public void handleInput (ByteBuffer message, ChannelFacade channelFacade)
	{
		String req=new String(message.array());
		System.out.println("Message:: "+req);
						
	}
	public void starting (ChannelFacade channelFacade)
	{
		
	}

	public void started (ChannelFacade channelFacade)
	{
		
	}

	public void endOfInput (ChannelFacade channelFacade)
	{

	}
	public void stopping (ChannelFacade channelFacade)
	{
	
	}
	public void stopped (ChannelFacade channelFacade)
	{
		
	}
}
