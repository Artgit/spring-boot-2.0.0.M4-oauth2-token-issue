package com.decisionwanted.domain.api.decision.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.decisionwanted.domain.api.dto.decision.CreateDecisionRequest;
import com.decisionwanted.domain.api.dto.decision.DecisionResponse;
import com.decisionwanted.domain.api.security.util.SecurityTestUtils;

public class DecisionTestUtils {

	final static Logger logger = LoggerFactory.getLogger(DecisionTestUtils.class);

	public static RestTemplate restTemplate = new RestTemplate();

	public static DecisionResponse createDecision(String name, String username, String password, int port) {

		String accessToken = SecurityTestUtils.loginAndGetAccessToken(username, password, port);

		CreateDecisionRequest request = new CreateDecisionRequest(name);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(SecurityTestUtils.AUTH_HEADER_NAME, "Bearer " + accessToken);
		HttpEntity<CreateDecisionRequest> requestEntity = new HttpEntity<CreateDecisionRequest>(request, headers);

		ResponseEntity<DecisionResponse> responseEntity = restTemplate.exchange(
				String.format("http://localhost:%d/api/v1.0/decisions", port), HttpMethod.POST, requestEntity,
				DecisionResponse.class);
		return responseEntity.getBody();
	}

}