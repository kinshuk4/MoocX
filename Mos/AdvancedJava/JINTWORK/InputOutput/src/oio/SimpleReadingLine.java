package oio;

import java.io.*;
public class SimpleReadingLine {
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		String s;
		try {
			while((s = in.readLine()).length() != 0)
			System.out.println(s);
		} catch(IOException e) {}
	}
}

