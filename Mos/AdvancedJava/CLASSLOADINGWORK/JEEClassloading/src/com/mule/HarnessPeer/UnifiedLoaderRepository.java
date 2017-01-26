package com.mule.HarnessPeer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UnifiedLoaderRepository {
	private static final UnifiedLoaderRepository ulr=new UnifiedLoaderRepository();
	private ConcurrentMap<String, Class> classCache=null;
	private UnifiedLoaderRepository() {
		super();
		classCache=new ConcurrentHashMap<String, Class>();
	}
	
	public static UnifiedLoaderRepository getInstance(){
		return ulr;
	}
	
	public Class updateCacheIfAbsent(String name,Class loadedbyme){
		System.out.println("UnifiedLoaderRepository :: updateCacheIfAbsent :: "+name);
		return classCache.putIfAbsent(name, loadedbyme);
	}
	public Class getFromCache(String name){
		System.out.println("UnifiedLoaderRepository :: getFromCache :: "+name);
		return classCache.get(name);
	}
	
}
