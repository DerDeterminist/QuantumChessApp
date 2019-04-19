package com.example.quantumchessapp.figuren;

import android.content.Context;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

@SuppressWarnings("unused")
public class Knight extends Piece
{
   public Knight(Context context, Position position, Player owner, double status,
                 PieceColor color)
   {
      super(context, position, owner, status, color);
   }

   @Override
   protected int getImageResource()
   {
      return color.equals(PieceColor.BLACK) ? R.drawable.blackknight : R.drawable.whiteknight;
   }
}
