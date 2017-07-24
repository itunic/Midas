package com.itunic.midas.io.core;

import java.net.SocketAddress;

import com.itunic.midas.io.core.commons.HandlerTools;
import com.itunic.midas.io.core.handler.MessageSendHandler;
import com.itunic.midas.io.core.initializer.MessageInitializerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RequestDispatcher implements Runnable {
	private EventLoopGroup worker = null;
	private SocketAddress serviceAddress = null;
	private RPCServiceLoader loader = null;

	public RequestDispatcher(SocketAddress serviceAddress, EventLoopGroup worker, RPCServiceLoader loader) {
		this.serviceAddress = serviceAddress;
		this.worker = worker;
		this.loader = loader;
	}

	@Override
	public void run() {
		Bootstrap b = new Bootstrap();
		b.group(worker).channel(NioSocketChannel.class)
				.handler(MessageInitializerFactory.getSendChildHandler(HandlerTools.JDK_SEND));
		// .option(ChannelOption.SO_KEEPALIVE, true);
		ChannelFuture cf = b.connect(serviceAddress);
		cf.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture f) throws Exception {

				if (f.isSuccess()) {
					MessageSendHandler handle = f.channel().pipeline().get(MessageSendHandler.class);
					loader.setHandle(handle);
				} else {
					f.cause().printStackTrace();
					worker.submit(new Runnable() {
						@Override
						public void run() {
							RequestDispatcher.this.run();
						}
					});
				}
			}
		});
	}
}
