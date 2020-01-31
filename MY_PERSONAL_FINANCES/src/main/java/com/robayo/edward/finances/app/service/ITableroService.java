package com.robayo.edward.finances.app.service;

import com.robayo.edward.finances.app.models.TableroForm;

public interface ITableroService {
	public TableroForm consultaTablero(TableroForm filtro,Long idUsuario);
}
