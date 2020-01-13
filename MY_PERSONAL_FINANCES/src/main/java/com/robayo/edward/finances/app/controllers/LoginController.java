package com.robayo.edward.finances.app.controllers;

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
import com.robayo.edward.finances.app.service.ILoginBusiness;

@Controller
@SessionAttributes("usuario")
public class LoginController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ILoginBusiness loginBusiness;
	@Autowired
	private BCryptPasswordEncoder passwordEncorder;

	@GetMapping(value = { "/" })
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash, Locale locale) {
		if (principal != null) {
			flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
		}

		if (logout != null) {
			model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
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

	@PostMapping("/register")
	public String formUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model,
			Locale locale, @RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));
			return "register";
		}

		if (!foto.isEmpty()) {

		}

		usuario.setPassword(passwordEncorder.encode(usuario.getPassword()));
		try {
			loginBusiness.crearUsuario(usuario, Usuario.ROL_USUARIO);
		} catch (ServiceException e) {
			if(e.getMessage().equals("usuarioExistente"))
				model.addAttribute("error", messageSource.getMessage("text.register.validation.usuarioExistente",
						new Object[] { usuario.getEmail() }, locale));
			
			return "register";
		}

		status.setComplete();
		flash.addFlashAttribute("success", messageSource.getMessage("text.register.exitoso", null, locale));

		return "redirect:/";
	}

}
