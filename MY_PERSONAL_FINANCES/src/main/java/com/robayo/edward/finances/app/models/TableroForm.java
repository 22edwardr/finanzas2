package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class TableroForm implements Serializable {

	private static final long serialVersionUID = 1L;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	@NotNull
	@Min(1)
	@Max(3)
	private Integer tipoConsulta;
	private Long idCategoria;
	private Long idFuente;
	private Double valorTotal;
	private List<FuenteResumen> fuenteResumen;
	private List<MovimientoResumen> movimientoResumen;
	// https://canvasjs.com/spring-mvc-charts/line-chart/
	private Map<String, Double> graficoAhorros;
	// https://canvasjs.com/spring-mvc-charts/animated-chart/
	private Map<String, Double> graficoCategorias;

	@AssertTrue
	private boolean isConsultaFecha() {
		if (tipoConsulta != 1) {
			return fecha != null;
		}

		return true;
	}

	public List<FuenteResumen> getFuenteResumen() {
		return fuenteResumen;
	}

	public void setFuenteResumen(List<FuenteResumen> fuenteResumen) {
		this.fuenteResumen = fuenteResumen;
	}

	public List<MovimientoResumen> getMovimientoResumen() {
		return movimientoResumen;
	}

	public void setMovimientoResumen(List<MovimientoResumen> movimientoResumen) {
		this.movimientoResumen = movimientoResumen;
	}

	public Long getIdFuente() {
		return idFuente;
	}

	public void setIdFuente(Long idFuente) {
		this.idFuente = idFuente;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(Integer tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Map<String, Double> getGraficoAhorros() {
		return graficoAhorros;
	}

	public void setGraficoAhorros(Map<String, Double> graficoAhorros) {
		this.graficoAhorros = graficoAhorros;
	}

	public Map<String, Double> getGraficoCategorias() {
		return graficoCategorias;
	}

	public void setGraficoCategorias(Map<String, Double> graficoCategorias) {
		this.graficoCategorias = graficoCategorias;
	}

}
