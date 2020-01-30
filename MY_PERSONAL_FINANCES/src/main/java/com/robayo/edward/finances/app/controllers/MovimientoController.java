package com.robayo.edward.finances.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.robayo.edward.finances.app.models.Categoria;
import com.robayo.edward.finances.app.models.CustomUser;
import com.robayo.edward.finances.app.models.Fuente;
import com.robayo.edward.finances.app.models.Movimiento;
import com.robayo.edward.finances.app.models.MovimientoForm;
import com.robayo.edward.finances.app.service.ICategoriaService;
import com.robayo.edward.finances.app.service.IFuenteService;
import com.robayo.edward.finances.app.service.IMovimientoService;
import com.robayo.edward.finances.app.utils.MessageType;

@Secured("ROLE_USUARIO")
@Controller
@RequestMapping("/movimiento")
@SessionAttributes("movimientoForm")
public class MovimientoController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IMovimientoService movimientoService;
	@Autowired
	private ICategoriaService categoriaService;
	@Autowired
	private IFuenteService fuenteService;
	
	@ModelAttribute("categorias")
	public Map<Long,String> consultaCategorias(Authentication auth){
		Long usuarioId;
		List<Categoria> categoriasLista;
		Map<Long,String> categorias;
		
		usuarioId = ((CustomUser)auth.getPrincipal()).getId();
		categoriasLista = categoriaService.consultaTodosActivos(usuarioId);
		categorias = new HashMap<>();
		
		if(categoriasLista != null && categoriasLista.size() > 0)
			categoriasLista.forEach(categoria -> {
				String nombreCategoria;
				nombreCategoria = categoria.getNombre();
				nombreCategoria = nombreCategoria != null && nombreCategoria.trim().length() > 0 ? " - " +nombreCategoria : "";
				
				categorias.put(categoria.getId(), categoria.getNomeclatura() +nombreCategoria);
			});
		
		return categorias;
		
	}
	
	@ModelAttribute("fuentes")
	public Map<Long,String> consultaFuentes(Authentication auth){
		Long usuarioId;
		List<Fuente> fuentesLista;
		Map<Long,String> fuentes;
		
		usuarioId = ((CustomUser)auth.getPrincipal()).getId();
		fuentesLista = fuenteService.consultaTodosActivos(usuarioId);
		fuentes = new HashMap<>();
		
		if(fuentesLista != null && fuentesLista.size() > 0)
			fuentesLista.forEach(categoria -> {
				String nombreCategoria;
				nombreCategoria = categoria.getNombre();
				nombreCategoria = nombreCategoria != null && nombreCategoria.trim().length() > 0 ? " - " +nombreCategoria : "";
				
				fuentes.put(categoria.getId(), categoria.getNomeclatura() +nombreCategoria);
			});
		
		return fuentes;
		
	}
	
	@GetMapping("/")
	public String getIndex(Model model,Locale locale) {
		ArrayList<Movimiento> movimientos;
		MovimientoForm movimientoForm;
		
		movimientos = new ArrayList<>(50);
		
		movimientoForm = new MovimientoForm();
		movimientoForm.setMovimientos(movimientos);
		
		model.addAttribute("titulo",messageSource.getMessage("text.movimiento.titulo", null, locale));
		model.addAttribute("movimientoForm", movimientoForm);
		
		return "movimiento/form";
	}
	
	@GetMapping(value = "/tipoCategoria/{idCategoria}", produces = { "plain/text" })
	public @ResponseBody String getTipoCategoria(Model model, @PathVariable Long idCategoria,Authentication auth) {
		Long usuarioId; 
		Categoria categoria;
		
		usuarioId = ((CustomUser)auth.getPrincipal()).getId();
		categoria = categoriaService.consultaUno(idCategoria, usuarioId);
		
		return categoria != null ? categoria.getTipo() : "D";
	}
	
	@PostMapping("/agregarLinea")
	public String postAgregarLinea(@ModelAttribute MovimientoForm movimientoForm,Model model) {
		Long idMax;
		Movimiento movimiento;
		
		idMax=-1L;
		movimiento = new Movimiento();
		
		for (Movimiento actual : movimientoForm.getMovimientos()) {
			if(actual.getId() > idMax)
				idMax = actual.getId();
		}
		
		movimiento.setId(++idMax);
		
		movimientoForm.getMovimientos().add(movimiento);
		model.addAttribute("movimientoForm",movimientoForm);
		
		return "movimiento/form :: itemsEditar";
	}
	
	@PostMapping("/quitarLinea")
	public String postQuitarLinea(@ModelAttribute MovimientoForm movimientoForm,@RequestParam Long idEliminar, Model model) {
		
		List<Movimiento> movimientos =movimientoForm.getMovimientos().stream().filter(x -> x.getId().compareTo(idEliminar) != 0).collect(Collectors.toList()); 
		movimientoForm.setMovimientos(movimientos);
		model.addAttribute("movimientoForm",movimientoForm);
		
		return "movimiento/form :: itemsEditar";
	}
	
	
	@PostMapping("/form")
	public String postForm(@Valid @ModelAttribute("movimientoForm") MovimientoForm movimientoForm,BindingResult result,Model model,RedirectAttributes flash, Locale locale, SessionStatus status, Authentication auth) {
		if(result.hasErrors()) {
			model.addAttribute("titulo",messageSource.getMessage("text.movimiento.titulo", null, locale));
			return "movimiento/form";
		}
		
		Long idUsuario = ((CustomUser) auth.getPrincipal()).getId();
		
		for(Movimiento movimiento : movimientoForm.getMovimientos()) {
			movimiento.setIdUsuario(idUsuario);
			movimientoService.crear(movimiento);
		}
		
		flash.addFlashAttribute(MessageType.success.toString(), messageSource.getMessage("text.movimiento.registroExitoso", null, locale));
		status.setComplete();
		
		return "redirect:/";
	}
	
}
