package com.itunic.midas.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import com.itunic.midas.exceptions.RPCException;
import com.itunic.midas.exceptions.RemoteException;
import com.itunic.midas.io.model.request.RPCRequestMessageModel;

public abstract class RMI {
	@SuppressWarnings("unchecked")
	public static <T> T rpc(Class<?> serviceInterface) {
		return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[] { serviceInterface },
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws RPCException, InterruptedException, RemoteException {
						RPCRequestMessageModel request = new RPCRequestMessageModel();
						request.setRequestTime(System.currentTimeMillis());
						request.setMessageId(UUID.randomUUID().toString());
						request.setArgs(args);
						request.setTypeParameters(method.getParameterTypes());
						request.setClassName(method.getDeclaringClass().getName());
						request.setMethodName(method.getName());
						return new InvokerFactory().builder().start(request);
					}
				});
	}
}
