package com.example.quantumchessapp.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.quantumchessapp.Account;
import com.example.quantumchessapp.R;

public class RegisterActivity extends AppCompatActivity
{
   private Button confirm;
   private EditText accName;
   private CheckBox show_username_online;
   private Account account;

   public RegisterActivity()
   {
      account = Account.getInstance();
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);

      confirm = findViewById(R.id.confirm);
      accName = findViewById(R.id.accName);
      show_username_online = findViewById(R.id.show_username_online);

      intListener();
   }

   private void intListener()
   {
      confirm.setOnClickListener(v -> {
         if (accName.getText().length() != 0)
         {
            account.setShowUserNameOnline(show_username_online.isChecked());
            account.setUserName(accName.getText().toString());
            account.saveAccountData();
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
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
   }
}
