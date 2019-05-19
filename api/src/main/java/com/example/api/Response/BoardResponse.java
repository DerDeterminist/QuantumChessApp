package com.example.api.Response;

import com.example.api.containter.BoardCont;
import com.example.api.containter.StatusCont;
import com.example.backend.Game.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoardResponse extends AbstractResponse
{
   private BoardCont boardCont;
   private ResponseStatus responseStatus;

   public BoardResponse(BoardCont boardCont, ResponseStatus responseStatus)
   {
      this.boardCont = boardCont;
      setStatus(new StatusCont(responseStatus));
   }
}