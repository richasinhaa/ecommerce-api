package com.api.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class ResourceNotFoundException extends RuntimeException {
    private String code;
    private String message;
    private String field;
    private String status;

    public ResourceNotFoundException( String code, String message, String field, String status) {
        this.code = code;
        this.message = message;
        this.field = field;
        this.status = status;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}