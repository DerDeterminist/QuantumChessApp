package com.example.api.containter;

import com.example.backend.Game.ResponseStatus;
import lombok.Data;

@Data
public class ResponseContent
{
   private BoardContainer boardContainer;
   private ResponseStatus responseStatus;

   public ResponseContent(BoardContainer boardContainer, ResponseStatus responseStatus)
   {
      this.boardContainer = boardContainer;
      this.responseStatus = responseStatus;
   }
}