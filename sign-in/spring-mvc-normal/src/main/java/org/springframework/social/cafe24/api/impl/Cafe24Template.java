package org.springframework.social.cafe24.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cafe24Template extends AbstractOAuth2ApiBinding implements Cafe24 {

	private String appId;
	
	private String mallId;

	private ObjectMapper objectMapper;
	
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


	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
		objectMapper = new ObjectMapper();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	/*@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		Map<Class<?>, String> implicitCollections = new HashMap<Class<?>, String>();

		// marshaller.setConverters(converterMatchers);
		Map<String, Object> aliases = new HashMap<String, Object>();
		// aliases.put("playlist", PlaylistUpdate.class.getName());


		Map<String, Class<?>> useAttributeFor = new HashMap<String, Class<?>>();


		return messageConverters;
	}*/
	
}
