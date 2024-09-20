package test.com.testcollection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TestWebConfig implements WebMvcConfigurer{

	//welcome file을 지정 메소드 추가가능 - http://localhost:8801/ <<<< index.html
	//static/index.html파일 생성
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("forward:/index.html");
	}
}
