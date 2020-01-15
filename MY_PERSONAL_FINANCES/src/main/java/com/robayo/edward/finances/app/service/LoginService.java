package com.robayo.edward.finances.app.service;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robayo.edward.finances.app.exception.handler.ServiceException;
import com.robayo.edward.finances.app.models.Mail;
import com.robayo.edward.finances.app.models.Usuario;
import com.robayo.edward.finances.app.repository.ILoginDao;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private ILoginDao loginDao;
	@Autowired
	private ICorreoService correoService;
	@Autowired
	private IUtilsService utilsService;
	@Autowired
	private Environment env;

	@Override
	@Transactional
	public void crearUsuario(Usuario usuario, String rol) {
		if (loginDao.existeUsuario(usuario.getEmail()))
			throw new ServiceException("text.register.validation.usuarioExistente",
					new Object[] { usuario.getEmail() });

		Long idUsuario;

		idUsuario = loginDao.crearUsuario(usuario);

		if (idUsuario > 0) {
			if (Usuario.ROL_ADMIN.equals(rol))
				loginDao.crearRol(idUsuario, Usuario.ROL_ADMIN);
			loginDao.crearRol(idUsuario, Usuario.ROL_USUARIO);

		}

	}

	@Override
	public void envioCorreoConfirmacionUsuario(Usuario usuario) {
		String tokenConfirmacion;
		Mail mail;

		tokenConfirmacion = utilsService.randomString(10);
		mail = new Mail();
		mail.setMailFrom("erobayo@cromasoft.com");
		mail.setMailTo(usuario.getEmail());
		mail.setMailSubject("MyPersonalFinances - Confirmacion de Usuario");
		mail.setMailContent("Saludos\n\n"
				+ "Un nuevo usuario ha sido creado en nuestro sistema con tu cuenta de correo, por favor confirma que eres tu siguiendo el siguiente link:\n"
				+ env.getProperty("application.host") + "/confirmacionCorreo?token=" + tokenConfirmacion
				+ " , si no recibimos una confirmacion por parte tuya, la cuenta creada a tu nombre sera eliminada en un plazo minimo de 24 horas");

		correoService.enviar(mail);

		loginDao.actualizarTokenConfirmacionUsuario(usuario.getEmail(), tokenConfirmacion);
	}

	@Override
	public String confirmacionCorreoConfirmacionUsuario(String tokenConfirmacion) {
		if (tokenConfirmacion != null) {
			String email;
			email = loginDao.consultaEmailUsuarioTokenConfirmacion(tokenConfirmacion);

			if (email != null && email.trim().length() > 0) {
				loginDao.actualizarTokenConfirmacionUsuario(email, tokenConfirmacion);

				return email;
			}
		}

		throw new ServiceException("text.exception.tokenInvalido");
	}

}
