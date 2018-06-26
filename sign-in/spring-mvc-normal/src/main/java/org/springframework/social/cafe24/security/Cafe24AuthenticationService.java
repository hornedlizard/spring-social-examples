package org.springframework.social.cafe24.security;

import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class Cafe24AuthenticationService extends OAuth2AuthenticationService<Cafe24> {

	
	public Cafe24AuthenticationService(String appId, String appSecret, String redirectUri) {
		super(new Cafe24ConnectionFactory(appId, appSecret, redirectUri));
	}

}
