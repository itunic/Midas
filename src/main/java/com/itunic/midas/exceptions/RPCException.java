package com.itunic.midas.exceptions;

public class RPCException extends Throwable {

	private static final long serialVersionUID = -1281918839038988397L;

	public RPCException() {
		super();
	}

	public RPCException(String message) {
		super(message);
	}

	public RPCException(String message, Throwable cause) {
		super(message, cause);
	}

	public RPCException(Throwable cause) {
		super(cause);
	}
}
