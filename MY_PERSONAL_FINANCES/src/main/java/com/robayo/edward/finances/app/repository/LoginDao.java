package com.robayo.edward.finances.app.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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
	public String consultaEmailUsuarioTokenConfirmacion(String tokenConfirmacion) {
		List<String> listEmails;
		
		listEmails = jdbcTemplate.queryForList("select email from usuario where token_confirmacion = ? limit 1", new Object[] { tokenConfirmacion },
				String.class);
		
		return listEmails.isEmpty() ? null : listEmails.get(0);
		
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
	public void actualizarTokenConfirmacionUsuario(String correo, String tokenConfirmacion) {
		jdbcTemplate.update("update usuario set token_confirmacion = ? where email = ?", tokenConfirmacion, correo);
	}

	@Override
	public int eliminarUsuariosSinConfirmacion() {
		return jdbcTemplate.update("delete from usuario where token_confirmacion is not null  and DATE_ADD(fecha_creacion,interval 1 day) < NOW()");
	}

}
