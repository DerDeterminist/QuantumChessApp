package com.example.quantumchessapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View
{

   Paint black = new Paint();
   Paint wight = new Paint();

   public Board(Context context, int wight, int hight)
   {
      super(context);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int wight, int hight)
   {
      super(context, attrs);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr, int wight, int hight)
   {
      super(context, attrs, defStyleAttr);
      initUI();
   }

   public Board(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int wight, int hight)
   {
      super(context, attrs, defStyleAttr, defStyleRes);
      initUI();
   }

   @Override
   protected void onDraw(Canvas canvas)
   {
      super.onDraw(canvas);
      canvas.drawRect(0, 0, 50, 50, wight);// TODO: 16.04.2019 Schachbrett
   }

   private void initUI()
   {
      black.setColor(Color.BLACK);
      black.setAntiAlias(true);
      wight.setColor(Color.WHITE);
      wight.setAntiAlias(true);
   }

   @Override
   public boolean onTouchEvent(MotionEvent event)
   {
      boolean touchEvent = super.onTouchEvent(event);

      invalidate();
      return true;
   }
}
