package com.robayo.edward.finances.app.repository;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginDao {
	public boolean existeUsuario(String email);

	public Long crearUsuario(Usuario usuario);
	
	public int crearRol(Long idUsuario,String rol);
}
