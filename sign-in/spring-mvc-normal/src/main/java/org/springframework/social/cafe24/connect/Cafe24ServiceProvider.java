package org.springframework.social.cafe24.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.api.impl.Cafe24Template;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class Cafe24ServiceProvider extends AbstractOAuth2ServiceProvider<Cafe24> {

	// private static final String URL = ".cafe24api.com/api/v2/oauth/";
	// private String mallId = null;
	private static final Logger logger = LoggerFactory.getLogger(Cafe24ServiceProvider.class);
	static {
		logger.info("Cafe24ServiceProvider started");
	}

	public Cafe24ServiceProvider(String clientId, String clientSecret) {
		this(clientId, clientSecret, null, null);
	}
	public Cafe24ServiceProvider(String appId, String appSecret, String redirectUri, String mallId) {
		super(new Cafe24OAuth2Template(appId, appSecret, redirectUri, mallId));
	}
	
	/*private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
		
		
		OAuth2Template oAuth2Template = new OAuth2Template(appId, 
				appSecret, 
				"https://utkg3000" + URL + "authorize",
				"https://utkg3000" + URL + "token");
		oAuth2Template.setUseParametersForClientAuthentication(true);
		
		return oAuth2Template;
	}*/

	@Override
	public Cafe24 getApi(String accessToken) {
		// TODO Auto-generated method stub
		return new Cafe24Template(accessToken);
	}


}
