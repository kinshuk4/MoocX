package nio.ftpserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import utils.*;
public class FileTransferServer {
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	private static int PORT_NUMBER = 5555;
	
	private ServerSocketChannel serverChannel=null;
	private ServerSocket serverSocket=null;
	private Selector selector =null;
	private Parser parser=null;
	private String PARSERCLASSNAME="nio.ftpserver.FtpSyntacticParser";
	/**
	 * Use the same byte buffer for all channels.  A single thread is
	 * servicing all the channels, so no danger of concurrent acccess.
	 */
	public void initialize (String [] argv)
	{
		int port = PORT_NUMBER;
		if (argv.length > 1) {	// override default listen port
			port = Integer.parseInt (argv [1]);
		}
		try {
			//integrate the protocol parser
			logger.info("Starting Server");
			parser=(Parser)Integrator.integrate(PARSERCLASSNAME);
			serverChannel = ServerSocketChannel.open();
			serverSocket = serverChannel.socket();
			selector = Selector.open();
			serverSocket.bind (new InetSocketAddress (port));
			serverChannel.configureBlocking (false);
			registerChannel(selector, serverChannel,SelectionKey.OP_ACCEPT);
			logger.info("Server Initialized waiting for connection");
			logger.info("Server Socket :: "+ serverSocket);
			this.processSelectedKeys();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				serverChannel.close();
				selector.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	protected void processSelectedKeys(){
		logger.info("processSelectedKeys()");
		while (true) {
			// this may block for a long time, upon return the
			// selected set contains keys of the ready channels
			int n=0;
			try {
				n = selector.select();
			} catch (IOException e) {logger.warning("Problem During SELECT");e.printStackTrace();}
			if (n == 0) {
				continue;	// nothing to do
			}
			// get an iterator over the set of selected keys
			Iterator it = selector.selectedKeys().iterator();
			// look at each key in the selected set
			while (it.hasNext()) {
				try {
					SelectionKey key = (SelectionKey) it.next();
					// Is a new connection coming in?
					if (key.isAcceptable()) {
						logger.info("key.isAcceptable()");
						ServerSocketChannel server =
							(ServerSocketChannel) key.channel();
						SocketChannel channel;
						try {
							channel = server.accept();
							Socket s=channel.socket();
							logger.info("SocketChannel :: "+channel);
							logger.info("Socket :: "+s);
							logger.info("Socket Details :: Localport :: "+s.getLocalPort()+" remote port:: "+s.getPort()+" localaddress :: "+s.getLocalSocketAddress()+" remoteaddress :: "+s.getRemoteSocketAddress());
							registerChannel (selector, channel,SelectionKey.OP_READ);
						} catch (IOException e) {logger.warning("Problem During ACCEPT");	e.printStackTrace();}
					}
					// is there data to read on this channel?
					if (key.isReadable()) {
						logger.info("key.isReadable()");
						parser.parse(key);
						
					}
					if (key.isWritable()){
						logger.info("key.isWritable()");
					}
					// remove key from selected set, it's been handled
					it.remove();
					logger.info("Removing the Key :: ");
				} catch (CancelledKeyException e) {e.printStackTrace();	}
				
			}
		}
	}
	/**
	 * Register the given channel with the given selector for
	 * the given operations of interest
	 * @throws IOException 
	 */
	protected void registerChannel (Selector selector,
		SelectableChannel channel, int ops) throws IOException
	{
		if (channel == null) {
			return;		// could happen
		}
		// set the new channel non-blocking
		channel.configureBlocking (false);
		// register it with the selector
		channel.register (selector, ops);
	}
	public static void main (String [] argv)
	{
		new FileTransferServer().initialize(argv);
	}
	static{
		logger.setLevel(Level.CONFIG);
		try {
			FileHandler logFile= new FileHandler("LogToFile2.txt");
		    logFile.setFormatter(new SimpleFormatter());
		    logger.addHandler(logFile);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
