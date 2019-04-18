package com.example.quantumchessapp.Board;

import android.content.Context;
import android.graphics.Color;

public class WhiteTile extends Tile
{

   public WhiteTile(Context context)
   {
      super(context);
      m_view.setColorFilter(Color.BLACK);
   }
}
