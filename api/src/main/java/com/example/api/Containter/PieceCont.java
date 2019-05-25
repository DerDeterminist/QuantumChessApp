package com.example.api.Containter;

import com.example.backend.Pieces.Piece;
import lombok.Data;

@Data
public class PieceCont
{
   private PieceType type;
   private double status;
   private int x;
   private int y;

   public PieceCont(Piece piece)
   {
      this.type = PieceType.valueOf(piece.getClass().getSimpleName().toUpperCase());
      this.status = piece.getStatus();
      this.x = piece.getCurrentTile().getX();
      this.y = piece.getCurrentTile().getY();
   }
}