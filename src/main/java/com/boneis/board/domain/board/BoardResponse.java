package com.boneis.board.domain.board;

import com.boneis.board.domain.ResultDomain;
import lombok.Data;

import java.util.List;

@Data
public class BoardResponse extends ResultDomain {
  private List<Board> boardList;
  private DetailBoard detailBoard;
}
