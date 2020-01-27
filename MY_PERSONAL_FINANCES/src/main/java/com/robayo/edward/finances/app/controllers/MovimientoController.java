package com.robayo.edward.finances.app.controllers;

import java.util.ArrayList;
import java.util.Calendar;
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
	
	private Movimiento addDummyData(int iteration) {
		Movimiento movimiento;
		
		movimiento = new Movimiento();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -iteration);
		
		movimiento.setFecha(cal.getTime());
		movimiento.setDescripcion("Descripcion "+ iteration);
		movimiento.setValor((double) (2000 * iteration));
		movimiento.setIdFuente(1L);
		movimiento.setIdCategoria(2L);
		
		return movimiento;
		
	}
	
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
