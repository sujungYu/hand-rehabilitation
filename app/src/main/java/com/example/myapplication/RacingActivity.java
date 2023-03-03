package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.Random;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class RacingActivity extends AppCompatActivity {

    private static final int INIT_SPEED = 7;
    private static final int SPEED_INTERVAL = 2;
    private static final int MAX_SPEED = 40;

    private View contLabel;
    private TextView tvNotify;
    private TextView tvScore, tvLevel, tvBest;
    private ImageView ivCenter;
    private RacingView racingView;
    private int score, level, bestScore;
    private int playCount;
    private int boardWidth, viewHeight, blockSize;

    private BluetoothSPP bt;
    String[] array;
    double a, b, c, d, e, f, g, h, i, j, totalM, totalG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_racing);

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                array = message.split(",");
                a = 0.0;
                b = 0.0;
                c = 0.0;
                d = 0.0;
                e = 0.0;
                f = 0.0;
                g = 0.0;
                h = 0.0;
                i = 0.0;
                j = 0.0;
                if (array.length == 1) {
                    a = Double.parseDouble(array[0]);
                }

                if (array.length == 2) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                }

                if (array.length == 3) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                }

                if (array.length == 4) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                }

                if (array.length == 5) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                }

                if (array.length == 6) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                    f = Double.parseDouble(array[5]);
                }

                if (array.length == 7) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                    f = Double.parseDouble(array[5]);
                    g = Double.parseDouble(array[6]);
                }

                if (array.length == 8) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                    f = Double.parseDouble(array[5]);
                    g = Double.parseDouble(array[6]);
                    h = Double.parseDouble(array[7]);
                }

                if (array.length == 9) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                    f = Double.parseDouble(array[5]);
                    g = Double.parseDouble(array[6]);
                    h = Double.parseDouble(array[7]);
                    i = Double.parseDouble(array[8]);
                }

                if (array.length == 10) {
                    a = Double.parseDouble(array[0]);
                    b = Double.parseDouble(array[1]);
                    c = Double.parseDouble(array[2]);
                    d = Double.parseDouble(array[3]);
                    e = Double.parseDouble(array[4]);
                    f = Double.parseDouble(array[5]);
                    g = Double.parseDouble(array[6]);
                    h = Double.parseDouble(array[7]);
                    i = Double.parseDouble(array[8]);
                    j = Double.parseDouble(array[9]);
                }
                totalM = a + b + c + d + e;
                totalG = f + g + h + i + j;

                if(totalM<=500){
                    racingView.moveRight();

                }
                if(totalG>=530){
                    racingView.moveLeft();
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
        contLabel = findViewById(R.id.contNotify);
        tvNotify = (TextView) findViewById(R.id.notify);
        tvScore = (TextView) findViewById(R.id.score);
        tvLevel = (TextView) findViewById(R.id.level);
        tvBest = (TextView) findViewById(R.id.best);

        ivCenter = (ImageView) findViewById(R.id.imgCenter);
        ivCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        racingView = (RacingView) findViewById(R.id.racingView);

        playCount = 0;
        initialize();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        racingView.reset();
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    private Handler racingHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RacingView.MSG_SCORE:
                    score = score + (level);
                    tvScore.setText(String.valueOf(score));
                    break;
                case RacingView.MSG_COLLISION:
                    boolean achieveBest = false;
                    if (bestScore < score) {
                        tvBest.setText(String.valueOf(score));
                        bestScore = score;
                        saveBestScore(bestScore);
                        achieveBest = true;
                    }
                    collision(achieveBest);
                    break;
                case RacingView.MSG_COMPLETE:
                    level++;

                    if (racingView.getSpeed() < MAX_SPEED) {
                        racingView.setSpeed(racingView.getSpeed() + SPEED_INTERVAL);
                    }

                    tvLevel.setText(String.valueOf(level));
                    prepare();
                    break;
                default:
                    break;
            }
        }
    };

    private void initialize() {
        reset();
        prepare();
    }

    private int loadBestScore() {
        SharedPreferences preferences = getSharedPreferences("MyFirstGame", Context.MODE_PRIVATE);
        if (preferences.contains("BestScore")) {
            return preferences.getInt("BestScore", 0);
        } else {
            return 0;
        }
    }

    private void saveBestScore(int bestScore) {
        SharedPreferences preferences = getSharedPreferences("MyFirstGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("BestScore", bestScore);
        editor.commit();
    }

    private void reset() {
        score = 0;
        level = 1;
        bestScore = loadBestScore();

        racingView.setSpeed(INIT_SPEED);
        racingView.setPlayState(RacingView.PlayState.Ready);

        tvScore.setText(String.valueOf(score));
        tvLevel.setText(String.valueOf(level));
        tvBest.setText(String.valueOf(bestScore));
    }

    private void prepare() {
        tvLevel.setText(String.valueOf(level));
        tvNotify.setText("단계 " + level);
        showLabelContainer();

        ivCenter.setImageResource(R.drawable.ic_play);
    }

    private void play() {
        if (racingView.getPlayState() == RacingView.PlayState.Collision) {
            initialize();

            racingView.reset();
            return;
        }

        // Click on playing
        if (racingView.getPlayState() == RacingView.PlayState.Playing) {
            pause();
        } else {
            ivCenter.setImageResource(R.drawable.ic_pause);


            // Click on pause
            if (racingView.getPlayState() == RacingView.PlayState.Pause) {
                racingView.resume();
            } else if (racingView.getPlayState() == RacingView.PlayState.LevelUp) {
                racingView.resume();
                hideLabelContainer();
            } else {
                // Click on stop
                playCount++;

                hideLabelContainer();
                racingView.play(racingHandler);
            }
        }
    }

    private void pause() {
        ivCenter.setImageResource(R.drawable.ic_play);
        racingView.pause();
    }

    private void collision(boolean achieveBest) {
        if (achieveBest) {
            tvNotify.setText("최고점수 달성!");

        } else {
            tvNotify.setText("재도전!");
        }

        contLabel.setVisibility(View.VISIBLE);
        contLabel.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));

        ivCenter.setImageResource(R.drawable.ic_retry);
    }

    private void showLabelContainer() {
        contLabel.setVisibility(View.VISIBLE);
        contLabel.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
    }

    private void hideLabelContainer() {
        Animation anim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                contLabel.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contLabel.startAnimation(anim);
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