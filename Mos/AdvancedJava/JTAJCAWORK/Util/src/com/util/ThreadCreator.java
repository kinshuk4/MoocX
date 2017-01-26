package com.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ThreadCreator {
	private static ThreadCreator instance=new ThreadCreator();
	
	private List<Thread> threads =Collections.synchronizedList(new ArrayList<Thread>()) ;
	private ThreadCreator(){}
	public static ThreadCreator getInstance(){
		return instance;
	}
	public void createThread(String name,Runnable work){
		PoolableThread t=new PoolableThread(name);
		t.start();
		threads.add(t);
		t.setTask(work);
	}
	public void stopThreads(){
		for (Thread t : threads) {
			t.interrupt();
		}
	}
}
