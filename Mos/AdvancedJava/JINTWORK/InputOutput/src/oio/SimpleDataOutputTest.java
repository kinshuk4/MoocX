package oio;

// DataOutputTest.java

import java.io.DataOutputStream; 
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleDataOutputTest {

  public SimpleDataOutputTest() {
  }

  public static void main(String args[]) {
    FileOutputStream fileOut = null;
    DataOutputStream dataOut = null;

    try {
      fileOut = new FileOutputStream("datatest.txt");
      dataOut = new DataOutputStream(fileOut);
      
      /*
       * We write out the following record consisting of:
       * boolean: true
       * int:     98765
       * float:   1.5f
       * long:    3210L
       * double:  25.65d
       */

      dataOut.writeBoolean(true);
      dataOut.writeInt(98765);
      dataOut.writeFloat(1.5f);
      dataOut.writeLong(3210L);
      dataOut.writeDouble(25.65d);
      dataOut.flush();

      dataOut.close();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
