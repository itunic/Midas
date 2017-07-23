package com.itunic.midas.io.netty.handler;

import java.util.concurrent.ConcurrentHashMap;

import com.itunic.midas.model.request.RPCRequestMessageModel;
import com.itunic.midas.model.response.RPCMessageCallBack;
import com.itunic.midas.model.response.RPCResponseMessageModel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageSendHandler extends ChannelInboundHandlerAdapter {
	private static ConcurrentHashMap<String, RPCMessageCallBack> mapCallBack = new ConcurrentHashMap<String, RPCMessageCallBack>();
	private ChannelHandlerContext ctx = null;

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RPCResponseMessageModel response = (RPCResponseMessageModel) msg;
		String messageId = response.getMessageId();
		RPCMessageCallBack callBack = mapCallBack.get(messageId);
		System.out.println(mapCallBack.size()+"--"+Thread.currentThread().getName());
		if (null != callBack) {
			mapCallBack.remove(messageId);
			callBack.finish(response);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	public RPCMessageCallBack sendMessageRequest(RPCRequestMessageModel request) {
		RPCMessageCallBack callBack = new RPCMessageCallBack();
		mapCallBack.put(request.getMessageId(), callBack);
		ctx.writeAndFlush(request);
		return callBack;

	}

}
