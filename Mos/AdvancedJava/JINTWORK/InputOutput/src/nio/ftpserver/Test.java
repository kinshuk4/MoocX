package nio.ftpserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Test {
	private static int FILE_NAME_SIZE = 20;
	private static int DIRECTION_SIZE = 2;
	private static int FILE_SIZE_SIZE=8;
	private static int NUMFILE_SIZE=4;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> files=new ArrayList<String>();
		files.add("hassan.zip");
		files.add("gulamali.zip");
		files.add("jagjit.zip");
		//StringBuilder sb=new StringBuilder();
		ByteBuffer bf=ByteBuffer.allocateDirect(90);
		bf.put(DataIO.writeFixedString("d",DIRECTION_SIZE).getBytes());
		bf.putInt(3);
		ListIterator<String> it=files.listIterator();
		for (int i=0;i<3;i++) {
			if(it.hasNext()){
				String file =it.next();
				try {
					FileChannel fc=new RandomAccessFile(file,"r").getChannel();
					fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
					bf.put(DataIO.writeFixedString(file,FILE_NAME_SIZE).getBytes()).putLong(fc.size());
					System.out.println("file size :: "+fc.size());
				} catch (FileNotFoundException e) {e.printStackTrace();}
				catch(IOException ie){ie.printStackTrace();}
				
			}else{
				bf.put(DataIO.writeFixedString("Dummy",FILE_NAME_SIZE).getBytes()).putLong(Long.MIN_VALUE);}
		}
		parseHeaders(bf);
		
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

}
