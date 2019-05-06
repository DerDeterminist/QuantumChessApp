package com.example.backend.Game;

import java.util.Arrays;

public class DefaultGameImpl extends GameImpl
{

   /**
    * One variant of {@code GameImpl} witch is basically normal chess
    *
    * @param playerWhite player one
    * @param playerBlack player two
    * @see GameImpl
    */
   public DefaultGameImpl(Player playerWhite, Player playerBlack)
   {
      super(8, 8, playerWhite, playerBlack);

      Board board = getBoard();

      playerWhite.setPieces(Arrays.asList(
            board.instantiatePiece(playerWhite, 0, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 1, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 2, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 3, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 4, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 5, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 6, 1, "Pawn"),
            board.instantiatePiece(playerWhite, 7, 1, "Pawn"),

            board.instantiatePiece(playerWhite, 0, 0, "Rook"),
            board.instantiatePiece(playerWhite, 1, 0, "Knight"),
            board.instantiatePiece(playerWhite, 2, 0, "Bishop"),
            board.instantiatePiece(playerWhite, 3, 0, "Queen"),
            board.instantiatePiece(playerWhite, 4, 0, "King"),
            board.instantiatePiece(playerWhite, 5, 0, "Bishop"),
            board.instantiatePiece(playerWhite, 6, 0, "Knight"),
            board.instantiatePiece(playerWhite, 7, 0, "Rook")));

      playerBlack.setPieces(Arrays.asList(
            board.instantiatePiece(playerBlack, 0, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 1, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 2, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 3, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 4, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 5, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 6, 6, "Pawn"),
            board.instantiatePiece(playerBlack, 7, 6, "Pawn"),

            board.instantiatePiece(playerBlack, 0, 7, "Rook"),
            board.instantiatePiece(playerBlack, 1, 7, "Knight"),
            board.instantiatePiece(playerBlack, 2, 7, "Bishop"),
            board.instantiatePiece(playerBlack, 3, 7, "Queen"),
            board.instantiatePiece(playerBlack, 4, 7, "King"),
            board.instantiatePiece(playerBlack, 5, 7, "Bishop"),
            board.instantiatePiece(playerBlack, 6, 7, "Knight"),
            board.instantiatePiece(playerBlack, 7, 7, "Rook")));

      try
      {
         BordBuilder.buildRectangle(board, playerWhite, playerBlack);
      }
      catch (OutOfBoardException e)
      {
         e.printStackTrace();
      }
   }
}
