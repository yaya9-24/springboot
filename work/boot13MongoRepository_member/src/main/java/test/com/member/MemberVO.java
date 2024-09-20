package test.com.member;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "member")
@Data
public class MemberVO {
	
	private String mid;
	@Id
	private String _id;
	private int num;
	private String id;
	private String pw;
	private String name;
	private String tel;
}
