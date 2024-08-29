package test.com.member.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	//application.properties에서 설정한 변수(file.dir)를 DI
	@Value("${file.dir}")
	private String realPath;

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
	public String insertOK(MemberVO vo) throws IllegalStateException, IOException {
		log.info("/member/insertOK");
		log.info("vo:{}",vo);
		
		//스프링프레임워크에서 사용하던 리얼패스 사용불가.
		//String realPath = context.getRealPath("resources/upload_img");
		
		//@Value("${file.dir}")로 획득한 절대경로 사용해야함.
		log.info(realPath);

		String originName = vo.getFile().getOriginalFilename();
		log.info("originName:{}", originName);

		if (originName.length() == 0) {
			vo.setProfile("default.png");
		} else {
			String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
			vo.setProfile(save_name);

			File f = new File(realPath, save_name);
			vo.getFile().transferTo(f);

			//// create thumbnail image/////////
			BufferedImage original_buffer_img = ImageIO.read(f);
			BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphic = thumb_buffer_img.createGraphics();
			graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

			File thumb_file = new File(realPath, "thumb_" + save_name);

			ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);

		}
		
		int result = service.insertOK(vo);
		log.info("result:{}", result);

		return "redirect:/member/selectAll";	
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
