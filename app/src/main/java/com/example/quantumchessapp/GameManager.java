package com.example.quantumchessapp;

import com.example.api.Api;
import com.example.api.GameVariant;
import com.example.api.containter.BoardContainer;
import com.example.api.containter.ResponseStatus;
import com.example.api.containter.ResponseTiles;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameManager
{
   private static String gameID;
   private static Api api;

   private static List<Player> players;
   private static boolean isGameWon;
   private static boolean lastMoveWasValid;
   private static Player winner;

   private static int wight;
   private static int height;
   private static double maxPieceStatus;

   public static void newGame(Player player, Player player1, GameVariant variance)
   {
      api = Api.getInstance(variance);
      gameID = api.startGame();
      players = new ArrayList<>();
      players.add(player);
      players.add(player1);

      BoardContainer boardContainer = api.getCompleteBord(gameID).getBoardContainer();
      height = boardContainer.getHeight();
      wight = boardContainer.getWith();
      maxPieceStatus = boardContainer.getMaxPieceStatus();
   }

   public static List<Position> getPossibleMoves(Position startPosition, boolean qMove)
   {
      ResponseTiles possibleMoves =
            api.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition), qMove);
      convertStatus(possibleMoves.getStatus());
      return possibleMoves.getTiles().stream().map(tileContainer -> convertXYToPosition(tileContainer.getX(), tileContainer.getY()))
            .collect(Collectors.toList());
   }

   public static void movePiece(Position startPosition, Position toMoveToPosition, boolean qMove)
   {
      convertStatus(
            api.movePiece(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove));
   }

   public static boolean isPieceOfActivePlayer(Position position)
   {
      return api.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position));
   }

   private static void convertStatus(final ResponseStatus responseStatus)
   {
      isGameWon = responseStatus.isGameWon();
      lastMoveWasValid = responseStatus.isLastMoveWasValid();
      winner = players.stream().filter(player -> player.getId() == responseStatus.getWinner()).findAny().orElse(null);
   }

   private static Position convertXYToPosition(int x, int y)
   {
      return new Position(x, height - 1 - y);
   }

   private static int convertPositionHeight(Position position)
   {
      return height - 1 - position.getY();
   }

   private static int convertPositionWight(Position position)
   {
      return position.getX();
   }

   public static List<Player> getPlayers()
   {
      return players;
   }

   public static boolean isGameWon()
   {
      return isGameWon;
   }

   public static boolean isLastMoveValid()
   {
      return lastMoveWasValid;
   }

   public static Player getWinner()
   {
      return winner;
   }

   public static int getWight()
   {
      return wight;
   }

   public static int getHeight()
   {
      return height;
   }

   public static double getMaxPieceStatus()
   {
      return maxPieceStatus;
   }
}
