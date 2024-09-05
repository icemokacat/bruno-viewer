package moka.brunoviewer.global.model.http;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2xx 성공 시 반환 객체
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {

	private HttpStatus httpStatus;
	private String message;
	private T data;

}
