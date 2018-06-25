package org.springframework.social.cafe24.api.impl;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

public class Cafe24Template extends AbstractOAuth2ApiBinding implements Cafe24 {

	private String appId;
	
	private String mallId;
	
	public Cafe24Template(String accessToken) {
		this(accessToken, null);
	}

	public Cafe24Template(String accessToken, String mallId) {
		this(accessToken, mallId, null);
	}
	
	public Cafe24Template(String accessToken, String mallId, String appId) {
		super(accessToken);
		this.mallId = mallId;
		this.appId = appId;
		initialize();
	}
	
	@Override
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
	}
	
	private void initialize() {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
	}



	@Override
	public RestTemplate getRestTemplate() {
		return getRestTemplate();
	}
	
	
}
