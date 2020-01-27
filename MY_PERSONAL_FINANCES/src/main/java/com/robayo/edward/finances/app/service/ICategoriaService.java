package com.robayo.edward.finances.app.service;

import java.util.List;

import com.robayo.edward.finances.app.models.Categoria;

public interface ICategoriaService {
	public void crear(Categoria categoria);

	public void actualizar(Categoria categoria);

	public void actualizarEstado(Categoria categoria);

	public List<Categoria> consultaTodos(Long usuarioId, String likeText);
	
	public Categoria consultaUno(Long id,Long usuarioId);

	public List<Categoria> consultaTodosActivos(Long usuarioId);
}
