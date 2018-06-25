package net.petrikainulainen.spring.social.signinmvc.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petri Kainulainen
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    protected static final String VIEW_NAME_LOGIN_PAGE = "user/login";



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request) {
        LOGGER.debug("Rendering login page.");
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
