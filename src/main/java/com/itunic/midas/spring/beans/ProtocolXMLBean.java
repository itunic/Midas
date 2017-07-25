package com.itunic.midas.spring.beans;

public class ProtocolXMLBean {
	private String name;
	private Integer port;
	private Integer threads;
	private String serialization;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public String getSerialization() {
		return serialization;
	}

	public void setSerialization(String serialization) {
		this.serialization = serialization;
	}

	@Override
	public String toString() {
		return "ProtocolXMLBean [name=" + name + ", port=" + port + ", threads=" + threads + ", serialization="
				+ serialization + "]";
	}

}
