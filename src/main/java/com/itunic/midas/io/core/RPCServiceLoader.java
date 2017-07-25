package com.itunic.midas.io.core;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.itunic.midas.io.core.handler.MessageSendHandler;
import com.itunic.midas.io.model.request.RPCRequestMessageModel;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class RPCServiceLoader {
	// private volatile static RPCServiceLoader loader = null;
	private volatile MessageSendHandler handle = null;
	private Lock lock = new ReentrantLock();
	private Condition over = lock.newCondition();
	private EventLoopGroup worker = new NioEventLoopGroup();
	// private ThreadPoolExecutor thread =
	// ThreadPoolExecutorFactory.getThreadPoolExecutor();

	private RPCServiceLoader() {
	};

	public static RPCServiceLoader getInstance() {
		return new RPCServiceLoader();
	}

	public Object start(RPCRequestMessageModel request) throws InterruptedException {
		try {
			this.loadService(getTestServiceActiveConnection());
			return this.getHandle().sendMessageRequest(request).start();
		} finally {
			worker.shutdownGracefully();
		}
	}

	public String[] getServiceActiveConnection() {
		return null;
	}

	public SocketAddress getTestServiceActiveConnection() {

		return new InetSocketAddress("localhost", 21880);

	}

	private void loadService(SocketAddress socketAddress) {
		RequestDispatcher client = new RequestDispatcher(socketAddress, worker, this);
		// System.out.println("thread"+thread.getTaskCount());
		// thread.submit(client);
		client.run();
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
