package com.example.quantumchessapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.quantumchessapp.Account;
import com.example.quantumchessapp.R;

public class MainMenuActivity extends AppCompatActivity
{
   private Button offlineGame;
   private Button onlineGame;
   private Button brenden;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main_menu);

      offlineGame = findViewById(R.id.offlineGame);
      onlineGame = findViewById(R.id.onlineGame);
      brenden = findViewById(R.id.beenden);

      initListener();
   }

   private void initListener()
   {
      final Intent gameModeActivity = new Intent(this, GameModeActivity.class);
      offlineGame.setOnClickListener(v -> startActivity(gameModeActivity));

      final Intent registerActivity = new Intent(this, RegisterActivity.class);
      Account account = new Account(this);
      onlineGame.setOnClickListener(v -> {
         if (account.hasAccount())
         {
            account.loadAccountData();
            // TODO: 12.05.2019 start online game
         }
         else
         {
            startActivity(registerActivity);
         }
      });

      brenden.setOnClickListener(v -> {
         System.exit(0); // TODO: 16.04.2019 richtig beenden
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
