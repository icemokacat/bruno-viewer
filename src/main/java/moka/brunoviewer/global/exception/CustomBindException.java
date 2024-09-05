package moka.brunoviewer.global.exception;

import java.io.Serial;

public class CustomBindException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 1L;

	public CustomBindException() {
		super();
	}

	public CustomBindException(String message) {
		super(message);
	}

	public CustomBindException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomBindException(Throwable cause) {
		super(cause);
	}

	protected CustomBindException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
