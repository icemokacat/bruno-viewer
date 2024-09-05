package moka.brunoviewer.global.model.http;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.NonNull;

/**
 * Rest API 반환 객체
 * 2xx httpStatus 시 반환 객체
 * 성공한 경우에만 사용
 * */
public class RestResponse extends ResponseEntity<HttpResponse<?>> {

	public RestResponse(HttpResponse body, HttpStatus status) {
		super(body, status);
	}

	public RestResponse(Object resultData) {
		super(HttpResponse.builder()
			.httpStatus(HttpStatus.OK)
			.message("OK")
			.data(resultData)
			.build(), HttpStatus.OK);
	}

	public RestResponse(Object resultData, HttpHeaders headers) {
		super(HttpResponse.builder()
			.httpStatus(HttpStatus.OK)
			.message("OK")
			.data(resultData)
			.build(), headers, HttpStatus.OK);
	}

	public static RestResponse cacheResponse(Object resultData,@NonNull CacheControl cacheControl) {
		ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
		builder.cacheControl(cacheControl);
		return new RestResponse(resultData, builder.build().getHeaders());
	}

	public static RestResponse OK() {
		// no data return
		HttpResponse<?> response = HttpResponse.builder()
			.httpStatus(HttpStatus.OK)
			.message(HttpStatus.OK.getReasonPhrase())
			.build();
		return new RestResponse(response, HttpStatus.OK);
	}

	public static RestResponse NoContent(){
		// no data return
		HttpResponse<?> response = HttpResponse.builder()
			.httpStatus(HttpStatus.NO_CONTENT)
			.message(HttpStatus.NO_CONTENT.getReasonPhrase())
			.build();
		return new RestResponse(response, HttpStatus.NO_CONTENT);
	}

}
