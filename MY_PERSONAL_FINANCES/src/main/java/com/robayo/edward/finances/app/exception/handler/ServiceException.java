package com.robayo.edward.finances.app.exception.handler;

import com.robayo.edward.finances.app.utils.MessageType;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private MessageType type;
	private String message;
	private Object[] messageParams;

	public ServiceException(Throwable e) {
		this(e, e.getMessage());
	}

	public ServiceException(Throwable e, String message) {
		this(e, message, MessageType.error, null);
	}

	public ServiceException(Throwable e, String message, MessageType type, Object[] messageParams) {
		this(message, type, messageParams);
		this.initCause(e);

	}

	public ServiceException(String message) {
		this(message, MessageType.error, null);
	}
	
	public ServiceException(String message,Object[] messageParams) {
		this(message, MessageType.error, messageParams);
	}

	public ServiceException(String message, MessageType type, Object[] messageParams) {
		this.type = type;
		this.message = message;
		this.messageParams = messageParams;
	}

	public MessageType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public Object[] getMessageParams() {
		return messageParams;
	}

}
