package com.robayo.edward.finances.app.exception.handler;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ServiceExceptionTypes type;
	private String message;
	private Object[] messageParams;

	public ServiceException(Throwable e) {
		this(e, e.getMessage());
	}

	public ServiceException(Throwable e, String message) {
		this(e, message, ServiceExceptionTypes.error, null);
	}

	public ServiceException(Throwable e, String message, ServiceExceptionTypes type, Object[] messageParams) {
		this(message, type, messageParams);
		this.initCause(e);

	}

	public ServiceException(String message) {
		this(message, ServiceExceptionTypes.error, null);
	}
	
	public ServiceException(String message,Object[] messageParams) {
		this(message, ServiceExceptionTypes.error, messageParams);
	}

	public ServiceException(String message, ServiceExceptionTypes type, Object[] messageParams) {
		this.type = type;
		this.message = message;
		this.messageParams = messageParams;
	}

	public ServiceExceptionTypes getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public Object[] getMessageParams() {
		return messageParams;
	}

}
