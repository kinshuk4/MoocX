package oio.streams;

import java.io.IOException;

public class AvailableBytes {

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            byte[] b = new byte[10];
            int offset = 0;

            while (offset < b.length) {
                int available = 0;
                while (!((available = System.in.available()) > 0)) {
                    //may be do something else
                    Thread.sleep(2000);

                }

                System.out.println("available :: :: " + available);
                int bytesRead = System.in.read(b, offset, available);
                if (bytesRead == -1) {
                    break; // end of stream
                }
                offset += bytesRead;
            }
            for (int s = 0; s < b.length; s++) {
                System.out.write(b[s]);
            }
            for (int s = 0; s < b.length; s++) {
                System.out.print(b[s]);
            }
        } catch (IOException ex) {
            System.err.println("Couldn't read from System.in!");
        }

    }
}
