package com.decisionwanted.domain.api.security.util;

import java.util.Arrays;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

public class SecurityTestUtils {

	public static final String AUTH_HEADER_NAME = "Authorization";
	public static final String AUTH_COOKIE_NAME = "AUTH-TOKEN";

	public static String loginAndGetAccessToken(String username, String password, int port) {
		
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password);
		resourceDetails.setAccessTokenUri(String.format("http://localhost:%d/api/oauth/token", port));
		resourceDetails.setClientId("clientapp");
		resourceDetails.setClientSecret("123456");
		resourceDetails.setGrantType("password");
		resourceDetails.setScope(Arrays.asList("read", "write"));

		DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
		restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));

		return restTemplate.getAccessToken().toString();
	}

}
