package com.itunic.midas.io.core;

import java.util.concurrent.ConcurrentHashMap;

import com.itunic.midas.io.core.commons.HandlerTools;
import com.itunic.midas.io.core.initializer.MessageInitializerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ResponseDispatcher implements Runnable {
	private static final ConcurrentHashMap<String, Class<?>> serviceMap = new ConcurrentHashMap<String, Class<?>>();
	private int port;

	public ResponseDispatcher(int port) {
		this.port = port;
	}

	public void register(Class<?> serviceInterface, Class<?> impl) {
		serviceMap.put(serviceInterface.getName(), impl);
	}

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(MessageInitializerFactory.getRevcChildHandler(HandlerTools.JDK_RECV, serviceMap))
					.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = b.bind(port).sync();
			System.out.printf("Netty RPC Server start success port:%d\n", port);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		ResponseDispatcher server = new ResponseDispatcher(8080);
		//server.register(RPCTest.class, RPCTestImpl.class);
		server.run();
	}
}
