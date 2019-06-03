package com.example.restapi;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.Api;
import com.example.api.Request.Game.BoardRequest;
import com.example.api.Request.Game.GameRequest;
import com.example.api.Request.Game.MovePieceRequest;
import com.example.api.Request.Game.PossibleMovesRequest;
import com.example.api.Request.Game.StartRequest;
import com.example.api.Response.AbstractResponse;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StartResponse;
import com.example.api.Response.TileResponse;
import com.google.gson.Gson;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

public class GameClient implements Api
{
   private static Gson gson = new Gson();
   private static RequestQueue queue;

   public GameClient(Context context)
   {
      queue = Volley.newRequestQueue(context);
   }

   @Override
   public StartResponse startGame()
   {
      StartRequest startRequest = new StartRequest();
      return (StartResponse) request(startRequest, StartResponse.class);
   }

   @Override
   public TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      PossibleMovesRequest request = new PossibleMovesRequest();
      request.setGameID(gameID);
      request.setQMove(qMove);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setGameID(gameID);
      return (TileResponse) request(request, TileResponse.class);
   }

   @Override
   public ChangeResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      MovePieceRequest request = new MovePieceRequest();
      request.setGameID(gameID);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setXTo(xTo);
      request.setYTo(yTo);
      request.setQMove(qMove);
      return (ChangeResponse) request(request, ChangeResponse.class);
   }

   @Override
   public BoardResponse getCompleteBord(String gameID)
   {
      BoardRequest request = new BoardRequest();
      request.setGameID(gameID);
      return (BoardResponse) request(request, BoardResponse.class);
   }

   @Override
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return null;
   }

   private static AbstractResponse request(GameRequest gameRequest, final Class<? extends AbstractResponse> aClass)
   {
      final ResponseHolder holder = new ResponseHolder();
      try
      {
         JsonObjectRequest request =
               new JsonObjectRequest(Request.Method.POST, gameRequest.getUrl(), new JSONObject(gson.toJson(gameRequest)),
                     new Response.Listener<JSONObject>()
                     {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                           holder.setObject(gson.fromJson(response.toString(), aClass));
                        }
                     }, new Response.ErrorListener()
               {
                  @Override
                  public void onErrorResponse(VolleyError error)
                  {
                     error.printStackTrace();
                  }
               });
         queue.add(request);
      }
      catch (JSONException e)
      {
         e.printStackTrace();
      }

      return holder.getObject();
   }

   @Data
   private static class ResponseHolder
   {
      private AbstractResponse object;
   }
}
