package test.com.member;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO dao;
	
	@Autowired
	MemberMongoRepository mongoRepo;

	public long insertOne(MemberVO vo) {
		
		return mongoRepo.save(vo).getNum();
	}
	
	public long updateOne(MemberVO vo) {
		vo.set_id(vo.getMid());
		return mongoRepo.save(vo).getNum();
	}
	
	public long deleteOne(MemberVO vo) {
		if(vo.getMid()==null) {
			mongoRepo.deleteByNum(vo.getNum());
		}else {
			vo.set_id(vo.getMid());
			mongoRepo.delete(vo);
		}		
		return 1;
	}

	public List<MemberVO> findAll(int page, int limit) {
		PageRequest pageable = PageRequest.of(page-1,limit,Sort.by(Sort.Direction.DESC,"num"));
		return mongoRepo.findAllByOrderByNumDesc(pageable);
	}

	public List<MemberVO> searchList(String searchKey, String searchWord, int page, int limit) {
		PageRequest pageable = PageRequest.of(page-1,limit,Sort.by(Sort.Direction.DESC,"num"));
		if(searchKey.equals("id")) {
			return mongoRepo.findByIdContaining(searchWord,pageable);
		}else {
			return mongoRepo.findByNameContaining(searchWord,pageable);
		}
		
	}

	public MemberVO findOne(MemberVO vo) {
		if(vo.getMid()==null) {
			return mongoRepo.findByNum(vo.getNum());
		}else {
			return mongoRepo.findBy_id(new ObjectId(vo.getMid()));
		}
		
	}
}
