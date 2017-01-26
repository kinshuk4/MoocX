package nio.file.misc;




import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.io.IOException;

/**
 * Test copying between channels.
 * 
 * Created and tested: Dec 31, 2001
 * Revised for NIO book, April 2002
 * @author Ron Hitchens (ron@ronsoft.com)
 * @version $Id: ChannelCopy.java,v 1.4 2002/04/21 05:10:56 ron Exp $
 */
public class ChannelCopy
{
	/**
	 * This code copies data from stdin to stdout.  Like the 'cat'
	 * command, but without any useful options.
	 */
	public static void main (String [] argv)
		throws IOException
	{
		ReadableByteChannel source = Channels.newChannel (System.in);
		WritableByteChannel dest = Channels.newChannel (System.out);

		channelCopy1 (source, dest);
		// alternatively, call channelCopy2 (source, dest);

		source.close();
		dest.close();
	}

	/**
	 * Channel copy method 1.  This method copies data from the src
	 * channel and writes it to the dest channel until EOF on src.
	 * This implementation makes use of compact() on the temp buffer
	 * to pack down the data if the buffer wasn't fully drained.  This
	 * may result in data copying, but minimizes system calls.  It also
	 * requires a cleanup loop to make sure all the data gets sent.
	 */
	private static void channelCopy1 (ReadableByteChannel src,
		WritableByteChannel dest)
		throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect (16 * 1024);

		while (src.read (buffer) != -1) {
			// prepare the buffer to be drained
			buffer.flip();
			//CharBuffer cb=buffer.asCharBuffer();
			System.out.println("Char Buffer");
		    // write to the channel, may block
			dest.write (buffer);

			// If partial transfer, shift remainder down
			// If buffer is empty, same as doing clear()
			buffer.rewind();
			String encoding = System.getProperty("file.encoding");
		    CharBuffer cb=Charset.forName(encoding).decode(buffer);
		    if ((cb.toString()).equals("EXIT\r\n")){
		    	System.out.println("equuals");
		    	break;
		    }
		    
		    	
		    buffer.compact();
		}

		// EOF will leave buffer in fill state
		buffer.flip();

		// make sure the buffer is fully drained.
		while (buffer.hasRemaining()) {
			dest.write (buffer);
		}
	}

	/**
	 * Channel copy method 2.  This method performs the same copy, but
	 * assures the temp buffer is empty before reading more data.  This
	 * never requires data copying but may result in more systems calls.
	 * No post-loop cleanup is needed because the buffer will be empty
	 * when the loop is exited.
	 */
	private static void channelCopy2 (ReadableByteChannel src,
		WritableByteChannel dest)
		throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocateDirect (16 * 1024);

		while (src.read (buffer) != -1) {
			// prepare the buffer to be drained
			buffer.flip();

			// make sure the buffer was fully drained.
			while (buffer.hasRemaining()) {
				dest.write (buffer);
			}

			// make the buffer empty, ready for filling
			buffer.clear();
		}
	}
}


