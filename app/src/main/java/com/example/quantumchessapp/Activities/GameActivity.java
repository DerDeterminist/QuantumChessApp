package com.example.quantumchessapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.quantumchessapp.GameManager;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.util.List;
import java.util.Optional;

public class GameActivity extends AppCompatActivity
{
   private LinearLayout board;
   private ConstraintLayout background;
   private Optional<ImageButton> activePiece = Optional.empty();
   private List<Position> possiblePositions;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_game);
      board = findViewById(R.id.board);
      background = findViewById(R.id.background_game);

      GameManager.newGame(new Player(), new Player());

      initListener();
   }

   private void initListener()
   {
      background.setOnClickListener(v -> activePiece = Optional.empty());
      for (int y = 0; y < board.getChildCount(); y++)
      {
         LinearLayout row = (LinearLayout) board.getChildAt(y);
         for (int x = 0; x < row.getChildCount(); x++)
         {
            ImageButton tile = (ImageButton) row.getChildAt(x);
            Position position = new Position(x, y);
            tile.setOnClickListener(v -> {
               if (activePiece.isPresent())
               {
                  if (possiblePositions.contains(position))
                  {
                     // TODO: 01.05.2019 Image animation

                     activePiece = Optional.empty();
                  }
               }
               else
               {
                  activePiece = Optional.of(tile);
                  possiblePositions = GameManager.getPossibleMoves(position, false);
               }
            });
         }
      }
   }

   @Override
   protected void onResume()
   {
      super.onResume();
      hideSystemUI();
   }

   private void hideSystemUI()
   {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
   }
}
