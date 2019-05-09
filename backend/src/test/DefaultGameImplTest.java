import com.example.backend.Game.ResponseStatus;
import com.example.backend.GameManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
class DefaultGameImplTest
{
   // TODO: 09.05.2019 change test so it only uses methods of gameManager
   private GameManager gameManager;
   private String gameID;

   @Before
   void setUp()
   {
      gameManager = GameManager.getInstance();
   }

   @Test
   void testPlayers()
   {
      gameID = gameManager.startGame();
   }

   @Test
   void testInitialPossibleMovement()
   {
      for (int i = 0; i < gameManager.getBoard(gameID).getWith() && i != 1 & i != 6; i++)
      {
         assertTrue(gameManager.getPossibleMoves(gameID,i, 0, false).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 0, true).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 7, false).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 7, true).isEmpty());

         assertEquals((2), gameManager.getPossibleMoves(gameID, i, 1, false).size());
         assertEquals((3), gameManager.getPossibleMoves(gameID, i, 1, true).size());
         assertEquals((2), gameManager.getPossibleMoves(gameID, i, 6, false).size());
         assertEquals((3), gameManager.getPossibleMoves(gameID, i, 6, true).size());

         assertTrue(gameManager.getPossibleMoves(gameID,i, 2, true).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 2, false).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 3, true).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 3, false).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 4, true).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 4, false).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 5, true).isEmpty());
         assertTrue(gameManager.getPossibleMoves(gameID,i, 5, false).isEmpty());
      }

      assertEquals((2), gameManager.getPossibleMoves(gameID, 1, 0, false).size());
      assertEquals((7), gameManager.getPossibleMoves(gameID, 1, 0, true).size());
      assertEquals((2), gameManager.getPossibleMoves(gameID, 6, 0, false).size());
      assertEquals((7), gameManager.getPossibleMoves(gameID, 6, 0, true).size());

      assertEquals((2), gameManager.getPossibleMoves(gameID, 6, 7, false).size());
      assertEquals((7), gameManager.getPossibleMoves(gameID, 6, 7, true).size());
      assertEquals((2), gameManager.getPossibleMoves(gameID, 1, 7, false).size());
      assertEquals((7), gameManager.getPossibleMoves(gameID, 1, 7, true).size());

      assertEquals((2), gameManager.getPossibleMoves(gameID, 1, 1, false).size());
      assertEquals((3), gameManager.getPossibleMoves(gameID, 1, 1, true).size());
      assertEquals((2), gameManager.getPossibleMoves(gameID, 1, 6, false).size());
      assertEquals((3), gameManager.getPossibleMoves(gameID, 1, 6, true).size());
      assertEquals((2), gameManager.getPossibleMoves(gameID, 6, 6, false).size());
      assertEquals((3), gameManager.getPossibleMoves(gameID, 6, 6, true).size());
      assertEquals((2), gameManager.getPossibleMoves(gameID, 6, 1, false).size());
      assertEquals((3), gameManager.getPossibleMoves(gameID, 6, 1, true).size());
   }

   @Test
   void testGetCompleteBord()
   {
//      checkCompleteBord(32);
   }

   @Test
   void testNMovement()
   {
      assertTrue(gameManager.movePiece(gameID,0, 1, 0, 3, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(0, 1).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 3).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,0, 0, 0, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(0, 0).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 1).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,1, 0, 0, 2, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(1, 0).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 2).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,1, 1, 1, 3, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(1, 1).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(1, 3).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,2, 0, 1, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(2, 0).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(1, 1).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,3, 1, 3, 3, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(3, 1).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(3, 3).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,3, 0, 3, 2, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(3, 0).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(3, 2).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,4, 0, 3, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(4, 0).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(3, 1).getPiece().isPresent());

//      checkCompleteBord(32);
   }

   @Test
   void testCapture()
   {
      assertTrue(gameManager.movePiece(gameID,0, 6, 0, 4, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(0, 6).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 4).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,1, 1, 1, 3, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(1, 1).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(1, 3).getPiece().isPresent());

      assertTrue(gameManager.movePiece(gameID,1, 3, 0, 4, false).isLastMoveWasValid());
      assertFalse(gameManager.getBoard(gameID).getTileAt(1, 3).getPiece().isPresent());
      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 4).getPiece().isPresent());

//      checkCompleteBord(31);
   }

