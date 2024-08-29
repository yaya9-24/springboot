package test.com.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.member.mapper.MemberMapper;
import test.com.member.model.MemberVO;

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
}
