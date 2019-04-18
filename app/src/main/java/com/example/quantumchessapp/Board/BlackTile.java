package com.example.quantumchessapp.Board;

import android.content.Context;
import android.graphics.Color;

public class BlackTile extends Tile
{

   public BlackTile(Context context)
   {
      super(context);
      m_view.setColorFilter(Color.BLACK);
   }
}
