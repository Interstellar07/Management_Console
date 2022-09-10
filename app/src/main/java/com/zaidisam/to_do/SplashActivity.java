package com.zaidisam.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;




public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }, 3000);

    }
}