package com.robayo.edward.finances.app.models;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ROL_USUARIO = "USUARIO";
	public static final String ROL_ADMIN= "ADMINISTRADOR";
	private Long id;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String nombre;
	@NotEmpty
	@Size(min = 8, max = 20)
	private String password;
	private String confirmPassword;
	private boolean enabled;
	private String pregunta1;
	private String pregunta2;
	private String respuesta1;
	private String respuesta2;
	private String foto;
	
	@Override
	public String toString() {
		return "Usuario[email:" + email + ",nombre:" +nombre + "]";
	}

	@AssertTrue
	public boolean isCorrectPassword() {
		String password = this.password != null ? this.password : "";
		String confirmPassword = this.confirmPassword != null ? this.confirmPassword : "";

		return password.equals(confirmPassword);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPregunta1() {
		return pregunta1;
	}

	public void setPregunta1(String pregunta1) {
		this.pregunta1 = pregunta1;
	}

	public String getPregunta2() {
		return pregunta2;
	}

	public void setPregunta2(String pregunta2) {
		this.pregunta2 = pregunta2;
	}

	public String getRespuesta1() {
		return respuesta1;
	}

	public void setRespuesta1(String respuesta1) {
		this.respuesta1 = respuesta1;
	}

	public String getRespuesta2() {
		return respuesta2;
	}

	public void setRespuesta2(String respuesta2) {
		this.respuesta2 = respuesta2;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
