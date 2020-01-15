package com.robayo.edward.finances.app.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.robayo.edward.finances.app.exception.handler.ServiceException;
import com.robayo.edward.finances.app.models.Usuario;
import com.robayo.edward.finances.app.service.ILoginService;
import com.robayo.edward.finances.app.service.IUploadFileService;
import com.robayo.edward.finances.app.utils.MessageType;
import com.robayo.edward.finances.app.utils.MessageViewAggregator;

@Controller
@SessionAttributes("usuario")
public class LoginController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ILoginService loginBusiness;
	@Autowired
	private BCryptPasswordEncoder passwordEncorder;
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = { "/" })
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash, Locale locale) {
		if (principal != null) {
			flash.addFlashAttribute(MessageType.info.toString(), messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute(MessageType.error.toString(), messageSource.getMessage("text.login.error", null, locale));
		}

		if (logout != null) {
			model.addAttribute(MessageType.success.toString(), messageSource.getMessage("text.login.logout", null, locale));
		}

		return "login";
	}

	@GetMapping("/register")
	public String register(Model model, Locale locale) {
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));

		return "register";
	}
	
	@GetMapping("/confirmacionCorreo")
	public String confirmacionCorreo(@RequestParam(value = "token", required = false) String token, Locale locale,RedirectAttributes flash) {
		String email;
		
		email = null;
		
		try {
			email = loginBusiness.confirmacionCorreoConfirmacionUsuario(token);
		}catch(ServiceException e) {
			MessageViewAggregator.addMessageToModelFromServiceException(messageSource, flash, locale, e);
		}
		
		if(email != null) 
			flash.addAttribute(MessageType.success.toString(),messageSource.getMessage("text.register.titulo", new Object[] {email}, locale));

		return "redirect:/login";
	}
	
	@PostMapping("/register")
	public String formUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model,
			Locale locale, @RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash)
			throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));
			return "register";
		}

		try {
			if (!foto.isEmpty()) {
				String fileName;

				fileName = uploadFileService.copy(foto);

				flash.addFlashAttribute(MessageType.info.toString(), messageSource.getMessage("text.cliente.flash.foto.subir.success",
						new Object[] { fileName }, locale));
				usuario.setFoto(fileName);
			}

			usuario.setPassword(passwordEncorder.encode(usuario.getPassword()));

			loginBusiness.crearUsuario(usuario, Usuario.ROL_USUARIO);
			
			loginBusiness.envioCorreoConfirmacionUsuario(usuario);
		} catch (ServiceException e) {
			MessageViewAggregator.addMessageToModelFromServiceException(messageSource, model, locale, e);
			return "register";
		}

		status.setComplete();
		flash.addFlashAttribute(MessageType.success.toString(), messageSource.getMessage("text.register.exitoso", null, locale));

		return "redirect:/";
	}

}
