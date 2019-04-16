package com.example.quantumchessapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity
{
   private Button offlineGame;
   private Button onlineGame;
   private Button brenden;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.main_menu_fullscreen);

      offlineGame = findViewById(R.id.offlineGame);
      onlineGame = findViewById(R.id.onlineGame);
      brenden = findViewById(R.id.beenden);

      initListener();
   }

   private void initListener()
   {
      offlineGame.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {

         }
      });

      onlineGame.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {

         }
      });

      brenden.setOnClickListener(new View.OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            System.exit(0);
         }
      });
   }

   @Override
   protected void onResume()
   {
      super.onResume();
      hideSystemUI();
   }

   private void hideSystemUI()
   {
      View decorView = getWindow().getDecorView();
      decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
   }
}
