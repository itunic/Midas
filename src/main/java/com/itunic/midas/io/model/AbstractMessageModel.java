package com.itunic.midas.io.model;

import java.io.Serializable;

/**
 * 
 * 消息的基本信息的一般抽象！
 * 
 * @ClassName AbstractMessageModel
 * @author yinbin
 * @Date 2017年7月20日 上午10:55:45
 * @version 1.0.0
 */
public abstract class AbstractMessageModel implements Serializable {
	
	private static final long serialVersionUID = 566294618948955376L;
	// 消息组标识
	protected String messageGroupId;
	// 消息标识
	protected String messageId;
	// 消息类型
	protected String messageType;
	// 消息状态码
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
