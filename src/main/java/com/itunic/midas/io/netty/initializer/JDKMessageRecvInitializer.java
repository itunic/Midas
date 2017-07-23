package com.itunic.midas.io.netty.initializer;

import java.util.concurrent.ConcurrentHashMap;

import com.itunic.midas.io.netty.handler.MessageRecvHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class JDKMessageRecvInitializer extends ChannelInitializer<SocketChannel> {
	private ConcurrentHashMap<String, Class<?>> serviceMap = null;

	public JDKMessageRecvInitializer(ConcurrentHashMap<String, Class<?>> serviceMap) {
		this.serviceMap = serviceMap;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast(new LengthFieldPrepender(4));
		pipeline.addLast(new ObjectEncoder());
		pipeline.addLast(
				new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
		pipeline.addLast(new MessageRecvHandler(this.serviceMap));

	}

}
