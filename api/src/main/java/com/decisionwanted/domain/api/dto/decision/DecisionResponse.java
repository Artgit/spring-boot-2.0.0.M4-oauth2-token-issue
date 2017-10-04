package com.decisionwanted.domain.api.dto.decision;

import java.io.Serializable;

public class DecisionResponse implements Serializable {

	private static final long serialVersionUID = -2186714221645615568L;

	private Long id;

	public DecisionResponse() {
	}

	public DecisionResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}