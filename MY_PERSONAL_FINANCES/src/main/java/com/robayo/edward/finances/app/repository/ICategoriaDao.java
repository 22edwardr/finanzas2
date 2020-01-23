package com.robayo.edward.finances.app.repository;

import java.util.List;

import com.robayo.edward.finances.app.models.Categoria;

public interface ICategoriaDao {

	public void crearCategoria(Categoria categoria);
	
	public void actualizarCategoria(Categoria categoria);
	
	public void actualizarEstadoCategoria(Long id, boolean estado);
	
	public List<Categoria> consultaTodos(Long usuarioId, String likeText);
	
	public Categoria consultaUno(Long id);
	
}
