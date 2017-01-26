package org.mk.training.mail.inflow;

import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkManager;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkListener;

import java.util.concurrent.PriorityBlockingQueue;

public class NewMsgsWorker implements Work, WorkListener {
	private boolean released;

	private WorkManager mgr;

	private PriorityBlockingQueue<MailActivation> pollQueue;

	private boolean trace;

	public NewMsgsWorker(WorkManager mgr) {
		this.mgr = mgr;
		// The capacity needs to be externalized
		this.pollQueue = new PriorityBlockingQueue<MailActivation>(1024);

	}

	public void watch(MailActivation activation) throws InterruptedException {
		System.out.println("NewMsgsWorker.watch(MailActivation activation):: ");
		long now = System.currentTimeMillis();
		activation.updateNextNewMsgCheckTime(now);
		pollQueue.put(activation);
		System.out
				.println("NewMsgsWorker.watch(MailActivation activation):: end ::");
	}

	public void release() {
		released = true;

	}

	public void run() {
		System.out.println("NewMsgsWorker.run():: START:: ");
		while (released == false) {
			try {
				MailActivation ma = (MailActivation) pollQueue.take();
				if (ma.isReleased())
					continue;
				// Wait until its time to check for new msgs
				long now = System.currentTimeMillis();
	            long nextTime = ma.getNextNewMsgCheckTime();
	            long sleepMS = nextTime - now;
	            Thread.sleep(sleepMS);
	            if (released)
					break;

				// Now schedule excecution of the new msg check
				mgr.scheduleWork(ma, WorkManager.INDEFINITE, null, this);
			} catch (InterruptedException e) {

			} catch (WorkException e) {

			}
		}
		System.out.println("NewMsgsWorker.run():: END:: ");
	}

	// --- Begin WorkListener interface methods
	public void workAccepted(WorkEvent e) {
	}

	public void workRejected(WorkEvent e) {
	}

	public void workStarted(WorkEvent e) {
	}

	public void workCompleted(WorkEvent e) {
		System.out.println("NewMsgsWorker.workCompleted(WorkEvent e)");
		MailActivation activation = (MailActivation) e.getWork();
		try {
			watch(activation);
		} catch (InterruptedException ex) {
			
		}
		System.out.println("NewMsgsWorker.workCompleted(WorkEvent e)" + activation);
	}
	// --- End WorkListener interface methods

}
