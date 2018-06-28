package net.petrikainulainen.spring.social.signinmvc.config;

import net.petrikainulainen.spring.social.signinmvc.user.service.SimpleSignInAdapter;
import net.petrikainulainen.spring.social.signinmvc.util.Cafe24Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.social.UserIdSource;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.config.support.Cafe24UserIdSource;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Petri Kainulainen
 */
@Configuration
@EnableSocial
@EnableJpaRepositories
public class SocialContext implements SocialConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(SocialContext.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    public Cafe24ConnectionFactory cafe24ConnectionFactory(Environment env){
        Cafe24ConnectionFactory cafe24ConnectionFactory = new Cafe24ConnectionFactory(
                env.getProperty("cafe24.app.id"),
                env.getProperty("cafe24.app.secret"),
                env.getProperty("cafe24.redirect.uri"),
                env.getProperty("cafe24.scope")
        );
        return cafe24ConnectionFactory;
    }

    /**
     * Configures the connection factories for Facebook and Twitter.
     * @param cfConfig
     * @param env
     */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        logger.info("addConnectionFactories started..." );
         /*cfConfig.addConnectionFactory(new TwitterConnectionFactory(
                env.getProperty("twitter.consumer.key"),
                env.getProperty("twitter.consumer.secret")
        ));*/
        /*logger.info("cafe24 app id: " + env.getProperty("cafe24.app.id"));
        logger.info("cafe24 app secret: " + env.getProperty("cafe24.app.secret"));

        Cafe24ConnectionFactory ccf = new Cafe24ConnectionFactory(
                env.getProperty("cafe24.app.id"),
                env.getProperty("cafe24.app.secret"),
                env.getProperty("cafe24.redirect.uri")
        );*/

        cfConfig.addConnectionFactory(cafe24ConnectionFactory(env));
    }

    /**
     * The UserIdSource determines the account ID of the user. The example application
     * uses the username as the account ID.
     */
    @Override
    public UserIdSource getUserIdSource() {

//        return new AuthenticationNameUserIdSource();
        return new Cafe24UserIdSource();
    }


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        logger.info("getUsersConnectionRepository started..." );
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText()
        );

        return jdbcUsersConnectionRepository;
    }

    /**
     * This bean manages the connection flow between the account provider and
     * the example application.
     */
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        ConnectController connectController = new ConnectController(connectionFactoryLocator, connectionRepository);
        connectController.addInterceptor(new Cafe24Interceptor());

        return connectController;
    }

    /**
     * The Spring MVC Controller that allows users to sign-in with their provider accounts.
     */
    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
                                                             UsersConnectionRepository usersConnectionRepository) {

        return new ProviderSignInController(connectionFactoryLocator,
                usersConnectionRepository,
                new SimpleSignInAdapter());
    }


}
