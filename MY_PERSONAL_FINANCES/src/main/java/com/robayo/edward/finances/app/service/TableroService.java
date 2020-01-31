package com.robayo.edward.finances.app.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robayo.edward.finances.app.models.FuenteResumen;
import com.robayo.edward.finances.app.models.MovimientoResumen;
import com.robayo.edward.finances.app.models.TableroForm;
import com.robayo.edward.finances.app.repository.ITableroDao;

@Service
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

		valorDebito = (double) 0;
		valorCredito = (double) 0;

		switch (filtro.getTipoConsulta()) {
		case 2:
			fechaInicial = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR), filtro.getFecha().getMonth(), 1);
			fechaFinal = LocalDate.of(filtro.getFecha().get(ChronoField.YEAR), filtro.getFecha().getMonth(),
					filtro.getFecha().lengthOfMonth());
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

		if (!movimientoResumen.isEmpty()) {

			fuenteResumen = tableroDao.consultaFuenteResumen(fechaInicial, fechaFinal, filtro.getIdCategoria(),
					filtro.getIdFuente(), idUsuario);

			for (FuenteResumen fuente : fuenteResumen) {
				valorCredito += fuente.getEgresos();
				valorDebito += fuente.getIngresos();
			}

			resultado = new TableroForm();

			resultado.setFecha(filtro.getFecha());
			resultado.setTipoConsulta(filtro.getTipoConsulta());
			resultado.setIdCategoria(filtro.getIdCategoria());
			resultado.setIdFuente(filtro.getIdFuente());
			resultado.setFuenteResumen(fuenteResumen);
			resultado.setMovimientoResumen(movimientoResumen);
			resultado.setValorTotal(valorDebito - valorCredito);
			resultado.setGraficoAhorros(getGraficoAhorros(movimientoResumen, filtro.getTipoConsulta()));
			resultado.setGraficoCategorias(getGraficoCategorias(movimientoResumen));

			return resultado;
		} else {

			return null;
		}

	}

	private List<List<Map<Object, Object>>> getGraficoCategorias(List<MovimientoResumen> movimientoResumen) {
		List<List<Map<Object, Object>>> list;
		List<Map<Object, Object>> data;

		list = new ArrayList<>();
		data = new ArrayList<>();

		if (movimientoResumen != null) {

			Map<String, Double> categorias;
			categorias = new HashMap<>();

			movimientoResumen.forEach(c -> {
				if (!c.getDebito()) {
					Double valor;

					valor = categorias.putIfAbsent(c.getCategoria(), c.getValor());

					if (valor != null) {
						valor = valor + c.getValor();
						categorias.put(c.getCategoria(), valor);
					}
				}

			});

			for (Map.Entry<String, Double> categoria : categorias.entrySet()) {
				Map<Object, Object> punto;
				punto = new HashMap<>();

				punto.put("label", categoria.getKey());
				punto.put("y", categoria.getValue());

				data.add(punto);
			}
		}

		if (!data.isEmpty())
			list.add(data);
		else
			list = null;

		return list;

	}

	private List<List<Map<Object, Object>>> getGraficoAhorros(List<MovimientoResumen> movimientoResumen,
			Integer tipoConsulta) {
		List<List<Map<Object, Object>>> list;
		List<Map<Object, Object>> data;

		list = new ArrayList<>();
		data = new ArrayList<>();

		if (movimientoResumen != null) {

			Map<LocalDate, Double> fechas;
			fechas = new HashMap<>();

			movimientoResumen.forEach(c -> {
				LocalDate fecha;
				Double valorMovimiento;
				Double valor;

				if (tipoConsulta == 2) {
					fecha = c.getFecha();
				} else {
					fecha = LocalDate.of(c.getFecha().get(ChronoField.YEAR), c.getFecha().getMonth(), 1);
				}
				
				valorMovimiento = c.getValor() * (c.getDebito() ? 1 : -1);
				valor = fechas.putIfAbsent(fecha, valorMovimiento);

				if (valor != null) {
					valor = valor + valorMovimiento;
					fechas.put(fecha, valor);
				}
				
				System.out.println(fechas);

			});

			for (Map.Entry<LocalDate, Double> fecha : fechas.entrySet()) {
				Map<Object, Object> punto;
				ZonedDateTime zdt = ZonedDateTime.of(fecha.getKey(), LocalTime.of(0, 0), ZoneId.systemDefault());
				punto = new HashMap<>();

				punto.put("x", zdt.toInstant().toEpochMilli());
				punto.put("y", fecha.getValue() / 1000);

				data.add(punto);
			}
		}

		if (!data.isEmpty())
			list.add(data);
		else
			list = null;

		return list;
	}

}
