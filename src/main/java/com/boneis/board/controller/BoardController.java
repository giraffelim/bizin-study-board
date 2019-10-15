package com.boneis.board.controller;

import com.boneis.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String redirectMain(){
      return "redirect:1";
    }

    // Main Form
    @RequestMapping("/{pageNum}")
    public String main(Model model, @PathVariable int pageNum)throws Exception {
      double totalCount = boardService.getAllList().getBoardList().size();
      double page = Math.floor(totalCount/10);
      if(totalCount%10 > 0){
        page += 1;
      }
      if(pageNum > (int)page){
        log.warn("pageNum miss");
        pageNum = 1;
      }
      model.addAttribute("pageNum", pageNum);
      return "main/index";
    }

    // insertForm
    @RequestMapping("/insert")
    public String insertForm(){
      log.info("insert View");
      return "insert/insertView";
    }

    // replyForm
    @RequestMapping(value = "/reply/{boardNo}", method = RequestMethod.GET)
    public String replyForm(@PathVariable int boardNo, Model model, int pageNum){
      model.addAttribute("boardNo", boardNo);
      model.addAttribute("pageNum", pageNum);
      return "insert/insertView";
    }

    // detailForm
    @RequestMapping(value = "/detail/{boardNo}/{pageNum}", method = RequestMethod.GET)
    public String detailForm(@PathVariable int boardNo, Model model, @PathVariable int pageNum){
      model.addAttribute("boardNo",boardNo);
      model.addAttribute("pageNum",pageNum);
      return "detail/detailView";
    }

    // modifyForm
    @RequestMapping(value ="/modify/{boardNo}/{pageNum}", method = RequestMethod.GET)
    public String modifyForm(@PathVariable int boardNo, Model model, @PathVariable int pageNum){
      model.addAttribute("boardNo", boardNo);
      model.addAttribute("flag","modify");
      model.addAttribute("pageNum",pageNum);
      return "insert/insertView";
    }

}
