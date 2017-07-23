package com.itunic.midas.io.netty.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import com.itunic.midas.io.netty.producer.RPCMessageRecvTask;
import com.itunic.midas.io.netty.producer.ThreadPoolExecutorFactory;
import com.itunic.midas.model.request.RPCRequestMessageModel;
import com.itunic.midas.model.response.RPCResponseMessageModel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
	private Map<String, Class<?>> serviceMap = null;

	public MessageRecvHandler(ConcurrentHashMap<String, Class<?>> serviceMap) {
		this.serviceMap = serviceMap;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		RPCRequestMessageModel request = (RPCRequestMessageModel) msg;
		RPCResponseMessageModel response = new RPCResponseMessageModel();
		ThreadPoolExecutor thread = ThreadPoolExecutorFactory.getThreadPoolExecutor();
		thread.submit(new RPCMessageRecvTask(request, response, ctx, serviceMap));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
