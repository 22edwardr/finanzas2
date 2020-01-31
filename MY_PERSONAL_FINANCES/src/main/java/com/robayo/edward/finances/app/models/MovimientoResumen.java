package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MovimientoResumen implements Serializable{

	private static final long serialVersionUID = 1L;
	private LocalDate fecha;
	private String categoria;
	private String fuente;
	private String descripcion;
	private Double valor;
	
	public MovimientoResumen() {}
	
	public MovimientoResumen(LocalDate fecha, String categoria, String fuente, String descripcion, Double valor) {
		this.fecha = fecha;
		this.categoria = categoria;
		this.fuente = fuente;
		this.descripcion = descripcion;
		this.valor = valor;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

}
