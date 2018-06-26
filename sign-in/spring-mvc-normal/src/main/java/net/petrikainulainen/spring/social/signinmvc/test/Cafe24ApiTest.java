package net.petrikainulainen.spring.social.signinmvc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class Cafe24ApiTest {

    private static final Logger logger = LoggerFactory.getLogger(Cafe24ApiTest.class);

    @RequestMapping(value = "/test_auth", method = RequestMethod.GET)
    public void testAuth( HttpServletResponse response) {
        Cafe24ConnectionFactory connectionFactory
                = new Cafe24ConnectionFactory("JoBu2I4jB9tm4ajgPG53gB", "UBIFWXsaDNMgUYdxqN9wtD", "https://devbit004.cafe24.com");
        OAuth2Operations oAuth2Operations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://devbit004.cafe24.com");
        params.setScope("mall.read_application,mall.write_application,mall.read_order,mall.read_product,mall.write_product,mall.read_customer,mall.read_personal ");
        String authorizeUrl = oAuth2Operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
        logger.info("authorizeUrl: " + authorizeUrl);
        try {
            response.sendRedirect(authorizeUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
