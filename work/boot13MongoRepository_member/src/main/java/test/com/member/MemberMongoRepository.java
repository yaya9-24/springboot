package test.com.member;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberMongoRepository extends MongoRepository<MemberVO, String> {

	void deleteByNum(int num);

	List<MemberVO> findAllByOrderByNumDesc(PageRequest pageable);

	List<MemberVO> findByIdContaining(String searchWord, PageRequest pageable);

	List<MemberVO> findByNameContaining(String searchWord, PageRequest pageable);

	MemberVO findByNum(int num);

	MemberVO findBy_id(ObjectId objectId);

}
