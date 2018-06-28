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
@RequestMapping("/connect")
public class Cafe24Controller extends ConnectController {
    private static final Logger logger = LoggerFactory.getLogger(Cafe24Controller.class);
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectionRepository connectionRepository;
    private ConnectSupport connectSupport;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Inject
    public Cafe24Controller(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator,connectionRepository);
        this.connectSupport = new ConnectSupport(sessionStrategy);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value="/auth", method=RequestMethod.GET, params="code")
    public RedirectView testAuth(@PathVariable(value = "providerId") String providerId, NativeWebRequest request) {
        if (providerId == null) {
            logger.info("code: " + request.getParameter("code"));

        }

        return null;
    }



}
*/
