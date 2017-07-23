package com.itunic.midas.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.itunic.midas.io.netty.consumer.RPCServiceLoader;
import com.itunic.midas.model.request.RPCRequestMessageModel;

public abstract class RMI {
	@SuppressWarnings("unchecked")
	public static <T> T rpc(Class<?> serviceInterface) {
		return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[] { serviceInterface },
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						RPCRequestMessageModel request = new RPCRequestMessageModel();
						request.setMessageId(UUID.randomUUID().toString());
						request.setArgs(args);
						request.setTypeParameters(method.getParameterTypes());
						request.setClassName(method.getDeclaringClass().getName());
						request.setMethodName(method.getName());
						return RPCServiceLoader.getInstance().start(request);
					}
				});
	}
}
