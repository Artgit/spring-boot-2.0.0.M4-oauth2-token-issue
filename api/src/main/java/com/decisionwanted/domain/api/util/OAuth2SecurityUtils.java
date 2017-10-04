package com.decisionwanted.domain.api.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.decisionwanted.domain.api.security.authentication.UserAwareOAuth2Request;

public class OAuth2SecurityUtils {

	public static UserAwareOAuth2Request getUserAwareOAuth2Request(Authentication authentication) {
		if (!authentication.getClass().isAssignableFrom(OAuth2Authentication.class)) {
			throw new RuntimeException("unexpected authentication object, expected OAuth2 authentication object");
		}
		return (UserAwareOAuth2Request) ((OAuth2Authentication) authentication).getOAuth2Request();
	}

}