package com.itunic.midas.io.core.initializer;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelInitializer;

public abstract class MessageInitializerFactory {
	/**
	 * ChannelInitializer ��������
	 * 
	 * @param handlerType
	 * @return
	 */
	public static ChannelInitializer<?> getRevcChildHandler(String handlerType,ConcurrentHashMap<String, Object> serviceMap) {
		ChannelInitializer<?> initializer = null;
		switch (handlerType) {
		case "JDK-RECV":
			initializer = new JDKMessageRecvInitializer(serviceMap);
			break;
		case "JDK-SEND":
			initializer = new JDKMessageSendInitializer();
			break;
		default:
			System.out.println("未有匹配类型");
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
			System.out.println("δ�ҵ�����");
			break;
		}
		return initializer;

	}
}
