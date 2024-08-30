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
import test.com.member.model.BoardVO;
import test.com.member.service.BoardService;

@Slf4j
@Controller
public class BoardController {

	@Autowired
	BoardService service;

	@Value("${file.dir}")
	private String realPath;
	
	
	@GetMapping("/board/insert")
	public String insert() {
		log.info("insert()...");

		return "board/insert";
	}

	@GetMapping("/board/update")
	public String update(BoardVO vo, Model model) {
		log.info("update()...");

		BoardVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);
		return "board/update";
	}

	@GetMapping("/board/delete")
	public String delete() {
		log.info("delete()...");

		return "board/delete";
	}

	@GetMapping("/board/selectAll")
	public String selectAll(Model model
			,@RequestParam(defaultValue = "1")int cpage
			,@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("/board/selectAll");
		log.info("cpage:{}",cpage);
		log.info("pageBlock:{}",pageBlock);
		
		//List<BoardVO> list = service.selectAll();
		List<BoardVO> list = service.selectAllPageBlock(cpage,pageBlock);		
		log.info("list.size():{}",list.size());

		model.addAttribute("list", list);
		
		int total_rows = service.getTotalRows();
		log.info("total_rows:{}",total_rows);
		
		int totalPageCount;
		if(total_rows/pageBlock==0 ||total_rows%pageBlock!=0) {
			totalPageCount = (total_rows/pageBlock) +1;
		} else {
			totalPageCount = total_rows/pageBlock;
		}
		
		model.addAttribute("totalPageCount",totalPageCount);
		

		return "board/selectAll";
	}

	@GetMapping("/board/selectOne")
	public String selectOne(BoardVO vo, Model model) {
		log.info("/board/selectOne");

		BoardVO vo2 = service.selectOne(vo);
		log.info("vo2:{}", vo2);

		model.addAttribute("vo2", vo2);
		return "board/selectOne";
	}

	@GetMapping("/board/searchList")
	public String searchList(Model model,
			@RequestParam(defaultValue = "title") String searchKey,
			@RequestParam(defaultValue = "g") String searchWord,
			@RequestParam(defaultValue = "1")int cpage,
			@RequestParam(defaultValue = "5")int pageBlock) {
		log.info("/board/searchList");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		log.info("cpage:{}",cpage);
		log.info("pageBlock:{}",pageBlock);
		
		

		List<BoardVO> list = service.searchListPageBlock(searchKey, searchWord,cpage,pageBlock);
		log.info("list.size():{}",list.size());
		
		model.addAttribute("list", list);
		
		int total_rows = service.getSearchTotalRows(searchKey,searchWord);
		log.info("total_rows:{}",total_rows);
		
		int totalPageCount;
		if(total_rows/pageBlock==0 ||total_rows%pageBlock!=0) {
			totalPageCount = (total_rows/pageBlock) +1;
		} else {
			totalPageCount = total_rows/pageBlock;
		}
		
		model.addAttribute("totalPageCount",totalPageCount);

		return "board/selectAll";
	}

	@PostMapping("/board/insertOK")
	public String insertOK(BoardVO vo) throws IllegalStateException, IOException {
		log.info("/board/insertOK");
		log.info("{}", vo);

		log.info(realPath);

		String originName = vo.getFile().getOriginalFilename();
		log.info("originName:{}", originName);

		if (originName.length() == 0) {
			vo.setImg_name("default.png");
		} else {
			String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
			vo.setImg_name(save_name);

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

		if (result == 1) {
			return "redirect:/board/selectAll";
		} else {
			return "redirect:/board/insert";
		}
	}

	@PostMapping("/board/updateOK")
	public String updateOK(BoardVO vo) throws IllegalStateException, IOException {
		log.info("/board/updateOK");
		log.info("{}", vo);

		log.info(realPath);

		String originName = vo.getFile().getOriginalFilename();
		log.info("originName:{}", originName);

		if (originName.length() == 0) {
			vo.setImg_name(vo.getImg_name());
		} else {
			String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
			vo.setImg_name(save_name);

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
		
		int result = service.updateOK(vo);
		log.info("result:{}", result);

		if (result == 1) {
			return "redirect:/board/selectOne?num=" + vo.getNum();
		} else {
			return "redirect:/board/update?num=" + vo.getNum();
		}
	}

	@PostMapping("/board/deleteOK")
	public String deleteOK(BoardVO vo) {
		log.info("/board/deleteOK");
		log.info("{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);

		if (result == 1) {
			return "redirect:/board/selectAll";
		} else {
			return "redirect:/board/delete?num=" + vo.getNum();
		}
	}
}// end class
