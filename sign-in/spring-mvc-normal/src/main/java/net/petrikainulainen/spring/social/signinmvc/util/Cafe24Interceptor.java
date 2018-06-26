package net.petrikainulainen.spring.social.signinmvc.util;


import net.petrikainulainen.spring.social.signinmvc.user.dto.Cafe24UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Set;

public class Cafe24Interceptor implements ConnectInterceptor<Cafe24> {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24Interceptor.class);

    @Override
    public void preConnect(ConnectionFactory<Cafe24> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {

        Cafe24ConnectionFactory connectionFactory1 = (Cafe24ConnectionFactory) connectionFactory;

        logger.info("_csrf: " + request.getParameter("_csrf"));
        logger.info("redirect_uri: " + request.getParameter("redirect_uri"));
        logger.info("mall_id: " + request.getParameter("mall_id"));
        logger.info("scope: " + request.getParameter("scope"));

        parameters.add("scope", request.getParameter("scope"));
        parameters.add("redirect_uri", request.getParameter("redirect_uri"));
        parameters.add("mall_id", request.getParameter("mall_id"));

    }

    @Override
    public void postConnect(Connection<Cafe24> connection, WebRequest request) {
        logger.info("postConnect : request.getParameter(redirect_uri): " + request.getParameter("redirect_uri"));

    }
}
