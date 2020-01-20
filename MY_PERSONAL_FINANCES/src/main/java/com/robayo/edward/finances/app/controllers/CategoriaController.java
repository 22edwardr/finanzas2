package com.robayo.edward.finances.app.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.robayo.edward.finances.app.models.Categoria;
import com.robayo.edward.finances.app.service.ICategoriaService;
import com.robayo.edward.finances.app.service.ILoginService;

@Controller("/categoria")
public class CategoriaController {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ICategoriaService categoriaService;
	@Autowired
	private ILoginService loginService;
	
	@GetMapping("/{likeText}")
	public String buscarCategorias(@PathVariable(required = false, value="likeText") String likeText, Model model,Locale locale, Principal principal) {
		Authentication auth;
		Long idUsuario;
		List<Categoria> categorias;
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		idUsuario = -1L;
		categorias = categoriaService.consultaTodos(idUsuario, likeText);
		
		
		return "categoria/index";
	}
}
