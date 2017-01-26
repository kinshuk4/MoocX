package com.mule.Harness;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoingWhatYouShouldNotIllegalAccess {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
				Logger ucl0Log = Logger.getLogger("UCL0");
        URL jarurl=new URL("http","127.0.0.1",8080,"/files/repository/ex1.jar");
        ucl0Log.info("jar0 path: "+jarurl.toString());
		URL[] cp0 = {jarurl};
		URLClassLoader ucl0 = new URLClassLoader(cp0);
		Thread.currentThread().setContextClassLoader(ucl0);
		
		StringBuffer buffer = new
		    StringBuffer("ExIAEd Info");
		
		ucl0Log.info(buffer.toString());
		
		Class ctxClass1 = ucl0.loadClass("com.mk.classloadingsamples.ExCtx");
		buffer.setLength(0);
		buffer.append("ExCtx Info");
		ucl0Log.info(buffer.toString());
		Object ctx0 = ctxClass1.newInstance();

		try {
		    Class[] types = {Object.class};
		    Method useValue =
			ctxClass1.getDeclaredMethod("pkgUseValue", types);
		    Object[] margs = {null};
		    useValue.invoke(ctx0, margs);
		} catch(Exception e) {
		    ucl0Log.log(Level.SEVERE,"Failed to invoke ExCtx.pkgUseValue", e);
		}

	}

}
