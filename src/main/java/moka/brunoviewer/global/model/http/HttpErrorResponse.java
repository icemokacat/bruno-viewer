package moka.brunoviewer.global.model.http;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpErrorResponse {

	private HttpStatus httpStatus;
	private String errorCode;
	private Map<String,String> errorResult;
	private String message;
	private List<FieldError> errors;

}

