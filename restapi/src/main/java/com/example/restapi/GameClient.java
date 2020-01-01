package com.example.restapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.Request.Game.BoardRequest;
import com.example.api.Request.Game.GameRequest;
import com.example.api.Request.Game.MovePieceRequest;
import com.example.api.Request.Game.PieceOfActivePlayerRequest;
import com.example.api.Request.Game.PossibleMovesRequest;
import com.example.api.Request.Game.StartRequest;
import com.example.api.Response.AbstractResponse;
import com.example.api.Response.GameStateResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

/**
 * RestApi oriented at Api.
 *
 * @see com.example.api.Api
 */
public class GameClient
{
   private static Gson gson = new Gson();
   private static RequestQueue queue;

   public GameClient(Context context)
   {
      queue = Volley.newRequestQueue(context);
   }

   public void startGame(Consumer<GameStateResponse> consumer)
   {
      final StartRequest startRequest = new StartRequest();
      request(startRequest, GameStateResponse.class, consumer);
   }

   public void getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove, Consumer<TileResponse> consumer)
   {
      PossibleMovesRequest request = new PossibleMovesRequest();
      request.setGameID(gameID);
      request.setQMove(qMove);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setGameID(gameID);
      request(request, TileResponse.class, consumer);
   }

   public void movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove, Consumer<GameStateResponse> consumer)
   {
      MovePieceRequest request = new MovePieceRequest();
      request.setGameID(gameID);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setXTo(xTo);
      request.setYTo(yTo);
      request.setQMove(qMove);
      request(request, GameStateResponse.class, consumer);
   }

   public void getCompleteBord(String gameID, Consumer<GameStateResponse> consumer)
   {
      BoardRequest request = new BoardRequest();
      request.setGameID(gameID);
      request(request, GameStateResponse.class, consumer);
   }

   public void isPieceOfActivePlayer(String gameID, int x, int y, Consumer<PieceOfActivePlayerResponse> consumer)
   {
      PieceOfActivePlayerRequest request = new PieceOfActivePlayerRequest();
      request.setX(x);
      request.setY(y);
      request.setGameID(gameID);
      request(request, PieceOfActivePlayerResponse.class, consumer);
   }

   private static void request(GameRequest gameRequest, final Class<? extends AbstractResponse> aClass,
                               Consumer consumer)
   {
      try
      {
         final JsonObjectRequest request =
               new JsonObjectRequest(Request.Method.POST, gameRequest.getUrl(), new JSONObject(gson.toJson(gameRequest)),
                     response -> {
                        AbstractResponse response1 = gson.fromJson(response.toString(), aClass);
                        //noinspection unchecked
                        consumer.accept(response1);
                     }, Throwable::printStackTrace);
         queue.add(request);
      }
      catch (JSONException e)
      {
         e.printStackTrace();
      }
   }
}
