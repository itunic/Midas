package com.itunic.midas.io.core;


public abstract class AbstractExecutorFactory<T> {
	/**
	 * cpuºËÐÄÊý
	 */
	public  int CPU_CORES = Runtime.getRuntime().availableProcessors() * 2;

	public abstract  T getExecutor();
}
