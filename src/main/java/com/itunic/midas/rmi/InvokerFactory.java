package com.itunic.midas.rmi;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class InvokerFactory {
	private final static ConcurrentHashMap<String, Invoker> invokers = new ConcurrentHashMap<>();
	private String invokerType = null;

	public InvokerFactory() {
		this("");
	}

	public <T> InvokerFactory(String invokerType) {
		this.invokerType = invokerType;
	}

	@SuppressWarnings("unused")
	private boolean isNull() {
		Invoker invoke = invokers.get(invokerType);
		return invoke == null;
	}

	private void newInstances() {
		ServiceLoader<Invoker> loader = ServiceLoader.load(Invoker.class);
		loader.forEach(f -> {
			invokers.put(f.getNickname(), f);
		});
	}
	
	public Invoker builder(){
		if(isNull()){
			newInstances();
		}
		return null;
	}

}
