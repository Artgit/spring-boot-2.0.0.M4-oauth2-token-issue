package com.decisionwanted.domain.api.security.authentication;

import org.springframework.security.oauth2.provider.OAuth2Request;

public class UserAwareOAuth2Request extends OAuth2Request {

	private static final long serialVersionUID = -7328829441747399978L;

	private Long userId;

	public UserAwareOAuth2Request(OAuth2Request other, Long userId) {
		super(other);
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}