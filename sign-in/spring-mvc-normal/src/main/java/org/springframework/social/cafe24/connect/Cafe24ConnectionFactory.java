package org.springframework.social.cafe24.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;

public class Cafe24ConnectionFactory extends OAuth2ConnectionFactory<Cafe24> {

	private static final Logger logger = LoggerFactory.getLogger(Cafe24ConnectionFactory.class);
	static {
		logger.info("Cafe24ConnectionFactory started");
	}
	public Cafe24ConnectionFactory(String appId, String appSecret) {
//		this(appId, appSecret, null, null);
		super("cafe24", new Cafe24ServiceProvider(appId, appSecret), new Cafe24Adapter());

	}
	public Cafe24ConnectionFactory(String appId, String appSecret, String redirectUri, String mallId) {
		super("cafe24", new Cafe24ServiceProvider(appId, appSecret, redirectUri, mallId), new Cafe24Adapter());
	}

}
