package test.com.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import test.com.member.model.BoardVO;

@Mapper
public interface BoardMapper {

	public List<BoardVO> selectAll();

	public BoardVO selectOne(BoardVO vo);

	public List<BoardVO> searchListTitle(String searchWord);

	public List<BoardVO> searchListContent(String searchWord);

	public int insertOK(BoardVO vo);

	public int updateOK(BoardVO vo);

	public int deleteOK(BoardVO vo);
}
