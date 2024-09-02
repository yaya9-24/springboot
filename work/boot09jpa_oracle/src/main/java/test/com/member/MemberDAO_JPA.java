package test.com.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberDAO_JPA extends JpaRepository<MemberVO_JPA, Object> {

	//jpa함수 커스텀(정해진 규칙)
	public List<MemberVO_JPA> findByOrderByNumDesc();
	public List<MemberVO_JPA> findByOrderByNumAsc();
	
	//****JPQL : @Query(객체(대소문자 구분함)를 테이블로 표현하는 쿼리)
	@Query("select vo from MemberVO_JPA vo order by num desc")
	public List<MemberVO_JPA> selectAllDesc_JPQL();
	
	@Query("select vo from MemberVO_JPA vo order by num asc")
	public List<MemberVO_JPA> selectAllAsc_JPQL();
	
	//DB쿼리를 그대로 사용가능 - 복잡한 쿼리문 사용시 권장.
	@Query(nativeQuery = true, value = "select * from member_jpa order by num desc")
	public List<MemberVO_JPA> selectAllDesc_Native();
	
	@Query(nativeQuery = true, value = "select * from member_jpa order by num asc")
	public List<MemberVO_JPA> selectAllAsc_Native();
	
	@Query(nativeQuery = true, value = "select rnum,num,user_id,user_pw,user_name,user_tel,regdate from "
			+ "(select row_number() over(order by num desc) as rnum,"
			+ "num,user_id,user_pw,user_name,user_tel,regdate from member_jpa)"
			+ "where rnum between ?1 and  ?2")
	public List<MemberVO_JPA> selectAllPageBlock(long startRow, long endRow);
	
	//selectOne
	public MemberVO_JPA findByNum(int num);
	
	//searchList - 대소문자 구분
	public List<MemberVO_JPA> findByIdLike(String searchWord);
	public List<MemberVO_JPA> findByNameLike(String searchWord);
	
	//searchList - 대소문자 구분하지 않음
	public List<MemberVO_JPA> findByIdIgnoreCaseLike(String searchWord);
	public List<MemberVO_JPA> findByNameIgnoreCaseLike(String searchWord);
	
	//searchList - 대소문자 구분하지 않음 + 역정렬
	public List<MemberVO_JPA> findByIdIgnoreCaseLikeOrderByNumDesc(String searchWord);
	public List<MemberVO_JPA> findByNameIgnoreCaseLikeOrderByNumDesc(String searchWord);
	
	
	//searchListPageBlock + Native Query(대소문자 구분 안 함)
	@Query(nativeQuery = true, value="select * from (select member_jpa.*,rownum as rnum from member_jpa where upper(user_id) like upper(?1) order by num desc)"
			+ "	where rnum between ?2 and ?3")
	public List<MemberVO_JPA> searchListPageBlockById(String searchWord, int startRow, int endRow);
	
	@Query(nativeQuery = true, value="select * from (select member_jpa.*,rownum as rnum from member_jpa where upper(user_name) like upper(?1) order by num desc)"
			+ "	where rnum between ?2 and ?3")
	public List<MemberVO_JPA> searchListPageBlockByName(String searchWord, int startRow, int endRow);
	
	@Query(nativeQuery = true, value="select count(*) total_rows from member_jpa where user_id like ?1")
	public long getSearchTotalRowsId(String searchWord);
	
	@Query(nativeQuery = true, value="select count(*) total_rows from member_jpa where user_name like ?1")
	public long getSearchTotalRowsName(String searchWord);

}
