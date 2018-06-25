package org.springframework.social.cafe24.config.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;

public class Cafe24ApiHelper implements ApiHelper<Cafe24> {
	
	private final Logger logger = LoggerFactory.getLogger(Cafe24ApiHelper.class);
	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;

	public Cafe24ApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;		
	}
	
	@Override
	public Cafe24 getApi() {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting API binding instance for Cafe24");
		}
		// TODO Auto-generated method stub
		Connection<Cafe24> connection = usersConnectionRepository
										.createConnectionRepository(userIdSource.getUserId())
										.findPrimaryConnection(Cafe24.class);
		if (logger.isDebugEnabled() && connection == null) {
			logger.debug("No current connection. Returning default Cafe24Template instance.");
		}
		logger.info("userIdSource: " + userIdSource.getUserId());
		
		return connection != null ? connection.getApi() : null;
	}

}
