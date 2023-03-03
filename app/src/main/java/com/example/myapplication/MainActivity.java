package com.example.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity {

    Button muscle_btn, grip_btn, st_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //버튼눌렀을때
        Button muscleButton = (Button) findViewById(R.id.muscle_btn);
        muscleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), muscleActivity.class);
                startActivity(intent);
            }
        });

        Button gripButton = (Button) findViewById(R.id.grip_btn);
        gripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), gripActivity.class);
                startActivity(intent);
            }
        });

        Button mgButton = (Button) findViewById(R.id.musclegrip_btn);
        mgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RacingActivity.class);
                startActivity(intent);
            }
        });

        Button stButton = (Button) findViewById(R.id.st_btn);
        stButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), stActivity.class);
                startActivity(intent);
            }
        });
    }
}