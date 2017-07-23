package com.itunic.midas.io.netty.producer;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.itunic.midas.model.request.RPCRequestMessageModel;
import com.itunic.midas.model.response.RPCResponseMessageModel;

import io.netty.channel.ChannelHandlerContext;

public class RPCMessageRecvTask implements Runnable {
	private RPCRequestMessageModel request = null;
	private RPCResponseMessageModel response = null;
	private ChannelHandlerContext ctx = null;
	private Map<String, Class<?>> serviceMap = null;

	public RPCMessageRecvTask(RPCRequestMessageModel request, RPCResponseMessageModel response,
			ChannelHandlerContext ctx, Map<String, Class<?>> serviceMap) {
		this.request = request;
		this.response = response;
		this.ctx = ctx;
		this.serviceMap = serviceMap;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			this.response.setMessageId(this.request.getMessageId());
			Object obj = this.reflex();
			this.response.setResult(obj);
			this.response.setResponseTime(System.currentTimeMillis());
			ctx.writeAndFlush(this.response);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Object reflex()
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String className = request.getClassName();
		String methodName = request.getMethodName();
		Class<?> serviceInterface = serviceMap.get(className);
		Object[] args = request.getArgs();
		// method.invoke(serviceInterface.newInstance(), args);
		return MethodUtils.invokeMethod(serviceInterface.newInstance(), methodName, args);

	}

}
