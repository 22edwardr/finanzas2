package com.robayo.edward.finances.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.models.Fuente;
import com.robayo.edward.finances.app.repository.IFuenteDao;

@Service
public class FuenteService implements IFuenteService {

	@Autowired
	private IFuenteDao fuenteDao;

	@Override
	@Transactional
	public void crear(Fuente fuente) {
		fuenteDao.crear(fuente);
	}

	@Override
	@Transactional
	public void actualizar(Fuente fuente) {
		fuenteDao.actualizar(fuente);

	}

	@Override
	@Transactional
	public void actualizarEstado(Fuente fuente) {
		fuenteDao.actualizarEstado(fuente);
	}

	@Override
	public List<Fuente> consultaTodos(Long usuarioId, String likeText) {
		return fuenteDao.consultaTodos(usuarioId, likeText);
	}
	
	@Override
	public List<Fuente> consultaTodosActivos(Long usuarioId) {
		List<Fuente> fuentes;
		
		fuentes = fuenteDao.consultaTodos(usuarioId, null);
		
		if(fuentes != null)
			fuentes = fuentes.stream().filter(element -> element.isEstado()).collect(Collectors.toList());
		
		return fuentes;
	}

	@Override
	public Fuente consultaUno(Long id, Long usuarioId) {
		return fuenteDao.consultaUno(id, usuarioId);
	}

}
