package net.petrikainulainen.spring.social.signinmvc.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class SimpleSignInAdapter implements SignInAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleSignInAdapter.class);

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        logger.info("signIn userId: " + userId);
        logger.info("signIn userId: " + connection.getApi().toString());
        Iterator<String> keys = request.getParameterNames();
        logger.info("signIn keys.next()1: " + keys.next());
        logger.info("signIn keys.next()2: " + keys.next());
        logger.info("signIn keys.next()3: " + keys.next());
        logger.info("signIn keys.next()4: " + keys.next());
        logger.info("signIn keys.next()5: " + keys.next());
        try {
            request.getNativeResponse(HttpServletResponse.class).sendRedirect("/");
        } catch (IOException e) {
            logger.info("signIn sendRedirect 실패");
            e.printStackTrace();
        }

        return null;
    }
}
