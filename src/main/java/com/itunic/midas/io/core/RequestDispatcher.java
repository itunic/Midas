package com.itunic.midas.io.core;

import java.net.SocketAddress;

import com.itunic.midas.io.core.commons.HandlerTools;
import com.itunic.midas.io.core.initializer.MessageInitializerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RequestDispatcher {
	private EventLoopGroup worker = null;
	private static volatile RequestDispatcher dis = null;
	private static volatile Bootstrap b = null;

	public static RequestDispatcher getInstance(EventLoopGroup worker) {
		if (dis == null && b == null) {
			synchronized (RequestDispatcher.class) {
				if (dis == null && b == null) {
					dis = new RequestDispatcher(worker);
				}
			}
		}
		return dis;
	}

	private RequestDispatcher(EventLoopGroup worker) {
		this.worker =worker;
		b = new Bootstrap();
		b.group(worker).channel(NioSocketChannel.class)
				.handler(MessageInitializerFactory.getSendChildHandler(HandlerTools.JDK_SEND))
				.option(ChannelOption.SO_KEEPALIVE, true);
	}

	public void connect(final SocketAddress serviceAddress, final TransportCallBack call) {
		System.out.println(serviceAddress+"--"+b);
		
		ChannelFuture cf = b.connect(serviceAddress);
		cf.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture f) throws Exception {

				if (f.isSuccess()) {
					call.setCallBackFuture(f);
				} else {
					worker.submit(new Runnable() {
						@Override
						public void run() {
							RequestDispatcher.this.connect(serviceAddress, call);
						}
					});
				}
			}
		});
	}
}
