package org.mk.training.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppPauser {
	private static BufferedReader in=null;
	
	private AppPauser() {
		super();
		
	}
	public static void waitForEnterPressed() {
		try {
			System.out.println("Press <Enter> to continue..."+Thread.currentThread().getName());
			while (System.in.read()==-1);
				
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	synchronized public static String readInput() {
		try {
			System.out.println("Please input data ..."+Thread.currentThread().getName());
			if (in==null) {
				in=new BufferedReader(new InputStreamReader(System.in));
			}
			String input =null;
			while ((input=in.readLine()).length()==0);
			System.out.println("input :: "+input);
			return input;
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
	}

}
