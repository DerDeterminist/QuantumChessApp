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

import com.example.quantumchessapp.GameManager;
import com.example.quantumchessapp.GameVariant;
import com.example.quantumchessapp.PieceRenderer;
import com.example.quantumchessapp.Position;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.conts.Change;
import com.example.quantumchessapp.conts.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Activity fo playing any chess mode
 * <p>
 * extras:
 * - boolean "allowQMove" : are quantum moves allowed
 * - GameVariant "variant" : online or offline
 *
 * @see GameVariant
 */
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
   private Change change;

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
      GameVariant variant = (GameVariant) gameActivityIntent.getSerializableExtra("variant");

      findViews();

      GameManager.newGame(this, variant, gameState -> {
      });

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

   /**
    * Sets up a StatusIndicator that shows how likely it is that the Piece will be found there
    *
    * @param piece          the Piece for which to show the StatusIndicator
    * @param maxPieceStatus the maximal status a piece can have
    */
   private void addStatusIndicator(Piece piece, double maxPieceStatus)
   {
      ProgressBar progressBar = getProgressBarAt(piece.getX(), piece.getY());
      if (piece.getStatus() == maxPieceStatus)
      {
         progressBar.setVisibility(View.INVISIBLE);
      }
      else
      {
         progressBar.setMax((int) maxPieceStatus);
         progressBar.setProgress((int) piece.getStatus());
         progressBar.setVisibility(View.VISIBLE);
         progressBar.invalidate();
      }
   }

   /**
    * the heart of this activity. Controls every user input. Controls only. Implements the ObservablePattern
    *
    * @param position position the user clicked
    * @param piece    the ImageButton the user clicked
    * @param qMove    is this the setup for a quantum move
    */
   private void pieceOnClick(Position position, ImageButton piece, boolean qMove)
   {
      if (!GameManager.getStatus().isGameWon())
      {
         if (activePiece != null)
         {
            GameManager.movePiece(activePosition, position, this.qMove, state -> {
               if (state.getStatus().isLastMoveValid())
               {
                  markLastMove(R.drawable.transparent);
                  change = state.getChange();

                  change.getChanged().forEach(piece1 -> change(piece1, state.getBoard().getMaxPieceStatus()));
                  remove(change.getRemoved());
                  change.getAdded().forEach(piece1 -> add(piece1, state.getBoard().getMaxPieceStatus()));

                  if (state.getStatus().isGameWon())
                  {
                     showWinner();
                     return;
                  }
               }
               deSelect();
            });
         }
         else
         {
            GameManager.isPieceOfActivePlayer(position, pieceOfActivePlayer -> {
               if (pieceOfActivePlayer)
               {
                  setActivePiece(position, piece, qMove);
               }
               else
               {
                  deSelect();
               }
            });
         }
      }
   }

   /**
    * Sets the selected Piece the user wants to move with the next input
    */
   private void setActivePiece(Position position, ImageButton piece, boolean qMove)
   {
      this.qMove = qMove;
      activePiece = piece;
      activePosition = position;
      GameManager.getPossibleMoves(position, qMove, tileStatus -> {
         possiblePositions = tileStatus.getTiles().stream().map(
               tile -> new Position(tile.getX(), tile.getY())).collect(Collectors.toList());

         possiblePositions.stream()
               .map(GameActivity.this::getImageButtonAt)
               .forEach(imageButton -> imageButton.setBackgroundResource(R.drawable.selected));
      });
   }

   /**
    * Shows the winner of the Game.
    */
   private void showWinner()
   {
      Toast toast = Toast.makeText(this, "Game Over", Toast.LENGTH_LONG);
      toast.show();
      deSelect();
   }

   /**
    * Adds the Piece to the respective LinearLayout that holds the captured pieces
    *
    * @param piece the piece to add
    */
   private void addToCapturedPieces(Piece piece)
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

   /**
    * gets called when a piece wars added
    *
    * @param piece          Piece that got added
    * @param maxPieceStatus the maximal status a piece can have
    */
   private void add(Piece piece, double maxPieceStatus)
   {
      ImageButton newSpot = getImageButtonAt(piece.getX(), piece.getY());
      addStatusIndicator(piece, maxPieceStatus);
      newSpot.setImageDrawable(PieceRenderer.getPieceDrawable(piece, this));
      List<Piece> list = new ArrayList<>();
      for (Piece Piece1 : change.getRemoved())
      {
         if (piece.equals(Piece1))
         {
            list.add(Piece1);
         }
      }
      // when a Piece wars moved normally
      if (list.size() == 1)
      {
         Piece cont1 = list.get(0);
         lastOrigin = getImageButtonAt(cont1.getX(), cont1.getY());
         lastNewSpot = newSpot;
         markLastMove(R.drawable.lastmove);
      }
      // when a Piece wars split in 2
      change.getChanged().stream()
            .filter(Piece -> Piece.equals(piece) && (Piece.getX() != piece.getX() || Piece.getY() != piece.getY()))
            .findAny()
            .ifPresent(Piece -> {
               lastNewSpot = getImageButtonAt(Piece.getX(), Piece.getY());
               lastOrigin = newSpot;
               markLastMove(R.drawable.lastmove);
            });
   }

   /**
    * Gets called when a piece wars removed
    *
    * @param conts Pieces that got removed
    */
   private void remove(List<Piece> conts)
   {
      conts.forEach(cont -> {
         ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
         imageButton.setImageDrawable(null);
         if (allowQMove)
         {
            ProgressBar progressBar = getProgressBarAt(cont.getX(), cont.getY());
            progressBar.setVisibility(View.INVISIBLE);
         }
      });

      conts.stream()
            .distinct()
            .filter(cont -> !change.getAdded().contains(cont))
            .forEach(this::addToCapturedPieces);
   }

   /**
    * Gets called when a piece wars changed
    *
    * @param cont           Piece that got changed
    * @param maxPieceStatus the maximal status a piece can have
    */
   private void change(Piece cont, double maxPieceStatus)
   {
      ImageButton imageButton = getImageButtonAt(cont.getX(), cont.getY());
      imageButton.setImageDrawable(PieceRenderer.getPieceDrawable(cont, this));
      addStatusIndicator(cont, maxPieceStatus);
   }

   /**
    * @param resource with witch resource? red/transparent for example
    */
   private void markLastMove(int resource)
   {
      if (lastOrigin != null && lastNewSpot != null)
      {
         lastOrigin.setBackgroundResource(resource);
         lastNewSpot.setBackgroundResource(resource);
      }
   }

   /**
    * removes the selection of the user
    */
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
