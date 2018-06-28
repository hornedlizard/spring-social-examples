/*
package net.petrikainulainen.spring.social.signinmvc.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller("cafe24MainController")
public class Cafe24MainController extends ConnectController{
    private static final Logger logger = LoggerFactory.getLogger(Cafe24MainController.class);

    */
/**
     * Constructs a ConnectController.
     *
     * @param connectionFactoryLocator the locator for {@link ConnectionFactory} instances needed to establish connections
     * @param connectionRepository     the current user's {@link ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
     *//*

    @Inject
    public Cafe24MainController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value="/auth", method=RequestMethod.GET, params="code")
    public RedirectView oauth2Callback(@PathVariable(value = "providerId") String providerId, NativeWebRequest request) {
        if (providerId == null) {
            logger.info("code: " + request.getParameter("code"));

        }

        return super.oauth2Callback(providerId, request);
    }


}
*/
