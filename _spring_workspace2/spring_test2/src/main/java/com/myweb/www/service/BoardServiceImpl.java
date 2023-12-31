package com.myweb.www.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.repository.BoardDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardDAO bdao;

	@Override
	public int insert(BoardVO bvo) {
		log.info("insert service in");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("getList service in");
		return bdao.getList(pgvo);
	}

	@Override
	public Object getListOne(int bno) {
		int isOk = bdao.readCount(bno);
		return bdao.getDetail(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		return bdao.update(bvo);
	}

	@Override
	public int delete(int bno) {
		return bdao.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}
}
