package com.robayo.edward.finances.app.exception.handler;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private ServiceExceptionTypes type;
	private String message;

	public ServiceException(Throwable e) {
		this(e, e.getMessage());
	}
	
	public ServiceException(Throwable e,String message) {
		this(e,message,ServiceExceptionTypes.ERROR);
	}
	
	public ServiceException(Throwable e,String message,ServiceExceptionTypes type) {
		this(message,type);
		this.initCause(e);
		
	}
	
	public ServiceException(String message) {
		this(message,ServiceExceptionTypes.ERROR);
	}
	
	public ServiceException(String message,ServiceExceptionTypes type) {
		this.type = type;
		this.message = message;
	}
	
	public ServiceExceptionTypes getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	
}
