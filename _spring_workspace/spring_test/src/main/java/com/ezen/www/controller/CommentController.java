package com.ezen.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/comment/*")
@RestController
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	// ResponseEntity 객체 사용 : body내용 + httpStatus 상태
	// <> -> List<CommentVO> 처럼 사용할 수도 있음 
	// @RequestBody : body 값 추출
	// consumes : 가져오는 데이터의 형태
	// produces : 보내는 데이터 형식 / 나가는 데이터 타입 : MediaType
	// json : application/json, text : text_plain
	@PostMapping(value="/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		log.info(">>>>>> cvo >> {} "+cvo);
		int isOk = csv.post(cvo);
		return isOk>0? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR); //500번 에러
	}
	//return -> 통신상의 코드는 이미 정해져 있는 아이들임으로 형식을 바꿀 수 없음
	
	
	@GetMapping(value = "/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno") int bno){
		log.info(">>>> bno >> {} "+bno);
		
		List<CommentVO> list = csv.getList(bno);
		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("cno") int cno){
		log.info(">>> cno >> {} "+cno);
		int isOk = csv.delete(cno);
		return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//consumes : request body의 타입
	//produces : 내보내는 타입 (String일때는 생략해도 됨, json에서는 안적어주면 error)
	
	@PutMapping(value="/modify", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> update(@RequestBody CommentVO cvo){
		log.info(">>> cno >> {} "+cvo);
		int isOk = csv.update(cvo);
		return isOk>0 ? new ResponseEntity<String>("1", HttpStatus.OK) : 
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
