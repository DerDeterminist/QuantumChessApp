package com.example.quantumchessapp;

import android.content.Context;

import com.example.api.Api;
import com.example.api.LocaleAPI;
import com.example.api.Response.GameStateResponse;
import com.example.api.Response.TileResponse;
import com.example.quantumchessapp.conts.GameState;
import com.example.quantumchessapp.conts.Status;
import com.example.quantumchessapp.conts.TileStatus;
import com.example.restapi.GameClient;

import java.util.function.Consumer;

/**
 * Manages the currently played game.
 * Bundles the communication the different Apis.
 * Depending on the Api has to change the gameModel itself of takes advantage of the observerPattern
 *
 * @see LocaleAPI
 * @see GameClient
 */
public class GameManager
{
   private static Api api;
   private static GameClient client;

   private static GameVariant variant;

   private static String gameID;
   private static int height;
   private static Status status;

   public static void newGame(Context context, GameVariant variant, Consumer<GameState> consumer)
   {
      GameManager.variant = variant;
      switch (variant)
      {
         case OFFLINE:
            api = new LocaleAPI();
            GameStateResponse gameStateResponse1 = api.startGame();
            consumeGameState(consumer, gameStateResponse1);
            break;
         case ONLINE:
            client = new GameClient(context);
            client.startGame(gameStateResponse -> consumeGameState(consumer, gameStateResponse));
            break;
      }
   }

   private static void consumeGameState(Consumer<GameState> consumer, GameStateResponse gameStateResponse)
   {
      gameID = gameStateResponse.getGameID();
      GameState gameState = GameStateFactory.crateGameState(gameStateResponse);
      status = gameState.getStatus();
      height = gameState.getBoard().getHeight();
      consumer.accept(gameState);
   }

   public static void getPossibleMoves(Position startPosition, boolean qMove, Consumer<TileStatus> consumer)
   {
      switch (variant)
      {
         case OFFLINE:
            TileResponse possibleMoves =
                  api.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                        qMove);
            consumer.accept(TileStatusFactory.createTileStatus(possibleMoves));
            break;
         case ONLINE:
            client.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  qMove, response -> consumer.accept(TileStatusFactory.createTileStatus(response)));
            break;
      }
   }

   public static void movePiece(Position startPosition, Position toMoveToPosition, boolean qMove, Consumer<GameState> consumer)
   {
      switch (variant)
      {
         case OFFLINE:
            GameStateResponse gameStateResponse =
                  api.movePiece(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                        convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove);
            consumeGameState(consumer, gameStateResponse);
            break;
         case ONLINE:
            client.movePiece(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove,
                  gameStateResponse1 -> consumeGameState(consumer, gameStateResponse1));
            break;
      }
   }

   public static void isPieceOfActivePlayer(Position position, Consumer<Boolean> consumer)
   {
      switch (variant)
      {
         case OFFLINE:
            boolean pieceOfActivePlayer =
                  api.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position))
                        .isPieceOfActivePlayer();
            consumer.accept(pieceOfActivePlayer);
            break;
         case ONLINE:
            client.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position),
                  pieceOfActivePlayerResponse -> consumer.accept(pieceOfActivePlayerResponse.isPieceOfActivePlayer()));
            break;
      }
   }

   public static Status getStatus()
   {
      return status;
   }

   static int convertY(int y)
   {
      return height - 1 - y;
   }

   private static int convertPositionHeight(Position position)
   {
      return height - 1 - position.getY();
   }

   private static int convertPositionWight(Position position)
   {
      return position.getX();
   }
}
