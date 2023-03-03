package com.example.myapplication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;


import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;


public class pianoActivity extends AppCompatActivity {
    private BluetoothSPP bt;
    String[] array = new String[10];
    double a, b, c, d, e;

    Button 도, 레, 미, 파, 솔, 라, 시, 높은도;
    private SoundPool soundPool;
    private int sound_도, sound_레, sound_미, sound_파, sound_솔, sound_라, sound_시, sound_높은도;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        도 = (Button) findViewById(R.id.도);
        레 = (Button) findViewById(R.id.레);
        미 = (Button) findViewById(R.id.미);
        파 = (Button) findViewById(R.id.파);
        솔 = (Button) findViewById(R.id.솔);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        sound_도 = soundPool.load(this, R.raw.sound1, 1);
        sound_레 = soundPool.load(this, R.raw.sound2, 1);
        sound_미 = soundPool.load(this, R.raw.sound3, 1);
        sound_파 = soundPool.load(this, R.raw.sound4, 1);
        sound_솔 = soundPool.load(this, R.raw.sound5, 1);

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신



            public void onDataReceived(byte[] data, String message) {

                array = message.split(",");


                if (array.length == 5) {
                    a = Double.parseDouble(array[5]);
                }

                else if (array.length == 6) {
                    a = Double.parseDouble(array[5]);
                    b = Double.parseDouble(array[6]);
                }

                else if (array.length == 7) {
                    a = Double.parseDouble(array[5]);
                    b = Double.parseDouble(array[6]);
                    c = Double.parseDouble(array[7]);
                }

                else if (array.length == 8) {
                    a = Double.parseDouble(array[5]);
                    b = Double.parseDouble(array[6]);
                    c = Double.parseDouble(array[7]);
                    d = Double.parseDouble(array[8]);
                }

                else if (array.length == 9) {
                    a = Double.parseDouble(array[5]);
                    b = Double.parseDouble(array[6]);
                    c = Double.parseDouble(array[7]);
                    d = Double.parseDouble(array[8]);
                }

                else if (array.length == 10) {
                    a = Double.parseDouble(array[5]);
                    b = Double.parseDouble(array[6]);
                    c = Double.parseDouble(array[7]);
                    d = Double.parseDouble(array[8]);
                    e = Double.parseDouble(array[9]);
                }

                else if (array.length < 5) {
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    e = 0;
                }


                if (a > 200) {
                    도.performClick();
                }
                if (b > 200) {
                    레.performClick();
                }
                if (c > 200) {
                    미.performClick();
                }
                if (d > 200) {
                    파.performClick();
                }
                if (e > 200) {
                    솔.performClick();
                }
            }

        });


        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();

            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnConnect = findViewById(R.id.pbtnConnect); //연결시도
        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });


        도.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                if(x > 10)
                    {
                        soundPool.play(sound_도, 1, 1, 0, 0, 1);
                        x = 0;
                    }

                x = x+1;
            }
        });


        레.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                if(x > 10)
                {
                    soundPool.play(sound_레, 1, 1, 0, 0, 1);
                    x = 0;
                }
                x = x+1;
            }
        });

        미.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                if(x > 10)
                {
                    soundPool.play(sound_미, 1, 1, 0, 0, 1);
                    x = 0;
                }
                x = x+1;
            }
        });

        파.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                if(x > 10) {
                    soundPool.play(sound_파, 1, 1, 0, 0, 1);
                    x = 0;
                }
                x = x+1;
            }
        });

        솔.setOnClickListener(new View.OnClickListener() {
            int x = 0;
            @Override
            public void onClick(View v) {
                if(x > 10) {
                    soundPool.play(sound_솔, 1, 1, 0, 0, 1);
                    x = 0;
                }
                x = x+1;
            }
        });

    }


    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);

            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
