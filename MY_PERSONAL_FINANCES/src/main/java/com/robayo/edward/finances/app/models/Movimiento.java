package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idUsuario;
	@NotNull
	private Long idFuente;
	@NotNull
	private Long idCategoria;
	@NotNull
	private Double valor;
	private String descripcion;
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long usuarioId) {
		this.idUsuario = usuarioId;
	}

	public Long getIdFuente() {
		return idFuente;
	}

	public void setIdFuente(Long idFuente) {
		this.idFuente = idFuente;
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
