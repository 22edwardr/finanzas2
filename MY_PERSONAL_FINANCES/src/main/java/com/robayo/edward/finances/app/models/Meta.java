package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class Meta implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long idUsuario;
	@NotEmpty
	private String nombre;
	private String descripcion;
	@NotEmpty
	@Pattern(regexp = "[GA]")
	private String tipo;
	@NotNull
	private Double valor;
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaInicial;
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaFinal;
	private Double balance;

	@AssertTrue
	public boolean isFinalAfterInicial() {
		if (fechaInicial != null && fechaFinal != null) {
			return fechaInicial.isBefore(fechaFinal);
		}
		return false;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(LocalDate fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getBalanceActual() {
		Double valorActual;

		valorActual = valor * getPorcentaje() / 100.0;

		if ("A".equals(tipo))
			return balance - valorActual;
		else
			return valorActual - balance;
	}

	public Double getPorcentaje() {
		LocalDate actual;

		actual = LocalDate.now();

		if (actual.isAfter(fechaFinal))
			return 100.0;
		else if ((actual.isEqual(fechaInicial) || actual.isAfter(fechaInicial))
				&& (actual.isEqual(fechaFinal) || actual.isBefore(fechaFinal))) {
			long diasMeta;
			long diasTranscurridos;

			diasMeta = ChronoUnit.DAYS.between(fechaInicial, fechaFinal);
			diasTranscurridos = ChronoUnit.DAYS.between(fechaInicial, actual);

			return diasTranscurridos * 100.0 / diasMeta;
		}

		return 0.0;

	}

	public String getEstado() {
		if (getPorcentaje() == 100.0)
			if (getBalanceActual() >= 0)
				return "E";
			else
				return "F";
		else if (getBalanceActual() >= 0)
			return "C";
		else
			return "N";

	}

}
