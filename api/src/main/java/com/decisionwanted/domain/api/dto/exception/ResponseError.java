package com.decisionwanted.domain.api.dto.exception;

import java.io.Serializable;

public class ResponseError implements Serializable {

	private static final long serialVersionUID = 6789755541478358171L;

	private int code;

	private String message;

	public ResponseError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
