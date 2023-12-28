package com.ezen.www.repository;

import java.util.List;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.CommentCountVO;
import com.ezen.www.domain.FileCountVO;
import com.ezen.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList(PagingVO pgvo);

	BoardVO selectOne(int bno);

	int readCount(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	int getTotalCount(PagingVO pgvo);

	int selectBno();

//	int updateFileCount(FileCountVO fcvo);
//
//	int updateCommentCount(CommentCountVO ccvo);

	int updateCommentCount();

	int updateFileCount();


}
