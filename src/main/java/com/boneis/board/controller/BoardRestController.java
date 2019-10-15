package com.boneis.board.controller;

import com.boneis.board.domain.board.Board;
import com.boneis.board.domain.ResultDomain;
import com.boneis.board.domain.board.BoardResponse;
import com.boneis.board.domain.board.SearchParam;
import com.boneis.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    // getAllList
    @GetMapping(value = "/api/data")
    public BoardResponse getAllList() throws Exception {
      return boardService.getAllList();
    }

    // insertBoard
    @PostMapping(value = "/api/insert")
    public ResultDomain insert(@RequestBody Board board) throws Exception{
      return boardService.insertBoard(board);
    }

    // getBoard
    @GetMapping(value = "/api/get/{boardNo}")
    public BoardResponse getBoard(@PathVariable int boardNo)throws Exception{
      return boardService.getBoard(boardNo);
    }

    @PostMapping(value ="/api/replyInsert")
    public ResultDomain replyInsert(@RequestBody Board board) throws Exception{
      return boardService.insertReply(board);
    }

    @GetMapping(value = "/api/search")
    public BoardResponse searchList(SearchParam searchValue) throws Exception{
      // searchValue로 list 찾기
      log.info(searchValue.getValue());
      return boardService.getSearchList(searchValue);
    }

    @PutMapping(value = "/api/modify")
    public ResultDomain modify (@RequestBody Board board)throws Exception{
      log.info("modify ==> "+board);
      return boardService.modifyBoard(board);
    }

    @PostMapping(value = "/api/passConfirm")
    public ResultDomain passConfirm (@RequestBody Board board) throws  Exception {
      return boardService.passConfirm(board);
    }

    @DeleteMapping(value = "/api/remove/{boardNo}")
    public ResultDomain removeBoard (@PathVariable int boardNo) throws Exception{
      return boardService.removeBoard(boardNo);
    }
}
