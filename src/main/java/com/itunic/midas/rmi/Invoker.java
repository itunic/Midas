package com.itunic.midas.rmi;

import com.itunic.midas.io.model.request.RPCRequestMessageModel;

public interface Invoker {

	public Object start(RPCRequestMessageModel requestMessageModel) throws InterruptedException;

}
