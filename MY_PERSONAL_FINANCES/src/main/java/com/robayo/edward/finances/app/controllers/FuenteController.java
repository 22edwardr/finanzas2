package com.robayo.edward.finances.app.controllers;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.robayo.edward.finances.app.models.CustomUser;
import com.robayo.edward.finances.app.models.Fuente;
import com.robayo.edward.finances.app.service.IFuenteService;
import com.robayo.edward.finances.app.utils.MessageType;

@Secured("ROLE_USUARIO")
@Controller
@RequestMapping("/fuente")
@SessionAttributes("fuente")
public class FuenteController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IFuenteService fuenteService;

	@GetMapping("/")
	public String getAll(@RequestParam(required = false, value = "likeText") String likeText, Model model,
			Locale locale, Authentication auth) {
		Long idUsuario;
		List<Fuente> fuentes;

		idUsuario = ((CustomUser) auth.getPrincipal()).getId();
		fuentes = fuenteService.consultaTodos(idUsuario, likeText);

		model.addAttribute("fuentes", fuentes);
		model.addAttribute("titulo", messageSource.getMessage("text.fuente.listar.titulo", null, locale));

		return "fuente/index";
	}

	@GetMapping({ "/form", "/form/{id}" })
	public String getForm(@PathVariable(required = false, value = "id") Long id, Model model, Locale locale,
			RedirectAttributes flash, Authentication auth) {
		Fuente fuente;
		Long usuarioId;

		usuarioId = ((CustomUser) auth.getPrincipal()).getId();

		if (id != null) {
			fuente = fuenteService.consultaUno(id, usuarioId);

			if (fuente == null) {
				flash.addFlashAttribute(MessageType.error.toString(),
						messageSource.getMessage("text.fuente.noExisteFuente", null, locale));

				return "redirect:/fuente/";

			}

			model.addAttribute("titulo", messageSource.getMessage("text.fuente.editarFuente", null, locale));
		} else {
			fuente = new Fuente();
			fuente.setIdUsuario(usuarioId);
			model.addAttribute("titulo", messageSource.getMessage("text.fuente.crearFuente", null, locale));
		}

		model.addAttribute("fuente", fuente);

		return "fuente/form";
	}

	@GetMapping("/inactivarActivar/{id}")
	public String getInactivarActivar(@PathVariable("id") Long id, Model model, Locale locale, RedirectAttributes flash,
			Authentication auth) {
		Long usuarioId;
		Fuente fuente;

		usuarioId = ((CustomUser) auth.getPrincipal()).getId();
		fuente = fuenteService.consultaUno(id, usuarioId);

		if (fuente == null) {
			flash.addFlashAttribute(MessageType.error.toString(),
					messageSource.getMessage("text.fuente.noExisteFuente", null, locale));
		} else {
			Fuente fuenteNuevoEstado;

			fuenteNuevoEstado = new Fuente();
			fuenteNuevoEstado.setId(id);
			fuenteNuevoEstado.setEstado(!fuente.isEstado());

			fuenteService.actualizarEstado(fuenteNuevoEstado);

			flash.addFlashAttribute(MessageType.success.toString(),
					messageSource.getMessage("text.fuente.cambioEstadoExitoso",
							new Object[] { fuente.getNombre(),
									!fuente.isEstado() ? messageSource.getMessage("text.fuente.activo", null, locale)
											: messageSource.getMessage("text.fuente.inactivo", null, locale) },
							locale));
		}

		model.addAttribute("titulo", messageSource.getMessage("text.fuente.editarFuente", null, locale));

		return "redirect:/fuente/";
	}

	@PostMapping("/form")
	public String postForm(@Valid @ModelAttribute("fuente") Fuente fuente, BindingResult result, Model model,
			Locale locale, SessionStatus status, RedirectAttributes flash) {

		boolean editar;

		editar = fuente != null && fuente.getId() != null;

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource
					.getMessage(editar ? "text.fuente.editarFuente" : "text.fuente.crearFuente", null, locale));
			return "fuente/form";
		}

		String mensaje;

		if (editar) {
			fuenteService.actualizar(fuente);
			mensaje = "text.fuente.editarExitoso";
		} else {
			fuenteService.crear(fuente);
			mensaje = "text.fuente.crearExitoso";
		}

		status.setComplete();
		flash.addFlashAttribute(MessageType.success.toString(), messageSource.getMessage(mensaje, null, locale));

		return "redirect:/fuente/";

	}
}
