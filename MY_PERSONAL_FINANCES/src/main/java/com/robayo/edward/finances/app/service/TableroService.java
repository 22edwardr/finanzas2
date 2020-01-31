package com.robayo.edward.finances.app.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.robayo.edward.finances.app.models.FuenteResumen;
import com.robayo.edward.finances.app.models.MovimientoResumen;
import com.robayo.edward.finances.app.models.TableroForm;
import com.robayo.edward.finances.app.repository.ITableroDao;

public class TableroService implements ITableroService {
	@Autowired
	private ITableroDao tableroDao;

	@Override
	public TableroForm consultaTablero(TableroForm filtro, Long idUsuario) {
		TableroForm resultado;
		LocalDate fechaInicial;
		LocalDate fechaFinal;
		List<MovimientoResumen> movimientoResumen;
		List<FuenteResumen> fuenteResumen;
		Double valorDebito;
		Double valorCredito;

		resultado = new TableroForm();
		valorDebito = (double) 0;
		valorCredito = (double) 0;

		switch (filtro.getTipoConsulta()) {
		case 2:
			fechaInicial = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR),
					filtro.getFecha().get(ChronoField.MONTH_OF_YEAR), 1);
			fechaFinal = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR),
					filtro.getFecha().get(ChronoField.MONTH_OF_YEAR), filtro.getFecha().lengthOfMonth());
			break;
		case 3:
			fechaInicial = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR), Month.JANUARY, 1);
			fechaFinal = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR), Month.DECEMBER, 31);
			break;
		default:
			fechaInicial = null;
			fechaFinal = null;
			break;
		}

		movimientoResumen = tableroDao.consultaMovimientoResumen(fechaInicial, fechaFinal, filtro.getIdCategoria(),
				filtro.getIdFuente(), idUsuario);
		fuenteResumen = tableroDao.consultaFuenteResumen(fechaInicial, fechaFinal, filtro.getIdCategoria(),
				filtro.getIdFuente(), idUsuario);

		if (movimientoResumen != null)
			for (FuenteResumen fuente : fuenteResumen) {
				valorCredito += fuente.getEgresos();
				valorDebito += fuente.getIngresos();
			}

		resultado.setFecha(filtro.getFecha());
		resultado.setTipoConsulta(filtro.getTipoConsulta());
		resultado.setIdCategoria(filtro.getIdCategoria());
		resultado.setIdFuente(filtro.getIdFuente());
		resultado.setFuenteResumen(fuenteResumen);
		resultado.setMovimientoResumen(movimientoResumen);
		resultado.setValorTotal(valorDebito - valorCredito);

		return resultado;
	}

}
