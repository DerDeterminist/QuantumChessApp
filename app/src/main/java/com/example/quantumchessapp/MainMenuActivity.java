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
      super.onCreate(savedInstanceState); // TODO: 15.04.2019 Fullscreen

      setContentView(R.layout.activity_fullscreen);

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
}
