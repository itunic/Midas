package com.itunic.midas.io.model;

import java.io.Serializable;

/**
 * 
 * ��Ϣ�Ļ�����Ϣ��һ�����
 * 
 * @ClassName AbstractMessageModel
 * @author yinbin
 * @Date 2017��7��20�� ����10:55:45
 * @version 1.0.0
 */
public abstract class AbstractMessageModel implements Serializable {
	
	private static final long serialVersionUID = 566294618948955376L;
	// ��Ϣ���ʶ
	protected String messageGroupId;
	// ��Ϣ��ʶ
	protected String messageId;
	// ��Ϣ����
	protected String messageType;
	// ��Ϣ״̬��
	protected String messageCode;

	public String getMessageGroupId() {
		return messageGroupId;
	}

	public void setMessageGroupId(String messageGroupId) {
		this.messageGroupId = messageGroupId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

}
