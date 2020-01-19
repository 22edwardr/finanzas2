package com.robayo.edward.finances.app.repository;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginDao {
	public void actualizarTokenConfirmacionUsuario(String email, String tokenConfirmacion);
	
	public void actualizarPasswordUsuario(String email,String password);

	public String consultaEmailUsuarioTokenConfirmacion(String tokenConfirmacion);

	public Usuario consultaPreguntasEmailUsuario(String email);

	public boolean existeUsuario(String email);

	public boolean existeUsuarioPreguntas(Usuario usuario);
	
	public Long crearUsuario(Usuario usuario);

	public void crearRol(Long idUsuario, String rol);

	public int eliminarUsuariosSinConfirmacion();


}
