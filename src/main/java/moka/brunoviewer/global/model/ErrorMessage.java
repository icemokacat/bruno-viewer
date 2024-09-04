package moka.brunoviewer.global.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorMessage {
	// Common
	INVALID_INPUT_VALUE(	"Invalid Input Value"),
	ENTITY_NOT_FOUND(		"Entity Not Found"),
	INVALID_TYPE_VALUE(		"Invalid Type Value"),
	HANDLE_ACCESS_DENIED(	"Access is Denied"),
	BUSINESS_ERROR(			"Business Error"),
	INTERNAL_SERVER_ERROR(	"Server Error");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}
