package com.itunic.midas.io.core;

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
	private Lock lock = new ReentrantLock();
	private Condition over = lock.newCondition();
	private volatile ChannelFuture callBackFuture = null;
	private long timeMillis;

	public ChannelFuture getCallBackFuture() {
		try {
			lock.lock();
			if (callBackFuture == null) {
				over.await();
			}
			return callBackFuture;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return null;
		} finally {
			lock.unlock();
		}
	}

	public void setCallBackFuture(ChannelFuture callBackFuture) {
		try {
			lock.lock();
			this.callBackFuture = callBackFuture;
			over.signal();
		} finally {
			lock.unlock();
		}
		this.callBackFuture = callBackFuture;
	}

	public long getTimeMillis() {
		return timeMillis;
	}

	public void setTimeMillis(long timeMillis) {
		this.timeMillis = timeMillis;
	}

}
