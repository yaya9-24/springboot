package test.com.testcollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemoDAOimpl implements MemoDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public long insertOne(MemoVO vo) {
		log.info("insertOne()....");
		log.info("vo:{}", vo);
		
		long flag = 0;
		
		MemoVO vo2 = mongoTemplate.insert(vo);
		log.info("vo2:{}", vo2);
		if(vo2.get_id()!=null) flag = 1;
		
		return flag;
	}

	@Override
	public long insertMany() {
		log.info("insertMany()....");
		
		long flag = 0;
		
		try {

			List<MemoVO> vos = new ArrayList<MemoVO>();
			for (int i = 0; i < 5; i++) {
				MemoVO vo = new MemoVO();
				vo.setAge(106+i);
				vo.setName("lee"+i);
				vo.setOffice("samsung"+i);
				vo.setPhone("011"+i);
				vos.add(vo);
			}
			
			flag = mongoTemplate.insert(vos,MemoVO.class).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public long updateOne(MemoVO vo) {
		log.info("updateOne()....");
		log.info("vo:{}", vo);
		
		long flag = 0;
		
		try {
			// ~~~.find({age:102})
			BasicQuery query = new BasicQuery(String.format("{age:%d}",vo.getAge()));
			
			// ~~~.find({_id:ObjectId("6397fa8388a6555310a4c729")})
			//24HexString이 null값일 때는 대체값이 필요하다.
//			BasicQuery query = new BasicQuery(
//					String.format("{_id:ObjectId(\"%s\")}"
//							,vo.getMid()==null?"012345678901234567890123":vo.getMid()));
			Update update = new Update();
			update.set("name", vo.getName());
			update.set("office", vo.getOffice());
			update.set("phone", vo.getPhone());
			
			
			flag =  mongoTemplate.updateFirst(query, update, MemoVO.class).getModifiedCount();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public long updateMany(MemoVO vo) {
		log.info("updateMany()....");
		log.info("vo:{}", vo);
		
		long flag = 0;
		
		try {
			// ~~~.find({age:{$gte:102}})
			BasicQuery query = new BasicQuery(String.format("{age:{$gte:%d}}",vo.getAge()));
			
			// ~~~.find({_id:ObjectId("6397fa8388a6555310a4c729")})
			//24HexString이 null값일 때는 대체값이 필요하다.
//			BasicQuery query = new BasicQuery(
//					String.format("{_id:ObjectId(\"%s\")}"
//							,vo.getMid()==null?"012345678901234567890123":vo.getMid()));
			Update update = new Update();
			update.set("name", vo.getName());
			update.set("office", vo.getOffice());
			update.set("phone", vo.getPhone());
			
			
			flag =  mongoTemplate.updateMulti(query, update, MemoVO.class).getModifiedCount();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public long deleteOne(MemoVO vo) {
		log.info("deleteOne()....");
		log.info("vo:{}", vo);
		
		long flag = 0;
		
		try {
			//db.testcollection.deleteOne({age:101})
			BasicQuery query = new BasicQuery(String.format("{age:%d}}",vo.getAge()));
			
			flag = mongoTemplate.remove(query, MemoVO.class).getDeletedCount();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public long deleteMany(MemoVO vo) {
		log.info("deleteMany()....");
		log.info("vo:{}", vo);
		
		long flag = 0;
		
		try {
			//db.testcollection.deleteMany({age:{$gte:101}})
			BasicQuery query = new BasicQuery(String.format("{age:{$gte:%d}}}",vo.getAge()));
			
			flag = mongoTemplate.remove(query, MemoVO.class).getDeletedCount();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<MemoVO> findAll() {
		log.info("findAll()...");

		//filter or where 의 역할을 한다.
		BasicQuery query = new BasicQuery("{}");

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		return mongoTemplate.find(query, MemoVO.class);
	}

	@Override
	public List<MemoVO> findAll2(int page, int limit) {
		log.info("findAll2()...");
		log.info("page:{}",page);
		log.info("limit:{}",limit);
		
		//filter or where 의 역할을 한다.
		BasicQuery query = new BasicQuery("{}");

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		//skip & limit
		query.skip((page-1)*limit).limit(limit);
		
		return mongoTemplate.find(query, MemoVO.class);
	}

	@Override
	public List<MemoVO> searchList(String searchKey, String searchWord) {
		log.info("searchList()....");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		
		// ~~~.find({name:/nn/}).sort({age:-1})
		// ~~~.find({phone:/10/}).sort({age:-1})
		
		//filter or where 의 역할을 한다.
//		BasicQuery query = new BasicQuery("{"+searchKey+":/"+searchWord+"/}");
		BasicQuery query = new BasicQuery(String.format("{%s:/%s/}", searchKey,searchWord));

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		return mongoTemplate.find(query, MemoVO.class);
	}

	@Override
	public List<MemoVO> searchList2(String searchKey, String searchWord, int page, int limit) {
		log.info("searchList()....");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		
		// ~~~.find({name:/nn/}).sort({age:-1})
		// ~~~.find({phone:/10/}).sort({age:-1})
				
		//filter or where 의 역할을 한다.
//				BasicQuery query = new BasicQuery("{"+searchKey+":/"+searchWord+"/}");
		BasicQuery query = new BasicQuery(String.format("{%s:/%s/}", searchKey,searchWord));

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		query.skip((page-1)*limit).limit(limit);
				
		return mongoTemplate.find(query, MemoVO.class);
	}

	@Override
	public MemoVO findOne(MemoVO vo) {
		log.info("findOne()...");
		log.info("vo:{}",vo);

		// ~~~.find({age:11})
//		BasicQuery query = new BasicQuery("{age:"+vo.getAge()+"}");
//		BasicQuery query = new BasicQuery(String.format("{age:%d}",vo.getAge()));
		
		// ~~~.find({_id:ObjectId("6397fa8388a6555310a4c729")})
		//24HexString이 null값일 때는 대체값이 필요하다.
		BasicQuery query = new BasicQuery(
				String.format("{_id:ObjectId(\"%s\")}"
						,vo.getMid()==null?"012345678901234567890123":vo.getMid()));

		return mongoTemplate.findOne(query, MemoVO.class);
	}

	@Override
	public List<MemoVO> searchList3(int age1, int age2) {
		log.info("searchList3()....");
		log.info("age1:{}",age1);
		log.info("age2:{}",age2);
		
		//db.testcollection.find({age:{$gte:3,$lte:6}}) >>>> age >= 3 and age <= 6
		BasicQuery query = new BasicQuery(String.format("{age:{$gte:%d,$lte:%d}}", age1,age2));

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		return mongoTemplate.find(query, MemoVO.class);
	}

	@Override
	public List<MemoVO> searchList4(int age1, int age2) {
		log.info("searchList3()....");
		log.info("age1:{}",age1);
		log.info("age2:{}",age2);
		
		//db.testcollection.find({age:{$in:[5,8,10]}}) >>>> age in(5,8,10)
		BasicQuery query = new BasicQuery(String.format("{age:{$in:[%d,%d]}}", age1,age2));

		//sort
		query.with(Sort.by(Sort.Direction.DESC,"age"));
		
		return mongoTemplate.find(query, MemoVO.class);
	}

}