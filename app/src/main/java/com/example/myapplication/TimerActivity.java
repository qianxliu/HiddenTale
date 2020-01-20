package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class TimerActivity extends Activity {

    //public static final String EXTRA_MESSAGE = "message";
    private int seconds = 0;
    private boolean running;

    //helper to know it's a override true?
    //1、可以当注释用,方便阅读；
    //2、编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        runTimer();
        //Intent intent = getIntent();
        //String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        //TextView messageView = (TextView) findViewById(R.id.message);
        //messageView.setText(messageText);
    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        //final is constant
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
