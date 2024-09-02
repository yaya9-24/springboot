package test.com.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {

	@Autowired
	private MemberDAO_JPA jpa;

	public int insertOK(MemberVO_JPA vo) {
		//jpa 내장합수 save() : pk(num) 값이 있으면 수정,없으면 입력, dao에 재정의 할 필요없음.
		
		int num = 0;
		try {
			MemberVO_JPA vo2 = jpa.save(vo);
			log.info("vo2:{}",vo2);
			num = vo2.getNum(); //입력된 시퀀스번호 할당
		} catch (Exception e) {
			log.info("입력된 데이터를 확인하세요...");
		}
		return num; 
		
		
	}

	public int updateOK(MemberVO_JPA vo) {
		//regdate(insertable=false일 경우 null입력됨) 그래서 날짜를 넣어주면 됨.
		vo.setRegdate(new Timestamp(System.currentTimeMillis()));
		int num = 0;
		try {
			MemberVO_JPA vo2 = jpa.save(vo);
			log.info("vo2:{}",vo2);
			num = vo2.getNum(); //입력된 시퀀스번호 할당
		} catch (Exception e) {
			log.info("입력된 데이터를 확인하세요...");
		}
		return num; 
	}

	public int deleteOK(MemberVO_JPA vo) {
		int flag = 0;
		try {
			jpa.delete(vo); //jpa 내장함수 delete()
			flag=1;
		} catch (Exception e) {
			log.info("입력된 데이터를 확인하세요...");
		}
		return flag;
	}

	public List<MemberVO_JPA> selectAll() {

		//return jpa.findAll(); //jpa 내장함수 findAll()
		//return jpa.findByOrderByNumDesc(); //역정렬 jpa함수 커스텀(정해진 규칙)
		//return jpa.findByOrderByNumAsc(); //순정렬 jpa함수 커스텀(정해진 규칙)
		//return jpa.selectAllDesc_JPQL(); // JPA + SQL : 함수명을 임의지정가능
		//return jpa.selectAllAsc_JPQL(); // JPA + SQL : 함수명을 임의지정가능
		return jpa.selectAllDesc_Native(); // native쿼리 : 함수명을 임의지정가능
		//return jpa.selectAllAsc_Native(); // native쿼리 : 함수명을 임의지정가능
	}

	public List<MemberVO_JPA> selectAllPageBlock(long cpage, long pageBlock) {
		long startRow = 1+pageBlock*(cpage-1);
		long endRow =  pageBlock*cpage;
		
		return jpa.selectAllPageBlock(startRow,endRow);
	}

	public MemberVO_JPA selectOne(MemberVO_JPA vo) {
		
		return jpa.findByNum(vo.getNum()); //jpa함수 커스텀(정해진 규칙)
	}

	public List<MemberVO_JPA> searchList(String searchKey, String searchWord) {
		if(searchKey.equals("id")) {
			//1.대소문자 구분
			//return jpa.findByIdLike("%"+searchWord+"%"); //jpa함수 커스텀(정해진 규칙)
			//2.대소문자 구분 안 함
			//return jpa.findByIdIgnoreCaseLike("%"+searchWord+"%"); //jpa함수 커스텀(정해진 규칙)
			//3.대소문자 구분 안 함 + 정렬
			return jpa.findByIdIgnoreCaseLikeOrderByNumDesc("%"+searchWord+"%");
		}
		else {
			//return jpa.findByNameLike("%"+searchWord+"%"); //jpa함수 커스텀(정해진 규칙)
			//return jpa.findByNameIgnoreCaseLike("%"+searchWord+"%"); //jpa함수 커스텀(정해진 규칙)
			return jpa.findByNameIgnoreCaseLikeOrderByNumDesc("%"+searchWord+"%"); //jpa함수 커스텀(정해진 규칙)
		}
		
	}

	public List<MemberVO_JPA> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
		int startRow = (cpage-1)*pageBlock + 1;
		int endRow = startRow + pageBlock - 1;
		if(searchKey.equals("id")) {
			//4.Native
			return jpa.searchListPageBlockById("%"+searchWord+"%",startRow,endRow);
		}else {
			return jpa.searchListPageBlockByName("%"+searchWord+"%",startRow,endRow);
		}
	}

	public long getTotalRows() {
		
		return jpa.count();
	}

	public long getSearchTotalRows(String searchKey, String searchWord) {
		if(searchKey.equals("id")) {
			//4.Native
			return jpa.getSearchTotalRowsId("%"+searchWord+"%");
		}else {
			return jpa.getSearchTotalRowsName("%"+searchWord+"%");
		}
	}
}
