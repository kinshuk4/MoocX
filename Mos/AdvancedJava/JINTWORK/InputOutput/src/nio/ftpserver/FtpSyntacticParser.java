package nio.ftpserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FtpSyntacticParser implements Parser {
	private static int FILE_NAME_SIZE = 20;
	private static int DIRECTION_SIZE = 2;
	private static int FILE_SIZE_SIZE=8;
	private static int NUMFILE_SIZE=4;
	private static TransferModes mode=TransferModes.CHANNELTOCHANNEL;
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	
	private long totalbytecount=0;
	private ByteBuffer headerbuffer=ByteBuffer.allocateDirect(90);
	private MappedByteBuffer dataarray[]=null;
	private FileChannel fcarray[]=null;
	private long filesizes[]=null;
	private SocketChannel socketChannel=null; 
		 
	public void parse(SelectionKey key){
		socketChannel = (SocketChannel) key.channel();
		logger.info("parse():: closed or open :: " +socketChannel.isOpen());
		logger.info("parse()::");
		try {
			this.readHeader();
		} catch (IOException e) {e.printStackTrace();logger.info("CLOSED socket channel::"+socketChannel);try {	socketChannel.socket().close(); socketChannel.close();return;} catch (IOException e1) {	e1.printStackTrace();return;}
		}
		this.parseHeaders();
		logger.info("parse()::done ");
	}
	private void readHeader() throws IOException{
		//headerbuffer=ByteBuffer.allocateDirect(90);
		headerbuffer.clear();
		totalbytecount=0;
		while ((socketChannel.read(headerbuffer)>0)) {
			//wait till header is read
		}
		logger.info("headerbuffer.position() :: "+headerbuffer.position());
	}
	private void parseHeaders(){
		headerbuffer.flip();
		logger.info("socketChannel.socket() :: "+socketChannel.socket());
		logger.info("socketchannel.socket().isConnected() :: "+socketChannel.socket().isConnected());
		logger.info("socketchannel.socket().isClosed() :: "+socketChannel.socket().isClosed());
		logger.info("socketchannel.isOpen() :: "+socketChannel.isOpen());
		logger.info("socketchannel :: "+socketChannel);
		headerbuffer.limit(DIRECTION_SIZE);
		String encoding = System.getProperty("file.encoding");
	    CharBuffer cb=Charset.forName(encoding).decode(headerbuffer.slice());
	    String direction=DataIO.readFixedString(DIRECTION_SIZE, cb);
		logger.info("direction :: "+direction);
		logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
		headerbuffer.position(headerbuffer.limit()).limit(headerbuffer.limit()+NUMFILE_SIZE);
		int numfiles=headerbuffer.getInt();
		logger.info("no of files :: "+numfiles);
		dataarray=(MappedByteBuffer[])Array.newInstance(MappedByteBuffer.class, numfiles);
		fcarray=(FileChannel[])Array.newInstance(FileChannel.class, numfiles);
		filesizes=(long[])Array.newInstance(long.class, numfiles);
		logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
		for (int i = 0; i < numfiles; i++) {
			headerbuffer.limit(headerbuffer.limit()+FILE_NAME_SIZE);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
			CharBuffer cbi=Charset.forName(encoding).decode(headerbuffer.slice());
			String filename=DataIO.readFixedString(FILE_NAME_SIZE, cbi);
			logger.info("filename :: "+filename);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
			headerbuffer.position(headerbuffer.limit()).limit(headerbuffer.limit()+FILE_SIZE_SIZE);
			filesizes[i]=headerbuffer.getLong();
			totalbytecount=totalbytecount+filesizes[i];
			logger.info("filesize :: "+filesizes[i]);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
			try {
				fcarray[i]=new RandomAccessFile("copy"+filename,"rw").getChannel();
				dataarray[i]=fcarray[i].map(FileChannel.MapMode.READ_WRITE, 0, filesizes[i]);
			} catch (FileNotFoundException e) {e.printStackTrace();
			} catch (IOException e) {e.printStackTrace();}
		}
		//if(mode==TransferModes.MEMORYMAPPED)
			//this.readBodyWithMappedBuffers();	
		if(mode==TransferModes.CHANNELTOCHANNEL) 
			this.readBodyWithC2C();
		
	}
	private void readBodyWithC2C(){
		
		for (int i = 0; i < fcarray.length; i++) {
			logger.info("fcarray.length :: "+fcarray.length);
			long readcount=0;
			try {
				for(;;){
					readcount=fcarray[i].transferFrom(socketChannel, 0, fcarray[i].size());
					logger.info("readCount :: "+readcount);
					if (socketChannel!=null) {
						logger.info("socketscatterChannel.isConnected() :: "+socketChannel.isConnected());
						logger.info("socketscatterChannel.socketChannel.socket().isClosed() :: "+socketChannel.socket().isClosed());
						logger.info("socketscatterChannel.isOpen() :: "+socketChannel.isOpen());
						logger.info("socketscatterChannel :: "+"Not Null");
						if (!socketChannel.isOpen()||readcount==0) {
							
							break;
						}
					}
					else break;
				}
			} catch (IOException e) {e.printStackTrace();}
			
		}
	}
	/*private void readBodyWithMappedBuffers(){
		try {
			logger.info("reading data :: 1");
			long readcount=0;
			/*for (int i = 0; i < dataarray.length; i++) {
				while ((readcount=+socketscatterChannel.read(dataarray[i]))<fcarray[i].size())) {
					//wait till header is read
				}
				dataarray[i].force();
				fcarray[i].close();
			}*/
			/*
			 * will work if the system has huge physical memory or the scattergather buffer
			 * size is small.
			 */
			/*logger.info("totalbytecount :: "+(totalbytecount));
			while ((readcount=readcount+socketscatterChannel.read(dataarray))<totalbytecount) {
					//wait till header is read
					logger.info("readcount :: "+readcount);
					if (readcount<0) {
						socketscatterChannel.close();
					} 
			}
			logger.info("readCount"+readcount);
			for (int i = 0; i < dataarray.length; i++) {
				dataarray[i].force();
				fcarray[i].close();
			}
			logger.info("reading data :: 3");
		} catch (IOException e) {e.printStackTrace();}
	}*/
	public FtpSyntacticParser(){}
	static{
		logger.setLevel(Level.CONFIG);
		
	}
}
