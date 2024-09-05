package moka.brunoviewer.global.model.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestErrorResponse extends ResponseEntity<HttpErrorResponse> {

	public RestErrorResponse(HttpStatus httpStatus) {
		super(HttpErrorResponse.builder()
			.httpStatus(httpStatus)
			.message(httpStatus.getReasonPhrase())
			.build(), httpStatus);
	}

	public RestErrorResponse(HttpErrorResponse body) {
		super(body, body.getHttpStatus());
	}

}
