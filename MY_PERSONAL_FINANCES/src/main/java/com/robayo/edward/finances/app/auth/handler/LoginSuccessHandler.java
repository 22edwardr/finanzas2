package com.robayo.edward.finances.app.auth.handler;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.robayo.edward.finances.app.utils.MessageType;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();
		
		Locale locale = localeResolver.resolveLocale(request);
		String mensaje = messageSource.getMessage("text.login.success", new Object[] {authentication.getName()}, locale);
		
		flashMap.put(MessageType.success.toString(), mensaje);
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			logger.info(mensaje);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
