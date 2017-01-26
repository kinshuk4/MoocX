package oio;

// DataInputTest.java

import java.io.DataInputStream; import java.io.FileInputStream;
import java.io.IOException;

public class SimpleDataInputTest {

  public SimpleDataInputTest() {
  }

  public static void main(String args[]) {
    FileInputStream fileIn = null;
    DataInputStream dataIn = null;

    try {
      fileIn = new FileInputStream("datatest.txt");
      dataIn = new DataInputStream(fileIn);

      /*
       * We read in the following record consisting of:
       * boolean:
       * int:
       * float:
       * long:
       * double:
       */

      System.out.println("boolean:" + dataIn.readBoolean());
      System.out.println("int:    " + dataIn.readInt());
      System.out.println("float:  " + dataIn.readFloat());
      System.out.println("long:   " + dataIn.readLong());
      System.out.println("double: " + dataIn.readDouble());

      dataIn.close();
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
