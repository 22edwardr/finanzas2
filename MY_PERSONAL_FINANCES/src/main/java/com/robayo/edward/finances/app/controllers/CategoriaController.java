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

import com.robayo.edward.finances.app.models.Categoria;
import com.robayo.edward.finances.app.models.CustomUser;
import com.robayo.edward.finances.app.service.ICategoriaService;
import com.robayo.edward.finances.app.utils.MessageType;

@Secured("ROLE_USUARIO")
@Controller
@RequestMapping("/categoria")
@SessionAttributes("categoria")
public class CategoriaController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ICategoriaService categoriaService;
	
	@ModelAttribute("tiposCategoria")
	public Map<String,String> consultaTiposCategoria(Locale locale){
		Map<String,String> tipos;
		
		tipos = new HashMap<>();
		
		tipos.put("C", messageSource.getMessage("text.categorias.credito",null,locale));
		tipos.put("D", messageSource.getMessage("text.categorias.debito",null,locale));
		
		return tipos;
		
	}

	@GetMapping("/")
	public String getAll(@RequestParam(required = false, value = "likeText") String likeText, Model model,
			Locale locale, Authentication auth) {
		Long idUsuario;
		List<Categoria> categorias;

		idUsuario = ((CustomUser) auth.getPrincipal()).getId();
		categorias = categoriaService.consultaTodos(idUsuario, likeText);

		model.addAttribute("categorias", categorias);
		model.addAttribute("titulo", messageSource.getMessage("text.categoria.listar.titulo", null, locale));

		return "categoria/index";
	}

	@GetMapping({"/form","/form/{id}"})
	public String getForm(@PathVariable(required = false, value = "id") Long id, Model model, Locale locale,
			RedirectAttributes flash,Authentication auth) {
		Categoria categoria;
		Long idUsuario;
		
		idUsuario = ((CustomUser)auth.getPrincipal()).getId();

		if (id != null) {
			categoria = categoriaService.consultaUno(id,idUsuario);

			if (categoria == null) {
				flash.addFlashAttribute(MessageType.error.toString(),
						messageSource.getMessage("text.categorias.noExisteCategoria", null, locale));

				return "redirect:/categoria/";

			}

			model.addAttribute("titulo", messageSource.getMessage("text.categoria.editarCategoria", null, locale));
		} else {
			categoria = new Categoria();
			categoria.setIdUsuario(idUsuario);
			model.addAttribute("titulo", messageSource.getMessage("text.categoria.crearCategoria", null, locale));
		}
		
		model.addAttribute("categoria",categoria);

		return "categoria/form";
	}

	@GetMapping("/inactivarActivar/{id}")
	public String getInactivarActivar(@PathVariable("id") Long id, Model model, Locale locale,
			RedirectAttributes flash,Authentication auth) {
		Long idUsuario;
		Categoria categoria;
		
		idUsuario = ((CustomUser)auth.getPrincipal()).getId();

		categoria = categoriaService.consultaUno(id,idUsuario);

		if (categoria == null) {
			flash.addFlashAttribute(MessageType.error.toString(),
					messageSource.getMessage("text.categorias.noExisteCategoria", null, locale));
		} else {
			Categoria categoriaNuevoEstado;
			
			categoriaNuevoEstado = new Categoria();
			categoriaNuevoEstado.setId(id);
			categoriaNuevoEstado.setEstado(!categoria.isEstado());
			categoriaService.actualizarEstado(categoriaNuevoEstado);

			flash.addFlashAttribute(MessageType.success.toString(),
					messageSource
							.getMessage("text.categoria.cambioEstadoExitoso",
									new Object[] { categoria.getNombre(), !categoria.isEstado()
											? messageSource.getMessage("text.categorias.activo", null, locale)
											: messageSource.getMessage("text.categorias.inactivo", null, locale) },
									locale));
		}

		model.addAttribute("titulo", messageSource.getMessage("text.categoria.editarCategoria", null, locale));

		return "redirect:/categoria/";
	}

	@PostMapping("/form")
	public String postForm(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result, Model model,
			Locale locale, SessionStatus status, RedirectAttributes flash) {

		boolean editar;

		editar = categoria != null && categoria.getId() != null;

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage(
					editar ? "text.categoria.editarCategoria" : "text.categoria.crearCategoria", null, locale));
			return "categoria/form";
		}

		String mensaje;

		if (editar) {
			categoriaService.actualizar(categoria);
			mensaje = "text.categoria.editarExitoso";
		} else {
			categoriaService.crear(categoria);
			mensaje = "text.categoria.crearExitoso";
		}

		status.setComplete();
		flash.addFlashAttribute(MessageType.success.toString(), messageSource.getMessage(mensaje, null, locale));

		return "redirect:/categoria/";

	}
}
