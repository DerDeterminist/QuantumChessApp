package com.example.backend.Game;

class OutOfBoardException extends Exception
{
   OutOfBoardException(String message)
   {
      super(message);
   }
}
