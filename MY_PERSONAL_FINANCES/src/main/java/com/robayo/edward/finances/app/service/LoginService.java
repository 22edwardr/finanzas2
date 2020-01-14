package com.robayo.edward.finances.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.exception.handler.ServiceException;
import com.robayo.edward.finances.app.models.Usuario;
import com.robayo.edward.finances.app.repository.ILoginDao;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private ILoginDao loginDao;

	@Override
	@Transactional
	public void crearUsuario(Usuario usuario, String rol) {
		if (loginDao.existeUsuario(usuario.getEmail()))
			throw new ServiceException("text.register.validation.usuarioExistente",new Object[] {usuario.getEmail()});
		
		Long idUsuario;
		
		idUsuario = loginDao.crearUsuario(usuario);
		
		
		if (idUsuario > 0) {
			if (Usuario.ROL_ADMIN.equals(rol))
				loginDao.crearRol(idUsuario, Usuario.ROL_ADMIN);
			loginDao.crearRol(idUsuario, Usuario.ROL_USUARIO);
			
			
		}
		
	}

}
