package com.robayo.edward.finances.app.service;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginBusiness {
	public boolean existeUsuario(String email);

	public void crearUsuario(Usuario usuario,String rol);
}
