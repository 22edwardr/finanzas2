package com.robayo.edward.finances.app.service;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginService {
	public void crearUsuario(Usuario usuario,String rol);
	
	public void envioCorreoConfirmacionUsuario(Usuario usuario);
	
	public String confirmacionCorreoConfirmacionUsuario(String tokenConfirmacion);
	
	public int eliminarUsuariosSinConfirmacion();
}
