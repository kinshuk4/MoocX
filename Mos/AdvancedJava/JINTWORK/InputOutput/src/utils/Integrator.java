package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Integrator {
	private Integrator(){}
	
	public static Object integrate(String filename){
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in) );
		Class s=null;
		Object o=null;
		while (true){
			if (filename==null){
				try {
					filename=in.readLine();
				} catch (IOException e) {
					System.out.println("Control C and start again");
					e.printStackTrace();
					filename=null;
					continue;
					
				}
			}
			try {
				s = Class.forName(filename);
				
			} catch (ClassNotFoundException e) {
				System.out.println("Wrong class name or classpath");
				e.printStackTrace();
				continue;
			}
			try {
				o=s.newInstance();
				break;
			} catch (InstantiationException e) {
				System.out.println("Default and public constuctor required");
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				System.out.println("Default and public constuctor required");
				e.printStackTrace();
				continue;
			}
			
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return o;
	}
	
}
