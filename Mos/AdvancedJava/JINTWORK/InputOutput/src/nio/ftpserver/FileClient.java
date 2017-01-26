package nio.ftpserver;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileClient {
	
	private static int FILE_NAME_SIZE = 20;
	private static int DIRECTION_SIZE = 2;
	private static int FILE_SIZE_SIZE=8;
	private static int NUMFILE_SIZE=4;
	private static TransferModes mode=TransferModes.CHANNELTOCHANNEL;
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	
	private static Selector selector = null;
	private static SocketChannel socketchannel=null;
	
	private FileChannel fcarray[]=null;
	private HumanInterfaceController hic=null;
	private TranferParameters tp=null;
	private ByteBuffer headerbuffer=ByteBuffer.allocateDirect(90);;
	private MappedByteBuffer mappeddataarray[]=null;
	private GatheringByteChannel gathersocketchannel=null;
	
	public static void main(String[] args) {
		try{
			FileClient f=new FileClient();
			f.initialize(args);
			f.service();
		} catch (IOException e) {e.printStackTrace();}
		finally{
			try {
				selector.close();
				//socketchannel.socket().close();
				//socketchannel.close();
			} catch (IOException e1) {e1.printStackTrace();}
		}
	}
	protected void initialize(String []argv) throws IOException{
		
		String host = "localhost";
		int port = 5555;
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
		        | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);

		socketchannel.connect (addr);
		processSelectedKeys(false);
		hic=HumanInterfaceController.getInstance();
		tp=TranferParameters.getInstance();
	}
	protected void service(){
		do {
			logger.info("DOWHILE :: ");
			hic.queryTransferParameters(tp);
			System.out.println(tp);
			if(this.mapFiles()){
				try {
					this.processSelectedKeys(false);
				} catch (IOException e) {e.printStackTrace();}
			}
		} while (tp.isLinger());
		//try {
			//socketchannel.socket().shutdownOutput();
			//socketchannel.socket().shutdownInput();
			//selector.close();
			//socketchannel.socket().close();
			//socketchannel.close();
			
		//} catch (IOException e) {e.printStackTrace();}
	}
	protected void registerChannel (Selector selector,
			SelectableChannel channel, int ops) throws IOException
	{
		if (channel == null) {
			return;		// could happen
		}
		logger.info("registerChannel()::  READ OPS:: "+SelectionKey.OP_READ);
		logger.info("registerChannel()::  WRITE OPS:: "+SelectionKey.OP_WRITE);
		logger.info("registerChannel()::  CONNECT OPS:: "+SelectionKey.OP_CONNECT);
		logger.info("registerChannel()::  ACCEPT OPS:: "+SelectionKey.OP_ACCEPT);
		logger.info("registerChannel()::  INTERESTED OPS:: "+ops);
		// set the new channel non-blocking
		channel.configureBlocking (false);
		// register it with the selector
		channel.register (selector, ops);
	}
	protected boolean processSelectedKeys(boolean close) throws IOException{
		logger.info("processSelectedKeys()");
		boolean done=false;
		// this may block for a long time, upon return the
		// selected set contains keys of the ready channels
		int n = selector.select();
		if (n == 0) {
			// nothing to do
		}
		logger.info("processSelectedKeys():: ready OPS:: "+n);
		// get an iterator over the set of selected keys
		Iterator it = selector.selectedKeys().iterator();
		// look at each key in the selected set
		while (it.hasNext()) {
			SelectionKey key = (SelectionKey) it.next();
			// Is a new connection coming in?
			if (key.isConnectable()) {
				SocketChannel sc=(SocketChannel)key.channel();	
				while ( ! sc.finishConnect());//being safe
				done=true;
			}
			if (key.isReadable()) {
				logger.info("key.isReadable()");
				done=true;
			}
			if (key.isWritable()){
				logger.info("key.isWritable()");
				if (close){
					socketchannel.socket().close();
				}
				this.transfer(key);
				done=true;
			}
			// remove key from selected set, it's been handled
			logger.info("REMOVING THE KEY :: ");
			it.remove();
		}
		return done;
	}
	protected void transfer(SelectionKey key) {
		gathersocketchannel=(GatheringByteChannel)key.channel();
		logger.info("transfer():: up and away");
		headerbuffer.flip();
		long headercount=0;
		while (headerbuffer.hasRemaining()) {
			logger.info("headerbuffer.hasRemaining() :: "+headerbuffer.hasRemaining());
			try {
				headercount=gathersocketchannel.write (headerbuffer);
				logger.info("headercount :: "+headercount);
			} catch (IOException e) {e.printStackTrace();}
			// empty body
			// loop until write() returns zero
		}
		if (mode==TransferModes.MEMORYMAPPED) 
			this.transferMappedBuffers();
		if(mode==TransferModes.CHANNELTOCHANNEL) 
			this.transferC2C();
		logger.info("transfer():: done");
		
	}
	protected void transferC2C(){
		for (int i = 0; i < fcarray.length; i++) {
			long writecount=0;
			try {
				for(;;){
					writecount=writecount+fcarray[i].transferTo(0,fcarray[i].size(),gathersocketchannel);
					logger.info("writecount :: "+writecount);
					if (writecount==fcarray[i].size()){
						break;
					}
				}
				/*while((writecount=writecount+fcarray[i].transferTo(0,fcarray[i].size(),gathersocketchannel))<fcarray[i].size()){
					logger.info("write count :: " +writecount);
				}*/
			} catch (IOException e) {e.printStackTrace();}
			try {
				fcarray[i].close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	protected void transferMappedBuffers(){
		long count=0;
		for (int i = 0; i < mappeddataarray.length; i++) {
			logger.info("transfer():: checking buffer size"+mappeddataarray[i].capacity());
			while (mappeddataarray[i].hasRemaining()) {
				try {
					count=count+gathersocketchannel.write (mappeddataarray[i]);
					logger.info("writen bytes:: "+count );
				} catch (IOException e) {e.printStackTrace();}
				// empty body
				// loop until write() returns zero
			}	
			try {
				fcarray[i].close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	protected boolean mapFiles(){
		boolean transfer=false;
		headerbuffer.clear();
		List<String> files=tp.getFiles();
		ListIterator<String> it=files.listIterator();
		headerbuffer.put(DataIO.writeFixedString(tp.getMode(),DIRECTION_SIZE).getBytes());
		headerbuffer.putInt(files.size());
		mappeddataarray=(MappedByteBuffer[])Array.newInstance(MappedByteBuffer.class, files.size());
		fcarray=(FileChannel[])Array.newInstance(FileChannel.class, files.size());
		for (int i=0;i<3;i++) {
			if(it.hasNext()){
				String file =it.next();
				try {
					fcarray[i]=new RandomAccessFile(file,"r").getChannel();
					mappeddataarray[i]=fcarray[i].map(FileChannel.MapMode.READ_ONLY, 0, fcarray[i].size());
					headerbuffer.put(DataIO.writeFixedString(file,FILE_NAME_SIZE).getBytes()).putLong(fcarray[i].size());
					System.out.println("file size :: "+fcarray[i].size());
				} catch (FileNotFoundException e) {e.printStackTrace();mappeddataarray[i]=null;}
				catch(IOException ie){ie.printStackTrace();}
				
			}//else{
				//headerbuffer.put(DataIO.writeFixedString("Dummy",FILE_NAME_SIZE).getBytes()).putLong(Long.MIN_VALUE);}
		}
		logger.info("length of mappeddataarray"+mappeddataarray.length);
		parseHeaders(headerbuffer);
		if (mappeddataarray.length>0) {
			transfer=true;
		}
		return transfer;
	}
	protected static void parseHeaders(ByteBuffer buffer){
		buffer.flip();
		buffer.limit(DIRECTION_SIZE);
		String encoding = System.getProperty("file.encoding");
	    CharBuffer cb=Charset.forName(encoding).decode(buffer.slice());
	    String direction=DataIO.readFixedString(DIRECTION_SIZE, cb);
		System.out.println("direction :: "+direction);
		System.out.println("POSITION :: "+buffer.position()+" LIMIT :: "+buffer.limit());
		buffer.position(buffer.limit()).limit(buffer.limit()+NUMFILE_SIZE);
		int numfiles=buffer.getInt();
		System.out.println("no of files :: "+numfiles);
		System.out.println("POSITION :: "+buffer.position()+" LIMIT :: "+buffer.limit());
		for (int i = 0; i < numfiles; i++) {
			buffer.limit(buffer.limit()+FILE_NAME_SIZE);
			System.out.println("POSITION :: "+buffer.position()+" LIMIT :: "+buffer.limit());
			CharBuffer cbi=Charset.forName(encoding).decode(buffer.slice());
			String filename=DataIO.readFixedString(FILE_NAME_SIZE, cbi);
			System.out.println("filename :: "+filename);
			System.out.println("POSITION :: "+buffer.position()+" LIMIT :: "+buffer.limit());
			buffer.position(buffer.limit()).limit(buffer.limit()+FILE_SIZE_SIZE);
			long filesize=buffer.getLong();
			System.out.println("filesize :: "+filesize);
			System.out.println("POSITION :: "+buffer.position()+" LIMIT :: "+buffer.limit());
		}
		
	}
	   static{
		logger.setLevel(Level.CONFIG);
		
	}
}
