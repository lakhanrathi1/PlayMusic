package com.example.lakhan.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }



        new CountDownTimer(3000, 1000) {
            public void onFinish() {
                Intent startActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startActivity);
                finish();
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();

    }
}
