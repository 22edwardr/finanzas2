package com.robayo.edward.finances.app.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.robayo.edward.finances.app.exception.handler.ServiceException;
import com.robayo.edward.finances.app.models.Usuario;
import com.robayo.edward.finances.app.models.Usuario.OlvidoClaveValidatorEmail;
import com.robayo.edward.finances.app.models.Usuario.OlvidoClaveValidatorPreguntas;
import com.robayo.edward.finances.app.models.Usuario.RegistroValidator;
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
	public String index(Model model, Locale locale,HttpServletRequest request) {
		model.addAttribute("titulo",messageSource.getMessage("text.inicio.titulo", null, locale));
		
		for(GrantedAuthority a : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			logger.info(a);
		}
		return "index";
	}

	@GetMapping("/confirmacionCorreo")
	public String getConfirmacionCorreo(@RequestParam(value = "token", required = false) String token, Locale locale,
			RedirectAttributes flash) {
		String email;

		email = null;

		try {
			email = loginBusiness.confirmacionCorreoConfirmacionUsuario(token);
		} catch (ServiceException e) {
			MessageViewAggregator.addMessageToModelFromServiceException(messageSource, flash, locale, e);
		}

		if (email != null)
			flash.addFlashAttribute(MessageType.success.toString(),
					messageSource.getMessage("text.register.correoConfirmado", new Object[] { email }, locale));

		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash, Locale locale) {
		if (principal != null) {
			flash.addFlashAttribute(MessageType.info.toString(),
					messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/";
		}

		if (error != null) {
			model.addAttribute(MessageType.error.toString(),
					messageSource.getMessage("text.login.error", null, locale));
		}

		if (logout != null) {
			model.addAttribute(MessageType.success.toString(),
					messageSource.getMessage("text.login.logout", null, locale));
		}

		model.addAttribute("titulo", messageSource.getMessage("text.login.inicioDeSesion", null, locale));

		return "login";
	}

	@GetMapping("/olvidoClave")
	public String getOlvidoClave(Model model, Locale locale) {
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", messageSource.getMessage("text.olvidoClave.titulo", null, locale));

		return "olvidoClave";
	}

	@GetMapping(value = "/olvidoClave/cargarPreguntas/{email}", produces = { "application/json" })
	public @ResponseBody Usuario getCargarPreguntas(Model model, @PathVariable("email") String email) {
		return loginBusiness.consultaPreguntasEmailUsuario(email);
	}

	@GetMapping("/register")
	public String getRegister(Model model, Locale locale) {
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));

		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@Validated(value = RegistroValidator.class) @ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model, Locale locale, @RequestParam("file") MultipartFile foto,
			SessionStatus status, RedirectAttributes flash) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));
			return "register";
		}

		try {
			if (!foto.isEmpty()) {
				String fileName;

				fileName = uploadFileService.copy(foto);

				flash.addFlashAttribute(MessageType.info.toString(), messageSource
						.getMessage("text.cliente.flash.foto.subir.success", new Object[] { fileName }, locale));
				usuario.setFoto(fileName);
			}

			usuario.setPassword(passwordEncorder.encode(usuario.getPassword()));

			loginBusiness.crearUsuario(usuario, Usuario.ROL_USUARIO);

			loginBusiness.envioCorreoConfirmacionUsuario(usuario);
		} catch (ServiceException e) {
			MessageViewAggregator.addMessageToModelFromServiceException(messageSource, model, locale, e);
			model.addAttribute("titulo", messageSource.getMessage("text.register.titulo", null, locale));
			return "register";
		}

		status.setComplete();
		flash.addFlashAttribute(MessageType.success.toString(),
				messageSource.getMessage("text.register.exitoso", null, locale));

		return "redirect:/";
	}

	@PostMapping("/olvidoClave/email")
	public String postOlvidoClaveEmail(@Validated(value = OlvidoClaveValidatorEmail.class) @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model,
			Locale locale, SessionStatus status, RedirectAttributes flash) {
		
		if (result.hasErrors()) {

			model.addAttribute("titulo", messageSource.getMessage("text.olvidoClave.titulo", null, locale));

			return "olvidoClave";
		}

		loginBusiness.generarContrasenaUsuario(usuario.getEmail(), true);

		flash.addFlashAttribute(MessageType.success.toString(),
				messageSource.getMessage("text.olvidoClave.contrasenaGeneradaCorreo", null, locale));

		status.setComplete();

		return "redirect:/login";

	}

	@PostMapping("/olvidoClave/preguntas")
	public String postOlvidoClavePreguntas(
			@Validated(value = OlvidoClaveValidatorPreguntas.class) @ModelAttribute("usuario") Usuario usuario,
			BindingResult result, Model model, Locale locale, SessionStatus status, RedirectAttributes flash) {

		String contrasenaGenerada;

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.olvidoClave.titulo", null, locale));

			return "olvidoClave";
		}

		if (!loginBusiness.confirmacionPreguntasUsuario(usuario)) {
			model.addAttribute(MessageType.error.toString(),
					messageSource.getMessage("text.olvidoClave.preguntasNoCoinciden", null, locale));

			return "olvidoClave";
		}

		contrasenaGenerada = loginBusiness.generarContrasenaUsuario(usuario.getEmail(), false);

		flash.addFlashAttribute(MessageType.success.toString(), messageSource
				.getMessage("text.olvidoClave.contrasenaGenerada", new Object[] { contrasenaGenerada }, locale));

		status.setComplete();

		return "redirect:/login";

	}

}
