package xyz.niflheim.stockfish;

import com.example.api.LocaleAPI;
import com.example.api.Response.ChangeResponse;
import com.example.backend.GameManager;
import xyz.niflheim.stockfish.engine.enums.Option;
import xyz.niflheim.stockfish.engine.enums.Query;
import xyz.niflheim.stockfish.engine.enums.QueryType;
import xyz.niflheim.stockfish.engine.enums.Variant;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;

import java.util.function.Consumer;

public class StockfishAPI extends LocaleAPI
{
   private StockfishClient client;
   private GameManager gameManager;

   public StockfishAPI()
   {
      gameManager = GameManager.getInstance();
      initStockfish();
   }

   public void StockfishMove(String gameID, int difficultly, int moveTime, Consumer<ChangeResponse> consumer)
   {
      Query query =
            new Query.Builder(QueryType.Best_Move).setFen(gameManager.getFEN(gameID)).setDifficulty(difficultly).setDepth(23)
                  .setMovetime(moveTime * 1000)
                  .build();
      client.submit(query, response -> {

         ChangeResponse changeResponse =
               movePiece(gameID, fenConverter(response, 0), fenConverter(response, 1), fenConverter(response, 2),
                     fenConverter(response, 3), false);

         consumer.accept(changeResponse);
      });
   }

   private void initStockfish()
   {
      try
      {
         client = new StockfishClient.Builder()
               .setInstances(4)
               .setOption(Option.Threads, Runtime.getRuntime().availableProcessors())
               .setOption(Option.Minimum_Thinking_Time, 1000)
               .setVariant(Variant.DEFAULT).build();
      }
      catch (StockfishInitException e)
      {
         throw new RuntimeException(e);
      }
   }

   private int fenConverter(String fen, int pos)
   {
      char c = fen.charAt(pos);
      if (Character.isDigit(c))
      {
         return Integer.valueOf(String.valueOf(c));
      }
      else
      {
         return (int) c - 61;
      }
   }
}
