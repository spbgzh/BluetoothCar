package com.example.car;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;

public class ControlActivity extends AppCompatActivity implements View.OnTouchListener {
    final String deep_purple = "#9933cc";
    final String normal_purple ="#6600cc";
    private Button up,down,right,left,stop,speed_1,speed_2,speed_3;
    boolean turn_left,turn_right,go_ahead,go_back,down_stop,is_sp_1,is_sp_2,is_sp_3 = false;
    private final static char FRONT_3 = 'A';
    private final static char FRONT_2 = 'B';
    private final static char FRONT_1 = 'C';
    private final static char BACK_3 = 'D';
    private final static char BACK_2 = 'E';
    private final static char BACK_1 = 'F';
    private final static char FRONT_LEFT_3 = 'G';
    private final static char FRONT_LEFT_2 = 'H';
    private final static char FRONT_LEFT_1 = 'I';
    private final static char BACK_LEFT_3 = 'J';
    private final static char BACK_LEFT_2 ='K';
    private final static char BACK_LEFT_1 = 'L';
    private final static char FRONT_RIGHT_3 ='M';
    private final static char FRONT_RIGHT_2 = 'N';
    private final static char FRONT_RIGHT_1 = 'O';
    private final static char BACK_RIGHT_3 = 'P';
    private final static char BACK_RIGHT_2 = 'Q';
    private final static char BACK_RIGHT_1 = 'S';
    private final static char ROUND = 'T';
    private final static char STOP ='X';
    public String test1 = "";
    private Context context = this;


