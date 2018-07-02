package org.springframework.social.cafe24.api.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Cafe24Template extends AbstractOAuth2ApiBinding implements Cafe24 {

	private static final Logger logger = LoggerFactory.getLogger(Cafe24Template.class);


	private final String mallId;
	private ObjectMapper objectMapper;

	private ProductOperations productOperations;

	public Cafe24Template(String accessToken) {
		this(accessToken, null);
	}

	static {
		logger.info("Cafe24Template called...");
	}

	public Cafe24Template(String accessToken, String mallId) {
		/* AUTHORIZATION_HEADER를 사용하면 Authorization 헤더에 액세스 토큰 함께 전달. */
		/* OAuth2.0 버전이 Bearer로 Bearer {accessToken}으로 전달된다 */
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


		String connectionPath = connectionType != null && connectionType.length() > 0 ? "/" + connectionType : "";
		logger.info("fetchObjects connectionPath: " + connectionPath);

		String uri = getBaseApiUrl() + connectionPath;
		logger.info("fetchObjects uri: " + uri);


		/* 여기서 멈춤. 왜? fileds가 null인 경우 length가 없었기 때문.*/
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
		if (fields != null) {
			if (fields.length > 0) {
				logger.info("fetchObjects fields.length > 0");

				String joinedFields = join(fields);
				queryParameters.set("fields", joinedFields);
			}
		}


		logger.info("fetchObjects uriBuilder will be called...");
		URIBuilder uriBuilder = URIBuilder.fromUri(uri).queryParams(queryParameters);
		logger.info("fetchObjects uriBuilder created...");

		logger.info("fetchObjects uriBuilder.build().toString(): "  + uriBuilder.build().toString());
		logger.info("fetchObjects uriBuilder.build().toASCIIString(): "  + uriBuilder.build().toASCIIString());
		logger.info("fetchObjects uriBuilder.build().getPath(): "  + uriBuilder.build().getPath());
		logger.info("fetchObjects uriBuilder.build().getHost(): "  + uriBuilder.build().getHost());
		logger.info("fetchObjects uriBuilder.build().getScheme(): "  + uriBuilder.build().getScheme());
		logger.info("fetchObjects uriBuilder.build().getUserInfo(): "  + uriBuilder.build().getUserInfo());
		logger.info("fetchObjects uriBuilder.build().getAuthority(): "  + uriBuilder.build().getAuthority());
		logger.info("fetchObjects uriBuilder.build().getFragment(): "  + uriBuilder.build().getFragment());
		HttpHeaders headers = new HttpHeaders();
		/* 한글이 섞여있기 때문에 application/json;charset=UTF-8로 Content-Type 설정 */
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		/* RestTemplate을 쓰면 URLEncoder.encode(itemIds, "UTF-8");이 된다 */
		ResponseEntity<JsonNode> responseEntity
				= getRestTemplate().exchange(uriBuilder.build(), HttpMethod.GET, httpEntity, JsonNode.class);

		logger.info("fetchObjects responseEntity getStatusCode: "  + responseEntity.getStatusCode());
		logger.info("fetchObjects responseEntity getStatusCodeValue: "  + responseEntity.getStatusCodeValue());
		logger.info("fetchObjects responseEntity getHeaders().getLocation(): "  + responseEntity.getHeaders().getLocation());

		JsonNode jsonNode = responseEntity.getBody();
		/* 전달 받은 JsonNode 객체에서 products, orders 등 원하는 값을 받아서 역직렬화하여 리스트로 만들어 반환 */
		return deserializeDataList(jsonNode.get(connectionType), type);
	}

	/**
	 * @param jsonNode Api 서버와 통신 결과 반환 받은 JsonNode 객체
	 * @param elementType Product.class 등 반환 받으려는 객체의 타입
	 */

	@SuppressWarnings("unchecked")
	private <T> List<T> deserializeDataList(JsonNode jsonNode, final Class<T> elementType) {
		logger.info("deserializeDataList called...");
		Iterator<String> fieldNamesIterator = jsonNode.fieldNames();
		while (fieldNamesIterator.hasNext()) {
			logger.info("jsonNode.fieldNames(): " + fieldNamesIterator.next());

		}
		try {
			logger.info("jsonNode.toString(): " + jsonNode.toString());
			logger.info("deserializeDataList try to make CollectionType listType");

			CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, elementType);
			logger.info("deserializeDataList CollectionType listType.getTypeName: " + listType.getTypeName());

			logger.info("deserializeDataList List<T> result = objectMapper.reader(listType).readValue(jsonNode.toString())");

			/* 이 부분에서 멈추기에 FacebookObject 같은 추상 클래스 만들어서 Product.class가 상속하도록 함 */
			/* 매핑되지 않은 프로퍼티는 Cafe24Object의 add 메서드에서 hook을 하여 추가 */
			List<T> result = (List<T>) objectMapper.reader(listType).readValue(jsonNode.toString());

			logger.info("deserializeDataList try to read List<T> result");
			if (result != null) {
				for (T item : result) {
					logger.info("result item: " + item.toString());
				}
			}
			logger.info("deserializeDataList result 반환");

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
	protected void configureRestTemplate(RestTemplate restTemplate) {
		logger.info("configureRestTemplate called...");

		super.configureRestTemplate(restTemplate);
	}

	/* RestTemplate 데코레이션*/
	@Override
	protected RestTemplate postProcess(RestTemplate restTemplate) {
		logger.info("postProcess called");
		return super.postProcess(restTemplate);
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



	public String getMallId() {
		return mallId;
	}

	@Override
	public ProductOperations productOperations() {
		logger.info("productOperations called...");
		return productOperations;
	}


}
