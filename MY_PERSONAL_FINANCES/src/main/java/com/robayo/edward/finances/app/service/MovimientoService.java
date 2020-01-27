package com.robayo.edward.finances.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robayo.edward.finances.app.models.Movimiento;
import com.robayo.edward.finances.app.repository.IMovimientoDao;

@Service
public class MovimientoService implements IMovimientoService {
	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Override
	public void crear(Movimiento movimiento) {
		movimientoDao.crear(movimiento);
	}

}
