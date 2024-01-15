	package com.myweb.www.service;
	
	import java.util.List;
	
	import com.myweb.www.domain.BoardDTO;
	import com.myweb.www.domain.BoardVO;
	import com.myweb.www.domain.PagingVO;
	
	public interface BoardService {
	
		int insert(BoardDTO bdto);
	
		List<BoardVO> getList(PagingVO pgvo);
	
		BoardDTO getListOne(long bno);
	
		int update(BoardDTO boardDTO);
	
		int delete(int bno);
	
		int getTotalCount(PagingVO pgvo);
	
		int deleteFile(String uuid);
	
	}
