package org.springframework.social.cafe24.config.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.cafe24.config.support.Cafe24ApiHelper;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.cafe24.security.Cafe24AuthenticationService;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;

import java.util.Map;
import java.util.Set;

public class Cafe24ConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

	private static final Logger logger = LoggerFactory.getLogger(Cafe24ConfigBeanDefinitionParser.class);

	protected Cafe24ConfigBeanDefinitionParser() {
		super(Cafe24ConnectionFactory.class, Cafe24ApiHelper.class);
	}

	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		// TODO Auto-generated method stub
		return Cafe24AuthenticationService.class;
	}

	@Override
	protected BeanDefinition getAuthenticationServiceBeanDefinition(String appId, String appSecret,
                                                                    Map<String, Object> allAttributes) {
		logger.info("getAuthenticationServiceBeanDefinition started...");
		Set<String> keys = allAttributes.keySet();
		for (String key : keys) {
			logger.info("allAttributes(" + key + "): " + allAttributes.get(key));
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.genericBeanDefinition(authenticationServiceClass)
				.addConstructorArgValue(appId)
				.addConstructorArgValue(appSecret)
				.addConstructorArgValue(getRedirectUri(allAttributes))
				.addConstructorArgValue(getMallId(allAttributes));
		return builder.getBeanDefinition();
	}



	@Override
	protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
		logger.info("getConnectionFactoryBeanDefinition started...");
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Cafe24ConnectionFactory.class).addConstructorArgValue(appId).addConstructorArgValue(appSecret);
		builder.addConstructorArgValue(getRedirectUri(allAttributes));
		builder.addConstructorArgValue(getMallId(allAttributes));
		return builder.getBeanDefinition();
	}

	protected String getRedirectUri(Map<String, Object> allAttributes) {
		if (allAttributes.containsKey("redirect_uri")) {
			String redirectUri = (String)allAttributes.get("redirect_uri");
			logger.info("getRedirectUri redirectUri: " + redirectUri);

			return redirectUri.isEmpty() ? null : redirectUri;
		}

		logger.info("getRedirectUri return null");

		return null;

	}
	protected String getMallId(Map<String, Object> allAttributes) {
		if (allAttributes.containsKey("mall_id")) {
			String mallId = (String)allAttributes.get("mall_id");
			logger.info("getMallId mallId: " + mallId);

			return mallId.isEmpty() ? null : mallId;
		}
		logger.info("getMallId return null");

		return null;

	}

}
