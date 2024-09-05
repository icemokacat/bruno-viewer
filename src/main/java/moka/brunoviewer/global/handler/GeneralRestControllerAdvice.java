package moka.brunoviewer.global.handler;

import static org.springframework.boot.env.RandomValuePropertySourceEnvironmentPostProcessor.*;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.exception.Custom404Exception;
import moka.brunoviewer.global.exception.CustomIllegalArgumentException;
import moka.brunoviewer.global.exception.ImageNotFound;
import moka.brunoviewer.global.model.http.ErrorMessage;
import moka.brunoviewer.global.model.http.HttpErrorResponse;
import moka.brunoviewer.global.model.http.RestErrorResponse;

@Slf4j
@Order(ORDER)
@RestControllerAdvice(annotations = RestController.class)
public class GeneralRestControllerAdvice {

	public static final int ORDER = 0;

	/**
	 * 데이터 validation 에서 발생하는 예외를 처리한다.
	 * 혹은 business logic 에서 발생하는 예외를 처리한다.
	 */
	@ExceptionHandler(BindException.class)
	protected RestErrorResponse handleBindException(BindException exp) {
		log.error("handleBindException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(ErrorMessage.INVALID_INPUT_VALUE.getMessage())
				.errors(exp.getBindingResult().getFieldErrors())
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 *  parameter 의 특정 조건 등 클라이언트에서 잘못된 요청을 보낸 경우 발생하는 예외를 처리한다.
	 * */
	@ExceptionHandler(IllegalArgumentException.class)
	protected RestErrorResponse handleIllegalArgumentException(IllegalArgumentException exp) {
		log.error("handleIllegalArgumentException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				//.message(ErrorMessage.INVALID_INPUT_VALUE.getMessage())
				.message(exp.getMessage())
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * 파라미터 오류, 유효하지 않은 요청 등
	 * */
	@ExceptionHandler(CustomIllegalArgumentException.class)
	protected RestErrorResponse handleCustomIllegalArgumentException(CustomIllegalArgumentException exp) {
		log.error("handleCustomIllegalArgumentException", exp);

		HttpErrorResponse response;

		if(exp.getErrorMap() != null) {
			response = HttpErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(exp.getMessage())
				.errorResult(exp.getErrorMap())
				.build();
		}else{
			response = HttpErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(exp.getMessage())
				.build();
		}

		return new RestErrorResponse(response);
	}

	/**
	 * 지원하지 않은 HTTP Method 호출시 발생하는 예외를 처리한다.
	 * HttpStatus.METHOD_NOT_ALLOWED
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected RestErrorResponse handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException exp) {
		log.error("handleHttpRequestMethodNotSupportedException", exp);
		return new RestErrorResponse(HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * validation 혹은 @Validated 에서 발생하는 예외를 처리한다.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected RestErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
		log.error("handleMethodArgumentNotValidException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.BAD_REQUEST)
				.message(ErrorMessage.INVALID_INPUT_VALUE.getMessage())
				.errors(exp.getBindingResult().getFieldErrors())
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * 이미지를 찾을 수 없는 경우 로그 남기는 걸 최소화 하기 위해 별도
	 * */
	@ExceptionHandler(ImageNotFound.class)
	protected RestErrorResponse handleImageNotFoundException(ImageNotFound exp) {
		log.warn("Image Notfound {}", exp.getMessage());

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(exp.getMessage())
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * API 호출시 서비스 제공하지 않거나, 존재하지 않는 API를 호출하는 경우 발생하는 예외를 처리한다.
	 * */
	@ExceptionHandler(Custom404Exception.class)
	protected RestErrorResponse handleCustom404Exception(Custom404Exception exp) {
		log.error("handleCustom404Exception", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				.message(exp.getMessage())
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * FileNotFoundException
	 * */
	@ExceptionHandler(java.io.FileNotFoundException.class)
	protected RestErrorResponse handleFileNotFoundException(java.io.FileNotFoundException exp) {
		log.error("handleFileNotFoundException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.NOT_FOUND)
				// 이때 파일 시스템의 경로를 노출하면 안된다.
				.message("파일을 찾을 수 없습니다.")
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * API 호출시 지원하지 않는 (Controller 에서 받을 수 없는)
	 * HttpMediaTypeNotSupportedException 발생하는 예외를 처리한다.
	 * */
	@ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
	protected RestErrorResponse handleHttpMediaTypeNotSupportedException(
		org.springframework.web.HttpMediaTypeNotSupportedException exp) {
		log.error("handleHttpMediaTypeNotSupportedException", exp);

		String msg = "API에 보내는 Parameter의 Content-Type이 잘못되었습니다.";

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.message(msg)
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * java.sql.SQLException
	 * */
	@ExceptionHandler(java.sql.SQLException.class)
	protected RestErrorResponse handleSQLException(java.sql.SQLException exp) {
		log.error("handleSQLException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.errorCode("CUSTOM")
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.message("요청 중 오류가 발생했습니다.")
				.build();

		return new RestErrorResponse(response);
	}

	/**
	 * 500 에러
	 * */
	@ExceptionHandler(Exception.class)
	protected RestErrorResponse handleException(Exception exp) {
		log.error("handleException", exp);

		final HttpErrorResponse response =
			HttpErrorResponse.builder()
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.message(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage())
				.build();

		return new RestErrorResponse(response);
	}

}
