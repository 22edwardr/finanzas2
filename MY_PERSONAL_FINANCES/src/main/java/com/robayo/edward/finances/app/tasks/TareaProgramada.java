package com.robayo.edward.finances.app.tasks;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareaProgramada {
	
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Scheduled(cron="${application.scheduled.eliminacionCuentas.crono}")
	public void eliminacionCuentas() {
		logger.info(LocalDateTime.now());
	}
}
