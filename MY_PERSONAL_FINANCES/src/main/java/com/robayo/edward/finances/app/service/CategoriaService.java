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
	public void crearCategoria(Categoria categoria) {
		categoriaDao.crearCategoria(categoria);
	}

	@Override
	@Transactional
	public void actualizarCategoria(Categoria categoria) {
		categoriaDao.actualizarCategoria(categoria);

	}

	@Override
	@Transactional
	public void actualizarEstadoCategoria(Long id, boolean estado) {
		categoriaDao.actualizarEstadoCategoria(id, estado);
	}

	@Override
	public List<Categoria> consultaTodos(Long usuarioId, String likeText) {
		return categoriaDao.consultaTodos(usuarioId, likeText);
	}

	@Override
	public Categoria consultaUno(Long id) {
		return categoriaDao.consultaUno(id);
	}

}
