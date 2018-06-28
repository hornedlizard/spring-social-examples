package net.petrikainulainen.spring.social.signinmvc.util;


import net.petrikainulainen.spring.social.signinmvc.user.dto.Cafe24UserPrincipal;
import net.petrikainulainen.spring.social.signinmvc.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Set;

public class Cafe24Interceptor implements ConnectInterceptor<Cafe24> {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24Interceptor.class);

    @Autowired
    private JdbcUsersConnectionRepository userRepository;

    @Override
    public void preConnect(ConnectionFactory<Cafe24> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
        logger.info("mall_id: " + request.getParameter("mall_id"));
        parameters.add("mall_id", request.getParameter("mall_id"));
    }

    @Override
    public void postConnect(Connection<Cafe24> connection, WebRequest request) {
        logger.info("postConnect : request.getParameter(redirect_uri): " + request.getParameter("redirect_uri"));
        logger.info("postConnect : connection.createData().getProviderUserId(): " + connection.createData().getProviderUserId());

        ConnectionRepository connectionRepository = userRepository.createConnectionRepository("");
        connectionRepository.addConnection(connection);

    }
}
