package test.com.testcollection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "testcollection")
@Data
public class MemoVO {
	
	private String mid;
	private String _id;
	private int age;
	private String name;
	private String office;
	private String phone;

}
