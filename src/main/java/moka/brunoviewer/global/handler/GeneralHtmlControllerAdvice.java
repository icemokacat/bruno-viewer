package moka.brunoviewer.global.handler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.exception.Custom404Exception;
import moka.brunoviewer.global.model.HttpErrorResponse;

@Slf4j
@Order(GeneralRestControllerAdvice.ORDER + 1)
@ControllerAdvice
public class GeneralHtmlControllerAdvice {
	/*@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(FileNotFoundException.class)
	public Map<String, String> handle(FileNotFoundException e) {
		log.error(e.getMessage(), e);
		Map<String, String> errorAttributes = new HashMap<>();
		errorAttributes.put("code", "Totoro_NOT_FOUND");
		errorAttributes.put("message", e.getMessage());
		return errorAttributes;
	}*/

	/**
	 * API 호출시 서비스 제공하지 않거나, 존재하지 않는 API를 호출하는 경우 발생하는 예외를 처리한다.
	 * */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(Custom404Exception.class)
	protected Map<String, String> handle(Custom404Exception e) {
		/*log.error("handleCustom404Exception", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(exp.getMessage())
				.build();

		return new RestErrorResponse(response);*/

		log.error("handleCustom404Exception", e);

		HttpErrorResponse response = HttpErrorResponse.builder()
			.httpStatus(HttpStatus.NOT_FOUND)
			.message(e.getMessage())
			.build();

		// HttpErrorResponse to Map<String, String>

		Map<String, String> errorAttributes = new HashMap<>();
		errorAttributes.put("code"		, response.getErrorCode());
		errorAttributes.put("message"	, response.getMessage());
		return errorAttributes;
	}

}
