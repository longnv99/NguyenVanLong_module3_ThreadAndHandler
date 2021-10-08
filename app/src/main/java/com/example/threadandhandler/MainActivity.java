package com.example.threadandhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public
class MainActivity extends AppCompatActivity {

    TextView txtTime;
    Button btnStart, btnPause, btnReset;
    Handler handler;
    long milisecondsTime, startTime, timeBuff, updateTime = 0L;
    int minute, second, milisecond;
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        txtTime =findViewById(R.id.txtTime);
        btnStart=findViewById(R.id.btnStart);
        btnPause=findViewById(R.id.btnPause);
        btnReset=findViewById(R.id.btnStop);
        handler = new Handler();
    }

    public Runnable runnable = new Runnable() {
        @Override
        public
        void run() {
            milisecondsTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milisecondsTime;

            second = (int) (updateTime / 1000);
            second = second % 60;
            minute = second / 60;
            milisecond = (int) (updateTime % 1000);
            txtTime.setText(minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", milisecond));
            handler.postDelayed(this, 0);
        }
    };

    public
    void onStart(View view) {
        btnReset.setEnabled(false);
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        btnStart.setEnabled(false);
    }

    public
    void onPause(View view) {
        timeBuff += milisecondsTime;
        handler.removeCallbacks(runnable);
        btnReset.setEnabled(true);
        btnStart.setEnabled(true);
        btnStart.setText("Continue");
    }

    public
    void onReset(View view) {
        milisecondsTime = startTime = timeBuff = updateTime = 0L;
        minute = second = milisecond = 0;
        txtTime.setText("00:00:00");
        btnStart.setText("Start");
    }

}