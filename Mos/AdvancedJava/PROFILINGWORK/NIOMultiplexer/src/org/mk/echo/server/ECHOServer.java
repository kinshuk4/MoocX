
package org.mk.echo.server;

import org.mk.training.plexer.BufferFactory;
import org.mk.training.plexer.impl.DumbBufferFactory;
import org.mk.training.plexer.impl.GenericInputHandlerFactory;
import org.mk.training.plexer.impl.NioDispatcher;
import org.mk.training.plexer.impl.StandardAcceptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ECHOServer
{
	private ECHOServer()
	{
		// cannot instantiate
	}

	public static void main (String [] args)
		throws IOException
	{
		Executor executor = Executors.newCachedThreadPool();
		BufferFactory bufFactory = new DumbBufferFactory (1024);
		NioDispatcher dispatcher = new NioDispatcher (executor, bufFactory);
		StandardAcceptor acceptor = new StandardAcceptor (1234, dispatcher,
			new GenericInputHandlerFactory (ECHOHandler.class));
		dispatcher.start();
		acceptor.newThread();
	}
}
