package com.example.quantumchessapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.api.Containter.PieceCont;
import com.example.quantumchessapp.conts.Piece;
import com.example.quantumchessapp.conts.PieceColor;

/**
 * Maps the Piece form the api to a Drawable
 * @see PieceCont
 * @see Drawable
 */
public class PieceRenderer
{
   public static Drawable getPieceDrawable(Piece cont, Context context)
   {
      if (cont.getColor().equals(PieceColor.WHITE))
      {
         switch (cont.getType())
         {
            case BISHOP:
               return getDrawableById(R.drawable.whitebishop, context);
            case KING:
               return getDrawableById(R.drawable.whiteking, context);
            case KNIGHT:
               return getDrawableById(R.drawable.whiteknight, context);
            case PAWN:
               return getDrawableById(R.drawable.whitepawn, context);
            case QUEEN:
               return getDrawableById(R.drawable.whitequeen, context);
            case ROOK:
               return getDrawableById(R.drawable.whiterook, context);
         }
      }
      else
      {
         switch (cont.getType())
         {
            case BISHOP:
               return getDrawableById(R.drawable.blackbishop, context);
            case KING:
               return getDrawableById(R.drawable.blackking, context);
            case KNIGHT:
               return getDrawableById(R.drawable.blackknight, context);
            case PAWN:
               return getDrawableById(R.drawable.blackpawn, context);
            case QUEEN:
               return getDrawableById(R.drawable.blackqueen, context);
            case ROOK:
               return getDrawableById(R.drawable.blackrook, context);
         }
      }
      throw new RuntimeException();
   }

   private static Drawable getDrawableById(int id, Context context)
   {
      return ContextCompat.getDrawable(context, id);
   }
}
