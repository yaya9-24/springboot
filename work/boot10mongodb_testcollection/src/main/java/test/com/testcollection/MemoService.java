package test.com.testcollection;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemoService  {

	@Autowired
	MemoDAO dao;
	
	public long insertOne(MemoVO vo) {
		return dao.insertOne(vo);
	}

	
	public long insertMany() {
		return dao.insertMany();
	}

	
	public long updateOne(MemoVO vo) {
		return dao.updateOne(vo);
	}

	
	public long updateMany(MemoVO vo) {
		return dao.updateMany(vo);
	}

	
	public long deleteOne(MemoVO vo) {
		return dao.deleteOne(vo);
	}

	
	public long deleteMany(MemoVO vo) {
		return dao.deleteMany(vo);
	}

	
	public List<MemoVO> findAll() {
		return dao.findAll();
	}

	
	public List<MemoVO> findAll2(int page, int limit) {
		return dao.findAll2(page,limit);
	}

	
	public List<MemoVO> searchList(String searchKey, String searchWord) {
		return dao.searchList(searchKey,searchWord);
	}

	
	public List<MemoVO> searchList2(String searchKey, String searchWord, int page, int limit) {
		return dao.searchList2(searchKey,searchWord,page,limit);
	}

	
	public MemoVO findOne(MemoVO vo) {
		return dao.findOne(vo);
	}

	public List<MemoVO> searchList3(int age1, int age2) {
		return dao.searchList3(age1,age2);
	}


	public List<MemoVO> searchList4(int age1, int age2) {
		return dao.searchList4(age1,age2);
	}

}
