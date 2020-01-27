package com.robayo.edward.finances.app.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.Movimiento;

@Repository
public class MovimientoDao implements IMovimientoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void crear(Movimiento movimiento) {
		jdbcTemplate.update(
				"insert into movimiento (usuario_id,fuente_id,categoria_id,valor,descripcion,fecha)"
						+ "values (?,?,?,?,?,?)",
				new Object[] { movimiento.getIdUsuario(), movimiento.getIdFuente(), movimiento.getIdCategoria(),
						movimiento.getValor(), movimiento.getDescripcion(), movimiento.getFecha() });
	}

}
