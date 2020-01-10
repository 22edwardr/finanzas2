package com.robayo.edward.finances.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping(value = {"/"})
	public String index() {
		return "index";
	}

}
