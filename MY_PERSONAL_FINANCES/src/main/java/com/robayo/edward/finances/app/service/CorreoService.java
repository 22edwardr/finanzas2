package com.robayo.edward.finances.app.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.robayo.edward.finances.app.models.Mail;

@Service
public class CorreoService implements ICorreoService {
	@Autowired
	private JavaMailSender mailSender;

	public void enviar(Mail mail) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "MyPersonalFinances"));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());
			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException("errorEnvioCorreo", e);
		}

	}
}
