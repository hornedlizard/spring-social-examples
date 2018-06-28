package net.petrikainulainen.spring.social.signinmvc.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24ConnectionFactory;
import org.springframework.social.cafe24.connect.Cafe24OAuth2Template;
import org.springframework.social.connect.*;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UrlPathHelper;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @author Petri Kainulainen
 */
@Controller("LoginController")
@RequestMapping("/connect2")
public class LoginController extends ConnectController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    protected static final String VIEW_NAME_LOGIN_PAGE = "user/login";

    @Autowired
    private Cafe24ConnectionFactory cafe24ConnectionFactory;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final MultiValueMap<Class<?>, ConnectInterceptor<?>> connectInterceptors = new LinkedMultiValueMap<Class<?>, ConnectInterceptor<?>>();
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectionRepository connectionRepository;
    private final ConnectSupport connectSupport;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * Constructs a ConnectController.
     *
     * @param connectionFactoryLocator the locator for {@link ConnectionFactory} instances needed to establish connections
     * @param connectionRepository     the current user's {@link ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
     */
    @Inject
    public LoginController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
        this.connectSupport = new ConnectSupport(sessionStrategy);
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

    @ResponseBody
    @RequestMapping(value="/oauthtest", method=RequestMethod.GET, params="code")
    public String  testAuth(NativeWebRequest request) {
        logger.info("testAuth code: " + request.getParameter("code"));
        logger.info("testAuth state: " + request.getParameter("state"));
//        usersConnectionRepository.createConnectionRepository()
        try {
            Field mallId = Cafe24OAuth2Template.class.getField("mallId");
            logger.info("mallId.getName(): " + mallId.getName());
            logger.info("mallId.getType(): " + mallId.getType());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "testAuth";
    }

    @Override
    @RequestMapping(value="/{providerId}", method=RequestMethod.POST)
    public RedirectView connect(@PathVariable String providerId, NativeWebRequest request) {
        return super.connect(providerId, request);
    }

    @Override
    @RequestMapping(value = "/{providerId}/test", method = RequestMethod.GET,  params="code")
    public RedirectView oauth2Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            logger.info("oauth2Callback started...");
            logger.info("oauth2Callback providerId: " + providerId);
            logger.info("oauth2Callback getParameter(code): " + request.getParameter("code"));
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            logger.info("oauth2Callback connectionFactory.getClass().getName(): " + connectionFactory.getClass().getName());

            Connection<?> connection = connectSupport.completeConnection(connectionFactory, request);
            logger.info("connection getProviderId: " + connection.getKey().getProviderId());
            logger.info("connection getProviderUserId: " + connection.getKey().getProviderUserId());
            addConnection(connection, connectionFactory, request);
        } catch (Exception e) {
            sessionStrategy.setAttribute(request, PROVIDER_ERROR_ATTRIBUTE, e);
            logger.warn("1111Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + providerId +" connection status page.");
        }
        return connectionStatusRedirect(providerId, request);
    }

    private void addConnection(Connection<?> connection, ConnectionFactory<?> connectionFactory, WebRequest request) {
        logger.info("addConnection started...");
        try {
            logger.info("addConnection 1");

            connectionRepository.addConnection(connection);
            logger.info("addConnection 2");

            postConnect(connectionFactory, connection, request);
            logger.info("addConnection 3");

        } catch (DuplicateConnectionException e) {
            logger.info("addConnection DuplicateConnectionException");

            sessionStrategy.setAttribute(request, DUPLICATE_CONNECTION_ATTRIBUTE, e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void postConnect(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
        logger.info("postConnect started...");
        for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
            logger.info("postConnect interceptor: " + interceptor.getClass().getName());
            interceptor.postConnect(connection, request);
        }
    }

    private List<ConnectInterceptor<?>> interceptingConnectionsTo(ConnectionFactory<?> connectionFactory) {
        logger.info("interceptingConnectionsTo started...");

        Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
        List<ConnectInterceptor<?>> typedInterceptors = connectInterceptors.get(serviceType);
        if (typedInterceptors == null) {
            typedInterceptors = Collections.emptyList();
        }
        return typedInterceptors;
    }


    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        logger.info("connectionStatusRedirect started...");
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        String path = "/connect2/" + providerId + getPathExtension(servletRequest);
        logger.info("connectionStatusRedirect path: " + path);

        if (prependServletPath(servletRequest)) {
            path = servletRequest.getServletPath() + path;
            logger.info("connectionStatusRedirect path in if(prependServletPath): " + path);

        }
        return new RedirectView(path, true);
    }

    private boolean prependServletPath(HttpServletRequest request) {
        logger.info("prependServletPath started...");

        return !this.urlPathHelper.getPathWithinServletMapping(request).equals("");
    }

    private String getPathExtension(HttpServletRequest request) {
        logger.info("getPathExtension started...");

        String fileName = extractFullFilenameFromUrlPath(request.getRequestURI());
        logger.info("getPathExtension fileName: " + fileName);

        String extension = StringUtils.getFilenameExtension(fileName);
        logger.info("getPathExtension extension: " + extension);

        return extension != null ? "." + extension : "";
    }

    private String extractFullFilenameFromUrlPath(String urlPath) {
        logger.info("extractFullFilenameFromUrlPath started...");

        int end = urlPath.indexOf('?');
        if (end == -1) {
            end = urlPath.indexOf('#');
            if (end == -1) {
                end = urlPath.length();
            }
        }
        int begin = urlPath.lastIndexOf('/', end) + 1;
        logger.info("extractFullFilenameFromUrlPath begin: " + begin);

        int paramIndex = urlPath.indexOf(';', begin);
        logger.info("extractFullFilenameFromUrlPath paramIndex: " + paramIndex);

        end = (paramIndex != -1 && paramIndex < end ? paramIndex : end);
        logger.info("extractFullFilenameFromUrlPath end: " + end);
        String result = urlPath.substring(begin, end);
        logger.info("extractFullFilenameFromUrlPath result: " + result);

        return result;
    }
}
