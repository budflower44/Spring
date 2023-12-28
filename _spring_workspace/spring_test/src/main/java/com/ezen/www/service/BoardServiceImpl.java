package com.ezen.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.CommentCountVO;
import com.ezen.www.domain.FileCountVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardDAO;
import com.ezen.www.repository.CommentDAO;
import com.ezen.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private FileDAO fdao;
	
	@Inject
	private CommentDAO cdao;
	
	@Override
	public int register(BoardDTO bdto) {
		log.info("register service impl");
		//기본 보드 내용을 DB에 저장
		int isOk = bdao.insert(bdto.getBvo());
		
		//flist를 db에 저장 
		if(bdto.getFlist() == null) {
			//파일의 값이 없다면...
			isOk *= 1; //그냥 성공한걸로 처리
		}else {
			//파일 저장
			if(isOk > 0 && bdto.getFlist().size() > 0) {
				//fvo -> bno는 아직 설정되기 전.
				//현재 bdto 시점에서는 아직 bno가 생성되지 않음.
				//insert를 통해 자동생성 => DB에서 검색해서 가져오기
				int bno = bdao.selectBno();
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					//파일 저장
					isOk *= fdao.insertFile(fvo);
				}
				
			}
		}
		
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("selectList service impl");
		
		//선생님 풀이
		int isOk = bdao.updateCommentCount();
		if(isOk ==0) {
			log.info("updateCommentCount error");
		}
		int isfOk = bdao.updateFileCount();
		if(isfOk ==0) {
			log.info("updateCommentCount error");
		}
		return bdao.selectList(pgvo);
//		List<BoardVO> listBoardVO = bdao.selectList(pgvo);
//		
//		for(BoardVO bvo : listBoardVO) {
//			int bno = bvo.getBno();
//			
//			int isFileCount = fdao.fileCount(bno);
//			bvo.setFile_count(isFileCount);
//			FileCountVO fcvo = new FileCountVO(isFileCount, bno);
//			int isUpdateFileCount = bdao.updateFileCount(fcvo);
//			log.info("isUpdateFileCount >> "+(isUpdateFileCount>0?"OK":"Fail"));
//			
//			int isCommentCount = cdao.commentCount(bno);
//			bvo.setComment_count(isCommentCount);
//			CommentCountVO ccvo = new CommentCountVO(isCommentCount, bno);
//			int isUpdateCommentCount = bdao.updateCommentCount(ccvo);
//			log.info("isUpdateCommentCount >> "+(isUpdateCommentCount>0?"OK":"Fail"));
//		}
//		
//		return listBoardVO;
	}

	@Override
	public BoardDTO getDetail(int bno) {
		log.info("getDetail service impl");
		// read_count 증가
		int isOk = bdao.readCount(bno);
		log.info("readCount is "+((isOk>0)?"OK":"Fail"));
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBvo(bdao.selectOne(bno)); //게시글 내용 채우기
		boardDTO.setFlist(fdao.getFileList(bno)); //bno에 해당하는 모든 파일 리스트를 
		return boardDTO;
	}

	@Override
	public int modify(BoardDTO boardDTO) {
		log.info("modify service Impl");
		int isOk = bdao.update(boardDTO.getBvo()); //보드 내용 수정
		if(boardDTO.getFlist() == null){
			isOk *= 1; //이미 처리된 것과 같은 효과, 리턴으로 마무리해도 됨
		}else {
			if(isOk > 0 && boardDTO.getFlist().size()>0) {
				int bno = boardDTO.getBvo().getBno();
				for(FileVO fvo : boardDTO.getFlist()) {
					fvo.setBno(bno);
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		return isOk;
	}

	@Override
	public int remove(int bno) {
		log.info("remove service Impl");
		return bdao.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		log.info("getTotalCount service Impl");
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public int deleteFile(String uuid) {
		log.info("deleteFile service Impl");
		return fdao.deleteFile(uuid);
	}

	
}
