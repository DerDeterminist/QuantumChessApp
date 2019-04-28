package com.example.quantumchessapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import com.example.quantumchessapp.GameManager;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.figuren.Piece;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

public class GameActivity extends AppCompatActivity
{

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_game);
      GridView gridView = findViewById(R.id.grid);
      gridView.setNumColumns(GameManager.getWight());

      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
      gridView.addView(new Piece(this,new Position(1,1),new Player(),1, Piece.PieceColor.BLACK, Piece.PieceType.Rook));
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
