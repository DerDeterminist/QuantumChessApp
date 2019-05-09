package com.example.api.Containter;

import com.example.backend.Game.ResponseStatus;

@SuppressWarnings("unused")
public class ResponseContent
{
   private BoardContainer boardContainer;
   private ResponseStatus responseStatus;

   public ResponseContent(BoardContainer boardContainer, ResponseStatus responseStatus)
   {
      this.boardContainer = boardContainer;
      this.responseStatus = responseStatus;
   }

   public ResponseStatus getResponseStatus()
   {
      return responseStatus;
   }

   public void setResponseStatus(ResponseStatus responseStatus)
   {
      this.responseStatus = responseStatus;
   }

   public BoardContainer getBoardContainer()
   {
      return boardContainer;
   }

   public void setBoardContainer(BoardContainer boardContainer)
   {
      this.boardContainer = boardContainer;
   }
}