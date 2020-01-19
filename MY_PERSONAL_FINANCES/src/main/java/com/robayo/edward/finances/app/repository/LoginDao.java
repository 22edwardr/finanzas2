package com.robayo.edward.finances.app.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.Usuario;

@Repository
public class LoginDao implements ILoginDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void actualizarTokenConfirmacionUsuario(String correo, String tokenConfirmacion) {
		jdbcTemplate.update("update usuario set token_confirmacion = ? where email = ?", tokenConfirmacion, correo);
	}

	@Override
	public void actualizarPasswordUsuario(String email, String password) {
		jdbcTemplate.update("update usuario set password = ? where email = ?", password, email);

	}

	@Override
	public String consultaEmailUsuarioTokenConfirmacion(String tokenConfirmacion) {
		List<String> listEmails;

		listEmails = jdbcTemplate.queryForList("select email from usuario where token_confirmacion = ? limit 1",
				new Object[] { tokenConfirmacion }, String.class);

		return listEmails.isEmpty() ? null : listEmails.get(0);

	}

	@Override
	public Usuario consultaPreguntasEmailUsuario(String email) {
		return jdbcTemplate.query("select pregunta1, pregunta2 from usuario where email = ?", new Object[] { email },
				new ResultSetExtractor<Usuario>() {

					@Override
					public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {
						Usuario usuario = null;
						if (rs.next()) {
							usuario = new Usuario();

							usuario.setPregunta1(rs.getString("pregunta1"));
							usuario.setPregunta2(rs.getString("pregunta2"));
						}

						return usuario;

					}
				});

	}

	@Override
	public boolean existeUsuario(String email) {
		Integer count;

		count = jdbcTemplate.queryForObject("select count(*) from usuario where email = ?", new Object[] { email },
				Integer.class);

		return count != null && count > 0;
	}

	@Override
	public boolean existeUsuarioPreguntas(Usuario usuario) {
		Integer count;

		count = jdbcTemplate.queryForObject(
				"select count(*) from usuario where email = ? and pregunta1 = ? and pregunta2 = ?"
						+ " and respuesta1 = ? and respuesta2= ?",
				new Object[] { usuario.getEmail(), usuario.getPregunta1(), usuario.getPregunta2(),
						usuario.getRespuesta1(), usuario.getRespuesta2() },
				Integer.class);

		return count != null && count > 0;
	}

	@Override
	public Long crearUsuario(Usuario usuario) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"insert into usuario (email, nombre, password, enabled, pregunta1, pregunta2, respuesta1, respuesta2, foto, fecha_creacion) "
							+ "values(?,?,?,1,?,?,?,?,?,NOW())",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getNombre());
			ps.setString(3, usuario.getPassword());
			ps.setString(4, usuario.getPregunta1());
			ps.setString(5, usuario.getPregunta2());
			ps.setString(6, usuario.getRespuesta1());
			ps.setString(7, usuario.getRespuesta2());
			ps.setString(8, usuario.getFoto());
			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();

	}

	@Override
	public void crearRol(Long idUsuario, String rol) {
		jdbcTemplate.update("insert into rol (usuario_id, rol) values(?,?)", idUsuario, rol);
	}

	@Override
	public int eliminarUsuariosSinConfirmacion() {
		return jdbcTemplate.update(
				"delete from usuario where token_confirmacion is not null  and DATE_ADD(fecha_creacion,interval 1 day) < NOW()");
	}

}
