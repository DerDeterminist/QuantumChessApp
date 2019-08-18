package com.example.controller;

import com.example.api.Api;
import com.example.api.LocaleAPI;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * RestMapping for the LocaleApi
 *
 * @see LocaleAPI
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping(GameController.BASE_URL)
public class GameController
{
   static final String BASE_URL = "/api/v1/game";

   private Api api;
   private Logger logger = LoggerFactory.getLogger(this.getClass());

   public GameController()
   {
      api = new LocaleAPI();
   }

   @PostMapping(value = "/start", consumes = MediaType.APPLICATION_JSON_VALUE)
   public StartResponse startGame(@RequestBody StartRequest request)
   {
      StartResponse response = api.startGame();

      logger.info("Started game: " + response.toString());

      return response;
   }

   @PostMapping(value = "/possibleMoves", consumes = MediaType.APPLICATION_JSON_VALUE)
   public TileResponse getPossibleMoves(@RequestBody PossibleMovesRequest request)
   {
      logger.info("possibleMoves request: " + request.toString());

      TileResponse response =
            api.getPossibleMoves(request.getGameID(), request.getXFrom(), request.getYFrom(), request.isQMove());

      logger.info("possibleMoves response: gameID: " + response.toString());
      return response;
   }

   @PostMapping(value = "/movePiece", consumes = MediaType.APPLICATION_JSON_VALUE)
   public ChangeResponse movePiece(@RequestBody MovePieceRequest request)
   {
      logger.info("movePiece request: " + request.toString());

      ChangeResponse response =
            api.movePiece(request.getGameID(), request.getXFrom(), request.getYFrom(), request.getXTo(), request.getYTo(),
                  request.isQMove());

      logger.info("movePiece response: gameID: " + response.toString());
      return response;
   }

   @PostMapping(value = "/completeBord", consumes = MediaType.APPLICATION_JSON_VALUE)
   public BoardResponse getCompleteBord(@RequestBody BoardRequest request)
   {
      logger.info("completeBord request: " + request.toString());

      BoardResponse response = api.getCompleteBord(request.getGameID());

      logger.info("completeBord response: gameID: " + response.toString());
      return response;
   }

   @PostMapping(value = "/isPieceOfActivePlayer", consumes = MediaType.APPLICATION_JSON_VALUE)
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(@RequestBody PieceOfActivePlayerRequest request)
   {
      logger.info("isPieceOfActivePlayer request: " + request.toString());

      PieceOfActivePlayerResponse response =
            api.isPieceOfActivePlayer(request.getGameID(), request.getX(), request.getY());

      logger.info("isPieceOfActivePlayer response: gameID: " + response.toString());
      return response;
   }
}
