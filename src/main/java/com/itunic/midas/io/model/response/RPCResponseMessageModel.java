package com.itunic.midas.io.model.response;

public class RPCResponseMessageModel extends AbstractResponseMessageModel {

	private static final long serialVersionUID = -5614074151539082756L;
	private Object result;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
