package moka.brunoviewer.global.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@CrossOrigin(origins = "*")
public class WebErrorController extends BasicErrorController {

	public WebErrorController(ErrorAttributes errorAttributes,
								ServerProperties serverProperties,
								List<ErrorViewResolver> errorViewResolvers) {
		super(errorAttributes, serverProperties.getError(), errorViewResolvers);
	}

	@Override
	@ResponseBody
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		log.error("Handling error with status code: {}", status);
		//log.error("Request URI: {}", request.getRequestURI());
		log.error("Request URI: {}", getRequestURI(request));

		// Return view name based on the error status
		// if view-name is not set, default error view will be returned
		String viewName = "thymeleaf/error/error";
		return new ModelAndView(viewName, createErrorModelMap(request, status));
	}

	private Map<String, Object> createErrorModelMap(HttpServletRequest request, HttpStatus status) {

		String msg = getKorHttpStatusMessage(status);
		String requestURI = getRequestURI(request);

		Map<String, Object> model = new HashMap<>();
		model.put("status"	, status.value());
		model.put("message"	, msg);
		model.put("path"	, requestURI);

		//model.put("error"	, status.getReasonPhrase());

		return model;
	}

	private String getRequestURI(HttpServletRequest request){
		Object forwardUrl = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
		Object errorRequestUrl;
		if(forwardUrl == null){
			errorRequestUrl = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
		}
		else{
			errorRequestUrl = forwardUrl;
		}
		return errorRequestUrl == null ? null : errorRequestUrl.toString();
	}

	private String getKorHttpStatusMessage(HttpStatus status){
		return switch (status) {
			case BAD_REQUEST -> "잘못된 요청입니다.";
			case UNAUTHORIZED -> "인증이 필요합니다.";
			case FORBIDDEN -> "접근이 금지되었습니다.";
			case NOT_FOUND -> "요청한 페이지를 찾을 수 없습니다.";
			case METHOD_NOT_ALLOWED -> "허용되지 않은 메소드입니다.";
			case INTERNAL_SERVER_ERROR -> "서버에 오류가 발생하였습니다.";
			case SERVICE_UNAVAILABLE -> "서비스를 사용할 수 없습니다.";
			default -> "알 수 없는 오류가 발생하였습니다.";
		};
	}

}
