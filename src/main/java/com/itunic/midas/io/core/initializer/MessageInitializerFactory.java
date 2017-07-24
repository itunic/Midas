package com.itunic.midas.io.core.initializer;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelInitializer;

public abstract class MessageInitializerFactory {
	/**
	 * ChannelInitializer 工厂方法
	 * 
	 * @param handlerType
	 * @return
	 */
	public static ChannelInitializer<?> getRevcChildHandler(String handlerType,ConcurrentHashMap<String, Class<?>> serviceMap) {
		ChannelInitializer<?> initializer = null;
		switch (handlerType) {
		case "JDK-RECV":
			initializer = new JDKMessageRecvInitializer(serviceMap);
			break;
		case "JDK-SEND":
			initializer = new JDKMessageSendInitializer();
			break;
		default:
			System.out.println("未找到类型");
			break;
		}
		return initializer;

	}
	
	public static ChannelInitializer<?> getSendChildHandler(String handlerType) {
		ChannelInitializer<?> initializer = null;
		switch (handlerType) {
		case "JDK-SEND":
			initializer = new JDKMessageSendInitializer();
			break;
		default:
			System.out.println("未找到类型");
			break;
		}
		return initializer;

	}
}
