package com.example.evento;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIMEOUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Run","Running splash");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
//                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }
}
