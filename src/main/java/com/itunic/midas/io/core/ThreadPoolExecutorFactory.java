package com.itunic.midas.io.core;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorFactory<T> extends AbstractExecutorFactory<T> {
	private static volatile ThreadPoolExecutor exec = null;

	private ThreadPoolExecutorFactory() {
	}

	@SuppressWarnings("unchecked")
	public T getExecutor() {
		// TODO Auto-generated method stub
		return (T) new ThreadPoolExecutor(super.CPU_CORES, super.CPU_CORES, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public static ThreadPoolExecutor getThreadPoolExecutor() {
		if (null == exec) {
			synchronized (ThreadPoolExecutorFactory.class) {
				if (null == exec) {
					ThreadPoolExecutorFactory<ThreadPoolExecutor> tpef = new ThreadPoolExecutorFactory<ThreadPoolExecutor>();
					exec = tpef.getExecutor();
				}
			}
		}
		return exec;
	}

}
