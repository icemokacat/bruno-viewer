package moka.brunoviewer.global.exception;

import java.io.Serial;
import java.util.Map;

import lombok.Getter;

@Getter
public class CustomIllegalArgumentException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 1L;

	private Map<String,String> errorMap;

	public CustomIllegalArgumentException() {
		super();
	}

	public CustomIllegalArgumentException(String message) {
		super(message);
	}

	public CustomIllegalArgumentException(Map<String,String> errorMap) {
		super();
		this.errorMap = errorMap;
	}

	public CustomIllegalArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomIllegalArgumentException(Throwable cause) {
		super(cause);
	}

	protected CustomIllegalArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}
}
