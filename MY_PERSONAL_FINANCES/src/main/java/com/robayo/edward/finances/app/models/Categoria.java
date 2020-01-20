package com.robayo.edward.finances.app.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Categoria {
	private Long id;
	private Long idUsuario;
	@Size(max = 8)
	private String color;
	private String descripcion;
	private String nombre;
	@NotNull
	@Size(min = 3, max = 5)
	private String nomeclatura;
	@NotNull
	@Pattern(regexp = "[CD]")
	private String tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