    OutputStream outputStream = MainActivity.outputStream;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        right = (Button) findViewById(R.id.right);
        left = (Button) findViewById(R.id.left);
        stop = (Button) findViewById(R.id.stop);
        speed_1 = (Button) findViewById(R.id.speed_one);
        speed_2 = (Button) findViewById(R.id.speed_two);
        speed_3 = (Button) findViewById(R.id.speed_three);
        speed_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_1();
            }
        });
        speed_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_2();
            }
        });
        speed_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                click_3();
            }
        });
        up.setOnTouchListener(this);
        down.setOnTouchListener(this);
        right.setOnTouchListener(this);
        left.setOnTouchListener(this);
        speed_3.setOnTouchListener(this);
        speed_1.setOnTouchListener(this);
        speed_2.setOnTouchListener(this);
        stop.setOnTouchListener(this);
    }

    private void click_1(){
        is_sp_1 = true;
        is_sp_2 = false;
        is_sp_3 = false;
    }
    private void click_2(){
        is_sp_1 = false;
        is_sp_2 = true;
        is_sp_3 = false;
    }
    private void click_3(){
        is_sp_1 = false;
        is_sp_2 = false;
        is_sp_3 = true;
    }

    private void stop(){
        is_sp_3 = false;
        is_sp_2 = false;
        is_sp_1 = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        outputStream = MainActivity.outputStream;
        new touchThread(view,motionEvent).start();
        return false;
    }

    public void back(){
        Log.i("Redirection","Now directing to main page");
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void write(byte[] bytes) {
        outputStream = MainActivity.outputStream;
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            Log.e("Conversation err", "Error occurred when sending data", e);
        }
    }

    private class touchThread extends Thread{
        private View view = null;
        private MotionEvent motionEvent= null;
        touchThread(View view, MotionEvent motionEvent){
            this.view = view;
            this.motionEvent = motionEvent;
        }

        @Override
        public void run() {
            super.run();
            switch (view.getId()){
                case R.id.right:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        right.setBackgroundColor(Color.parseColor(deep_purple));
                        turn_right = true;
                        while(turn_right) {
                            if (go_ahead) {
                                if (is_sp_1) {
                                    write(String.valueOf(FRONT_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(FRONT_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(FRONT_RIGHT_3).getBytes());
                                }
                            } else if (go_back) {
                                if (is_sp_1) {
                                    write(String.valueOf(BACK_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(BACK_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(BACK_RIGHT_3).getBytes());
                                }
                            }
                        }
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        right.setBackgroundColor(Color.parseColor(normal_purple));
                        turn_right = false;
                    }
                    try {
                        Thread.sleep(700);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.left:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        left.setBackgroundColor(Color.parseColor(deep_purple));
                        turn_left = true;
                        while (turn_left) {
                            if (go_ahead) {
                                if (is_sp_1) {
                                    write(String.valueOf(FRONT_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(FRONT_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(FRONT_RIGHT_3).getBytes());
                                }
                            } else if (go_back) {
                                if (is_sp_1) {
                                    write(String.valueOf(BACK_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(BACK_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(BACK_RIGHT_3).getBytes());
                                }
                            }
                        }
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        left.setBackgroundColor(Color.parseColor(normal_purple));
                        turn_left = false;
                    }
                    try {
                        Thread.sleep(700);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.up:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        up.setBackgroundColor(Color.parseColor(deep_purple));
                        go_ahead = true;
                        while(go_ahead) {
                            if(turn_left){
                                if (is_sp_1) {
                                    write(String.valueOf(FRONT_LEFT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(FRONT_LEFT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(FRONT_LEFT_3).getBytes());
                                }
                            }else if (turn_right){
                                if (is_sp_1) {
                                    write(String.valueOf(FRONT_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(FRONT_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(FRONT_RIGHT_3).getBytes());
                                }
                            }else if(go_back){
                                write(String.valueOf(STOP).getBytes());
                            }else {
                                if (is_sp_1) {
                                    write(String.valueOf(FRONT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(FRONT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(FRONT_3).getBytes());
                                }
                            }
                            try {
                                Thread.sleep(700);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        up.setBackgroundColor(Color.parseColor(normal_purple));
                        go_ahead = false;
                    }
                    break;
                case R.id.down:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        down.setBackgroundColor(Color.parseColor(deep_purple));
                        go_back = true;
                        while(go_back) {
                            if(turn_left){
                                if (is_sp_1) {
                                    write(String.valueOf(BACK_LEFT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(BACK_LEFT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(BACK_LEFT_3).getBytes());
                                }
                            }else if (turn_right){
                                if (is_sp_1) {
                                    write(String.valueOf(BACK_RIGHT_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(BACK_RIGHT_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(BACK_RIGHT_3).getBytes());
                                }
                            }else if(go_ahead){
                                write(String.valueOf(STOP).getBytes());
                            }else {
                                if (is_sp_1) {
                                    write(String.valueOf(BACK_1).getBytes());
                                } else if (is_sp_2) {
                                    write(String.valueOf(BACK_2).getBytes());
                                } else if (is_sp_3) {
                                    write(String.valueOf(BACK_3).getBytes());
                                }
                            }
                            try {
                                Thread.sleep(700);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        down.setBackgroundColor(Color.parseColor(normal_purple));
                        go_back = false;
                    }
                    break;
                case R.id.stop:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        stop.setBackgroundColor(Color.parseColor(deep_purple));
                        down_stop = true;
                        while(down_stop){
                            write(String.valueOf(STOP).getBytes());
                            try {
                                Thread.sleep(700);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        stop.setBackgroundColor(Color.parseColor(normal_purple));
                        down_stop = false;
                    }
                    break;
                case R.id.speed_one:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        speed_1.setBackgroundColor(Color.parseColor(deep_purple));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        speed_1.setBackgroundColor(Color.parseColor(normal_purple));
                    }
                    break;
                case R.id.speed_two:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        speed_2.setBackgroundColor(Color.parseColor(deep_purple));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        speed_2.setBackgroundColor(Color.parseColor(normal_purple));
                    }
                    break;
                case R.id.speed_three:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        speed_3.setBackgroundColor(Color.parseColor(deep_purple));
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        speed_3.setBackgroundColor(Color.parseColor(normal_purple));
                    }
                    break;
            }
        }
    }
}
