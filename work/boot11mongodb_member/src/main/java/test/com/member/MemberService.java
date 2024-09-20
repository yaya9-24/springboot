package test.com.member;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO dao;

	public long insertOne(MemberVO vo) {
		return dao.insertOne(vo);
	}
	
	public long updateOne(MemberVO vo) {
		return dao.updateOne(vo);
	}
	
	public long deleteOne(MemberVO vo) {
		return dao.deleteOne(vo);
	}

	public List<MemberVO> findAll(int page, int limit) {
		return dao.findAll(page,limit);
	}

	public List<MemberVO> searchList(String searchKey, String searchWord, int page, int limit) {
		return dao.searchList(searchKey,searchWord,page,limit);
	}

	public MemberVO findOne(MemberVO vo) {
		return dao.findOne(vo);
	}
}
