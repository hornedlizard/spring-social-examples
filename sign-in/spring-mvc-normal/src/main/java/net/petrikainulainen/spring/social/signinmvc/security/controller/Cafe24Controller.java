/*
package net.petrikainulainen.spring.social.signinmvc.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller("cafe24Controller")
@RequestMapping("/test2")
public class Cafe24Controller extends ConnectController {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24Controller.class);
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectionRepository connectionRepository;
    private ConnectSupport connectSupport;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    */
/**
     * Constructs a ConnectController.
     *
     * @param connectionFactoryLocator the locator for {@link ConnectionFactory} instances needed to establish connections
     * @param connectionRepository     the current user's {@link ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
     *//*

    @Inject
    public Cafe24Controller(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator,connectionRepository);
        this.connectSupport = new ConnectSupport(sessionStrategy);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
    }

    @Override
    @RequestMapping(value="/{providerId}", method=RequestMethod.POST)
    public RedirectView connect(@PathVariable String providerId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        logger.info("buildOAuthUrl: " +  connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
        logger.info("redirect_uri: " + request.getParameter("redirect_uri"));
        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        logger.info("nativeRequest: " + nativeRequest.getRequestURI());
        logger.info("nativeRequest getPathInfo: " + nativeRequest.getPathInfo());
        logger.info("nativeRequest: " + nativeRequest.getServletPath());

        parameters.add("redirect_uri", request.getParameter("redirect_uri"));
        logger.info("buildOAuthUrl: " +  connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
        logger.info("parameters.get(redirect_uri): "+ parameters.get("redirect_uri"));
*/
/*
        connectSupport.setCallbackUrl(request.getParameter("redirect_uri"));
        logger.info("buildOAuthUrl: " +  connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
        logger.info("parameters.get(redirect_uri): "+ parameters.get("redirect_uri"));*//*



        try {
            return new RedirectView(connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
        } catch (Exception e) {
            return connectionStatusRedirect(providerId, request);
        }
    }
}
*/
