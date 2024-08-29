package test.com.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.member.mapper.BoardMapper;
import test.com.member.model.BoardVO;

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
}
