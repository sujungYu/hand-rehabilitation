package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class gripActivity extends AppCompatActivity {

    Button ballon_btn, piano_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grip);

        Button pianoButton = (Button) findViewById(R.id.piano_btn);
        pianoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), pianoActivity.class);
                startActivity(intent);
            }
        });
    }
}