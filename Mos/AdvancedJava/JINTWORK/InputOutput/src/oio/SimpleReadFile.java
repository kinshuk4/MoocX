package oio;

import java.io.*;
public class SimpleReadFile {
public static void main(String[] args) {
    try{
        BufferedReader in = new BufferedReader(new FileReader("TextFile.txt"));
    
        String s, s2 = new String();
        while((s = in.readLine())!= null)
        s2 += s + "\n";
        System.out.println(s2);
        in.close();
    }
     
    catch (Exception e){e.printStackTrace();};  
}
}
