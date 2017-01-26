package nio.socket.misc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class EchoServer {
	private static boolean debug=false;
	public static int PORT_NUMBER = 1234;
	private ServerSocketChannel serverChannel=null;
	private ServerSocket serverSocket=null;
	private Selector selector =null;
	
	
	/**
	 * Use the same byte buffer for all channels.  A single thread is
	 * servicing all the channels, so no danger of concurrent acccess.
	 */
	private ByteBuffer buffer = ByteBuffer.allocateDirect (1024);

	
	public void setUpConnection (String [] argv)
		
	{
		int port = PORT_NUMBER;
		if (argv.length > 1) {	// override default listen port
			port = Integer.parseInt (argv [1]);
		}
		if (debug){
			System.out.println ("setUpConnection():: Listening on port:: " + port);
		}
		
		// allocate an unbound server socket channel
		try {
			serverChannel = ServerSocketChannel.open();
			// Get the associated ServerSocket to bind it with
			serverSocket = serverChannel.socket();
			// create a new Selector for use below
			selector = Selector.open();
			// set the port the server channel will listen to
			serverSocket.bind (new InetSocketAddress (port));
			// set non-blocking mode for the listening socket
			serverChannel.configureBlocking (false);
			// register the ServerSocketChannel with the Selector
			registerChannel (selector,serverChannel, SelectionKey.OP_ACCEPT);
			this.processSelectedKeys();
		} catch (IOException e) {
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
		if (debug){
			System.out.println("registerChannel():: interested OPS:: "+ops);
			System.out.println("registerChannel():: READ OPS:: "+SelectionKey.OP_READ);
			System.out.println("registerChannel():: WRITE OPS:: "+SelectionKey.OP_WRITE);
			System.out.println("registerChannel():: CONNECT OPS:: "+SelectionKey.OP_CONNECT);
			System.out.println("registerChannel():: ACCEPT OPS:: "+SelectionKey.OP_ACCEPT);
		}

		// set the new channel non-blocking
		channel.configureBlocking (false);

		// register it with the selector
		channel.register (selector, ops);
	}
	/**
	 * Sample data handler method for a channel with data ready to read.
	 * @param key A SelectionKey object associated with a channel
	 *  determined by the selector to be ready for reading.  If the
	 *  channel returns an EOF condition, it is closed here, which
	 *  automatically invalidates the associated key.  The selector
	 *  will then de-register the channel on the next select call.
	 * @throws IOException 
	 */
	protected void echo(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		int count;
		if (debug){
			System.out.println("echo():: "+buffer);
		}
		buffer.clear();			// make buffer empty

		// loop while data available, channel is non-blocking
		while ((count = socketChannel.read (buffer)) > 0) {
			buffer.flip();		// make buffer readable
			// send the data, don't assume it goes all at once
			while (buffer.hasRemaining()) {
				socketChannel.write (buffer);
			}
			buffer.flip();
			buffer.rewind();
			String encoding = System.getProperty("file.encoding");
		    CharBuffer cb=Charset.forName(encoding).decode(buffer);
		    if ((cb.toString()).equals("EXIT\r\n")){
		    	if (debug){
					System.out.println("echo():: exiting");
					socketChannel.close();
				}
		    	buffer.clear();
		    	break;
		    }
		    
			// WARNING: the above loop is evil.  Because
			// it's writing back to the same non-blocking
			// channel it read the data from, this code can
			// potentially spin in a busy loop.  In real life
			// you'd do something more useful than this.
			buffer.clear();		// make buffer empty
		}
		if (count < 0) {
			// close channel on EOF, invalidates the key
			socketChannel.close();
		}
	}
	/**
	 * Spew a greeting to the incoming client connection.
	 * @param channel The newly connected SocketChannel to say hello to.
	 * @throws IOException 
	 */
	private void sayHello (SocketChannel channel) throws IOException
	{
		buffer.clear();
		buffer.put ("Hi there!\r\n".getBytes());
		buffer.flip();
		if (debug){
			System.out.println("sayHello():: "+buffer);
		}
		channel.write (buffer);
	}
	public void processSelectedKeys() throws IOException{
		while (true) {
			// this may block for a long time, upon return the
			// selected set contains keys of the ready channels
			if (debug){
				System.out.println("processSelectedKeys()::");
			}
			int n = selector.select();
			if (debug){
				System.out.println("processSelectedKeys():: n:: "+n);
			}
			if (n == 0) {
				continue;	// nothing to do
			}
			// get an iterator over the set of selected keys
			Iterator it = selector.selectedKeys().iterator();
			// look at each key in the selected set
			while (it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				// Is a new connection coming in?
				if (key.isAcceptable()) {
					if (debug){
						System.out.println("key.isAcceptable()::");
					}
					ServerSocketChannel server =
						(ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					registerChannel (selector, channel,
						SelectionKey.OP_READ);
					sayHello (channel);
				}
				// is there data to read on this channel?
				if (key.isReadable()) {
					if (debug){
						System.out.println("key.isReadable():: ");
					}
					echo(key);
				}
				// remove key from selected set, it's been handled
				it.remove();
			}
		}
	}
	public static void main (String [] argv)
	
	{
		if(argv.length>0)
			debug=	Boolean.parseBoolean(argv[0]);
			new EchoServer().setUpConnection(argv);
	}

}
class EchoClient{
	private static boolean debug=false;
	private ReadableByteChannel source = Channels.newChannel (System.in);
	private WritableByteChannel dest = Channels.newChannel (System.out);
	private ByteBuffer buffer = ByteBuffer.allocateDirect (1024);
	private Selector selector = null;
	private SocketChannel socketchannel=null;
	//private boolean read=false;
	public EchoClient(String []argv){
		if(argv.length>0);
		//debug=	Boolean.parseBoolean(argv[0]);
	}
	
	protected void startTransfer() throws IOException{
		this.processSelectedKeys();
		
		while (true) {
			source.read (buffer);
			// prepare the buffer to be drained
			buffer.flip();
			// make sure the buffer was fully drained.
			while (buffer.hasRemaining()) {
				socketchannel.write (buffer);
			}
			buffer.flip();
			String encoding = System.getProperty("file.encoding");
		    CharBuffer cb=Charset.forName(encoding).decode(buffer);
		    if ((cb.toString()).equals("EXIT\r\n")){
		    	if (debug){
					System.out.println("echo():: exiting");
					socketchannel.close();
				}
		    	buffer.clear();
		    	break;
		    }
			// make the buffer empty, ready for filling
			buffer.clear();
			/*read=false;//having read when next reading is done the flag will be made true
			while(!read){
				this.processSelectedKeys();
			}*/ // uncomment for selectNow() and comment the below line
			while((this.processSelectedKeys()==0));
		}
	} 
	protected void setUpTransfer(String []argv) throws IOException{
		if (debug){
			System.out.println("before registration:: ");
		}
		String host = "localhost";
		int port = 1234;
		if (argv.length == 3) {
			host = argv [1];
			port = Integer.parseInt (argv [2]);
		}
		selector=Selector.open();
		InetSocketAddress addr = new InetSocketAddress (host, port);
		socketchannel = SocketChannel.open();
		socketchannel.configureBlocking (false);
		
		registerChannel (selector, socketchannel,
				SelectionKey.OP_READ
		        | SelectionKey.OP_CONNECT);

		socketchannel.connect (addr);
		
		processSelectedKeys();
	}	
	protected void registerChannel (Selector selector,
			SelectableChannel channel, int ops) throws IOException
	{
		if (channel == null) {
			return;		// could happen
		}
		if (debug){
			System.out.println("registerChannel():: ");
			System.out.println("registerChannel():: interested OPS:: "+ops);
			System.out.println("registerChannel():: READ OPS:: "+SelectionKey.OP_READ);
			System.out.println("registerChannel():: WRITE OPS:: "+SelectionKey.OP_WRITE);
			System.out.println("registerChannel():: CONNECT OPS:: "+SelectionKey.OP_CONNECT);
			System.out.println("registerChannel():: ACCEPT OPS:: "+SelectionKey.OP_ACCEPT);
		}
		
		// set the new channel non-blocking
		channel.configureBlocking (false);
		// register it with the selector
		channel.register (selector, ops);
	}
	public int processSelectedKeys() throws IOException{
		if (debug){
			System.out.println("processSelectedKeys():: ");
		}
		
		// this may block for a long time, upon return the
		// selected set contains keys of the ready channels
		// however if selectNow() is used whic is non blocking 
		// then read flag has to be uncommented.. for coordination
		int n = selector.select();
		if (n == 0) {
			// nothing to do
		}
		if (debug){
			System.out.println("processSelectedKeys():: "+n);
		}
		// get an iterator over the set of selected keys
		Iterator it = selector.selectedKeys().iterator();
		// look at each key in the selected set
		while (it.hasNext()) {
			SelectionKey key = (SelectionKey) it.next();
			if (debug){
				System.out.println("processSelectedKeys():: interestedOPS:: "+key.readyOps());
			}
			// Is a new connection coming in?
			if (key.isConnectable()) {
				if (debug){
					System.out.println("key.isConnectable():: ");
				}
				SocketChannel sc=(SocketChannel)key.channel();	
				while ( ! sc.finishConnect()) {
					doSomethingUseful();//JUST BEING SAFE 
				}
				//registerChannel(selector,sc,SelectionKey.OP_READ);
					//registerChannel(selector,sc,SelectionKey.OP_WRITE);					
			}
			if (key.isReadable()) {
				if (debug){
					System.out.println("key.isReadable():: ");
				}
				readData(key);
				//read=true;//tell the interface thread have done reading for selectNow();
			} 
			if (key.isWritable()){
				if (debug){
					System.out.println("key.isWritable():: ");
				}
			}
			// remove key from selected set, it's been handled
			it.remove();
		}
		return n;
	}
	public static void main(String []argv){
		if (debug){
			System.out.println("main(String []argv):: "+"Starting client");
		}		
		EchoClient client=new EchoClient(argv);
		try {
			client.setUpTransfer(argv);
			client.startTransfer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (debug){
				System.out.println("main(String []argv):: "+"closing the damn thing");
				e.printStackTrace();
			}
		}
		finally{
			try {
				client.source.close();
				client.dest.close();
				client.selector.close();
				client.socketchannel.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	private static void doSomethingUseful()
	{
		if (debug){
			System.out.println ("doSomethingUseful():: "+"doing something useless");
		}				
	}
	protected void readData(SelectionKey key) throws IOException{
		if (debug){
			System.out.println("readData(SelectionKey key):: ");
		}			
		SocketChannel sc=(SocketChannel)key.channel();
		int count;
		while ((count = sc.read (buffer)) > 0)  {
			// prepare the buffer to be drained
			buffer.flip();
			if (debug){
				System.out.println("readData(SelectionKey key):: "+buffer);
			}			
			// make sure the buffer was fully drained.
			while (buffer.hasRemaining()) {
				dest.write (buffer);
			}
			if (debug){
				System.out.println("Written Data:: "+buffer);
			}
			// make the buffer empty, ready for filling
			buffer.clear();
		}
		//this.writeData(key);
	}
}
	
	
