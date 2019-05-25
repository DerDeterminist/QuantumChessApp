package com.example.quantumchessapp;

import com.example.api.Api;
import com.example.api.Containter.BoardCont;
import com.example.api.Containter.StatusCont;
import com.example.api.GameVariant;
import com.example.api.Request.AbstractRequest;
import com.example.api.Response.AbstractResponse;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameManager
{
   private static String gameID; // TODO: 19.05.2019 RuntimeException handling
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
      players = new ArrayList<>();
      players.add(player);
      players.add(player1);
      ClientProxy clientProxy = new ClientProxy()
      {
         @Override
         protected AbstractResponse doInBackground(AbstractRequest... abstractRequests)
         {
            gameID = api.startGame();
            return api.getCompleteBord(gameID);
         }
      };
      clientProxy.execute();
      try
      {
         BoardCont boardCont = ((BoardResponse) clientProxy.get()).getBoardCont();
         height = boardCont.getHeight();
         wight = boardCont.getWith();
         maxPieceStatus = boardCont.getMaxPieceStatus();
      }
      catch (ExecutionException | InterruptedException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }

   public static List<Position> getPossibleMoves(Position startPosition, boolean qMove)
   {
      ClientProxy clientProxy = new ClientProxy()
      {
         @Override
         protected AbstractResponse doInBackground(AbstractRequest... abstractRequests)
         {
            TileResponse possibleMoves =
                  api.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition), qMove);
            convertStatus(possibleMoves.getStatus());
            return possibleMoves;
         }
      };
      clientProxy.execute();
      try
      {
         return  ((TileResponse) clientProxy.get()).getTiles().stream()
               .map(tileContainer -> convertXYToPosition(tileContainer.getX(), tileContainer.getY()))
               .collect(Collectors.toList());
      }
      catch (ExecutionException | InterruptedException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }

   public static void movePPiece(Position startPosition, Position toMoveToPosition, boolean qMove)
   {
      ClientProxy clientProxy = new ClientProxy()
      {
         @Override
         protected AbstractResponse doInBackground(AbstractRequest... abstractRequests)
         {
            return api.movePiece(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove);
         }
      };
      clientProxy.execute();
      try
      {
         convertStatus(clientProxy.get().getStatus());
      }
      catch (ExecutionException | InterruptedException e)
      {
         e.printStackTrace();
      }
   }

   public static boolean isPieceOfActivePlayer(Position position)
   {
      ClientProxy clientProxy = new ClientProxy()
      {
         @Override
         protected AbstractResponse doInBackground(AbstractRequest... abstractRequests)
         {
            return api.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position));
         }
      };
      clientProxy.execute();
      try
      {
         return ((PieceOfActivePlayerResponse) clientProxy.get()).isPieceOfActivePlayer();
      }
      catch (InterruptedException | ExecutionException e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
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
