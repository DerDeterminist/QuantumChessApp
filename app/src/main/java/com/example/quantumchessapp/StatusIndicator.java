package com.example.quantumchessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class StatusIndicator extends FrameLayout
{
   public StatusIndicator(Context context)
   {
      super(context);
      LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      layoutInflater.inflate(R.layout.statusindicator, this, true);
   }
}
