package com.itunic.midas.io.core;

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itunic.midas.io.core.handler.MessageSendHandler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 
 * 获取客户端到服务端连接工厂类
 * 
 * @ClassName TransportClientFactory
 * @author yinbin
 * @Date 2017年7月27日 上午9:51:14
 * @version 1.0.0
 */
public class TransportClientFactory {
	private static Logger logger = LoggerFactory.getLogger(TransportClientFactory.class);
	private static EventLoopGroup worker = new NioEventLoopGroup();
	/**
	 * 客户端连接池
	 */
	private static ConcurrentHashMap<SocketAddress, TransportClientVo> clients = new ConcurrentHashMap<SocketAddress, TransportClientVo>();
	private static volatile boolean flag = false;
	static {
		ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(1);
		ses.scheduleAtFixedRate(new ClientsCeckTask(), 1, 5, TimeUnit.MINUTES);
	}

	/**
	 * 
	 * 获取一个客户端连接的MessageSendHandler
	 * 
	 * @author yinbin
	 * @Date 2017年7月27日 上午9:55:59
	 * @version 1.0.0
	 * @param address
	 * @return MessageSendHandler
	 */
	public static MessageSendHandler getTransportClient(SocketAddress address) {
		TransportClientVo call = clients.get(address);
		// 获取一个future，如果为空或不可用状态。则需要重新建立连接！
		if (call == null || !call.getFuture().isSuccess()) {
			flag = false;
			synchronized (TransportClientFactory.class) {
				if (!flag) {
					TransportCallBack ca = new TransportCallBack();
					TransportClientVo vo = new TransportClientVo();
					RequestDispatcher dis = RequestDispatcher.getInstance(worker);
					dis.connect(address, ca);
					// 将返回对象拷贝至TransportClientVo，目的是在并发场景下，消除锁带来的性能影响
					vo.setFuture(ca.getCallBackFuture());
					/**
					 * 将新的连接放入连接池
					 */
					clients.put(address, vo);
					flag = true;
				}
			}

		}
		return getHandle(clients.get(address));
	}

	/**
	 * 
	 * 获取该channel的MessageSendHandler
	 * 
	 * @author yinbin
	 * @Date 2017年7月27日 上午9:54:40
	 * @version 1.0.0
	 * @param future
	 * @return
	 */
	private static MessageSendHandler getHandle(TransportClientVo vo) {
		ChannelFuture future = vo.getFuture();
		MessageSendHandler handle = future.channel().pipeline().get(MessageSendHandler.class);
		vo.setTimeMillis(System.currentTimeMillis());
		// loader.setHandle(handle);
		return handle;
	}

	private static class ClientsCeckTask implements Runnable {

		@Override
		public void run() {
			if (clients.size() == 0) {
				logger.info("TransportGC:Transport连接池为空，暂不需要释放资源！");
				return;
			}
			long thisTime = System.currentTimeMillis();
			Set<SocketAddress> set = new HashSet<SocketAddress>();
			logger.info("TransportGC:开始执行，连接池资源数量:{}", clients.size());
			clients.forEach((key, value) -> {
				long oldTime = value.getTimeMillis();
				long checkTime = (thisTime - oldTime) / 1000 / 60;
				if (checkTime >= 10) {
					set.add(key);
				}
			});
			set.forEach(key -> {
				clients.remove(key);
			});
			logger.info("TransportGC:执行完毕，共释放资源数量：{}，连接池剩余资源数量：{}", set.size(), clients.size());
		}

	}
}
