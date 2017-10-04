package com.decisionwanted.domain.api.dto.decision;

import java.io.Serializable;

public class CreateDecisionRequest implements Serializable {

	private static final long serialVersionUID = 3507184707452519034L;

	private String name;

	public CreateDecisionRequest() {
	}

	public CreateDecisionRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}