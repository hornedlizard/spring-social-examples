/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.cafe24.config.support;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24OAuth2Template;
import org.springframework.web.util.CookieGenerator;

import java.util.Arrays;

/**
 * Utility class for managing the quick_start user cookie that remembers the signed-in user.
 * @author Keith Donald
 */
final class UserCookieGenerator {

	private static final Logger logger = LoggerFactory.getLogger(UserCookieGenerator.class);
	private final CookieGenerator userCookieGenerator = new CookieGenerator();


	public UserCookieGenerator() {
		userCookieGenerator.setCookieName(Cafe24OAuth2Template.getMallId());
		logger.info("UserCookieGenerator userCookieGenerator.getCookieName(): " + userCookieGenerator.getCookieName());
	}

	public void addCookie(String userId, HttpServletResponse response) {
		logger.info("addCookie called...");
		logger.info("addCookie userId: " + userId);

		userCookieGenerator.addCookie(response, userId);
	}
	
	public void removeCookie(HttpServletResponse response) {
		userCookieGenerator.addCookie(response, userCookieGenerator.getCookieName());
	}
	
	public String readCookieValue(HttpServletRequest request) {
		logger.info("readCookieValue called...");

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			logger.info("readCookieValue cookies is null...");

			return null;
		}
		for (Cookie cookie : cookies) {
			logger.info("readCookieValue cookies cookie.getName: " + cookie.getName());
			logger.info("readCookieValue cookies cookie.getValue: " + cookie.getValue());
			logger.info("readCookieValue cookies cookie.getPath: " + cookie.getPath());
			logger.info("readCookieValue cookies cookie.getComment: " + cookie.getComment());
			logger.info("readCookieValue cookies cookie.getDomain: " + cookie.getDomain());
			logger.info("readCookieValue cookies cookie.getVersion: " + cookie.getVersion());
			logger.info("readCookieValue cookies cookie.getSecure: " + cookie.getSecure());
			logger.info(" ");

			if (cookie.getName().equals(userCookieGenerator.getCookieName())) {
				String cookieValue = cookie.getValue();
				logger.info("readCookieValue cookie.getName().equals(userCookieGenerator.getCookieName()) cookieValue: " + cookieValue);

				return cookieValue;
			}
		}
		logger.info("readCookieValue return null");

		return null;
	}

}
