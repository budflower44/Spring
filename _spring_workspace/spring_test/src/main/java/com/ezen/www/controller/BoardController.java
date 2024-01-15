package com.ezen.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.FileHandler;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
	
	@Inject
	private BoardService bsv;
	
	@Inject
	private FileHandler fhd;
	
	//경로와 리턴의 값이 같을 경우 생략가능
	// /board/register => /board/register
	@GetMapping("/register")
	public void register() {}
	
	//@requestParam("name")String name : 파라미터 받을 때
	// required : 필수여부 (false) : 파라미터가 없어도 예외가 발생하지 않음.
	@PostMapping("/register")
	public String register(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo >>{} ", bvo);
		log.info(">>> files >> {} "+files.toString());
		// 파일 핸들러 처리.
		List<FileVO> flist = null;
		
		//파일이 있을 경우만 fhd를 호출
		if(files[0].getSize() > 0) { //정확한 처리를 위해 files[0]로 정확한 요소의 값이 있는지 체크, 빈 값이 인지되는 경우도 있음 
			flist = fhd.uploadFiles(files);
			log.info(">>>> flist >> {} "+flist);
		}else {
			log.info("file null");
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		
		int isOk = bsv.register(bdto);
		
		log.info(">>> board register >>> "+(isOk>0?"OK":"Fail"));
		//목적지 경로 
		return "redirect:/board/list";
	}
	
	// /board/ist => /board/list void 처리해도 상관없음.
	@GetMapping("/list")
	public String list(Model m, PagingVO pgvo) {
		log.info(">>>> pgvo >> {} "+pgvo); //pageNo, qty, type, keyword
		//리턴타입은 목적지 경로에 대한 타입 (destPage가 리턴이라고 생각)
		//Model 객체 => setAttribute 역할을 하는 객체
		m.addAttribute("list", bsv.getList(pgvo));
		
		//ph 객체 다시 생성 (pgvo, totalCount)
		int totalCount = bsv.getTotalCount(pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		m.addAttribute("ph", ph);
		return "/board/list";
	}
	
	@GetMapping({"/detail", "/modify"}) //중괄로로 묶어줘야 하나의 mapping으로 인식함
	public void detail(Model m, @RequestParam("bno") int bno) {
		log.info(">>>> bno > "+bno);
		//파일 내용도 포함해서 같이 보내기
		m.addAttribute("boardDTO", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo, @RequestParam(name="files", required = false)MultipartFile[] files) {
		log.info(">>> bvo >>{} "+bvo);
		List<FileVO> flist = null;
		if(files[0].getSize()>0) {
			flist = fhd.uploadFiles(files);
		}
		BoardDTO boardDTO = new BoardDTO(bvo, flist);
		
		//update
		int isOk = bsv.modify(boardDTO);
		// m.addAttribute("bno", bvo.getBno());
		log.info("modify is "+((isOk>0)?"OK":"Fail"));
		return "redirect:/board/detail?bno="+bvo.getBno(); //bno가 필요
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam("bno") int bno, RedirectAttributes re) {
		log.info(">>> bno >> {} "+bno);
		int isOk = bsv.remove(bno);
		//페이지가 새로고침 될 때 남아 있을 필요가 없는 데이터
		//리다이렉트 될 때 데이터를 보내는 객체(RedirectAttribute)
		//한번만 발화성으로 데이터를 보낼 때 사용
		re.addFlashAttribute("isDel", isOk);
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value="/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("uuid") String uuid){
		log.info(">>>> uuid >> {} "+uuid);
		int isOk = bsv.deleteFile(uuid);
		return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
