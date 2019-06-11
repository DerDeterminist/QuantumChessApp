package com.example.quantumchessapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.quantumchessapp.GameVariant;
import com.example.quantumchessapp.R;

public class GameModeActivity extends AppCompatActivity
{

   private Button normal;
   private Button quantum;

   private GameVariant variant;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game_mode);

      Intent gameModeActivityIntent = getIntent();
      variant = (GameVariant) gameModeActivityIntent.getSerializableExtra("variant");

      findViews();
      initListeners();
   }

   private void findViews()
   {
      normal = findViewById(R.id.normal);
      quantum = findViewById(R.id.quantum);
   }

   private void initListeners()
   {
      final Intent gameActivity = new Intent(this, GameActivity.class);
      gameActivity.putExtra("variant", variant);
      normal.setOnClickListener(v -> {
         gameActivity.putExtra("allowQMove", false);
         startActivity(gameActivity);
      });
      quantum.setOnClickListener(v -> {
         gameActivity.putExtra("allowQMove", true);
         startActivity(gameActivity);
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
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
   }
}
