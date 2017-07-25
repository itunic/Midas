package com.itunic.midas.io.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
	private ConcurrentHashMap<String, Class<?>> serviceMap = new ConcurrentHashMap<String, Class<?>>();
	private int port = 8080;
	private int threads = 4;
	private String serialization = HandlerTools.JDK_RECV;

	public ResponseDispatcher(int port, int threads, String serialization) {
		this.port = port;
		this.threads = threads;
		this.serialization = serialization;
	}

	public void register(Class<?> serviceInterface, Class<?> impl) {
		serviceMap.put(serviceInterface.getName(), impl);
	}

	public void register(ConcurrentHashMap<String, Class<?>> serviceMap) {
		this.serviceMap = serviceMap;
	}

	@Override
	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup(threads);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(MessageInitializerFactory.getRevcChildHandler(serialization, serviceMap))
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
		try {
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
