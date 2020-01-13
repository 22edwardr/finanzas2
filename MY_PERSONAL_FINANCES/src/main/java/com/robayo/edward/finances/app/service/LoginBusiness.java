package com.robayo.edward.finances.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.models.Usuario;
import com.robayo.edward.finances.app.repository.ILoginDao;

@Service
public class LoginBusiness implements ILoginBusiness {
	@Autowired
	private ILoginDao loginDao;

	@Override
	@Transactional(readOnly = true)
	public boolean existeUsuario(String email) {
		return loginDao.existeUsuario(email);
	}

	@Override
	@Transactional
	public void crearUsuario(Usuario usuario, String rol) {
		Long idUsuario;
		
		idUsuario = loginDao.crearUsuario(usuario);
		
		if (idUsuario > 0) {
			if (Usuario.ROL_ADMIN.equals(rol))
				loginDao.crearRol(idUsuario, Usuario.ROL_ADMIN);
			loginDao.crearRol(idUsuario, Usuario.ROL_USUARIO);
			
			
		}
	}

}
