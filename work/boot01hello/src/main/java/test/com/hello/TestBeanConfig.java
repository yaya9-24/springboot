package test.com.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBeanConfig {

	@Bean
	public TestBean testBean() {
		return new TestBean();
	}
	
	//Person-(name,age세터생성)을 bean등록 해보세요
	@Bean
	public Person person() {
		Person p = new Person();
		p.setName("kim");
		p.setAge(33);
		return p;
	}
}
