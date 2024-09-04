/**
 *
 */
package moka.brunoviewer.global.exception;

import java.io.Serial;

public class FileDownloadException extends RuntimeException {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -1633203815941464970L;

	public FileDownloadException(String message) {
		super(message);
	}

	public FileDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

}
