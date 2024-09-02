package test.com.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private MemberService service;

	@GetMapping("/member/insert")
	public String insert() {
		log.info("/member/insert");
		return "member/insert"; //resources/templates폴더에서 찾는다.
	}
	@GetMapping("/member/update")
	public String update(Model model,MemberVO_JPA vo) {
		log.info("/member/update");
		
		MemberVO_JPA vo2 = service.selectOne(vo);
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
	public String selectAll(Model model,
			@RequestParam(defaultValue = "1")long cpage,
			@RequestParam(defaultValue = "5")long pageBlock) {
		log.info("/member/selectAll");
		
		//List<MemberVO_JPA> list = service.selectAll();
		List<MemberVO_JPA> list = service.selectAllPageBlock(cpage,pageBlock);
		log.info("list.size():{}",list.size());
		for(MemberVO_JPA vo : list) {
			log.info(vo.toString());
		}
		model.addAttribute("list",list);
		
		//총 행의 개수 얻기.
		long total_rows = service.getTotalRows(); //select count(*) total_rows from member;
		log.info("total_rows:{}",total_rows);
		
		long totalPageCount = 0; 
		
		//총 행카운트와 페이지블럭을 나눌 때의 알고리즘을 추가하기.
		if (total_rows % pageBlock == 0) {
		    totalPageCount = total_rows / pageBlock;
		} else {
		    totalPageCount = (total_rows / pageBlock) + 1;
		}
		
		model.addAttribute("totalPageCount",totalPageCount);
		return "member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
	@GetMapping("/member/searchList")
	public String searchList(Model model,
			@RequestParam(defaultValue = "id")String searchKey,
			@RequestParam(defaultValue = "5")String searchWord,
			@RequestParam(defaultValue = "1")int cpage,
			@RequestParam(defaultValue = "5")int pageBlock){
		log.info("/member/searchList");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		log.info("searchWord:{}",cpage);
		log.info("searchWord:{}",pageBlock);
				
		//List<MemberVO_JPA> list = service.searchList(searchKey,searchWord);		
		List<MemberVO_JPA> list = service.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);		
		log.info("list.size():{}",list.size());
		for(MemberVO_JPA vo : list) {
			log.info(vo.toString());
		}
		model.addAttribute("list",list);
		
		//searchWord에 따른 총 카운트 얻기
		//select count(*) total_rows from member_jpa where user_id like '%ad%';
		//총 행의 개수 얻기.
		long total_rows = service.getSearchTotalRows(searchKey,searchWord); //select count(*) total_rows from member;
		log.info("total_rows:{}",total_rows);
		
		long totalPageCount = 0; 
		
		//총 행카운트와 페이지블럭을 나눌 때의 알고리즘을 추가하기.
		if(total_rows/pageBlock==0 ||total_rows%pageBlock!=0) {
			totalPageCount = (total_rows/pageBlock) +1;
		} else {
			totalPageCount = total_rows/pageBlock;
		}
		
		model.addAttribute("totalPageCount",totalPageCount);
		return "member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
	@GetMapping("/member/selectOne")
	public String selectOne(Model model,MemberVO_JPA vo) {
		log.info("/member/selectOne");
		log.info("vo:{}",vo);
		
		MemberVO_JPA vo2 = service.selectOne(vo);
		log.info("vo2:{}",vo2);
		
		model.addAttribute("vo2",vo2);
		
		return "member/selectOne"; //resources/templates폴더에서 찾는다.
	}
	@PostMapping("/member/insertOK")
	public String insertOK(MemberVO_JPA vo) {
		log.info("/member/insertOK");
		log.info("vo:{}",vo);
		
		int result = service.insertOK(vo);
		log.info("result:{}",result);
		
		return "redirect:/member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	@PostMapping("/member/updateOK")
	public String updateOK(MemberVO_JPA vo) {
		log.info("/member/updateOK");
		log.info("vo:{}",vo);
		
		int result = service.updateOK(vo);
		log.info("result:{}",result);
		
		return "redirect:/member/selectOne?num="+vo.getNum(); //resources/templates폴더에서 찾는다.
	}
	@PostMapping("/member/deleteOK")
	public String deleteOK(MemberVO_JPA vo) {
		log.info("/member/deleteOK");
		log.info("vo:{}",vo);
		
		int result = service.deleteOK(vo);
		log.info("result:{}",result);
		
		return "redirect:/member/selectAll"; //resources/templates폴더에서 찾는다.
	}
	
}
