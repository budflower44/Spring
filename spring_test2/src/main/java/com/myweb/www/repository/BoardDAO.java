package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(long bno);

	int update(BoardVO bvo);

	int delete(int bno);

	int readCount(long bno);

	int getTotalCount(PagingVO pgvo);

	long selectOneBno();

	void updateCmtCount();

	void updateFileCount();

	void readCountBno(@Param("bno") long bno,@Param("cnt") int cnt);

}
