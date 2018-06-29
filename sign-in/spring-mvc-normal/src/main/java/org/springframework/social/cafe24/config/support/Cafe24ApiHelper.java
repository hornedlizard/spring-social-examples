package org.springframework.social.cafe24.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.connect.Cafe24OAuth2Template;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;

public class Cafe24ApiHelper implements ApiHelper<Cafe24> {
	
	private final Logger logger = LoggerFactory.getLogger(Cafe24ApiHelper.class);
	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;

	public Cafe24ApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		logger.info("Cafe24ApiHelper created");
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;
		logger.info("Cafe24ApiHelper userIdSource: " + userIdSource.getUserId());

	}

	@Override
	public Cafe24 getApi() {
		logger.info("getApi() called...");
		if (logger.isDebugEnabled()) {
			logger.debug("Getting API binding instance for Cafe24");
		}
		// TODO Auto-generated method stub
		logger.info("getApi() userIdSource.getUserId(): " + userIdSource.getUserId());

		/*Connection<Cafe24> connection = usersConnectionRepository
										.createConnectionRepository(userIdSource.getUserId())
										.findPrimaryConnection(Cafe24.class);*/
		String mallId = Cafe24OAuth2Template.getMallId();
		logger.info("getApi mallId: " + mallId);
		Connection<Cafe24> connection = usersConnectionRepository
										.createConnectionRepository(mallId)
										.findPrimaryConnection(Cafe24.class);
		if (logger.isDebugEnabled() && connection == null) {
			logger.debug("No current connection. Returning default Cafe24Template instance.");
		}

		if (connection != null) {
			logger.info("getApi is not null");
		} else {
			logger.info("getApi is null");

		}
		return connection != null ? connection.getApi() : null;
	}

}
