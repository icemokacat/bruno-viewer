package moka.brunoviewer.global.exception;

import java.io.Serial;

import lombok.Getter;

public class FileUploadException extends RuntimeException {

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = -1633203815941464970L;

	@Getter
	public enum UploadErrorCode {

		MAX_FILE_SIZE_EXCEEDED("업로드 가능 용량을 초과하였습니다."),
		NOT_ALLOWED_FILE_EXTENSION("허용되지 않는 확장자입니다."),
		FILE_NOT_FOUND("파일을 찾을 수 없습니다."),
		FILE_NOT_WRITABLE("파일을 저장할 수 없습니다."),
		FILE_NOT_READABLE("파일을 읽을 수 없습니다."),
		ONLY_IMAGE_FILE_ALLOWED("이미지 파일만 업로드 가능합니다.");

		final String message;

		UploadErrorCode(String message) {
			this.message = message;
		}

	}

	public UploadErrorCode errorCode;

	public FileUploadException(UploadErrorCode errorCode) {
		super(errorCode.message);
		this.errorCode = errorCode;
	}

	public FileUploadException(UploadErrorCode errorCode, Throwable cause) {
		super(errorCode.message, cause);
		this.errorCode = errorCode;
	}

	public FileUploadException(String message) {
		super(message);
	}

	public UploadErrorCode getUploadErrorCode() {
		return errorCode;
	}

}
