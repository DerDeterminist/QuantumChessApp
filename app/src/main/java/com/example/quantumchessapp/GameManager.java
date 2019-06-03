package com.example.quantumchessapp;

import android.content.Context;
import com.example.api.Api;
import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.StatusCont;
import com.example.api.LocaleAPI;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.TileResponse;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;
import com.example.restapi.GameClient;

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

   public static void newGame(Context context, Player player, Player player1,
                              GameVariant variant)
   {
      switch (variant)
      {
         case OFFLINE:
            api = LocaleAPI.getInstance();
            break;
         case ONLINE:
            api = new GameClient(context);
            break;
      }
      players = new ArrayList<>();
      players.add(player);
      players.add(player1);
      gameID = api.startGame().getGameID();
      BoardCont boardCont = api.getCompleteBord(gameID).getBoardCont();
      height = boardCont.getHeight();
      wight = boardCont.getWith();
      maxPieceStatus = boardCont.getMaxPieceStatus();
   }

   public static List<Position> getPossibleMoves(Position startPosition, boolean qMove)
   {
      TileResponse possibleMoves =
            api.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition), qMove);
      convertStatus(possibleMoves.getStatus());
      return possibleMoves.getTiles().stream()
            .map(tileContainer -> convertXYToPosition(tileContainer.getX(), tileContainer.getY()))
            .collect(Collectors.toList());
   }

   public static ChangeCont movePiece(Position startPosition, Position toMoveToPosition, boolean qMove)
   {
      ChangeResponse changeResponse =
            api.movePiece(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove);
      convertStatus(changeResponse.getStatus());
      ChangeCont changeCont = changeResponse.getChangeCont();
      changeCont.getAdded().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      changeCont.getRemoved().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      changeCont.getChanged().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      return changeCont;
   }

   public static boolean isPieceOfActivePlayer(Position position)
   {
      return api.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position))
            .isPieceOfActivePlayer();
   }

   private static void convertStatus(final StatusCont statusCont)
   {
      isGameWon = statusCont.isGameWon();
      lastMoveWasValid = statusCont.isLastMoveWasValid();
      winner = players.stream().filter(player -> player.getId() == statusCont.getWinner()).findAny().orElse(null);
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
