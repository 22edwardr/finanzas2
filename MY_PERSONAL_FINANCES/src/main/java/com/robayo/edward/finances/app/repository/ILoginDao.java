package com.robayo.edward.finances.app.repository;

import com.robayo.edward.finances.app.models.Usuario;

public interface ILoginDao {
	public boolean existeUsuario(String email);
	
	public Long crearUsuario(Usuario usuario);
	
	public void crearRol(Long idUsuario,String rol);
	
	public void actualizarTokenConfirmacionUsuario(String email,String tokenConfirmacion);
	
	public String consultaEmailUsuarioTokenConfirmacion(String tokenConfirmacion);
	
	public int eliminarUsuariosSinConfirmacion();
}
