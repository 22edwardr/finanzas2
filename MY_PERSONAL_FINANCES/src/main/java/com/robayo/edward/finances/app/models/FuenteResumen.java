package com.robayo.edward.finances.app.models;

import java.io.Serializable;

public class FuenteResumen implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private Double egresos;
	private Double ingresos;
	
	public FuenteResumen() {}
	
	public FuenteResumen(String nombre,Double egresos,Double ingresos) {
		this.nombre = nombre;
		this.egresos = egresos;
		this.ingresos = ingresos;
	}
	
	public Double getBalance() {
		return (ingresos== null ? 0 : ingresos) - (egresos== null ? 0 : egresos);
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getEgresos() {
		return egresos;
	}
	public void setEgresos(Double egresos) {
		this.egresos = egresos;
	}
	public Double getIngresos() {
		return ingresos;
	}
	public void setIngresos(Double ingresos) {
		this.ingresos = ingresos;
	}

}
