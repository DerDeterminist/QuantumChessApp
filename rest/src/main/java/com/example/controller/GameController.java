package com.example.controller;

import com.example.api.Api;
import com.example.api.GameVariant;
import com.example.api.Request.Game.BoardRequest;
import com.example.api.Request.Game.MovePieceRequest;
import com.example.api.Request.Game.PieceOfActivePlayerRequest;
import com.example.api.Request.Game.PossibleMovesRequest;
import com.example.api.Request.Game.StartRequest;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StartResponse;
import com.example.api.Response.TileResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController
{
   public static final String BASE_URL = "/api/v1/game";

   private Api api;

   public GameController()
   {
      api = Api.getInstance(GameVariant.OFFLINE);
   }

   @PostMapping("/start")
   public StartResponse startGame(StartRequest requst)
   {
      return api.startGame();
   }

   @PostMapping("/possibleMoves")
   public TileResponse getPossibleMoves(PossibleMovesRequest request)
   {
      return api.getPossibleMoves(request.getGameID(), request.getXFrom(), request.getYFrom(), request.isQMove());
   }

   @PostMapping("/movePiece")
   public ChangeResponse movePiece(MovePieceRequest request)
   {
      return api.movePiece(request.getGameID(), request.getXFrom(), request.getYFrom(), request.getXTo(), request.getYTo(),
            request.isQMove());
   }

   @PostMapping("/completeBord")
   public BoardResponse getCompleteBord(BoardRequest request)
   {
      return api.getCompleteBord(request.getGameID());
   }

   @PostMapping("/isPieceOfActivePlayer")
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(PieceOfActivePlayerRequest request)
   {
      return api.isPieceOfActivePlayer(request.getGameID(), request.getX(), request.getY());
   }
}
