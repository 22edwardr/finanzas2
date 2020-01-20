package com.robayo.edward.finances.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.Categoria;

@Repository
public class CategoriaDao implements ICategoriaDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void crearCategoria(Categoria categoria) {
		jdbcTemplate.update(
				"insert into categoria (usuario_id,nomeclatura,tipo,estado,nombre,descripcion,color)"
						+ "values (?,?,?,?,?,?,?)",
				new Object[] { categoria.getIdUsuario(), categoria.getNomeclatura(), categoria.getTipo(), true,
						categoria.getNombre(), categoria.getDescripcion(), categoria.getColor() });
	}

	@Override
	public void actualizarCategoria(Categoria categoria) {
		jdbcTemplate.update(
				"update categoria set nomeclatura = ? , tipo = ? ,nombre = ?,descripcion = ?, color = ?"
						+ " where id = ?",
				new Object[] { categoria.getNomeclatura(), categoria.getTipo(), categoria.getNombre(),
						categoria.getDescripcion(), categoria.getColor(), categoria.getId() });

	}

	@Override
	public void actualizarEstadoCategoria(Long id, boolean estado) {
		jdbcTemplate.update("update categoria set estado = ?" + " where id = ?", new Object[] { estado, id });

	}

	@Override
	public List<Categoria> consultaTodos(Long usuarioId, String likeText) {
		String finalLikeText;
		
		finalLikeText = "%" + likeText != null ? likeText : "" + "%";
		
		return jdbcTemplate.query(
				"select id, usuario_id, nomeclatura, tipo, estado, nombre,descripcion , color from categoria where usuario_id = ? and (nombre like ? or nomeclatura like ?)",
				new Object[] { usuarioId , finalLikeText, finalLikeText}, new RowMapper<Categoria>() {

					@Override
					public Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
						Categoria categoria;

						categoria = new Categoria();

						categoria.setId(rs.getLong("id"));
						categoria.setIdUsuario(rs.getLong("usuario_id"));
						categoria.setNomeclatura(rs.getString("nomeclatura"));
						categoria.setTipo(rs.getString("tipo"));
						categoria.setEstado(rs.getBoolean("estado"));
						categoria.setNombre(rs.getString("nombre"));
						categoria.setDescripcion(rs.getString("descripcion"));
						categoria.setColor(rs.getString("color"));

						return categoria;
					}

				});
	}
}
