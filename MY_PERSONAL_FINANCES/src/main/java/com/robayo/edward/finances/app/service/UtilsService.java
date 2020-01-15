package com.robayo.edward.finances.app.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class UtilsService implements IUtilsService {

	@Override
	public String randomString(int limit) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(limit).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}

}
