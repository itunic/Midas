package com.itunic.midas.io.core;

import io.netty.channel.ChannelFuture;

public class TransportClientVo {
	private ChannelFuture future;
	private long timeMillis;
	public ChannelFuture getFuture() {
		return future;
	}
	public void setFuture(ChannelFuture future) {
		this.future = future;
	}
	public long getTimeMillis() {
		return timeMillis;
	}
	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}
	
	
}
