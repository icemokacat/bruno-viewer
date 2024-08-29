package moka.brunoviewer.global.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 *  Spring Application Context 환경변수 유틸리티
 * */
@Component
@RequiredArgsConstructor
public class EnvironmentUtil {

	private final Environment env;

	/**
	 *  환경변수 키값으로 환경변수 값을 가져온다.
	 * */
	public String get(String key) {
		return env.getProperty(key);
	}

	/**
	 * active profile을 가져온다.
	 * */
	public String getActiveProfile() {
		// 하나만 존재한다고 가정
		return env.getActiveProfiles()[0];
	}

	/**
	 * local 환경인지 판단
	 * */
	public boolean isLocal() {
		return "local".equals(getActiveProfile());
	}

	/**
	 * develop 환경인지 판단
	 * */
	public boolean isDevelop() {
		return "dev".equals(getActiveProfile());
	}

	/**
	 * product 환경인지 판단
	 * */
	public boolean isProduct() {
		return "prd".equals(getActiveProfile());
	}

	/**
	 * context path 를 가져온다.
	 * */
	public String getContextPath() {
		// 가져올때 active profile 에 따른 context path 를 가져온다.
		// global 에 설정된 context path 를 가져온다.
		String propKey = "global" + ".server.servlet.context-path";
		String contextPath = env.getProperty(propKey);
		contextPath = contextPath == null ? "" : contextPath;
		return contextPath;
	}

	/**
	 * FileUpload Global Dir
	 * */
	public String getFileUploadRootDir() {
		return env.getProperty("file.upload.path");
	}

}