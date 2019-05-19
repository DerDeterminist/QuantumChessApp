package com.example.api.containter;

import com.example.backend.Game.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoardResponse extends AbstractResponse
{
   private BoardCont m_boardCont;
   private ResponseStatus responseStatus;

   public BoardResponse(BoardCont boardCont, ResponseStatus responseStatus)
   {
      this.m_boardCont = boardCont;
      setStatus(new StatusCont(responseStatus));
   }
}