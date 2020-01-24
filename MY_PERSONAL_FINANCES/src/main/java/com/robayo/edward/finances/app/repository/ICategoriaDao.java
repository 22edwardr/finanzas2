package com.robayo.edward.finances.app.repository;

import java.util.List;

import com.robayo.edward.finances.app.models.Categoria;

public interface ICategoriaDao {

	public void crear(Categoria categoria);
	
	public void actualizar(Categoria categoria);
	
	public void actualizarEstado(Categoria categoria);
	
	public List<Categoria> consultaTodos(Long usuarioId, String likeText);
	
	public Categoria consultaUno(Long id,Long usuarioId);
	
}
