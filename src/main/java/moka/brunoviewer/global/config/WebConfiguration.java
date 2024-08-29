package moka.brunoviewer.global.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	/**
	 * 정적 리소스를 처리하기 위한 핸들러를 등록한다.
	 * */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// 캐쉬 설정
		// 실제 운영 시작 후 일정 안정화 기간 이후, 캐쉬 설정 20초로 변경 예정
		int cachePeriod = (int)TimeUnit.SECONDS.toSeconds(3);

		// 정적 리소스를 처리하기 위한 핸들러를 등록한다.
		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/")
			.setCachePeriod(cachePeriod);

	}
}
