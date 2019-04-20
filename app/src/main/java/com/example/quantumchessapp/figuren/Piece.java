package com.example.quantumchessapp.figuren;

import android.content.Context;
import com.example.quantumchessapp.R;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

public class Piece extends android.support.v7.widget.AppCompatImageView
{
   private Position position;
   private Player owner;
   private double status;
   private PieceColor color;
   private PieceType type;

   public Piece(Context context, Position position, Player owner, double status, PieceColor color, PieceType type)
   {
      super(context);
      this.position = position;
      this.owner = owner;
      this.status = status;
      this.color = color;
      this.type = type;

      setImageResource(getImageResource());
   }

   private int getImageResource()
   {
      switch (type)
      {
         case Bishop:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackbishop : R.drawable.whitebishop;
         case King:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackking : R.drawable.whiteking;
         case Knight:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackknight : R.drawable.whiteknight;
         case Pawn:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackpawn : R.drawable.whitepawn;
         case Queen:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackqueen : R.drawable.whitequeen;
         case Rook:
            return color.equals(PieceColor.BLACK) ? R.drawable.blackrook : R.drawable.whiterook;
         default:
            throw new IllegalArgumentException();
      }
   }

   public enum PieceType
   {
      Bishop,
      King,
      Knight,
      Pawn,
      Queen,
      Rook
   }

   public enum PieceColor
   {
      WHITE,
      BLACK
   }

   public Position getPosition()
   {
      return position;
   }

   public void setPosition(Position position)
   {
      this.position = position;
   }
}