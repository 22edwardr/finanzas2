package com.robayo.edward.finances.app.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Fuente {
	private Long id;
	private Long idUsuario;
	@Size(max = 7)
	private String color;
	private String nombre;
	@NotEmpty
	@Size(min = 3, max = 5)
	private String nomeclatura;
	private boolean estado;

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeclatura() {
		return nomeclatura;
	}

	public void setNomeclatura(String nomeclatura) {
		this.nomeclatura = nomeclatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
