package com.itunic.midas.model.response;

public abstract class AbstractMessageCallBack<T extends AbstractResponseMessageModel> {
	public abstract Object start();

	public abstract void finish(T response);
}
