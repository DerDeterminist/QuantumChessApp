package com.example.quantumchessapp.Board;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class Tile extends android.support.v7.widget.AppCompatImageView
{
   private Context m_context;
   protected ImageView m_view;

   public Tile(Context context)
   {
      super(context);
      m_context = context;
      m_view = new ImageView(context);
   }

   public Tile(Context context, AttributeSet attrs)
   {
      super(context, attrs);
   }

   public Tile(Context context, AttributeSet attrs, int defStyleAttr)
   {
      super(context, attrs, defStyleAttr);
   }

   @Override
   protected void onDraw(Canvas canvas)
   {
      m_view.draw(canvas);
   }
}