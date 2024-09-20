package test.com.member;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemberDAOimpl implements MemberDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public long insertOne(MemberVO vo) {
		log.info("insertOne()....");
		log.info("vo:{}", vo);
		long flag = 0;
		
		MemberVO vo2 = mongoTemplate.insert(vo);
		if(vo.get_id()!=null) flag = 1;
		return flag;
	}

	@Override
	public long updateOne(MemberVO vo) {
		log.info("updateOne()....");
		log.info("vo:{}", vo);
		long flag = 0;
		
		BasicQuery query = new BasicQuery(String.format("{num:%d}", vo.getNum()));
		
		Update update = new Update();
		update.set("id", vo.getId());
		update.set("pw", vo.getPw());
		update.set("name", vo.getName());
		update.set("tel", vo.getTel());
		
		flag = mongoTemplate.updateFirst(query, update, MemberVO.class).getModifiedCount();
		return flag;
	}

	@Override
	public long deleteOne(MemberVO vo) {
		log.info("deleteOne()....");
		log.info("vo:{}", vo);
		long flag = 0;
		
		try {
			
			BasicQuery query = new BasicQuery(String.format("{num:%d}", vo.getNum()));
			
			flag = mongoTemplate.remove(query, MemberVO.class).getDeletedCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<MemberVO> findAll(int page, int limit) {
		log.info("findAll()....");
		//query
		BasicQuery query = new BasicQuery("{}");
		
		//sort
		query.with(Sort.by(Sort.Direction.DESC,"num"));
		
		//skip & limit
		query.skip((page-1)*limit).limit(limit);
		

		return mongoTemplate.find(query, MemberVO.class);
	}

	@Override
	public List<MemberVO> searchList(String searchKey, String searchWord, int page, int limit) {
		log.info("searchList()....");
		log.info("searchKey:{}",searchKey);
		log.info("searchWord:{}",searchWord);
		
		//query
		BasicQuery query = new BasicQuery(String.format("{%s:/%s/}", searchKey,searchWord));
		
		//sort
		query.with(Sort.by(Sort.Direction.DESC,"num"));
		
		//skip & limit
		query.skip((page-1)*limit).limit(limit);
		

		return mongoTemplate.find(query, MemberVO.class);
	}

	@Override
	public MemberVO findOne(MemberVO vo) {
		log.info("findOne()....");
			
		//query
		BasicQuery query = new BasicQuery(String.format("{num:%d}", vo.getNum()));
		
		return mongoTemplate.findOne(query, MemberVO.class);
	}

}
