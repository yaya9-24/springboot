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
}