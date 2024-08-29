package test.com.member.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberVO {

	private int num;
	private String id;
	private String pw;
	private String name;
	private String tel;
	private String profile;
	private MultipartFile file;
}
