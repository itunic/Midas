package com.itunic.midas.model.request;


import com.itunic.midas.model.AbstractMessageModel;
/**
 * 
 *  ������Ϣ��һ�����
 * @ClassName AbstractRequestMessageModel
 * @author ����
 * @Date 2017��7��20�� ����11:05:47
 * @version 1.0.0
 */
public abstract class AbstractRequestMessageModel extends AbstractMessageModel {

	
	private static final long serialVersionUID = 8655973930143910657L;
	private long requestTime;

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

}
