package test.com.member.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {
	private int num;
    private String title;
    private String content;
    private String writer;
    private Date wdate;
    private String img_name;
    private MultipartFile file;
}
