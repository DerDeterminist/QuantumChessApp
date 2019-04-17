package com.example.quantumchessapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.example.quantumchessapp.spiel.Position;

public class Board extends View
{
   private int tilesInALine;

   public Board(Context context, int tilesInALine)
   {
      super(context);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int tilesInALine)
   {
      super(context, attrs);
      this.tilesInALine = tilesInALine;
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr, int tilesInALine)
   {
      super(context, attrs, defStyleAttr);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int tilesInALine)
   {
      super(context, attrs, defStyleAttr, defStyleRes);
      initUI();
   }

   private void initUI()
   {
   }

   public void addView(View view, Position position)
   {
      addView(view, position.getX(), position.getY());
   }

   public void addView(View view, int x, int y)
   {

   }
}
