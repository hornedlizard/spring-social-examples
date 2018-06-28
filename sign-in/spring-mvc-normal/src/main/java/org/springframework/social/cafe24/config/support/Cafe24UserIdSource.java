package org.springframework.social.cafe24.config.support;

import org.springframework.social.UserIdSource;
import org.springframework.social.cafe24.connect.Cafe24OAuth2Template;

import javax.inject.Inject;

public class Cafe24UserIdSource implements UserIdSource {

    @Override
    public String getUserId() {
        return Cafe24OAuth2Template.getMallId();
    }
}
