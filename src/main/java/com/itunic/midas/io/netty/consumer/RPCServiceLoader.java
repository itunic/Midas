package com.itunic.midas.io.netty.consumer;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.itunic.midas.io.netty.handler.MessageSendHandler;
import com.itunic.midas.io.netty.producer.ThreadPoolExecutorFactory;
import com.itunic.midas.model.request.RPCRequestMessageModel;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class RPCServiceLoader {
	/**
	 * 1，注册中心寻找报活服务 2，负载均衡算法 3，调用
	 */
	//private volatile static RPCServiceLoader loader = null;
	private volatile MessageSendHandler handle = null;
	private Lock lock = new ReentrantLock();
	private Condition over = lock.newCondition();
	private EventLoopGroup worker = new NioEventLoopGroup();
	//private ThreadPoolExecutor thread = ThreadPoolExecutorFactory.getThreadPoolExecutor();

	private RPCServiceLoader() {
	};

	public static RPCServiceLoader getInstance() {

	/*	if (null == loader) {
			synchronized (RPCServiceLoader.class) {
				if (null == loader) {
					loader = new RPCServiceLoader();
				}
			}
		}*/

		return new RPCServiceLoader();
	}

	public Object start(RPCRequestMessageModel request) throws InterruptedException {
		try {
			System.out.println("netty client start");
			this.loadService(getTestServiceActiveConnection());
			return this.getHandle().sendMessageRequest(request).start();
		} finally {
			worker.shutdownGracefully();
			 //thread.shutdown();
		}
	}

	/**
	 * 
	 * 注册中心获取连接
	 * 
	 * @author yinbin
	 * @Date 2017年7月21日 下午5:22:35
	 * @version 1.0.0
	 * @return
	 */
	public String[] getServiceActiveConnection() {
		return null;
	}

	public SocketAddress getTestServiceActiveConnection() {

		return new InetSocketAddress("localhost", 8080);

	}

	private void loadService(SocketAddress socketAddress) {
		NettyClient client = new NettyClient(socketAddress, worker, this);
		//System.out.println("thread"+thread.getTaskCount());
		//thread.submit(client);
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
