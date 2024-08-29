package test.com.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import test.com.member.model.MemberVO;
import test.com.member.service.MemberService;


@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	MemberService service;

	@GetMapping("/member/insert")
	public String insert() {
		log.info("/member/insert");
		return "member/insert"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/update")
	public String update(MemberVO vo,Model model) {
		log.info("/member/update");
		log.info("vo:{}",vo);
		
		MemberVO vo2 = service.selectOne(vo);
		log.info("vo2:{}",vo2);
		
		model.addAttribute("vo2",vo2);
		return "member/update"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/delete")
	public String delete() {
		log.info("/member/delete");
		return "member/delete"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/selectAll")
	public String selectAll(Model model) {
		log.info("/member/selectAll");
		
		List<MemberVO> list = service.selectAll();
		log.info("list.size():{}",list.size());
		
		model.addAttribute("list",list);
		
		return "member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
	@GetMapping("/member/searchList")
	public String searchList(Model model,
			@RequestParam(defaultValue = "id")String searchKey,
			@RequestParam(defaultValue = "ad")String searchWord) {
		log.info("/member/searchList");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		
		List<MemberVO> list = service.searchList(searchKey,searchWord);
		log.info("list.size():{}",list.size());
		
		model.addAttribute("list",list);
		
		return "member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/selectOne")
	public String selectOne(MemberVO vo,Model model) {
		
		log.info("/member/selectOne");
		log.info("vo:{}",vo);
		MemberVO vo2 = service.selectOne(vo);
		log.info("vo2:{}",vo2);
		
		model.addAttribute("vo2",vo2);
		
		return "member/selectOne"; //resources/templates폴더에서 찾는다.
	}
	@PostMapping("/member/insertOK")
	public String insertOK(MemberVO vo) {
		log.info("/member/insertOK");
		log.info("vo:{}",vo);
		
		int result = service.insertOK(vo);
		log.info("result:{}",result);
		if(result==1) {
			return "redirect:/member/selectAll"; 
		}else {
			return "redirect:/member/insert"; 
		}		
	}
	@PostMapping("/member/updateOK")
	public String updateOK(MemberVO vo) {
		log.info("/member/updateOK");
		log.info("vo:{}",vo);
		
		int result = service.updateOK(vo);
		log.info("result:{}",result);
		if(result==1) {
			return "redirect:/member/selectOne?num="+vo.getNum();
		}else {
			return "redirect:/member/update?num="+vo.getNum(); 
		}
	}
	@PostMapping("/member/deleteOK")
	public String deleteOK(MemberVO vo) {
		log.info("/member/deleteOK");
		log.info("vo:{}",vo);
		
		int result = service.deleteOK(vo);
		log.info("result:{}",result);
		if(result==1) {
			return "redirect:/member/selectAll";
		}else {
			return "redirect:/member/delete?num="+vo.getNum(); 
		}
		
	}
	
	//mission
	//boot04thymeleaf_member 프로젝트를 작성하세요.
	//타임리프엔진을 사용하시고 컨트롤러를 구현하세요.
	
}
