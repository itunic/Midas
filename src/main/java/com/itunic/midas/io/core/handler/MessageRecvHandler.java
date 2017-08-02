package com.itunic.midas.io.core.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import com.itunic.midas.io.core.ResponseProcessTask;
import com.itunic.midas.io.core.ThreadPoolExecutorFactory;
import com.itunic.midas.io.model.request.RPCRequestMessageModel;
import com.itunic.midas.io.model.response.RPCResponseMessageModel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageRecvHandler extends ChannelInboundHandlerAdapter {
	private Map<String, Object> serviceMap = null;

	public MessageRecvHandler(ConcurrentHashMap<String, Object> serviceMap) {
		this.serviceMap = serviceMap;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("RPCServer:"+Thread.currentThread().getName()+" id:"+this);
		RPCRequestMessageModel request = (RPCRequestMessageModel) msg;
		RPCResponseMessageModel response = new RPCResponseMessageModel();
		ThreadPoolExecutor thread = ThreadPoolExecutorFactory.getThreadPoolExecutor();
		thread.submit(new ResponseProcessTask(request, response, ctx, serviceMap));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
