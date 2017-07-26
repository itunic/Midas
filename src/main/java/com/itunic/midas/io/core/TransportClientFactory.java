package com.itunic.midas.io.core;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import com.itunic.midas.io.core.handler.MessageSendHandler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class TransportClientFactory {
	private static EventLoopGroup worker = new NioEventLoopGroup();
	private static ConcurrentHashMap<SocketAddress, ChannelFuture> clents = new ConcurrentHashMap<SocketAddress, ChannelFuture>();
	private static volatile boolean flag = false;

	public static MessageSendHandler getTransportClient(SocketAddress address) {
		ChannelFuture future = clents.get(address);

		if (future == null || !future.isSuccess()) {
			flag = false;
			synchronized (TransportClientFactory.class) {
				if (!flag) {
					TransportCallBack call = new TransportCallBack();
					RequestDispatcher dis = RequestDispatcher.getInstance(worker);
					dis.connect(address, call);
					future = call.getCallBackFuture();
					clents.put(address, future);
					flag = true;
				}
			}

		}
		return getHandle(clents.get(address));
	}

	private static MessageSendHandler getHandle(ChannelFuture future) {
		MessageSendHandler handle = future.channel().pipeline().get(MessageSendHandler.class);
		// loader.setHandle(handle);
		return handle;
	}
}
