package com.example.api;

import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.PlayerCont;
import com.example.api.Containter.TileCont;
import lombok.Data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Data
public class GameModel
{
   private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

   private String gameID;

   private List<PlayerCont> players;
   private boolean isGameWon;
   private boolean lastMoveWasValid;
   private PlayerCont winner;
   private List<TileCont> possibleMoves;

   private int wight;
   private int height;
   private double maxPieceStatus;

   private ChangeCont change;

   private boolean pieceOfActivePlayer;

   public void addPropertyChangeListener(String property, PropertyChangeListener listener)
   {
      propertyChangeSupport.addPropertyChangeListener(property, listener);
   }

   public void removePropertyChangeListener(PropertyChangeListener listener)
   {
      propertyChangeSupport.removePropertyChangeListener(listener);
   }

   public void setGameID(String gameID)
   {
      propertyChangeSupport.firePropertyChange("start", null, gameID);
      this.gameID = gameID;
   }

   public void setPossibleMoves(List<TileCont> possibleMoves)
   {
      propertyChangeSupport.firePropertyChange("possibleMoves", null, possibleMoves);
      this.possibleMoves = possibleMoves;
   }

   public void setChange(ChangeCont change)
   {
      propertyChangeSupport.firePropertyChange("move", null, change);
      this.change = change;
   }

   public void setHeight(int height)
   {
      propertyChangeSupport.firePropertyChange("board", null, height);
      this.height = height;
   }

   public void setPieceOfActivePlayer(boolean pieceOfActivePlayer)
   {
      propertyChangeSupport.firePropertyChange("pieceOfActivePlayer", !pieceOfActivePlayer, pieceOfActivePlayer);
      this.pieceOfActivePlayer = pieceOfActivePlayer;
   }

   public void clearListeners()
   {
      for (PropertyChangeListener propertyChangeListener : propertyChangeSupport.getPropertyChangeListeners())
      {
         propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
      }
   }
}
