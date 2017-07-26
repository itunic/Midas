package com.itunic.midas.io.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.itunic.midas.io.core.handler.MessageSendHandler;

import io.netty.channel.ChannelFuture;

public class TransportCallBack {
	private Lock lock = new ReentrantLock();
	private Condition over = lock.newCondition();
	private volatile MessageSendHandler handle = null;
	private volatile ChannelFuture callBackFuture = null;

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
	
	public MessageSendHandler getHandle() throws InterruptedException {
		try {
			lock.lock();
			if (handle == null) {
				over.await();
			}
			return handle;
		} finally {
			lock.unlock();
		}
	}

	public void setHandle(MessageSendHandler handle) {
		try {
			lock.lock();
			this.handle = handle;
			over.signal();
		} finally {
			lock.unlock();
		}

	}


}
