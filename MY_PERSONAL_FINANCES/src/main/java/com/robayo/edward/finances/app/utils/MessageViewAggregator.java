package com.robayo.edward.finances.app.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.Model;

import com.robayo.edward.finances.app.exception.handler.ServiceException;
import com.robayo.edward.finances.app.exception.handler.ServiceExceptionTypes;

public class MessageViewAggregator {

	public static boolean addMessageToModel(Model model, ServiceExceptionTypes type, String message, Object... params) {
		if (model != null && type != null) {
			model.addAttribute(type.toString(), message);

			return true;
		}

		return false;
	}

	public static boolean addMessageToModel(MessageSource messageSource, Model model, Locale locale,
			ServiceExceptionTypes type, String message, Object... params) {
		if (messageSource != null && locale != null) {
			String finalMessage;

			finalMessage = message;
			try {
				finalMessage = messageSource.getMessage(message, params, locale);
			} catch (NoSuchMessageException e) {
			}

			return addMessageToModel(model, type, finalMessage, params);
		}

		return false;
	}

	public static boolean addMessageToModelFromServiceException(MessageSource messageSource, Model model, Locale locale,
			ServiceException serviceException) {
		if (serviceException != null)
			return addMessageToModel(messageSource, model, locale, serviceException.getType(),
					serviceException.getMessage(), serviceException.getMessageParams());
		return false;
	}

	public static boolean addMessageToModelFromServiceException(MessageSource messageSource, Model model, Locale locale,
			ServiceException serviceException, Object... params) {
		if (serviceException != null)
			return addMessageToModelFromServiceException(messageSource, model, locale,
					new ServiceException(serviceException.getMessage(), serviceException.getType(), params));
		return false;
	}

}
