package com.robayo.edward.finances.app.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.Usuario;

@Repository
public class LoginDao implements ILoginDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean existeUsuario(String email) {
		Integer count;

		count = jdbcTemplate.queryForObject("select count(*) from usuario where email = ?", new Object[] { email },
				Integer.class);

		return count != null && count > 0;
	}

	@Override
	public Long crearUsuario(Usuario usuario) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(
					"insert into usuario (email, nombre, password, enabled, pregunta1, pregunta2, respuesta1, respuesta2, foto) "
							+ "values(?,?,?,1,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
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
	public int crearRol(Long idUsuario, String rol) {
		return jdbcTemplate.update("insert into rol (usuario_id, rol) values(?,?)", idUsuario, rol);
	}

}
