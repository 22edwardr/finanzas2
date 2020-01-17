package com.robayo.edward.finances.app.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.robayo.edward.finances.app.service.ILoginService;

@Component
public class TareaProgramada {
	@Autowired
	private ILoginService loginService;

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Scheduled(cron = "${application.scheduled.eliminacionCuentas.crono}")
	public void eliminacionCuentas() {
		try {
			logger.info(
					"Fueron eliminadas " + loginService.eliminarUsuariosSinConfirmacion() + " cuentas sin confirmar");
		} catch (Exception e) {
			logger.error("errorTareaEliminacionCuentas", e);
		}

	}
}
