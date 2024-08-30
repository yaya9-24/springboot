package test.com.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import test.com.member.mapper.MemberMapper;
import test.com.member.model.MemberVO;

@Slf4j
@Service
public class MemberService {

	
	@Autowired
	MemberMapper mapper;
	
	public int insertOK(MemberVO vo) {
		return mapper.insertOK(vo);
	}
	
	public List<MemberVO> selectAll(){
		return mapper.selectAll();
	}
	
	public int updateOK(MemberVO vo) {
		return mapper.updateOK(vo);
	}

	public MemberVO selectOne(MemberVO vo) {
		return mapper.selectOne(vo);
	}
	
	public int deleteOK(MemberVO vo) {
		return mapper.deleteOK(vo);
	}

	public List<MemberVO> searchList(String searchKey, String searchWord) {
		if(searchKey.equals("id")) {
			return mapper.searchListId("%"+searchWord+"%");
		}else {
			return mapper.searchListName("%"+searchWord+"%");
		}
		
	}

	public int getTotalRows() {
		
		return mapper.getTotalRows();
	}

	public List<MemberVO> selectAllPageBlock(int cpage, int pageBlock) {
		//오라클인 경우 rownum으로 읽어 올 시작행과 끝행을 얻어내는 알고리즘이 필요하다.
		//예: 1페이지(1-5), 2페이지(6-10),3페이지(11-15)
		int startRow = 1+pageBlock*(cpage-1);
		int endRow = pageBlock*cpage;
		log.info("startRow:{}",startRow);
		log.info("endRow:{}",endRow);
		
		return mapper.selectAllPageBlock(startRow,endRow);
	}

	public int getSearchTotalRows(String searchKey, String searchWord) {
		if(searchKey.equals("id")) {
			return mapper.getSearchTotalRowsId("%"+searchWord+"%");
		}else {
			return mapper.getSearchTotalRowsName("%"+searchWord+"%");
		}
		
	}

	public List<MemberVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		
		int startRow = 1+pageBlock*(cpage-1);
		int endRow = pageBlock*cpage;
		log.info("startRow:{}",startRow);
		log.info("endRow:{}",endRow);
		
		
		if(searchKey.equals("id")) {
			return mapper.searchListPageBlockId("%"+searchWord+"%",startRow,endRow);
		}else {
			return mapper.searchListPageBlockName("%"+searchWord+"%",startRow,endRow);
		}
	}
}
