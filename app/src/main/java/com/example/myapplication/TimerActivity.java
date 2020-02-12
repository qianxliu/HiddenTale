package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends Activity {

    //public static final String EXTRA_MESSAGE = "message";

    //rotate will destory activity and reset
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    //helper to know it's a override true?
    //1、可以当注释用,方便阅读；
    //2、编译器可以给你验证@Override下面的方法名是否是你父类中所有的，如果没有则报错。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
        //Intent intent = getIntent();
        //String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        //TextView messageView = (TextView) findViewById(R.id.message);
        //messageView.setText(messageText);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //savedInstanceState.putInt("name",value);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
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
        ////    一、使用Final修饰符修饰的类的特点：该类不能有子类；
        ////    二、使用Final修饰符修饰的对象的特点：该对象的引用地址不能改变；
        ////    三、使用Final修饰符修饰的方法的特点：该方法不能被重写；
        ////    四、使用Final修饰符修饰的变量的特点：该变量会变成常亮，值不能被改变。

        ////    Final修饰对象，该对象reference address couldn't be changed
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;

                //Locale.US 本地化 United States
                String time = String.format(Locale.US,"%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
