package com.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

public class MBeanPrinter {

	private MBeanPrinter() {
	}
	public static void printDetails(Object info){
		
		if (info!=null && info instanceof CompositeData[]) {
			
			CompositeData[] cda=(CompositeData[])info;
			for (int i = 0; i < cda.length; i++) {
				printDetails(cda[i]);
			}
		}
		else if (info!=null && info instanceof CompositeData) {
			
        	CompositeData cd=(CompositeData)info;
        	if ((cd.values() instanceof CompositeData)) {
        		System.out.println("cd.values() :: "+cd.values());
			}
        	else{
        		for (Iterator iter = cd.values().iterator(); iter.hasNext();) {
					printDetails(iter.next());
				}
        	}
		}
        else if (info!=null && info instanceof TabularData) {
        	
        	TabularData td=(TabularData)info;
        	Set ks=td.keySet();
        	for (Iterator iter = ks.iterator(); iter.hasNext();) {
        		List l=(List)iter.next();
        		Object o=td.get(l.toArray());
				printDetails(o);
			}
        }
        else if (info!=null && info instanceof String[]) {
        	String[] result=(String[])info;
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
			}
		}
        else {
        	System.out.println(info);
        }

	}

}
