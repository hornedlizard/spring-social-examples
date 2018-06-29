package net.petrikainulainen.spring.social.signinmvc.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Petri Kainulainen*/


@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    protected static final String VIEW_NAME_HOMEPAGE = "index";

    protected static final String VIEW_NAME_LOGIN_PAGE = "user/login";

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String showHomePage() {
        logger.debug("Rendering homepage.");
        return VIEW_NAME_HOMEPAGE;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request) {
        logger.debug("Rendering login page.");
        /*Cafe24ServiceProvider serviceProvider
                = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext())
                .getBean(Cafe24ServiceProvider.class);
        serviceProvider.setMallId("solendless");*/
        /*ConnectionFactoryRegistry registry
                = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext())
                .getBean(ConnectionFactoryRegistry.class);*/
        /*registry.addConnectionFactory(new Cafe24ConnectionFactory(
                "JoBu2I4jB9tm4ajgPG53gB",
                "UBIFWXsaDNMgUYdxqN9wtD",
                "solendless"
        ));*/


        return VIEW_NAME_LOGIN_PAGE;
    }
}
