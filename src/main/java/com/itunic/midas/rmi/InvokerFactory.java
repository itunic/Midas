package com.itunic.midas.rmi;

import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import com.itunic.midas.exceptions.RemoteException;

public final class InvokerFactory {
	private final static ConcurrentHashMap<String, Invoker> invokers = new ConcurrentHashMap<>();
	private String invokerType = null;
	public static final String MAPPING_CONFIG_PREFIX = "META-INF/services";
	private static final String DEFULT_RPC = "midas";

	public InvokerFactory() {
		this(DEFULT_RPC);
	}

	public <T> InvokerFactory(String invokerType) {
		this.invokerType = invokerType;
	}

	public boolean isNull(Invoker invoke) {

		return invoke == null;
	}

	private boolean flag = false;

	private void newInstances() throws RemoteException {
		flag = true;
		ServiceLoader<Invoker> loader = ServiceLoader.load(Invoker.class);
		loader.forEach(f -> {
			invokers.put(f.getNickname(), f);
		});
	}

	/**
	 * 构造一个不为空的invoker
	 * 
	 * @return Invoker
	 * @throws RemoteException 
	 */
	public Invoker builder() throws  RemoteException {
		Invoker invoke = getInvoker();
		if (isNull(invoke) && invokers.size() == 0 && !flag) {
			newInstances();
			return builder();
		}
		return invoke;
	}

	/**
	 * 获取一个Invoker，如果没有则返回null
	 * 
	 * @return
	 */
	public Invoker getInvoker() {
		return invokers.get(invokerType);
	}

}
