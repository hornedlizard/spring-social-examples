package net.petrikainulainen.spring.social.signinmvc.user.dto;

import java.security.Principal;

public class Cafe24UserPrincipal implements Principal {
    private String mallId;

    private String redirectUri;

    private String scope;

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getName() {
        return this.mallId;
    }
}
