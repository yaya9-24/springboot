package test.com.member;

import java.util.List;

public interface MemberDAO {

	public long insertOne(MemberVO vo);
	
	public long updateOne(MemberVO vo);
	
	public long deleteOne(MemberVO vo);
	
	public List<MemberVO> findAll(int page,int limit);
	
	public List<MemberVO> searchList(String searchKey,String searchWord,int page,int limit);
	
	public MemberVO findOne(MemberVO vo);
	
}
