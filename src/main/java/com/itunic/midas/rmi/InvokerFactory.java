package com.itunic.midas.rmi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Manifest;

import org.apache.commons.io.IOUtils;

import com.itunic.midas.io.core.RPCServiceLoader;

public final class InvokerFactory {
	private final static ConcurrentHashMap<String, Invoker> invokers = new ConcurrentHashMap<>();
	private String invokerType = null;
	public static final String MAPPING_CONFIG_PREFIX = "META-INF/services";

	public InvokerFactory() {
		this("netty");
	}

	public <T> InvokerFactory(String invokerType) {
		this.invokerType = invokerType;
	}

	public boolean isNull(Invoker invoke) {

		return invoke == null;
	}

	private boolean flag = false;

	private void newInstances() throws Exception {
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
	 * @throws Exception
	 */
	public Invoker builder() throws Exception {
		Invoker invoke = getInvoker();
		if (isNull(invoke) && invokers.size() == 0 && !flag) {
			newInstances();
			return builder();
		}
		System.out.println(invoke);
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
