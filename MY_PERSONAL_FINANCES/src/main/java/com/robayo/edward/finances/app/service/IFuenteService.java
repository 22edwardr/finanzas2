package com.robayo.edward.finances.app.service;

import java.util.List;

import com.robayo.edward.finances.app.models.Fuente;

public interface IFuenteService {
	public void crear(Fuente fuente);

	public void actualizar(Fuente fuente);

	public void actualizarEstado(Fuente fuente);

	public List<Fuente> consultaTodos(Long usuarioId, String likeText);

	public Fuente consultaUno(Long id, Long usuarioId);
}
