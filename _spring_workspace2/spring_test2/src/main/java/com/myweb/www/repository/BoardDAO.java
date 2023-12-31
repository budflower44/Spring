package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	Object getDetail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	int readCount(int bno);

	int getTotalCount(PagingVO pgvo);

}
