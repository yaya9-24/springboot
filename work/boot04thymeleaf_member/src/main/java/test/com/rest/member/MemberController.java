package test.com.rest.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MemberController {

	@GetMapping("/member/insert")
	public String insert() {
		log.info("/member/insert");
		return "member/insert"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/update")
	public String update() {
		log.info("/member/update");
		return "member/update"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/delete")
	public String delete() {
		log.info("/member/delete");
		return "member/delete"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/selectAll")
	public String selectAll() {
		log.info("/member/selectAll");
		return "member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/selectOne")
	public String selectOne() {
		log.info("/member/selectOne");
		return "member/selectOne"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/insertOK")
	public String insertOK(MemberVO vo) {
		log.info("/member/insertOK");
		log.info("vo:{}",vo);
		
		return "redirect:/member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/updateOK")
	public String updateOK(MemberVO vo) {
		log.info("/member/updateOK");
		log.info("vo:{}",vo);
		
		return "redirect:/member/selectOne?num="+vo.getNum(); //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/deleteOK")
	public String deleteOK(MemberVO vo) {
		log.info("/member/deleteOK");
		log.info("vo:{}",vo);
		
		return "redirect:/member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
	//mission
	//boot04thymeleaf_member 프로젝트를 작성하세요.
	//타임리프엔진을 사용하시고 컨트롤러를 구현하세요.
	
}
