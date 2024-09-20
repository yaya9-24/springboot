package test.com.member;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberRestController {
	
	@Autowired
	MemberService service;

	
	@GetMapping("/findAll.do")
	public List<MemberVO> findAll(@RequestParam(defaultValue = "1")int page,
			@RequestParam(defaultValue = "3")int limit) {
		log.info("/findAll.do");
		
		List<MemberVO> list = service.findAll(page,limit);
		log.info("list.size():{}",list.size());
		
		return list;
	}
	
	
	@GetMapping("/searchList.do")
	public List<MemberVO> searchList(@RequestParam(defaultValue = "id")String searchKey,
			@RequestParam(defaultValue = "ad")String searchWord,
			@RequestParam(defaultValue = "1")int page,
			@RequestParam(defaultValue = "3")int limit) {
		log.info("/searchList.do");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		
		List<MemberVO> list = service.searchList(searchKey,searchWord,page,limit);
		log.info("list.size():{}",list.size());
		
		return list;
	}
	
	
	@GetMapping("/findOne.do")
	public MemberVO findOne(MemberVO vo) {
		log.info("/findOne.do");
		
		MemberVO vo2 = service.findOne(vo);
		log.info("vo2:{}",vo2);
		
		return vo2;
	}
	
	
	@GetMapping("/insertOneOK.do")
	public Map<String,Long> insertOneOK(MemberVO vo) {
		log.info("/insertOneOK.do");
		
		long result = service.insertOne(vo);
		log.info("result:{}",result);
		
		Map<String,Long> map = new HashMap<String, Long>();
		map.put("result", result);
		
		return map;
	}
	
	
	@GetMapping("/updateOneOK.do")
	public Map<String,Long> updateOneOK(MemberVO vo) {
		log.info("/updateOneOK.do");
		
		long result = service.updateOne(vo);
		log.info("result:{}",result);
		
		Map<String,Long> map = new HashMap<String, Long>();
		map.put("result", result);
		
		return map;
	}
	
	
	@GetMapping("/deleteOneOK.do")
	public Map<String,Long> deleteOneOK(MemberVO vo) {
		log.info("/deleteOneOK.do");
		
		long result = service.deleteOne(vo);
		log.info("result:{}",result);
		
		Map<String,Long> map = new HashMap<String, Long>();
		map.put("result", result);
		
		return map;
	}
}
