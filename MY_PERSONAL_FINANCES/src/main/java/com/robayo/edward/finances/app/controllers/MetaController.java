package com.robayo.edward.finances.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.robayo.edward.finances.app.models.Meta;
import com.robayo.edward.finances.app.service.IMetaService;
import com.robayo.edward.finances.app.utils.MessageType;

@Secured("ROLE_USUARIO")
@Controller
@RequestMapping("/meta")
@SessionAttributes("meta")
public class MetaController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IMetaService metaService;

	@ModelAttribute("tiposMeta")
	public Map<String, String> consultaTiposMeta(Locale locale) {
		Map<String, String> tipos;

		tipos = new HashMap<>();

		tipos.put("A", messageSource.getMessage("text.meta.ahorro", null, locale));
		tipos.put("G", messageSource.getMessage("text.meta.gasto", null, locale));

		return tipos;

	}

	@GetMapping("/")
	public String getAll(@RequestParam(required = false, value = "likeText") String likeText, Model model,
			Locale locale, Authentication auth) {
		Long idUsuario;
		List<Meta> metas;

		idUsuario = ((CustomUser) auth.getPrincipal()).getId();
		metas = metaService.consultaTodos(idUsuario, likeText);

		model.addAttribute("metas", metas);
		model.addAttribute("titulo", messageSource.getMessage("text.meta.listar.titulo", null, locale));

		return "meta/index";
	}

	@GetMapping({ "/form", "/form/{id}" })
	public String getForm(@PathVariable(required = false, value = "id") Long id, Model model, Locale locale,
			RedirectAttributes flash, Authentication auth) {
		Meta meta;
		Long idUsuario;

		idUsuario = ((CustomUser) auth.getPrincipal()).getId();

		if (id != null) {
			meta = metaService.consultaUno(id, idUsuario);

			if (meta == null) {
				flash.addFlashAttribute(MessageType.error.toString(),
						messageSource.getMessage("text.meta.noExisteMeta", null, locale));

				return "redirect:/meta/";

			}

			model.addAttribute("titulo", messageSource.getMessage("text.meta.editarMeta", null, locale));
		} else {
			meta = new Meta();
			meta.setIdUsuario(idUsuario);
			model.addAttribute("titulo", messageSource.getMessage("text.meta.crearMeta", null, locale));
		}

		model.addAttribute("meta", meta);

		return "meta/form";
	}

	@GetMapping("/eliminar/{id}")
	public String getInactivarActivar(@PathVariable("id") Long id,Locale locale, RedirectAttributes flash,
			Authentication auth) {
		Long idUsuario;
		Meta meta;

		idUsuario = ((CustomUser) auth.getPrincipal()).getId();

		meta = metaService.consultaUno(id, idUsuario);

		if (meta == null) {
			flash.addFlashAttribute(MessageType.error.toString(),
					messageSource.getMessage("text.meta.noExisteMeta", null, locale));
		} else {
			metaService.eliminar(meta);

			flash.addFlashAttribute(MessageType.success.toString(),
					messageSource.getMessage("text.meta.eliminarExitoso", null, locale));
		}

		return "redirect:/meta/";
	}

	@PostMapping("/form")
	public String postForm(@Valid @ModelAttribute("meta") Meta meta, BindingResult result, Model model,
			Locale locale, SessionStatus status, RedirectAttributes flash) {

		boolean editar;

		editar = meta != null && meta.getId() != null;

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage(
					editar ? "text.meta.editarMeta" : "text.meta.crearMeta", null, locale));
			return "meta/form";
		}

		String mensaje;

		if (editar) {
			metaService.actualizar(meta);
			mensaje = "text.meta.editarExitoso";
		} else {
			metaService.crear(meta);
			mensaje = "text.meta.crearExitoso";
		}

		status.setComplete();
		flash.addFlashAttribute(MessageType.success.toString(), messageSource.getMessage(mensaje, null, locale));

		return "redirect:/meta/";

	}
}
