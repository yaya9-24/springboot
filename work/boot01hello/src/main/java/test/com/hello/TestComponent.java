package test.com.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestComponent implements ApplicationRunner{

	@Autowired
	UserComponent userCom;
	
	public TestComponent () {
		log.info("TestComponent()...");
		log.info("userCom:{}",userCom);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userCom.start();
		log.info("userCom:{}",userCom);
		
	}
}
