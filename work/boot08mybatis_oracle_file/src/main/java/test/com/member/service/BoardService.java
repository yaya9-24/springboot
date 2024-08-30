package test.com.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import test.com.member.mapper.BoardMapper;
import test.com.member.model.BoardVO;

@Slf4j
@Service
public class BoardService {

	@Autowired
	BoardMapper mapper;

	public List<BoardVO> selectAll() {
		return mapper.selectAll();
	}

	public BoardVO selectOne(BoardVO vo) {

		return mapper.selectOne(vo);
	}

	public List<BoardVO> searchList(String searchKey, String searchWord) {
		if (searchKey.equals("title")) {
			return mapper.searchListTitle("%" + searchWord + "%");
		} else{
			return mapper.searchListContent("%" + searchWord + "%");
		}
	}

	public int insertOK(BoardVO vo) {

		return mapper.insertOK(vo);
	}

	public int updateOK(BoardVO vo) {
		return mapper.updateOK(vo);
	}

	public int deleteOK(BoardVO vo) {
		return mapper.deleteOK(vo);
	}

	public int getTotalRows() {
		
		return mapper.getTotalRows();
	}

	public List<BoardVO> selectAllPageBlock(int cpage, int pageBlock) {
		int startRow = 1+pageBlock*(cpage-1);
		int endRow = pageBlock*cpage;
		log.info("startRow:{}",startRow);
		log.info("endRow:{}",endRow);
				
		return mapper.selectAllPageBlock(startRow,endRow);
	}

	public List<BoardVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		int startRow = 1+pageBlock*(cpage-1);
		int endRow = pageBlock*cpage;
		log.info("startRow:{}",startRow);
		log.info("endRow:{}",endRow);
		
		
		if(searchKey.equals("title")) {
			return mapper.searchListPageBlockTitle("%"+searchWord+"%",startRow,endRow);
		}else {
			return mapper.searchListPageBlockContent("%"+searchWord+"%",startRow,endRow);
		}
	}

	public int getSearchTotalRows(String searchKey, String searchWord) {
		if(searchKey.equals("title")) {
			return mapper.getSearchTotalRowsTitle("%"+searchWord+"%");
		}else {
			return mapper.getSearchTotalRowsContent("%"+searchWord+"%");
		}
		
	}
}
