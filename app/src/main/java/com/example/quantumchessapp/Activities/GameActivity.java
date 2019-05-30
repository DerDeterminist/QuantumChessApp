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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.PieceCont;
import com.example.api.GameVariant;
import com.example.quantumchessapp.GameManager;
import com.example.quantumchessapp.PieceRenderer;
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

   private LinearLayout blackCapituredPieces;
   private LinearLayout whiteCapituredPieces;

   private ChangeCont change;

   @SuppressLint("ResourceType")
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.game_activity);
      board = findViewById(R.id.board);
      background = findViewById(R.id.background_game);
      blackCapituredPieces = findViewById(R.id.blackCapituredPieces);
      whiteCapituredPieces = findViewById(R.id.whiteCapituredPieces);

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
                  change = GameManager.movePiece(activePosition, position, false);
                  if (GameManager.isLastMoveValid())
                  {
                     change.getRemoved().forEach(this::remove);
                     change.getAdded().forEach(this::add);
                     change.getChanged().forEach(this::change);

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

   private void addToCapituredPieces(PieceCont piece)
   {
      ImageView imageView = new ImageView(this);
      imageView.setImageDrawable(PieceRenderer.getPieceDrawable(piece, this));
      switch (piece.getColor())
      {
         case WHITE:
            blackCapituredPieces.addView(imageView);
            break;
         case BLACK:
            whiteCapituredPieces.addView(imageView);
            break;
      }
   }

   private void add(PieceCont cont)
   {
      ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
      imageButton.setImageDrawable(PieceRenderer.getPieceDrawable(cont, this));
   }

   private void remove(PieceCont cont)
   {
      ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
      imageButton.setImageDrawable(null);
      if (!change.getAdded().contains(cont))
      {
         addToCapituredPieces(cont);
      }
   }

   private void change(PieceCont cont)
   {
      // TODO: 26.05.2019
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

   private ImageButton getImageButtonAt(int x, int y)
   {
      return getImageButtonAt(new Position(x, y));
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
