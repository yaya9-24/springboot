package test.com.testcollection;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*
 * 스프링 부트에서 몽고디비를 연동할 때 MongoRepository와 MongoTemplate는 각각 다른 방식으로 데이터베이스와 상호작용합니다.

1. **MongoRepository:**
   - MongoRepository는 Spring Data MongoDB 모듈에서 제공하는 인터페이스입니다.
   - Spring Data MongoDB는 데이터베이스와의 상호작용을 위한 고수준 추상화를 제공합니다.
   - MongoRepository를 사용하면 데이터베이스 작업에 필요한 CRUD(Create, Read, Update, Delete) 메서드를 
   		직접 구현할 필요 없이 인터페이스를 확장하여 사용할 수 있습니다.
   - 예를 들어, `CrudRepository`나 `PagingAndSortingRepository` 인터페이스를 상속하여 
   		Repository 인터페이스를 정의하고, 해당 인터페이스를 구현하면 데이터베이스에 대한 CRUD 작업을 수행할 수 있습니다.

2. **MongoTemplate:**
   - MongoTemplate은 몽고디비에 대한 저수준의 접근을 제공하는 클래스입니다.
   - 이 클래스를 사용하면 직접 쿼리를 작성하여 데이터베이스와 상호작용할 수 있습니다.
   - 데이터베이스에 대한 세부적인 제어가 필요할 때 주로 사용됩니다. 예를 들어, 복잡한 질의를 수행하거나 
   		직접 쿼리를 작성해야 하는 경우에 유용합니다.
   - MongoTemplate을 사용하면 몽고디비의 모든 기능을 활용할 수 있지만, 직접 쿼리를 작성해야 하므로 
   		개발자가 쿼리를 작성하는 부분에 대한 책임이 있습니다.

	따라서 MongoRepository는 데이터베이스 작업을 추상화하고 간단하게 사용할 수 있도록 도와주는 반면, 
	MongoTemplate은 좀 더 저수준의 접근을 제공하여 개발자가 직접 데이터베이스에 대한 제어를 할 수 있습니다. 
	선택은 프로젝트의 요구사항과 개발자의 선호도에 따라 달라집니다.
 * */

@Service
public class MemoService  {

	@Autowired
	MemoDAO dao;
	
	@Autowired
	MemoMongoRepository mongoRepo;
	
	public long insertOne(MemoVO vo) {
		//주의 : 내장메소드 save()의 인자객체 속성중 _id값이 있으면 수정, 없으면 입력
		return mongoRepo.save(vo).getAge();
	}

	
	public long insertMany() {
		List<MemoVO> vos = new ArrayList<MemoVO>();
		for (int i = 0; i < 5; i++) {
			MemoVO vo = new MemoVO();
			vo.setAge(106+i);
			vo.setName("lee"+i);
			vo.setOffice("samsung"+i);
			vo.setPhone("011"+i);
			vos.add(vo);
		}
		return mongoRepo.saveAll(vos).size();
	}

	
	public long updateOne(MemoVO vo) {
		//주의 : 내장메소드 save()의 인자객체 속성중 _id값이 있으면 수정, 없으면 입력
		vo.set_id(vo.getMid());
		return mongoRepo.save(vo).getAge();
	}

	//한 번에 여러 개 수정하는 것은 기존의 mongoTemplate을 사용하는 것이 일반적이다.
	public long updateMany(MemoVO vo) {
		return dao.updateMany(vo);
	}

	
	public long deleteOne(MemoVO vo) {
		if(vo.getMid()==null) {
			mongoRepo.deleteByAge(vo.getAge()); //네이밍 룰
		}else {
			//mid를 _id에 재할당
			vo.set_id(vo.getMid());
			mongoRepo.delete(vo); //내장 메소드
		}

		return 1;
	}

	
	public long deleteMany(MemoVO vo) {
		return mongoRepo.deleteByGteAge(vo.getAge()); //@Query 사용
	}

	
	public List<MemoVO> findAll() {
//		return mongoRepo.findAll(); //내장 메소드, 정렬없음.
		
		//정렬이 필요하면 아래처럼
		return mongoRepo.findAll(Sort.by(Sort.Direction.DESC,"age"));
	}

	
	public List<MemoVO> findAll2(int page, int limit) {
		//페이징 처리객체 PageRequest
		//주의 : PageRequest에서 첫페이지는 0이다. 따라서 변수page-1 처리가 필요하다.
		PageRequest pageable = PageRequest.of(page-1, limit,Sort.by(Sort.Direction.DESC,"age"));
		return mongoRepo.findAllByOrderByAgeDesc(pageable); //네이밍 룰
	}

	
	public List<MemoVO> searchList(String searchKey, String searchWord) {
		if(searchKey.equals("name")) {
			return mongoRepo.findByNameLike(searchWord); //네이밍 룰
		}else {
			return mongoRepo.findByPhoneLike(searchWord); //네이밍 룰
		}
		
	}

	
	public List<MemoVO> searchList2(String searchKey, String searchWord, int page, int limit) {
		
		PageRequest pageable = PageRequest.of(page-1, limit,Sort.by(Sort.Direction.DESC,"age"));
		if(searchKey.equals("name")) {
			return mongoRepo.findByNameContaining(searchWord,pageable); //네이밍 룰
		}else {
			return mongoRepo.findByPhoneContaining(searchWord,pageable); //네이밍 룰
		}
	}

	
	public MemoVO findOne(MemoVO vo) {
		if(vo.getMid()==null) {
			return mongoRepo.findByage(vo.getAge()); //내장메소드 아님, 네이밍 룰에 맞춰서 커스텀 필요
		}else {
			return mongoRepo.findBy_id(new ObjectId(vo.getMid())); //내장메소드 아님, 네이밍 룰에 맞춰서 커스텀 필요
		}
		
	}

	public List<MemoVO> searchList3(int age1, int age2) {
//		return mongoRepo.findByAgeBetween(age1,age2); //네이밍 룰 , age>3 and age<6 : 오라클 between과 다르다.
		return mongoRepo.findByAgeBetweenThen(age1,age2); //사용자정의 @Query, age>=3 and age<=6 : 오라클 between
	}


	public List<MemoVO> searchList4(int age1, int age2) {
		return mongoRepo.findByAgeOrIn(age1,age2);
	}

}
