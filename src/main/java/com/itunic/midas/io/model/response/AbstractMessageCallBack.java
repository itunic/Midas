package com.itunic.midas.io.model.response;

public abstract class AbstractMessageCallBack<T extends AbstractResponseMessageModel> {
	public abstract Object start();

	public abstract void finish(T response);
}
