package com.itunic.midas.io.core;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.channel.ChannelFuture;

/**
 * 
 * 连接回调。
 * 
 * @ClassName TransportCallBack
 * @author yinbin
 * @Date 2017年7月27日 上午9:59:51
 * @version 1.0.0
 */
public class TransportCallBack {
	private final Lock lock = new ReentrantLock();
	private final Condition over = lock.newCondition();
	private volatile ChannelFuture callBackFuture = null;
	private long timeMillis;

	public ChannelFuture getCallBackFuture() {
		if (!isDone()) {
			lock.lock();
			try {
				while (!isDone()) {
					over.await(0, TimeUnit.MILLISECONDS);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		return callBackFuture;
	}

	public void setCallBackFuture(ChannelFuture callBackFuture) {
		this.callBackFuture = callBackFuture;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

	public boolean isDone() {
		return callBackFuture != null;
	}

}
