package moka.brunoviewer.global.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import moka.brunoviewer.global.config.spring.BrunoProperty;

/**
 * Spring bean 추가 등록을 위한 class
 * */
@Slf4j
@Configuration
public class SpringBeanConfiguration implements ApplicationContextAware, InitializingBean, DisposableBean {

	private ApplicationContext applicationContext;

	@Bean
	@ConfigurationProperties("bruno")
	public BrunoProperty brunoProperty() {
		return new BrunoProperty();
	}

	@Override
	public void setApplicationContext(@NonNull  ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet()  {
		// bean 등록이 완료되면 실행
	}

	@Override
	public void destroy() {
		// bean 소멸시 실행
	}


}
