package moka.brunoviewer.global.component.error;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 기본 error 페이지 경로가 아닌 커스텀 error 페이지 경로로 설정
 * 및 커스텀 error 페이지에 대한 설정
 * */
@Component
public class CustomErrorViewResolver implements ErrorViewResolver {
	@Override
	public ModelAndView resolveErrorView(
			HttpServletRequest request,
			HttpStatus status,
			Map<String, Object> model) {
		String viewName = "thymeleaf/error/error";
		return new ModelAndView(viewName, model);
	}
}
