package com.itunic.midas.io.model.request;

public class RPCRequestMessageModel extends AbstractRequestMessageModel {

	private static final long serialVersionUID = -6470005281208811707L;
	private String className;
	private String methodName;
	private Class<?>[] typeParameters;
	private Object[] args;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getTypeParameters() {
		return typeParameters;
	}

	public void setTypeParameters(Class<?>[] typeParameters) {
		this.typeParameters = typeParameters;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}
