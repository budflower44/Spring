package com.myweb.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.repository.BoardDAO;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	private final BoardDAO bdao;
	private final FileDAO fdao;
	private final CommentDAO cdao;

	@Transactional
	@Override
	public int insert(BoardDTO bdto) {
		log.info("insert service in");
		//bvo boardMapper / flist fileMapper 등록
		log.info(">>>>> dsdfsfsd >>>>>>>"+bdto.getBvo());
		int isOk = bdao.insert(bdto.getBvo());
		
		if(bdto.getFlist()==null) {
			return isOk;
		}
		
		//bvo insert 후 파일도 있다면...
		if(isOk > 0 && bdto.getFlist().size() > 0) {
			//bno setting
			long bno = bdao.selectOneBno(); //가장 마지막에 등록된 bno
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("getList service in");
		bdao.updateCmtCount();
		bdao.updateFileCount();
		return bdao.getList(pgvo);
	}
	
	@Transactional
	@Override
	public BoardDTO getListOne(long bno) {
//		int isOk = bdao.readCount(bno);
		//readCount
		bdao.readCountBno(bno, 1); //bno, +1
		BoardVO bvo = bdao.getDetail(bno);
		List<FileVO>flist = fdao.getFileList(bno);
		BoardDTO bdto = new BoardDTO(bvo, flist);
		return bdto;
	}
	
	@Transactional
	@Override
	public int update(BoardDTO boardDTO) {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>@@@@@@@@@ service in");
		int isOk = bdao.update(boardDTO.getBvo());
		bdao.readCountBno(boardDTO.getBvo().getBno(), -2);
		if(boardDTO.getFlist() == null) {
			return isOk;
		}else {
			long bno = boardDTO.getBvo().getBno();
			for(FileVO fvo : boardDTO.getFlist()) {
				fvo.setBno(bno);
				fdao.insertFile(fvo);				
			}
		}
		return isOk;
	}

	@Override
	public int delete(int bno) {
		return bdao.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public int deleteFile(String uuid) {
		return fdao.deleteFile(uuid);
	}
}
