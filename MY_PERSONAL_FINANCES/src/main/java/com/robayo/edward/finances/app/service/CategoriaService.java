package com.robayo.edward.finances.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.models.Categoria;
import com.robayo.edward.finances.app.repository.ICategoriaDao;

@Service
public class CategoriaService implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional
	public void crear(Categoria categoria) {
		categoriaDao.crear(categoria);
	}

	@Override
	@Transactional
	public void actualizar(Categoria categoria) {
		categoriaDao.actualizar(categoria);

	}

	@Override
	@Transactional
	public void actualizarEstado(Categoria categoria) {
		categoriaDao.actualizarEstado(categoria);
	}

	@Override
	public List<Categoria> consultaTodos(Long usuarioId, String likeText) {
		return categoriaDao.consultaTodos(usuarioId, likeText);
	}

	@Override
	public Categoria consultaUno(Long id,Long usuarioId) {
		return categoriaDao.consultaUno(id,usuarioId);
	}

}
