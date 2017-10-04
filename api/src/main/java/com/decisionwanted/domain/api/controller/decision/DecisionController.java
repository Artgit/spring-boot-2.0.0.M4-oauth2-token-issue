package com.decisionwanted.domain.api.controller.decision;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.decisionwanted.domain.api.dto.decision.CreateDecisionRequest;
import com.decisionwanted.domain.api.dto.decision.DecisionResponse;

@RestController
@RequestMapping("/v1.0/decisions")
public class DecisionController {


	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public DecisionResponse create(@Valid @RequestBody CreateDecisionRequest request, Authentication authentication) {

		return new DecisionResponse(777L);
	}

}