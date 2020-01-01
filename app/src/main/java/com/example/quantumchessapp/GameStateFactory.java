package com.example.quantumchessapp;

import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.PieceCont;
import com.example.api.Containter.StatusCont;
import com.example.api.Response.GameStateResponse;
import com.example.quantumchessapp.conts.Board;
import com.example.quantumchessapp.conts.Change;
import com.example.quantumchessapp.conts.GameState;
import com.example.quantumchessapp.conts.Piece;
import com.example.quantumchessapp.conts.PieceColor;
import com.example.quantumchessapp.conts.PieceType;
import com.example.quantumchessapp.conts.Status;

import java.util.stream.Collectors;

class GameStateFactory
{
   static GameState crateGameState(GameStateResponse response)
   {
      GameState result = new GameState();
      result.setGameID(response.getGameID());
      result.setBoard(crateBoard(response.getBoardCont()));
      result.setChange(crateChange(response.getChangeCont()));
      result.setStatus(crateStatus(response.getStatus()));
      return result;
   }

   private static Status crateStatus(StatusCont statusCont)
   {
      Status status = new Status();
      status.setLastMoveValid(statusCont.isLastMoveValid());
      status.setGameWon(statusCont.isGameWon());
      status.setWinner(statusCont.getWinner());
      status.setActivePlayer(statusCont.getActivePlayer());
      status.setLoser(statusCont.getLoser());
      return status;
   }

   private static Change crateChange(ChangeCont changeCont)
   {
      Change change = new Change();
      change.setAdded(changeCont.getAdded().stream().map(GameStateFactory::cretePiece).collect(Collectors.toList()));
      change.setChanged(changeCont.getChanged().stream().map(GameStateFactory::cretePiece).collect(Collectors.toList()));
      change.setRemoved(changeCont.getRemoved().stream().map(GameStateFactory::cretePiece).collect(Collectors.toList()));
      return change;
   }

   private static Board crateBoard(BoardCont boardCont)
   {
      Board board = new Board();
      board.setHeight(boardCont.getHeight());
      board.setWith(boardCont.getWith());
      board.setMaxPieceStatus(boardCont.getMaxPieceStatus());
      board.setPieces(boardCont.getPieces().stream().map(GameStateFactory::cretePiece).collect(Collectors.toSet()));
      return board;
   }

   private static Piece cretePiece(PieceCont cont)
   {
      Piece piece = new Piece();
      piece.setId(cont.getId());
      piece.setColor(cratePieceColor(cont.getColor()));
      piece.setStatus(cont.getStatus());
      piece.setType(cratePieceType(cont.getType()));
      piece.setX(cont.getX());
      piece.setY(GameManager.convertY(cont.getY()));
      return piece;
   }

   private static PieceType cratePieceType(com.example.api.Containter.PieceType type)
   {
      return PieceType.valueOf(type.toString());
   }

   private static PieceColor cratePieceColor(com.example.api.Containter.PieceColor color)
   {
      return PieceColor.valueOf(color.toString());
   }
}
