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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cafe24OAuth2Template extends OAuth2Template {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24OAuth2Template.class);

    private final String redirectUri;
    private final String clientInfo;

    public Cafe24OAuth2Template(String appId, String appSecret, String redirectUri) {
        super(appId, appSecret, getAuthorizeUrl(), getAccessTokenUrl());
        logger.info("Cafe24OAuth2Template appId: " + appId);
        logger.info("Cafe24OAuth2Template appSecret: " + appSecret);
        this.redirectUri = redirectUri;
        this.clientInfo = "client_id=" + formEncode(appId);
    }

    protected static String getAuthorizeUrl() {
        String authorizeUrl = "https://null.cafe24api.com/api/v2/oauth/authorize";
        logger.info("getAuthorizeUrl authorizeUrl: " + authorizeUrl);
        return authorizeUrl;
    }

    protected static String getAccessTokenUrl() {
        String accessTokenUrl = "https://null.cafe24api.com/api/v2/oauth/token";
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
        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            logger.info("key: " + key);
        }
        for (String key : keys) {
            logger.info("buildAuthenticateUrl parameter.get(" + key + "): " + parameters.get(key));
        }
        logger.info("buildAuthenticateUrl redirectUri" + redirectUri);

        if (redirectUri != null) parameters.setRedirectUri(redirectUri);
        return super.buildAuthenticateUrl(grantType, parameters);
    }

    @Override
    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
    }

    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {

        Set<String> keys = parameters.keySet();
        for (String key : keys) {
            logger.info("key: " + key);
        }
        for (String key : keys) {
            logger.info("buildAuthorizeUrl parameter.get(" + key + "): " + parameters.get(key));
        }
        logger.info("buildAuthorizeUrl redirectUri" + redirectUri);
//        logger.info("buildAuthorizeUrl getAuthorizeUrl: " + getAuthorizeUrl(String.valueOf(parameters.get("mallId"))));
        logger.info("buildAuthorizeUrl super.buildAuthorizeUrl(grantType, parameters): " + super.buildAuthorizeUrl(grantType, parameters));

        if (redirectUri != null) parameters.setRedirectUri(redirectUri);
        String authorizeUrl = customBuildAuthUrl(grantType, parameters);
        logger.info("buildAuthorizeUrl authorizeUrl: " + authorizeUrl);
        return authorizeUrl;
    }

    @Override
    public AccessGrant exchangeForAccess(String authorizationCode,
                                         String redirectUri,
                                         MultiValueMap<String, String> additionalParameters) {
        return super.exchangeForAccess(authorizationCode,
                                        this.redirectUri != null ? this.redirectUri : redirectUri,
                                        additionalParameters);
    }

    private String customBuildAuthUrl(GrantType grantType, OAuth2Parameters parameters) {
        String baseAuthUrl = "https://"
                + parameters.get("mall_id").get(0)
                + ".cafe24api.com/api/v2/oauth/authorize?" + clientInfo;
        parameters.remove("mall_id");
        StringBuilder authUrl = new StringBuilder(baseAuthUrl);
        if (grantType == GrantType.AUTHORIZATION_CODE) {
            authUrl.append('&').append("response_type").append('=').append("code");
        } else if (grantType == GrantType.IMPLICIT_GRANT) {
            authUrl.append('&').append("response_type").append('=').append("token");
        }
        for (Iterator<Map.Entry<String, List<String>>> additionalParams = parameters.entrySet().iterator(); additionalParams.hasNext();) {
            Map.Entry<String, List<String>> param = additionalParams.next();
            String name = formEncode(param.getKey());
            logger.info("name: " + name);
            for (Iterator<String> values = param.getValue().iterator(); values.hasNext();) {
                authUrl.append('&').append(name).append('=').append(formEncode(values.next()));
            }
        }
        return authUrl.toString();
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

}
