package com.itunic.midas.registration.vo;

import java.io.Serializable;

public class ConnectionVo implements Serializable {
	private static final long serialVersionUID = -3640224971250618243L;
	private String regType;
	private String remoteUrl;
	private boolean isActive;

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
