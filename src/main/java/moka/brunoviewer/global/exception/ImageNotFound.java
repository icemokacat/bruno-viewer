package moka.brunoviewer.global.exception;

public class ImageNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ImageNotFound() {
		super();
	}

	public ImageNotFound(String message) {
		super(message);
	}

	public ImageNotFound(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageNotFound(Throwable cause) {
		super(cause);
	}

	protected ImageNotFound(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}
}
