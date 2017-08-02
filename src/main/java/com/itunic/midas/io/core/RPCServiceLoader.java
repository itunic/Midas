package com.itunic.midas.io.core;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.itunic.midas.io.core.handler.MessageSendHandler;
import com.itunic.midas.io.model.request.RPCRequestMessageModel;

public class RPCServiceLoader {
	private volatile static RPCServiceLoader loader = null;

	private RPCServiceLoader() {
	};

	public static RPCServiceLoader getInstance() {
		if (loader == null) {
			synchronized (RPCServiceLoader.class) {
				if (loader == null) {
					loader = new RPCServiceLoader();
				}
			}
		}
		return loader;
	}

	public Object start(RPCRequestMessageModel request) throws InterruptedException {
		MessageSendHandler handle = loader.loadService(loader.getTestServiceActiveConnection());

		return handle.sendMessageRequest(request).start();

	}

	public String[] getServiceActiveConnection() {
		return null;
	}

	public SocketAddress getTestServiceActiveConnection() {
		long a = System.currentTimeMillis();
		if (a % 2 == -1) {
			return new InetSocketAddress("localhost", 21880);
		} else {
			return new InetSocketAddress("localhost", 20880);
		}

	}

	private MessageSendHandler loadService(SocketAddress socketAddress) {
		return TransportClientFactory.getTransportClient(socketAddress);
	}

}
