package net.petrikainulainen.spring.social.signinmvc.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import java.util.Iterator;

public class Cafe24Interceptor implements ConnectInterceptor<Cafe24> {
    private static final Logger logger = LoggerFactory.getLogger("");

    @Override
    public void preConnect(ConnectionFactory<Cafe24> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {

        logger.info("request.getContextPath(): " + request.getContextPath());


        Iterator<String > nameIterator = request.getHeaderNames();
        logger.info("nameIterator1: " + nameIterator.next());
        logger.info("nameIterator2: " + nameIterator.next());
        logger.info("nameIterator3: " + nameIterator.next());
        logger.info("nameIterator4: " + nameIterator.next());
        logger.info("nameIterator5: " + nameIterator.next());
        logger.info("nameIterator6: " + nameIterator.next());
        logger.info("nameIterator7: " + nameIterator.next());
        logger.info("nameIterator8: " + nameIterator.next());
        logger.info("nameIterator9: " + nameIterator.next());
        logger.info("nameIterator10: " + nameIterator.next());
        logger.info("nameIterator11: " + nameIterator.next());
        logger.info("nameIterator12: " + nameIterator.next());
        logger.info("nameIterator13: " + nameIterator.next());
        logger.info("nameIterator14: " + nameIterator.next());

        logger.info("request.getHeader(host): " + request.getHeader("host"));
        logger.info("request.getHeader(connection): " + request.getHeader("connection"));
        logger.info("request.getHeader(content-length): " + request.getHeader("content-length"));
        logger.info("request.getHeader(cache-control): " + request.getHeader("cache-control"));
        logger.info("request.getHeader(origin): " + request.getHeader("origin"));
        logger.info("request.getHeader(upgrade-insecure-requests): " + request.getHeader("upgrade-insecure-requests"));
        logger.info("request.getHeader(dnt): " + request.getHeader("dnt"));
        logger.info("request.getHeader(content-type): " + request.getHeader("content-type"));
        logger.info("request.getHeader(user-agent): " + request.getHeader("user-agent"));



        String paramName = request.getParameterNames().toString();
        logger.info("request.getParameterNames(): " + paramName);
        logger.info("request.getParameter(redirect_uri): " + request.getParameter("redirect_uri"));
        logger.info("request.getParameter(scope): " + request.getParameter("scope"));



    }

    @Override
    public void postConnect(Connection<Cafe24> connection, WebRequest request) {
        logger.info("postConnect : request.getParameter(redirect_uri): " + request.getParameter("redirect_uri"));

    }
}
