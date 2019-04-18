package com.example.quantumchessapp.Board;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.quantumchessapp.spiel.Position;

public class Board extends ViewGroup
{
   private int tilesInALine;
   private Context m_context;

   LinearLayout height;

   public Board(Context context)
   {
      super(context);
      this.tilesInALine = 5;
      m_context = context;
      height = new LinearLayout(m_context);
      initUI();
   }

   public Board(Context context, AttributeSet attrs)
   {
      super(context, attrs);
      this.tilesInALine = tilesInALine;
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr)
   {
      super(context, attrs, defStyleAttr);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
   {
      super(context, attrs, defStyleAttr, defStyleRes);
      initUI();
   }

   private void initUI()
   {
      for (int i = 0; i < tilesInALine; i++)
      {
         LinearLayout linearLayout = new LinearLayout(m_context);
         linearLayout.setOrientation(LinearLayout.HORIZONTAL);
         linearLayout.addView(i % 2 == 0 ? new BlackTile(m_context) : new WhiteTile(m_context), 50, 50);
         height.addView(linearLayout);
      }
      this.addView(height);
      LayoutInflater layoutinflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      layoutinflater.inflate(LayoutParams.MATCH_PARENT, this);
   }

   public void addView(View view, Position position)
   {
      addView(view, position.getX(), position.getY());
   }

   public void addView(View view, int x, int y)
   {
      if (x < tilesInALine && y < tilesInALine)
      {

      }
   }

   @Override
   protected void onLayout(boolean changed, int l, int t, int r, int b)
   {

   }
}