//   @Test
//   void testQMovement()
//   {
//      assertTrue(gameManager.movePiece(gameID,0, 1, 0, 3, false).isLastMoveWasValid());
//      assertFalse(gameManager.getBoard(gameID).getTileAt(0, 1).getPiece().isPresent());
//      assertTrue(gameManager.getBoard(gameID).getTileAt(0, 3).getPiece().isPresent());
//
//      assertTrue(gameManager.movePiece(gameID,1, 0, 1, 4, true).isLastMoveWasValid());
//      checkQPiece(1, 0, 1, 4);
//
//      assertTrue(gameManager.movePiece(gameID,0, 0, 0, 2, true).isLastMoveWasValid());
//      checkQPiece(0, 0, 0, 2);
//
//      assertTrue(gameManager.movePiece(gameID,7, 1, 7, 4, true).isLastMoveWasValid());
//      checkQPiece(7, 1, 7, 4);
//
//      assertTrue(gameManager.movePiece(gameID,4, 1, 4, 2, false).isLastMoveWasValid());
//      assertFalse(gameManager.getBoard(gameID).getTileAt(4, 1).getPiece().isPresent());
//      assertTrue(gameManager.getBoard(gameID).getTileAt(4, 2).getPiece().isPresent());
//
//      assertTrue(gameManager.movePiece(gameID,3, 0, 4, 3, true).isLastMoveWasValid());
//      checkQPiece(3, 0, 4, 3);
//
//      assertTrue(gameManager.movePiece(gameID,5, 0, 3, 4, true).isLastMoveWasValid());
//      checkQPiece(5, 0, 3, 4);
//
//      assertTrue(gameManager.movePiece(gameID,4, 0, 3, 2, true).isLastMoveWasValid());
//      checkQPiece(4, 0, 3, 2);
//   }

   @Test
   void testWrongInput()
   {
      assertTrue(gameManager.getPossibleMoves(gameID,-1, -1, true).isEmpty());
      assertTrue(gameManager.getPossibleMoves(gameID,-1, -1, false).isEmpty());
      assertFalse(gameManager.movePiece(gameID,-1, -1, 1, 1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,-1, -1, 1, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, -1, -1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, -1, -1, false).isLastMoveWasValid());

      assertTrue(gameManager.getPossibleMoves(gameID,1, -1, true).isEmpty());
      assertTrue(gameManager.getPossibleMoves(gameID,1, -1, false).isEmpty());
      assertFalse(gameManager.movePiece(gameID,1, -1, 1, 1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, -1, 1, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, 1, -1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, 1, -1, false).isLastMoveWasValid());

      assertTrue(gameManager.getPossibleMoves(gameID,-1, 1, true).isEmpty());
      assertTrue(gameManager.getPossibleMoves(gameID,-1, 1, false).isEmpty());
      assertFalse(gameManager.movePiece(gameID,-1, 1, 1, 1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,-1, 1, 1, 1, false).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, -1, 1, true).isLastMoveWasValid());
      assertFalse(gameManager.movePiece(gameID,1, 1, -1, 1, false).isLastMoveWasValid());
   }

   @Test
   void testWinning()
   {
      checkResponseStatus(gameManager.movePiece(gameID,4, 6, 4, 5, false), true, false, 0, -1);
      checkResponseStatus(gameManager.movePiece(gameID,4, 1, 4, 2, false), true, false, 0, -1);
      checkResponseStatus(gameManager.movePiece(gameID,3, 0, 7, 4, false), true, false, 0, -1);
      checkResponseStatus(gameManager.movePiece(gameID,7, 4, 4, 7, false), true, true, 1, 0);
   }
//
//   @Test
//   void testMeasurement()
//   {
//      List<Boolean> stats = new ArrayList<>();
//      stats.add(Boolean.FALSE);
//      stats.add(Boolean.TRUE);
//      while (!stats.isEmpty())
//      {
//         setUp();
//
//         assertTrue(gameManager.movePiece(gameID,0, 1, 0, 4, true).isLastMoveWasValid());
//         checkQPiece(0, 1, 0, 4);
//
//         boolean moved = gameManager.movePiece(gameID,0, 6, 0, 4, true).isLastMoveWasValid();
//         stats.remove(moved);
//         if (moved)
//         {
//            Assertions
//                  .assertEquals((Piece.MAX_STATUS / 2), gameManager.getBoard(gameID).getTileAt(0, 6).getPiece().get().getStatus());
//            assertEquals((gameManager.getBoard(gameID).getTileAt(0, 4).getPiece().get().getInstances().get(0),
//                  gameManager.getBoard(gameID).getTileAt(0, 6).getPiece().get());
//         }
//         else
//         {
//            assertEquals((Piece.MAX_STATUS, gameManager.getBoard(gameID).getTileAt(0, 6).getPiece().get().getStatus());
//            assertEquals((gameManager.getBoard(gameID).getTileAt(0, 4).getPiece().get().getInstances().get(0),
//                  gameManager.getBoard(gameID).getTileAt(0, 1).getPiece().get());
//         }
//      }
//   }

   private void checkResponseStatus(ResponseStatus responseStatus, boolean valid, boolean won, int losers, int winner)
   {
      assertEquals((valid), responseStatus.isLastMoveWasValid());
      assertEquals((won), responseStatus.isGameWon());
      assertEquals((losers), responseStatus.getLoser().size());
      assertEquals((winner), responseStatus.getWinner());
   }

//   private void checkCompleteBord(int anzPieces)
//   {
//      Board board = gameManager.getBoard(gameID);
//      assertEquals((8), board.getHeight());
//      assertEquals((8), board.getWith());
//      assertEquals((anzPieces), board.);
//   }

//   private void checkQPiece(int... coordinates)
//   {
//      for (int i = 0; i < coordinates.length; i = i + 2)
//      {
//         Tile tile = gameManager.getBoard(gameID).getTileAt(coordinates[i], coordinates[i + 1]);
//         assertEquals((Piece.MAX_STATUS / coordinates.length * 2), tile.getPiece().get().getStatus());
//      }
//   }
}