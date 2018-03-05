package org.spgroup.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class InputValidator {
	private static Logger logger = LoggerFactory
			.getLogger(InputValidator.class);
	private static final String PATTERN = "^[a-z0-9._-]+@[a-z0-9-]+(?:\\.[a-z0-9-]+)*$";

	public static void email(String... emails) throws ApiException {
		for (String email : emails) {
			if (StringUtils.isEmpty(email)) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "Email is empty");
			}

			if (email.length() > 50) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "Email length execeeded max length 50 characters.");
			}

			if (!email.matches(PATTERN)) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "Incorrect email");
			}
		}
	}
	
	public static void friends(List<String> friends) throws ApiException {
		if(friends == null || friends.size()!=2) {
			logger.error("Bad request with incorrect Input of Friend array.");
			throw new ApiException(HttpStatus.BAD_REQUEST, "Incorrect input size");
		}
		email(friends.toArray(new String[0]));
	}
	
}
