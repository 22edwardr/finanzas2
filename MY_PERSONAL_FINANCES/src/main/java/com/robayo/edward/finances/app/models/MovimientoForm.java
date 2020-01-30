package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class MovimientoForm implements Serializable {

	private static final long serialVersionUID = 1L;
	@Valid
	@NotEmpty
	private List<Movimiento> movimientos;

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

}
