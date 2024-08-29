package test.com.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import test.com.member.model.MemberVO;

@Mapper
public interface MemberMapper {
	
	//추상메소드명(예:insertOK)이 sqlMapper_*.xml 문서의 id와 같아야한다.
	public int insertOK(MemberVO vo);
	
	public List<MemberVO> selectAll();

	public int updateOK(MemberVO vo);

	public MemberVO selectOne(MemberVO vo);

	public int deleteOK(MemberVO vo);

	public List<MemberVO> searchListId(String searchWord);

	public List<MemberVO> searchListName(String searchWord);
}
