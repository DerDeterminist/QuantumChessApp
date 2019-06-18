package com.example.quantumchessapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.quantumchessapp.Account;
import com.example.quantumchessapp.GameVariant;
import com.example.quantumchessapp.R;

public class MainMenuActivity extends AppCompatActivity
{
   private Button offlineGame;
   private Button onlineGame;
   private Button brenden;
   private Button ki;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main_menu);

      offlineGame = findViewById(R.id.offlineGame);
      onlineGame = findViewById(R.id.onlineGame);
      brenden = findViewById(R.id.beenden);
      ki = findViewById(R.id.ki);

      initListener();
   }

   private void initListener()
   {
      final Intent gameModeActivity = new Intent(this, GameModeActivity.class);
      offlineGame.setOnClickListener(v -> {
         gameModeActivity.putExtra("variant", GameVariant.OFFLINE);

      });

      final Intent registerActivity = new Intent(this, RegisterActivity.class);
      Account account = new Account(this);
      onlineGame.setOnClickListener(v -> {
         if (account.hasAccount())
         {
            account.loadAccountData();
            gameModeActivity.putExtra("variant", GameVariant.ONLINE);
            startActivity(gameModeActivity);
         }
         else
         {
            startActivity(registerActivity);
         }
      });

      ki.setOnClickListener(v -> {
         gameModeActivity.putExtra("variant", GameVariant.KI);
         startActivity(gameModeActivity);
      });

      brenden.setOnClickListener(v -> System.exit(0));
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
