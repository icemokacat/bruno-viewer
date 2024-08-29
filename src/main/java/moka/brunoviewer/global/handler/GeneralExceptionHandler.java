package moka.brunoviewer.global.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Parameter가 잘못된 경우 발생하는 Exception을 처리한다.
	 * */
	@ExceptionHandler(value
		= { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(
		RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		log.error("handleConflict", ex);
		return handleExceptionInternal(ex, bodyOfResponse,
			new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
