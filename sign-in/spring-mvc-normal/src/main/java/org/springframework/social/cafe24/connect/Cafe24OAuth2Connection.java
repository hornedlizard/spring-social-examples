package org.springframework.social.cafe24.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Cafe24OAuth2Connection extends AbstractConnection<Cafe24> {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24OAuth2Connection.class);
    private static final long serialVersionUID = 4057584084077577480L;

    private transient final OAuth2ServiceProvider<Cafe24> serviceProvider;

    private String accessToken;

    private String refreshToken;

    private Long expireTime;

    private transient Cafe24 api;

    private transient Cafe24 apiProxy;


    public Cafe24OAuth2Connection(String providerId, String providerUserId, String accessToken, String refreshToken, Long expireTime, OAuth2ServiceProvider serviceProvider, ApiAdapter<Cafe24> apiAdapter) {
        super(apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(accessToken, refreshToken, expireTime);
        initApi();
        initApiProxy();
        initKey(providerId, providerUserId);
        logger.info("Cafe24OAuth2Connection created...");
    }


    private void initAccessTokens(String accessToken, String refreshToken, Long expireTime) {
        logger.info("initAccessTokens called...");

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    private void initApi() {
        logger.info("initApi called...");

        api = serviceProvider.getApi(accessToken);
    }

    @SuppressWarnings("unchecked")
    private void initApiProxy() {
        logger.info("initApiProxy called...");

        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
        logger.info("initApiProxy apiType.getName(): " + apiType.getName());

        if (apiType.isInterface()) {
            logger.info("initApiProxy apiType is Interface: ");

            apiProxy = (Cafe24) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[] { apiType }, new ApiInvocationHandler());
            logger.info("initApiProxy apiProxy.getMallId(): " + apiProxy.getMallId());

        }
    }

    @Override
    public Cafe24 getApi() {
        logger.info("getApi called...");

        if (apiProxy != null) {
            logger.info("getApi when apiProxy is not null...");

            return apiProxy;
        } else {
            logger.info("getApi when apiProxy is null...");

            synchronized (getMonitor()) {
                return api;
            }
        }
    }

    @Override
    public ConnectionData createData() {
        logger.info("createData called...");

        synchronized (getMonitor()) {
            ConnectionData connectionData
                    = new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken, null, refreshToken, expireTime);
            logger.info("connectionData: " + connectionData.getProviderUserId());
            logger.info("connectionData: " + connectionData.getAccessToken());
            logger.info("connectionData: " + connectionData.getProviderId());
            logger.info("connectionData: " + connectionData.getRefreshToken());
            logger.info("connectionData: " + connectionData.getDisplayName());

            return connectionData;
        }
    }

    private class ApiInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("ApiInvocationHandler invoke called... ");

            synchronized (getMonitor()) {
                if (hasExpired()) {
                    throw new ExpiredAuthorizationException(getKey().getProviderId());
                }
                try {
                    return method.invoke(Cafe24OAuth2Connection.this.api, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }

}
