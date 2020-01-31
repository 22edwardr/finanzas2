package com.robayo.edward.finances.app.repository;

import java.time.LocalDate;
import java.util.List;

import com.robayo.edward.finances.app.models.FuenteResumen;
import com.robayo.edward.finances.app.models.MovimientoResumen;

public interface ITableroDao {
	
	public List<MovimientoResumen> consultaMovimientoResumen(LocalDate fechaInicial,LocalDate fechaFinal,Long idCategoria,Long idFuente, Long idUsuario);
	public List<FuenteResumen> consultaFuenteResumen(LocalDate fechaInicial,LocalDate fechaFinal,Long idCategoria,Long idFuente, Long idUsuario);

}
