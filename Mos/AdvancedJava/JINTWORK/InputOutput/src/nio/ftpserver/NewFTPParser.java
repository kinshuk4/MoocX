package 
nio.ftpserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class NewFTPParser implements Parser {
	private static int FILE_NAME_SIZE = 20;
	private static int DIRECTION_SIZE = 2;
	private static int FILE_SIZE_SIZE=8;
	private static int NUMFILE_SIZE=4;
	
	private static TransferModes mode=TransferModes.CHANNELTOCHANNEL;
	private static Logger logger=Logger.getLogger("nio.ftpserver");
	
	private long totalbytecount=0;
	private ByteBuffer headerbuffer=ByteBuffer.allocateDirect(90);
	private long filesizes[]=null;
	private SocketChannel socketChannel=null; 

	public void parse(SelectionKey key) {
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
		headerbuffer.limit(DIRECTION_SIZE);
		String encoding = System.getProperty("file.encoding");
	    CharBuffer cb=Charset.forName(encoding).decode(headerbuffer.slice());
	    String direction=DataIO.readFixedString(DIRECTION_SIZE, cb);
		logger.info("direction :: "+direction);
		logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
		headerbuffer.position(headerbuffer.limit()).limit(headerbuffer.limit()+NUMFILE_SIZE);
		int numfiles=headerbuffer.getInt();
		logger.info("no of files :: "+numfiles);
		logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
		String [] filenames=new String[numfiles];
		
		for (int i = 0; i < numfiles; i++) {
			headerbuffer.limit(headerbuffer.limit()+FILE_NAME_SIZE);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
			CharBuffer cbi=Charset.forName(encoding).decode(headerbuffer.slice());
			filenames[i]=DataIO.readFixedString(FILE_NAME_SIZE, cbi);
			logger.info("filename :: "+filenames[i]);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
			headerbuffer.position(headerbuffer.limit()).limit(headerbuffer.limit()+FILE_SIZE_SIZE);
			filesizes[i]=headerbuffer.getLong();
			totalbytecount=totalbytecount+filesizes[i];
			logger.info("filesize :: "+filesizes[i]);
			logger.info("POSITION :: "+headerbuffer.position()+" LIMIT :: "+headerbuffer.limit());
		}

	}
}
