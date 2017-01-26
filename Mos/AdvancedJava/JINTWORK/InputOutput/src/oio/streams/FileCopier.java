package oio.streams;

import java.io.*;
public class FileCopier {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java FileCopier infile outfile");
    }
    try {
      copy(args[0], args[1]);
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
  public static void copy(String inFile, String outFile)
   throws IOException {

    FileInputStream  fin = null;
    FileOutputStream fout = null;
    try {
      fin  = new FileInputStream(inFile);
      fout = new FileOutputStream(outFile);
      StreamCopier.copy(fin, fout);
    }
    finally {
      try {
        if (fin != null) fin.close( );
      }
      catch (IOException ex) {
      }
      try {
        if (fout != null) fout.close( );
       }
      catch (IOException ex) { }
    }
  }
}

