package com.robayo.edward.finances.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.models.Meta;
import com.robayo.edward.finances.app.repository.IMetaDao;

@Service
public class MetaService implements IMetaService {

	@Autowired
	private IMetaDao metaDao;

	@Override
	@Transactional
	public void crear(Meta categoria) {
		metaDao.crear(categoria);
	}

	@Override
	@Transactional
	public void actualizar(Meta categoria) {
		metaDao.actualizar(categoria);

	}

	@Override
	public List<Meta> consultaTodos(Long usuarioId, String likeText) {
		List<Meta> lista;
		
		lista = metaDao.consultaTodos(usuarioId, likeText);
		lista.forEach(c -> {
			c.setBalance(metaDao.consultaBalance(c.getId()));
		});
		
		return lista;
	}
	
	@Override
	public Meta consultaUno(Long id,Long usuarioId) {
		return metaDao.consultaUno(id,usuarioId);
	}

	@Override
	@Transactional
	public void eliminar(Meta meta) {
		metaDao.eliminar(meta);
	}

}
