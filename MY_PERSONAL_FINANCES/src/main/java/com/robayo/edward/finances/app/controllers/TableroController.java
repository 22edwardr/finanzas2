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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.robayo.edward.finances.app.models.Categoria;
import com.robayo.edward.finances.app.models.CustomUser;
import com.robayo.edward.finances.app.models.Fuente;
import com.robayo.edward.finances.app.models.TableroForm;
import com.robayo.edward.finances.app.service.ICategoriaService;
import com.robayo.edward.finances.app.service.IFuenteService;
import com.robayo.edward.finances.app.service.ITableroService;
import com.robayo.edward.finances.app.utils.MessageType;

@Secured("ROLE_USUARIO")
@Controller
@RequestMapping("/tablero")
@SessionAttributes("tableroForm")
public class TableroController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ITableroService tableroService;
	@Autowired
	private ICategoriaService categoriaService;
	@Autowired
	private IFuenteService fuenteService;

	@ModelAttribute("categorias")
	public Map<Long, String> consultaCategorias(Authentication auth) {
		Long usuarioId;
		List<Categoria> categoriasLista;
		Map<Long, String> categorias;

		usuarioId = ((CustomUser) auth.getPrincipal()).getId();
		categoriasLista = categoriaService.consultaTodosActivos(usuarioId);
		categorias = new HashMap<>();

		if (categoriasLista != null && categoriasLista.size() > 0)
			categoriasLista.forEach(categoria -> {
				String nombreCategoria;
				nombreCategoria = categoria.getNombre();
				nombreCategoria = nombreCategoria != null && nombreCategoria.trim().length() > 0
						? " - " + nombreCategoria
						: "";

				categorias.put(categoria.getId(), categoria.getNomeclatura() + nombreCategoria);
			});

		return categorias;

	}

	@ModelAttribute("fuentes")
	public Map<Long, String> consultaFuentes(Authentication auth) {
		Long usuarioId;
		List<Fuente> fuentesLista;
		Map<Long, String> fuentes;

		usuarioId = ((CustomUser) auth.getPrincipal()).getId();
		fuentesLista = fuenteService.consultaTodosActivos(usuarioId);
		fuentes = new HashMap<>();

		if (fuentesLista != null && fuentesLista.size() > 0)
			fuentesLista.forEach(categoria -> {
				String nombreCategoria;
				nombreCategoria = categoria.getNombre();
				nombreCategoria = nombreCategoria != null && nombreCategoria.trim().length() > 0
						? " - " + nombreCategoria
						: "";

				fuentes.put(categoria.getId(), categoria.getNomeclatura() + nombreCategoria);
			});

		return fuentes;

	}

	@GetMapping("/")
	public String getIndex(Model model, Locale locale) {
		TableroForm tableroForm;

		tableroForm = new TableroForm();

		model.addAttribute("titulo", messageSource.getMessage("text.tablero.titulo", null, locale));
		model.addAttribute("tableroForm", tableroForm);

		return "tablero/form";
	}

	@PostMapping("/form")
	public String postForm(@Valid @ModelAttribute("tableroForm") TableroForm filtro, BindingResult result, Model model,
			Locale locale, SessionStatus status, Authentication auth) {

		model.addAttribute("titulo", messageSource.getMessage("text.tablero.titulo", null, locale));

		if (result.hasErrors())
			return "tablero/form";

		Long idUsuario = ((CustomUser) auth.getPrincipal()).getId();

		TableroForm tableroForm;
		tableroForm = tableroService.consultaTablero(filtro, idUsuario);

		if (tableroForm != null) {
			model.addAttribute("tableroForm", tableroForm);
		} else {
			model.addAttribute(MessageType.warning.toString(),
					messageSource.getMessage("text.tablero.noResultados", null, locale));
		}

		return "tablero/form";
	}

}
