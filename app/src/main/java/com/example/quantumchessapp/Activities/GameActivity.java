package com.example.quantumchessapp.Activities;

import android.annotation.SuppressLint;
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

import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity
{
   private LinearLayout board;
   private ConstraintLayout background;

   private ImageButton activePiece = null;
   private Position activePosition;
   private List<Position> possiblePositions;

   @SuppressLint("ResourceType")
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
      background.setOnClickListener(v -> deSelect());
      for (int y = 0; y < board.getChildCount(); y++)
      {
         LinearLayout row = (LinearLayout) board.getChildAt(y);
         for (int x = 0; x < row.getChildCount(); x++)
         {
            ImageButton tile = (ImageButton) row.getChildAt(x);
            Position position = new Position(x, y);
            tile.setOnClickListener(v -> {
               if (activePiece != null)
               {
                  GameManager.movePiece(activePosition, position, false);
                  if (GameManager.isLastMoveValid())
                  {
                     v.setBackground(activePiece.getBackground());
                     activePiece.setBackgroundResource(R.drawable.transparent);
                  }
                  deSelect();
               }
               else
               {
                  activePiece = tile;
                  activePosition = position;
                  possiblePositions = GameManager.getPossibleMoves(position, false);
                  possiblePositions.stream()
                        .map(position1 -> (ImageButton) ((LinearLayout) board.getChildAt(position1.getY()))
                              .getChildAt(position1.getY()))
                        .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.selected));
               }
               board.invalidate();
            });
         }
      }
   }

   private void deSelect()
   {
      activePiece = null;
      activePosition = null;
      possiblePositions.stream()
            .map(position1 -> (ImageButton) ((LinearLayout) board.getChildAt(position1.getY()))
                  .getChildAt(position1.getY()))
            .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.transparent));
      possiblePositions = Collections.emptyList();
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
