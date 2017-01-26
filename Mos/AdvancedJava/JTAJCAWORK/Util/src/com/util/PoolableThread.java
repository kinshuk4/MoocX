package com.util;

class PoolableThread extends Thread {

	Runnable task = null;

	public PoolableThread(String arg0) {
		super(arg0);
	}

	synchronized void setTask(Runnable task) {
		this.task = task;
		notify();
	}

	synchronized void executeTasks() {
		while (!Thread.interrupted()) {
			try {
				if (task == null) {
					wait();
				}
			} catch (InterruptedException ex) {

				// Interrupted
			}
			task.run();
			task = null;
		}
	}

	public void run() {
		executeTasks();
	}
}
