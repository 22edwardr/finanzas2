package com.robayo.edward.finances.app.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.robayo.edward.finances.app.models.CustomUser;

@Repository
public class CustomUserDetailsDao implements ICustomUserDetailsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public CustomUser loadUserByUsername(String username) {
		return jdbcTemplate.query(
				"select email username,password,enabled,nombre,id from usuario where email = ? and token_confirmacion is null",
				new Object[] { username }, new ResultSetExtractor<CustomUser>() {

					@Override
					public CustomUser extractData(ResultSet rs) throws SQLException, DataAccessException {
						CustomUser customUser = null;
						if (rs.next()) {
							customUser = new CustomUser(rs.getString("nombre"), rs.getLong("id"), null,
									rs.getString("password"), rs.getString("username"), true, true, true,
									rs.getBoolean("enabled"));

						}

						return customUser;

					}
				});
	}

	@Override
	public List<String> loadRolesByUsername(String username) {
		return jdbcTemplate.queryForList(
				"select r.rol from usuario u inner join rol r on r.usuario_id = u.id where u.email = ? ", String.class,
				username);
	}

}
