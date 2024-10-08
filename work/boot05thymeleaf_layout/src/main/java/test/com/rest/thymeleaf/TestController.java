package test.com.rest.thymeleaf;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class TestController {

	@GetMapping("/test/insert")
	public String insert() {
		log.info("/test/insert");
		return "test/insert"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/update")
	public String update() {
		log.info("/test/update");
		return "test/update"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/delete")
	public String delete() {
		log.info("/test/delete");
		return "test/delete"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/selectAll")
	public String selectAll() {
		log.info("/test/selectAll");
		return "test/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/selectOne")
	public String selectOne() {
		log.info("/test/selectOne");
		return "test/selectOne"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/insertOK")
	public String insertOK(TestVO vo) {
		log.info("/test/insertOK");
		log.info("vo:{}",vo);
		
		return "redirect:/test/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/updateOK")
	public String updateOK(TestVO vo) {
		log.info("/test/updateOK");
		log.info("vo:{}",vo);
		
		return "redirect:/test/selectOne?num="+vo.getNum(); //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/test/deleteOK")
	public String deleteOK(TestVO vo) {
		log.info("/test/deleteOK");
		log.info("vo:{}",vo);
		
		return "redirect:/test/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
	//mission
	//boot04thymeleaf_member 프로젝트를 작성하세요.
	//타임리프엔진을 사용하시고 컨트롤러를 구현하세요.
	
}
