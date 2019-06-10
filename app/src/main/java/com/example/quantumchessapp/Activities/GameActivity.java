package com.example.quantumchessapp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.PieceCont;
import com.example.api.Containter.TileCont;
import com.example.quantumchessapp.GameManager;
import com.example.quantumchessapp.GameVariant;
import com.example.quantumchessapp.PieceRenderer;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity
{
   //if quantum moves are allowed. get set as a extra before starting this activity
   boolean allowQMove;

   //layouts
   private LinearLayout board;
   private ConstraintLayout background;

   //the selected piece and additional information
   private ImageButton activePiece = null;
   private Position activePosition;
   private List<Position> possiblePositions = Collections.emptyList();
   private boolean qMove;

   //layout for the captured pieces.
   private LinearLayout blackCapturedPieces;
   private LinearLayout whiteCapturedPieces;

   //represents the changes to pieces in the last move
   private ChangeCont change;

   //helps to indicate the last move
   private ImageButton lastOrigin;
   private ImageButton lastNewSpot;

   @SuppressLint("ResourceType")
   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_game);

      Intent gameActivityIntent = getIntent();
      allowQMove = gameActivityIntent.getBooleanExtra("allowQMove", false);

      findViews();

      GameManager.newGame(this, new Player(), new Player(), GameVariant.OFFLINE);

      initListener();
   }

   private void findViews()
   {
      board = findViewById(R.id.board);
      background = findViewById(R.id.background_game);
      blackCapturedPieces = findViewById(R.id.blackCapturedPieces);
      whiteCapturedPieces = findViewById(R.id.whiteCapturedPieces);
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

            piece.setOnLongClickListener(v -> {
               pieceOnClick(position, piece, allowQMove);
               return true;
            });
            piece.setOnClickListener(v -> pieceOnClick(position, piece, false));
         }
      }
   }

   private void addStatusIndicator(PieceCont cont)
   {
      ProgressBar progressBar = getProgressBarAt(cont.getX(), cont.getY());
      if (cont.getStatus() == GameManager.getModel().getMaxPieceStatus())
      {
         progressBar.setVisibility(View.INVISIBLE);
      }
      else
      {
         progressBar.setMax((int) GameManager.getModel().getMaxPieceStatus());
         progressBar.setProgress((int) cont.getStatus());
         progressBar.setVisibility(View.VISIBLE);
         progressBar.invalidate();
      }
   }

   private void pieceOnClick(Position position, ImageButton piece, boolean qMove)
   {
      if (!GameManager.getModel().isGameWon())
      {
         if (activePiece != null)
         {
            PropertyChangeListener moveListener = evt -> {
               if (GameManager.getModel().isLastMoveWasValid())
               {
                  removeLastMoveIndicator();
                  change = (ChangeCont) evt.getNewValue();

                  change.getChanged().forEach(GameActivity.this::change);
                  change.getRemoved().forEach(GameActivity.this::remove);
                  change.getAdded().forEach(GameActivity.this::add);

                  if (GameManager.getModel().isGameWon())
                  {
                     showWinner();
                     return;
                  }
               }
               deSelect();
               GameManager.getModel().clearListeners();
            };
            GameManager.addPropertyChangeListener("move", moveListener);
            GameManager.movePiece(activePosition, position, this.qMove);
         }
         else
         {
            PropertyChangeListener changeListener = evt -> {
               System.out.println(Thread.currentThread().getName());
               if (((boolean) evt.getNewValue()))
               {
                  setActivePiece(position, piece, qMove);
               }
               else
               {
                  deSelect();
                  GameManager.getModel().clearListeners();
               }
            };
            GameManager.addPropertyChangeListener("pieceOfActivePlayer", changeListener);
            GameManager.isPieceOfActivePlayer(position);
         }
      }
   }

   private void removeLastMoveIndicator()
   {
      if (lastOrigin != null && lastNewSpot != null)
      {
         markLastMove(R.drawable.transparent);
      }
   }

   private void setActivePiece(Position position, ImageButton piece, boolean qMove)
   {
      this.qMove = qMove;
      activePiece = piece;
      activePosition = position;
      PropertyChangeListener possibleMovesListener = evt -> {
         possiblePositions = ((List<TileCont>) evt.getNewValue()).stream().map(
               tileCont -> GameManager.convertXYToPosition(tileCont.getX(), tileCont.getY())).collect(Collectors.toList());

         possiblePositions.stream()
               .map(GameActivity.this::getImageButtonAt)
               .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.selected));
         GameManager.getModel().clearListeners();
      };
      GameManager.addPropertyChangeListener("possibleMoves", possibleMovesListener);
      GameManager.getPossibleMoves(position, qMove);
   }

   private void showWinner()
   {
      Toast toast = Toast.makeText(this, "Game Over", Toast.LENGTH_LONG);
      toast.show();
      deSelect();
   }

   private void addToCapturedPieces(PieceCont piece)
   {
      ImageView imageView = new ImageView(this);
      imageView.setImageDrawable(PieceRenderer.getPieceDrawable(piece, this));
      LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.MATCH_PARENT,
                  1);
      imageView.setLayoutParams(layoutParams);
      switch (piece.getColor())
      {
         case WHITE:
            blackCapturedPieces.addView(imageView);
            break;
         case BLACK:
            whiteCapturedPieces.addView(imageView);
            break;
      }
   }

   private void add(PieceCont cont)
   {
      ImageButton newSpot = getImageButtonAt(cont.getX(), cont.getY());
      newSpot.setImageDrawable(PieceRenderer.getPieceDrawable(cont, this));
      change.getRemoved().stream()
            .filter(cont::equals)
            .findAny()
            .ifPresent(cont1 -> {
               lastOrigin = getImageButtonAt(cont1.getX(), cont1.getY());
               lastNewSpot = newSpot;
               markLastMove(R.drawable.lastmove);
            });
      if (allowQMove && cont.getStatus() < GameManager.getModel().getMaxPieceStatus())
      {
         addStatusIndicator(cont);
      }
   }

   private void markLastMove(int resource)
   {
      lastOrigin.setBackgroundResource(resource);
      lastNewSpot.setBackgroundResource(resource);
   }

   private void remove(PieceCont cont)
   {
      ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
      imageButton.setImageDrawable(null);
      if (allowQMove)
      {
         ProgressBar progressBar = getProgressBarAt(cont.getX(), cont.getY());
         progressBar.setVisibility(View.INVISIBLE);
      }
      if (!change.getAdded().contains(cont))
      {
         addToCapturedPieces(cont);
      }
   }

   private void change(PieceCont cont)
   {
      ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
      imageButton.setImageDrawable(PieceRenderer.getPieceDrawable(cont, this));
      addStatusIndicator(cont);
   }

   private void deSelect()
   {
      activePiece = null;
      activePosition = null;
      possiblePositions.stream()
            .map(this::getImageButtonAt)
            .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.transparent));
      possiblePositions = Collections.emptyList();
      markLastMove(R.drawable.lastmove);
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

   private ProgressBar getProgressBarAt(int x, int y)
   {
      return (ProgressBar) ((FrameLayout) ((LinearLayout)
            board.getChildAt(y))
            .getChildAt(x))
            .getChildAt(1);
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
