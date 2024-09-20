package test.com.testcollection;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MemoMongoRepository extends MongoRepository<MemoVO, String> {

	//네이밍 룰에 맞춰서 구현
	MemoVO findByage(int age);
	MemoVO findBy_id(ObjectId objectId);
	
	//네이밍 룰에 맞춰서 구현
	List<MemoVO> findAllByOrderByAgeDesc(PageRequest pageable);
	
	//네이밍 룰에 맞춰서 구현
	List<MemoVO> findByNameLike(String searchWord);
	List<MemoVO> findByPhoneLike(String searchWord);
	
	//네이밍 룰에 맞춰서 구현
	List<MemoVO> findByNameContaining(String searchWord, PageRequest pageable);
	List<MemoVO> findByPhoneContaining(String searchWord, PageRequest pageable);
	
	//네이밍 룰에 맞춰서 구현
	List<MemoVO> findByAgeBetween(int age1, int age2);
	
	
	//네이밍 룰없이 개발자가 자유롭게 메소드명 구현
	@Query(value = "{age:{$gte:?0,$lte:?1}}")  //age >= 3 and age <= 6
	List<MemoVO> findByAgeBetweenThen(int age1, int age2);
	
	//네이밍 룰없이 개발자가 자유롭게 메소드명 구현
	@Query(value = "{age:{$in:[?0,?1]}}")
	List<MemoVO> findByAgeOrIn(int age1, int age2);
	
	//네이밍 룰에 맞춰서 구현
	void deleteByAge(int age);
	
	//네이밍 룰없이 개발자가 자유롭게 메소드명 구현
	@Query(value = "{age:{$gte:?0}}",delete=true)
	long deleteByGteAge(int age);

}
