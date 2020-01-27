package com.robayo.edward.finances.app.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MovimientoForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Movimiento> movimientos;

	public ArrayList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(ArrayList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

}
