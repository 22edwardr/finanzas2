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

import com.robayo.edward.finances.app.models.Meta;

@Repository
public class MetaDao implements IMetaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void crear(Meta meta) {
		jdbcTemplate.update(
				"insert into meta (usuario_id,nombre,tipo,valor,fecha_inicial,fecha_final,descripcion) values (?,?,?,?,?,?,?)",
				new Object[] { meta.getIdUsuario(), meta.getNombre(), meta.getTipo(), meta.getValor(),
						meta.getFechaInicial(), meta.getFechaFinal(), meta.getDescripcion() });
	}

	@Override
	public void actualizar(Meta meta) {
		jdbcTemplate.update(
				"update meta set nombre = ?, tipo = ?, valor = ?, fecha_inicial = ?, fecha_final = ?, descripcion = ? where id = ?",
				new Object[] { meta.getNombre(), meta.getTipo(), meta.getValor(), meta.getFechaInicial(),
						meta.getFechaFinal(), meta.getDescripcion(), meta.getId() });

	}

	@Override
	public List<Meta> consultaTodos(Long usuarioId, String likeText) {
		String finalLikeText;

		finalLikeText = "%" + (likeText != null ? likeText : "") + "%";

		return jdbcTemplate.query(
				"select mt.id,mt.usuario_id,mt.nombre,mt.tipo,mt.valor,mt.fecha_inicial,mt.fecha_final,mt.descripcion from meta mt where mt.usuario_id = ? and mt.nombre like ? order by mt.fecha_final desc",
				new Object[] { usuarioId, finalLikeText }, new RowMapper<Meta>() {

					@Override
					public Meta mapRow(ResultSet rs, int rowNum) throws SQLException {
						return getMeta(rs);
					}

				});
	}

	@Override
	public Meta consultaUno(Long id, Long usuarioId) {
		return jdbcTemplate.query(
				"select mt.id,mt.usuario_id,mt.nombre,mt.tipo,mt.valor,mt.fecha_inicial,mt.fecha_final,mt.descripcion from meta mt where mt.id = ? and mt.usuario_id = ?",
				new Object[] { id, usuarioId }, new ResultSetExtractor<Meta>() {

					@Override
					public Meta extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return getMeta(rs);
						}

						return null;

					}
				});

	}

	private Meta getMeta(ResultSet rs) throws SQLException {
		Meta meta;

		meta = new Meta();

		meta.setId(rs.getLong("id"));
		meta.setIdUsuario(rs.getLong("usuario_id"));
		meta.setNombre(rs.getString("nombre"));
		meta.setTipo(rs.getString("tipo"));
		meta.setValor(rs.getDouble("valor"));
		meta.setFechaInicial(rs.getDate("fecha_inicial") != null ? rs.getDate("fecha_inicial").toLocalDate() : null);
		meta.setFechaFinal(rs.getDate("fecha_final") != null ? rs.getDate("fecha_final").toLocalDate() : null);
		meta.setDescripcion(rs.getString("descripcion"));

		return meta;
	}

	@Override
	public void eliminar(Meta meta) {
		jdbcTemplate.update("delete from meta where id = ?", new Object[] { meta.getId() });
	}

	@Override
	public Double consultaBalance(Long id) {
		return jdbcTemplate.queryForObject(
				"select case mt.tipo when 'A' then ifnull(sum(case c.tipo when 'D' then m.valor else m.valor * -1 end),0) "
						+ " else ifnull(sum(case c.tipo when 'C' then m.valor else 0 end),0) end balance"
						+ " from meta mt join movimiento m on m.fecha >= mt.fecha_inicial and m.fecha <= mt.fecha_final and m.usuario_id = mt.usuario_id "
						+ " join categoria c on m.categoria_id = c.id where mt.id = ?",
				new Object[] { id }, Double.class);
	}
}
