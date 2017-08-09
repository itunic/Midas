package com.itunic.midas.rmi;

import com.itunic.midas.io.model.request.RPCRequestMessageModel;

public interface Invoker {

	/**
	 * 启动请求
	 * 
	 * @param requestMessageModel
	 * @return
	 * @throws InterruptedException
	 */
	public Object start(RPCRequestMessageModel requestMessageModel) throws InterruptedException;

	/**
	 * 返回加载器昵称
	 * 
	 * @return nickname
	 */
	public String getNickname();
}
