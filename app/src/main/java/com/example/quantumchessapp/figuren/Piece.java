package com.example.quantumchessapp.figuren;

import android.content.Context;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

public abstract class Piece extends android.support.v7.widget.AppCompatImageView
{
   private Position position;
   private Player owner;
   private double status;
   protected PieceColor color;

   public Piece(Context context, Position position, Player owner, double status, PieceColor color)
   {
      super(context);
      this.position = position;
      this.owner = owner;
      this.status = status;
      this.color = color;
      setImageResource(getImageResource());
   }

   protected abstract int getImageResource();


   public enum PieceColor
   {
      WHITE,
      BLACK
   }
}