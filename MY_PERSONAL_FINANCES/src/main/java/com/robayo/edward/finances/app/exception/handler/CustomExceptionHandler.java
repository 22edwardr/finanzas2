package com.robayo.edward.finances.app.exception.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@ExceptionHandler(Exception.class)
	public String customExceptionHandler(Exception e,Model model) {
		
		LocalDateTime dateTime;
		DateTimeFormatter format;
		String ticket;
		
		dateTime = LocalDateTime.now();
		format = DateTimeFormatter.ofPattern("yyyyMMddHH");
		ticket = dateTime.format(format) + String.format("%03d",(int) (Math.random() * 99999)); 
		
		logger.error("Ticket" +  ticket , e);
		
		model.addAttribute("ticket",ticket);
		model.addAttribute("titulo","Error");
		
		return "error";
	}

}
