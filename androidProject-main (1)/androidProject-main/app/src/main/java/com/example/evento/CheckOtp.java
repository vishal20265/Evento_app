package com.example.evento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckOtp extends AppCompatActivity {
    private Button verify;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        String otp = getIntent().getStringExtra("otp");

        verify = findViewById(R.id.verify);
        et = findViewById(R.id.otp);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterred = et.getText().toString();
                if (enterred.equals(otp)) {
                    startActivity(new Intent(CheckOtp.this, MainActivity.class));
                }else {
                    Toast.makeText(CheckOtp.this, "Incorrect otp enterred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}