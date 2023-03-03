package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

import static java.sql.Types.TIME;

public class ballonActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView imageView1;
    RelativeLayout layout;
    TextView count;
    private BluetoothSPP bt;
    String[] array;
    double a, b, c, d, e, total;

    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ballon);

        count = (TextView)findViewById(R.id.count);

        Button finishBtn=findViewById(R.id.button2);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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


                if (array.length >= 1) {
                    a = Double.parseDouble(array[0]);
                }

                if (array.length >= 2) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                }

                if (array.length >= 3) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                }

                if (array.length >= 4) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                }

                if (array.length >= 5) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                }


                total = a + b + c + d + e;

                float flex_data = (float) (852 - total);
                float increase = (float) (flex_data * 0.04);

                while (true) {
                    imageView1.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                    if (total <= 852) {
                        if (a <= 180 && b <= 147 && c <= 178 && d <= 177 && e <= 170) {
                            imageView.setScaleX(increase);
                            imageView.setScaleY(increase);


                            if (a <= 130 && b <= 87 && c <= 103 && d <= 103 && e <= 98) {
                                imageView1.setVisibility(View.VISIBLE);
                                imageView.setVisibility(View.INVISIBLE);
                                count.setText(String.valueOf(score++) + "점");
                                break;


                            }
                        }
                    }
                    break;
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

        Button btnConnect = findViewById(R.id.btnConnect); //연결시도
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

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView.setBackgroundColor(Color.WHITE);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout2);
        layout.setBackgroundColor(Color.WHITE);
        imageView.setVisibility(View.VISIBLE);
        imageView1.setVisibility(View.INVISIBLE);
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