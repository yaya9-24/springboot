package test.com.hello;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Person {
	
	
	private String name;
	private int age;
	
	public Person () {
		log.info("Person()...");
	}
}
