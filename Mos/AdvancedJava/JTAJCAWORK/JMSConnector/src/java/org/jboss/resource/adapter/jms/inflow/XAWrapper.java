package org.jboss.resource.adapter.jms.inflow;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class XAWrapper implements XAResource {
	private XAResource xar=null;

	public XAWrapper(XAResource xar){
		this.xar=xar;
	}
	public void commit(Xid arg0, boolean arg1) throws XAException {
		System.out.println("XAWrapper().commit():: "+arg0+" :: "+arg1);
		xar.commit(arg0, arg1);

	}

	public void end(Xid arg0, int arg1) throws XAException {
		System.out.println("XAWrapper().end():: "+arg0+" :: "+arg1);
		xar.end(arg0, arg1);
	}

	public void forget(Xid arg0) throws XAException {
		System.out.println("XAWrapper().forget():: "+arg0);
		xar.forget(arg0);
	}

	public int getTransactionTimeout() throws XAException {
		int tto=xar.getTransactionTimeout();
		System.out.println("XAWrapper().getTransactionTimeout():: "+tto);
		return tto;
	}

	public boolean isSameRM(XAResource arg0) throws XAException {
		boolean samerm=xar.isSameRM(arg0);
		System.out.println("XAWrapper().isSameRM():: "+samerm);
		return samerm;	}

	public int prepare(Xid arg0) throws XAException {
		int rt=xar.prepare(arg0);
		System.out.println("XAWrapper().prepare():: "+arg0+" ReturnType:: "+rt);
		return rt;
	}

	public Xid[] recover(int arg0) throws XAException {
		return xar.recover(arg0);
	}

	public void rollback(Xid arg0) throws XAException {
		System.out.println("XAWrapper().rollback():: "+arg0);
		xar.rollback(arg0);

	}

	public boolean setTransactionTimeout(int arg0) throws XAException {
		return xar.setTransactionTimeout(arg0);
	}

	public void start(Xid arg0, int arg1) throws XAException {
		System.out.println("XAWrapper().start():: "+arg0+" :: "+arg1);
		xar.start(arg0, arg1);
	}

}
