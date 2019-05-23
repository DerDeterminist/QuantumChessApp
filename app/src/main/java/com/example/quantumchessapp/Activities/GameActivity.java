package com.example.quantumchessapp.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.api.GameVariant;
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
   private List<Position> possiblePositions = Collections.emptyList();

   @SuppressLint("ResourceType")
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.game_activity);
      board = findViewById(R.id.board);
      background = findViewById(R.id.background_game);

      GameManager.newGame(new Player(), new Player(), GameVariant.OFFLINE);

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
            Position position = new Position(x, y);
            ImageButton piece = getImageButtonAt(position);
            piece.setOnClickListener(v -> {
               if (activePiece != null)
               {
                  GameManager.movePiece(activePosition, position, false);
                  if (GameManager.isLastMoveValid())
                  {
                     v.setBackground(activePiece.getBackground());
                     activePiece.setBackgroundResource(R.drawable.transparent);
                     ((ImageButton) v).setImageDrawable(activePiece.getDrawable());
                     activePiece.setImageDrawable(null);

                     Toast next_player = Toast.makeText(this, getString(R.string.nextPlayer), Toast.LENGTH_SHORT);
                     next_player.setGravity(Gravity.CENTER, 0, 0);
                     next_player.show();
                  }
                  deSelect();
               }
               else
               {
                  if (GameManager.isPieceOfActivePlayer(position))
                  {
                     activePiece = piece;
                     activePosition = position;
                     possiblePositions = GameManager.getPossibleMoves(position, false);
                     possiblePositions.stream()
                           .map(this::getImageButtonAt)
                           .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.selected));
                  }
                  else
                  {
                     deSelect();
                  }
               }
            });
         }
      }
   }

   private void deSelect()
   {
      activePiece = null;
      activePosition = null;
      possiblePositions.stream()
            .map(this::getImageButtonAt)
            .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.transparent));
      possiblePositions = Collections.emptyList();
   }

   private ImageButton getImageButtonAt(Position position)
   {
      return (ImageButton) ((FrameLayout) ((LinearLayout)
            board.getChildAt(position.getY()))
            .getChildAt(position.getX()))
            .getChildAt(0);
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
