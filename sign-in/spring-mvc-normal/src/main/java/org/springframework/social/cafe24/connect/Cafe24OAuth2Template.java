package org.springframework.social.cafe24.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

public class Cafe24OAuth2Template extends OAuth2Template {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24OAuth2Template.class);

    private String redirectUri;
    private String mallId;

    @Inject
    private AuthorizationCodeResourceDetails details;

    public Cafe24OAuth2Template(String appId, String appSecret, String redirectUri, String mallId) {
        super(appId, appSecret, getAuthorizeUrl(mallId), getAccessTokenUrl(mallId));
        this.redirectUri = redirectUri;
        this.mallId = mallId;
        logger.info("Cafe24OAuth2Template redirectUri: " + redirectUri);
        logger.info("Cafe24OAuth2Template mallId: " + mallId);

    }

    protected static String getAuthorizeUrl(String mallId) {
        String authorizeUrl = "https://" + mallId + ".cafe24api.com/api/v2/oauth/authorize";
        logger.info("getAuthorizeUrl authorizeUrl: " + authorizeUrl);
        return authorizeUrl;
    }

    protected static String getAccessTokenUrl(String mallId) {
        String accessTokenUrl = "https://" + mallId + ".cafe24api.com/api/v2/oauth/token";
        logger.info("getAccessTokenUrl accessTokenUrl: " + accessTokenUrl);
        return accessTokenUrl;
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken,
                                            String scope,
                                            String refreshToken,
                                            Long expiresIn,
                                            Map<String, Object> response) {
        return super.createAccessGrant(accessToken, scope, refreshToken, expiresIn, response);
    }

    @Override
    public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
        logger.info("details.getAccessTokenUri(): " + details.getAccessTokenUri());
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            logger.info("buildAuthenticateUrl parameter.get(" + key + "): " + parameters.get(key));
        }
        logger.info("buildAuthenticateUrl redirectUri" + redirectUri);

        if (redirectUri != null) parameters.setRedirectUri(redirectUri);
        return super.buildAuthenticateUrl(grantType, parameters);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
        logger.info("details.getAccessTokenUri(): " + details.getAccessTokenUri());

        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            logger.info("buildAuthorizeUrl parameter.get(" + key + "): " + parameters.get(key));
        }
        logger.info("buildAuthorizeUrl redirectUri" + redirectUri);
        logger.info("buildAuthorizeUrl getAuthorizeUrl: " + getAuthorizeUrl(String.valueOf(parameters.get("mallId"))));
        logger.info("buildAuthorizeUrl super.buildAuthorizeUrl(grantType, parameters): " + super.buildAuthorizeUrl(grantType, parameters));

        if (redirectUri != null) parameters.setRedirectUri(redirectUri);
        return super.buildAuthorizeUrl(grantType, parameters);
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode,
                                         String redirectUri,
                                         MultiValueMap<String, String> additionalParameters) {
        return super.exchangeForAccess(authorizationCode,
                                        this.redirectUri != null ? this.redirectUri : redirectUri,
                                        additionalParameters);
    }


}
