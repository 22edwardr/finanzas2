package com.robayo.edward.finances.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.Fuente;

@Repository
public class FuenteDao implements IFuenteDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void crear(Fuente fuente) {
		jdbcTemplate.update("insert into fuente (usuario_id,nomeclatura,estado,nombre,color) values (?,?,?,?,?)",
				new Object[] { fuente.getIdUsuario(), fuente.getNomeclatura(), true, fuente.getNombre(),
						fuente.getColor() });
	}

	@Override
	public void actualizar(Fuente fuente) {
		jdbcTemplate.update("update fuente set nomeclatura = ? , nombre = ?, color = ? where id = ?",
				new Object[] { fuente.getNomeclatura(), fuente.getNombre(), fuente.getColor(), fuente.getId() });

	}

	@Override
	public void actualizarEstado(Fuente fuente) {
		jdbcTemplate.update("update fuente set estado = ? where id = ?",
				new Object[] { fuente.isEstado(), fuente.getId() });

	}

	@Override
	public List<Fuente> consultaTodos(Long usuarioId, String likeText) {
		String finalLikeText;

		finalLikeText = "%" + (likeText != null ? likeText : "") + "%";

		return jdbcTemplate.query(
				"select id, usuario_id, nomeclatura, estado, nombre , color from fuente where usuario_id = ? and nombre like ?",
				new Object[] { usuarioId, finalLikeText }, new RowMapper<Fuente>() {

					@Override
					public Fuente mapRow(ResultSet rs, int rowNum) throws SQLException {
						return getFuente(rs);
					}

				});
	}

	@Override
	public Fuente consultaUno(Long id, Long usuarioId) {
		return jdbcTemplate.query(
				"select id, usuario_id, nomeclatura, estado, nombre , color from fuente where id = ? and usuario_id = ?",
				new Object[] { id, usuarioId }, new ResultSetExtractor<Fuente>() {

					@Override
					public Fuente extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return getFuente(rs);
						}

						return null;

					}
				});

	}

	private Fuente getFuente(ResultSet rs) throws SQLException {
		Fuente fuente;

		fuente = new Fuente();

		fuente.setId(rs.getLong("id"));
		fuente.setIdUsuario(rs.getLong("usuario_id"));
		fuente.setNomeclatura(rs.getString("nomeclatura"));
		fuente.setEstado(rs.getBoolean("estado"));
		fuente.setNombre(rs.getString("nombre"));
		fuente.setColor(rs.getString("color"));

		return fuente;
	}
}
