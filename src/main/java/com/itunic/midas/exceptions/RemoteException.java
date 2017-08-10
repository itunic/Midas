package com.itunic.midas.exceptions;

public class RemoteException extends Throwable {

	private static final long serialVersionUID = -4789180619791120857L;

	public RemoteException() {
		super();
	}

	public RemoteException(String message) {
		super(message);
	}

	public RemoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoteException(Throwable cause) {
		super(cause);
	}
}
