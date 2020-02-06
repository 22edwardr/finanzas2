package com.robayo.edward.finances.app.service;

import java.util.List;

import com.robayo.edward.finances.app.models.Meta;

public interface IMetaService {
	public void crear(Meta meta);

	public void actualizar(Meta meta);
	
	public void eliminar(Meta meta);

	public List<Meta> consultaTodos(Long usuarioId, String likeText);
	
	public Meta consultaUno(Long id,Long usuarioId);
}
