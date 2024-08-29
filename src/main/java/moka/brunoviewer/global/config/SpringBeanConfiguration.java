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
import moka.brunoviewer.global.config.spring.MarkdownViewerProperty;
import moka.brunoviewer.global.reader.BrunoDocReader;
import moka.brunoviewer.global.reader.MarkdownFileReader;

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

	@Bean
	@ConfigurationProperties("mdreader")
	public MarkdownViewerProperty markdownViewerProperty() {
		return new MarkdownViewerProperty();
	}

	@Override
	public void setApplicationContext(@NonNull  ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet()  {
		// 빈들을 로그로 출력 - 문제확인시 주석 해제
		/*String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			log.warn("Bean name: {}", beanName);
		}*/
	}

	@Override
	public void destroy() {
		// MarkdownFileReader 객체를 종료시킨다.
		log.info("SpringBeanConfiguration destroy check");
		MarkdownFileReader markdownFileReader = applicationContext.getBean(MarkdownFileReader.class);
		markdownFileReader.shutdown();

		BrunoDocReader brunoDocReader = applicationContext.getBean(BrunoDocReader.class);
		brunoDocReader.shutdown();
	}


}
