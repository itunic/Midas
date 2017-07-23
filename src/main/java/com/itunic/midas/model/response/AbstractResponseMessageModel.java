package com.itunic.midas.model.response;


import com.itunic.midas.model.AbstractMessageModel;

public abstract class AbstractResponseMessageModel extends AbstractMessageModel {

	private static final long serialVersionUID = 7642378106940722244L;
	private long responseTime;

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

}
