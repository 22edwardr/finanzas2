package com.robayo.edward.finances.app.exception.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String customExceptionHandler(HandlerMethod handlerMethod) {
		return "error";
	}

}
