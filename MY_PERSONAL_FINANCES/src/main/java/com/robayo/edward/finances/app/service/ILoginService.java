package com.robayo.edward.finances.app.service;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginService {
	public String confirmacionCorreoConfirmacionUsuario(String tokenConfirmacion);
	
	public boolean confirmacionPreguntasUsuario(Usuario usuario);

	public Usuario consultaPreguntasEmailUsuario(String email);

	public void crearUsuario(Usuario usuario, String rol);

	public void envioCorreoConfirmacionUsuario(Usuario usuario);

	public int eliminarUsuariosSinConfirmacion();

	public String generarContrasenaUsuario(String email, boolean enviarCorreo);

}
