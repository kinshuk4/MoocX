package com.mk.classloadingsamples;
import java.io.IOException;
import java.util.logging.Logger;

public class ExCtx
{
    ExObj value;
    
    public ExCtx() 
        throws Throwable
    {
    	System.out.println("ExCtx() :: ClassLoader :: ("+this.getClass().getClassLoader().hashCode()+") :: Class :: "+this.getClass()+"("+this.getClass().hashCode()+")");
    	ExObj2 obj2=new ExObj2();
    	System.out.println("ExCtx() :: ClassLoader :: ("+obj2.getClass().getClassLoader().hashCode()+") :: Class :: "+obj2.getClass()+"("+obj2.getClass().hashCode()+")");
        value = new ExObj();
        System.out.println("hi");
        System.out.println("ExCtx() :: ClassLoader :: ("+value.getClass().getClassLoader().hashCode()+") :: Class :: "+value.getClass()+"("+value.getClass().hashCode()+")");
        System.out.println("hi1");
        System.out.println("ExCtx() :: ClassLoader :: ("+value.ivar.getClass().getClassLoader().hashCode()+") :: Class :: "+value.ivar.getClass()+"("+value.ivar.getClass().hashCode()+")");
        obj2 = value.ivar;
        
    }

    public Object getValue()
    {
        return value;
    }

    public void useValue(Object obj) 
        throws Exception
    {
        Logger log = Logger.getLogger("ExCtx.class");
        StringBuffer buffer = new
            StringBuffer("useValue2.arg class");
                log.info(buffer.toString());
        buffer.setLength(0);
        buffer.append("useValue2.ExObj class");
        log.info(buffer.toString());
        ExObj ex = (ExObj) obj;
    }

    void pkgUseValue(Object obj) 
        throws Exception
    {
        Logger log = Logger.getLogger("ExCtx.class");
        log.info("In pkgUseValue");
    }
    public void useInterfaceValue(Object s){
    	((Service)s).doSomeService();
    }
}