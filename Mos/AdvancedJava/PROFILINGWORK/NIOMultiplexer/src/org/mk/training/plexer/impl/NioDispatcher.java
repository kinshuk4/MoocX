package org.mk.training.plexer.impl;

import org.mk.training.plexer.BufferFactory;
import org.mk.training.plexer.ChannelFacade;
import org.mk.training.plexer.Dispatcher;
import org.mk.training.plexer.InputHandler;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NioDispatcher implements Dispatcher, Runnable {
	private final Logger logger = Logger.getLogger(getClass().getName());
	private final ReadWriteLock selectorGuard = new ReentrantReadWriteLock();
	private final Executor executor;
	private final Selector selector;
	private final BlockingQueue<HandlerAdapter> statusChangeQueue;
	private final BufferFactory bufferFactory;
	private volatile boolean dispatching = true;

	public NioDispatcher(Executor executor, BufferFactory bufferFactory)
			throws IOException {
		this.executor = executor;
		this.bufferFactory = bufferFactory;
		statusChangeQueue = new ArrayBlockingQueue<HandlerAdapter>(100);
		selector = Selector.open();
	}

	public Thread start() {
		Thread thread = new Thread(this);
		thread.start();
		return thread;
	}

	/*
	 * Implementation of Runnable interface
	 */
	public void run() {
		try {
			dispatch();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unexpected I/O Exception", e);
		}
		Set<SelectionKey> keys = selector.selectedKeys();
		for (SelectionKey key : keys) {
			HandlerAdapter adapter = (HandlerAdapter) key.attachment();

			unregisterChannel(adapter);
		}
		try {
			selector.close();
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Unexpected I/O Exception closing selector", e);
		}
	}

	/*
	 * Implementation of Dispatcher interface
	 * @see org.mk.training.plexer.Dispatcher#dispatch()
	 */	
	public void dispatch() throws IOException {
		while (dispatching) {
			System.out.println("while (dispatching)");
			selectorGuardBarrier();
			selector.select();
			checkStatusChangeQueue();
			Set<SelectionKey> keys = selector.selectedKeys();
			for (SelectionKey key : keys) {
				HandlerAdapter adapter = (HandlerAdapter) key.attachment();
				invokeHandler(adapter);
			}
			keys.clear();
		}
	}

	public void shutdown() {
		dispatching = false;
		selector.wakeup();
	}
	public ChannelFacade registerChannel(SelectableChannel channel,
			InputHandler handler) throws IOException {
		channel.configureBlocking(false);
		HandlerAdapter adapter = new HandlerAdapter(handler, this,
				bufferFactory);
		adapter.registering();
		acquireSelectorGuard();
		try {
			SelectionKey key = channel.register(selector, SelectionKey.OP_READ,
					adapter);
			adapter.setKey(key);
			adapter.registered();

			return adapter;
		} finally {
			releaseSelectorGuard();
		}
	}

	public void unregisterChannel(ChannelFacade token) {
		if (!(token instanceof HandlerAdapter)) {
			throw new IllegalArgumentException("Not a valid registration token");
		}
		HandlerAdapter adapter = (HandlerAdapter) token;
		SelectionKey selectionKey = adapter.key();
		acquireSelectorGuard();
		try {
			adapter.unregistering();
			selectionKey.cancel();
		} finally {
			releaseSelectorGuard();
		}
		adapter.unregistered();
	}

	/*
	 * package-local called from HandlerAdapter
	 *
	 *	Place the given HandlerAdapter instance on the status change queue.
	 * The loop and nested try/catch blocks are to properly handle the
	 * InterruptedException that might be thrown when adding to the
	 * completion queue. That exception is unlikely to ever happen here,
	 * but this is the proper code to handle the general case.
	 */
	void enqueueStatusChange(HandlerAdapter adapter) {
		boolean interrupted = false;

		try {
			while (true) {
				try {
					statusChangeQueue.put(adapter);
					selector.wakeup();
					return;
				} catch (InterruptedException e) {
					interrupted = true;
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}

	/*
	 * private methods that always run in the selection
	 * thread, and hence do not need the selectorGuard.
	 */
	private void invokeHandler(HandlerAdapter adapter) {
		adapter.prepareToRun();
		adapter.key().interestOps(0);
		executor.execute(new HandlerFutureTask(adapter));
	}

	private void checkStatusChangeQueue() {
		HandlerAdapter adapter;

		while ((adapter = statusChangeQueue.poll()) != null) {
			if (adapter.isDead()) {
				unregisterChannel(adapter);
			} else {
				resumeSelection(adapter);
			}
		}
	}

	private void resumeSelection(HandlerAdapter adapter) {
		SelectionKey key = adapter.key();
		if (key.isValid())
			key.interestOps(adapter.getInterestOps());
	}

	/**
	 * Called to acquire and then immediately release a write lock on the
	 * selectorGuard object. This method is only called by the selection thread
	 * and it has the effect of making that thread wait until all read locks
	 * have been released.
	 */
	private void selectorGuardBarrier() {
		selectorGuard.writeLock().lock();
		selectorGuard.writeLock().unlock();
	}

	/**
	 * Reader lock acquire/release, called by non-selector threads
	 * 
	 * Grab a read lock on the selectorGuard object. A handler thread calls this
	 * method when it wants to mutate the state of the Selector. It must call
	 * releaserSelectorGuard when it is finished, because selection will not
	 * resume until all read locks have been released.
	 */
	private void acquireSelectorGuard() {
		selectorGuard.readLock().lock();
		selector.wakeup();
	}

	/**
	 * Undo a previous call to acquireSelectorGuard to indicate that the calling
	 * thread no longer needs access to the Selector object.
	 */
	private void releaseSelectorGuard() {
		selectorGuard.readLock().unlock();
	}

	/*
	 * Specialized FutureTask class that wraps a HandlerAdapter instance
	 * for execution by the thread pool Executor. This class overrides
	 * the done() method to place the contained HandlerAdapter object
	 * onto a BlockingQueue for the selection thread to see.
	 */
	private class HandlerFutureTask extends FutureTask<HandlerAdapter> {
		private final HandlerAdapter adapter;

		public HandlerFutureTask(HandlerAdapter adapter) {
			super(adapter);
			this.adapter = adapter;
		}

		protected void done() {
			enqueueStatusChange(adapter);

			try {
				/*
				 * Get result returned by call(), or cause
				 * deferred exception to be thrown. We know
				 * the result will be the adapter instance
				 * stored above, so we ignore it.
				 */
				get();

				/*
				 * Extension point: You may choose to extend the
				 * InputHandler and HandlerAdapter classes to add
				 * methods for handling these exceptions. This
				 * method is still running in the worker thread.
				 */
			} catch (ExecutionException e) {
				adapter.die();
				logger.log(Level.WARNING, "Handler died", e.getCause());
			} catch (InterruptedException e) {
				Thread.interrupted();
				logger.log(Level.WARNING, "Handler interrupted", e);
			}
		}
	}
}
