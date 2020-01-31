package com.robayo.edward.finances.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.FuenteResumen;
import com.robayo.edward.finances.app.models.MovimientoResumen;

@Repository
public class TableroDao implements ITableroDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<MovimientoResumen> consultaMovimientoResumen(LocalDate fechaInicial, LocalDate fechaFinal, Long idCategoria,
			Long idFuente, Long idUsuario) {
		return jdbcTemplate.query(
				"select m.fecha,c.nomeclatura categoria,c.nombre categoriaNom,f.nomeclatura fuente,f.nombre fuenteNom, m.descripcion,m.valor, case c.tipo when 'D' then 1 else 0 end esDebito from movimiento m join categoria c on m.categoria_id = c.id join fuente f on m.fuente_id = f.id " 
				+ "	where m.fecha >= ifnull(?,m.fecha) and m.fecha <= ifnull(?,m.fecha) " 
				+ "	and m.usuario_id = ? and m.categoria_id = ifnull(?,m.categoria_id) and m.fuente_id = ifnull(?,m.fuente_id) order by m.fecha",
				new Object[] { fechaInicial , fechaFinal, idUsuario, idCategoria,idFuente}, new RowMapper<MovimientoResumen>() {

					@Override
					public MovimientoResumen mapRow(ResultSet rs, int rowNum) throws SQLException {
						LocalDate fecha = rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null; 
						String categoria = rs.getString("categoriaNom");
						String fuente = rs.getString("fuenteNom");
						
						categoria = categoria != null ? rs.getString("categoria") + " - " + categoria : rs.getString("categoria");
						fuente = fuente != null ? rs.getString("fuente") + " - " + fuente : rs.getString("fuente");
						
						return new MovimientoResumen(fecha,categoria,fuente,rs.getString("descripcion"),rs.getDouble("valor"),rs.getBoolean("esDebito"));
					}

				});
	}

	@Override
	public List<FuenteResumen> consultaFuenteResumen(LocalDate fechaInicial, LocalDate fechaFinal, Long idCategoria,
			Long idFuente, Long idUsuario) {
		return jdbcTemplate.query(
				"select f.nomeclatura fuenteNom, f.nombre fuente, sum(case c.tipo when 'D' then m.valor else 0 end) ingresos, " 
				+ "	sum(case c.tipo when 'C' then m.valor else 0 end) egresos   " 
				+ "	from movimiento m join categoria c on m.categoria_id = c.id " 
				+ "	join fuente f on m.fuente_id = f.id " 
				+ "	where m.fecha >= ifnull(?,m.fecha) and m.fecha <= ifnull(?,m.fecha) " 
				+ "	and m.usuario_id = ? and m.categoria_id = ifnull(?,m.categoria_id) and m.fuente_id = ifnull(?,m.fuente_id) " 
				+ "	group by f.nomeclatura, f.nombre",
				new Object[] { fechaInicial , fechaFinal, idUsuario, idCategoria,idFuente}, new RowMapper<FuenteResumen>() {

					@Override
					public FuenteResumen mapRow(ResultSet rs, int rowNum) throws SQLException {
						String fuente = rs.getString("fuenteNom");
						
						fuente = fuente != null ? rs.getString("fuente") + " - " + fuente : rs.getString("fuente");
						
						return new FuenteResumen(fuente,rs.getDouble("egresos"),rs.getDouble("ingresos"));
					}

				});
	}

}
