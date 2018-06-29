package org.springframework.social.cafe24.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.cafe24.api.ProductOperations;
import org.springframework.social.cafe24.api.impl.json.Cafe24Module;
import org.springframework.social.facebook.api.impl.json.FacebookModule;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cafe24Template extends AbstractOAuth2ApiBinding implements Cafe24 {

	private static final Logger logger = LoggerFactory.getLogger(Cafe24Template.class);


	private String mallId;
	private ObjectMapper objectMapper;

	private ProductOperations productOperations;

	public Cafe24Template(String accessToken) {
		this(accessToken, null);
	}

	static {
		logger.info("Cafe24Template called...");
	}

	public Cafe24Template(String accessToken, String mallId) {
		super(accessToken, TokenStrategy.AUTHORIZATION_HEADER);
		this.mallId = mallId;
		logger.info("mallId: " + this.mallId);

		initialize();
	}


	@Override
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
	}

	private void initialize() {
		logger.info("initialize started...");
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApi();
	}


	public void initSubApi() {
		logger.info("initSubApi started...");

		productOperations = new ProductTemplate(this);
	}


	public String getBaseApiUrl() {
		logger.info("getBaseApiUrl: " + "https://" + this.mallId + ".cafe24api.com/api/v2/admin");
		return "https://" + this.mallId + ".cafe24api.com/api/v2/admin";
	}

	//https://{{mallid}}.cafe24api.com/api/v2/admin/products
	public <T> List<T> fetchObjects(String connectionType, Class<T> type, String... fields) {
		logger.info("fetchObjects called...");

		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
		if (fields.length > 0) {
			String joinedFields = join(fields);
			queryParameters.set("fields", joinedFields);
		}
		String connectionPath = connectionType != null && connectionType.length() > 0 ? "/" + connectionType : "";
		URIBuilder uriBuilder = URIBuilder.fromUri(getBaseApiUrl() + connectionPath).queryParams(queryParameters);
		logger.info("fetchObjects uriBuilder.build().getPath(): "  + uriBuilder.build().getPath());
		JsonNode jsonNode = getRestTemplate().getForObject(uriBuilder.build(), JsonNode.class);
		return deserializeDataList(jsonNode, type);
	}

	/*private <T> List<T> makeList(Class<T> type, String connectionType, JsonNode jsonNode) {
		List<T> data = deserializeDataList(jsonNode.get(connectionType), type);
//		getJsonMessageConverter().getObjectMapper().readValue()
		return null;
	}*/

	@SuppressWarnings("unchecked")
	private <T> List<T> deserializeDataList(JsonNode jsonNode, final Class<T> elementType) {
		logger.info("deserializeDataList called...");

		try {
			CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, elementType);
			return (List<T>) objectMapper.reader(listType).readValue(jsonNode.toString()); // TODO: EXTREMELY HACKY--TEMPORARY UNTIL I FIGURE OUT HOW JACKSON 2 DOES THIS
		} catch (IOException e) {
			throw new UncategorizedApiException("cafe24", "Error deserializing data from cafe24: " + e.getMessage(), e);
		}
	}


	private String join(String[] strings) {
		logger.info("join called...");

		StringBuilder builder = new StringBuilder();
		if(strings.length > 0) {
			builder.append(strings[0]);
			for (int i = 1; i < strings.length; i++) {
				builder.append("," + strings[i]);
			}
		}
		return builder.toString();
	}


	@Override
	public RestTemplate getRestTemplate() {
		logger.info("getRestTemplate called...");

		return super.getRestTemplate();
	}


	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		logger.info("getJsonMessageConverter called...");

		MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Cafe24Module());
		logger.info("getJsonMessageConverter Cafe24Module registered...");

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

	public String getMallId() {
		return mallId;
	}

	@Override
	public ProductOperations productOperations() {
		logger.info("productOperations called...");
		return productOperations;
	}


}
