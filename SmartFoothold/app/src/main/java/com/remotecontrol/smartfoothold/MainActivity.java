package com.remotecontrol.smartfoothold;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;  // 높이 표시
    Button button1; // 30cm
    Button button2; // 20cm
    Button button3; // 10cm
    Button button4; // 원위치 (0cm)
    Button button5; // 정지

    ImageButton imageButton1;   // 업 버튼
    ImageButton imageButton2;   // 다운 버튼

    Handler handler = new Handler();
    MoveThread thread;

    int height; // 높이

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "환영합니다.", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "원하는 메뉴를 선택하세요.", Toast.LENGTH_LONG).show();

        height = 0;
        textView = findViewById(R.id.textView);
        textView.setText(height + " cm");

        thread = new MoveThread();
        thread.start();

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.setGoalHeight(30);
                thread.restart();
            }
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.setGoalHeight(20);
                thread.restart();
            }
        });

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.setGoalHeight(10);
                thread.restart();
            }
        });

        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.setGoalHeight(0);
                thread.restart();
            }
        });

        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.setRunning(false);   // 비활성화 상태로 만들기
            }
        });

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (height < 30) {
                    thread.setGoalHeight(height + 1);
                    thread.restart();
                }
            }
        });

        imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (height > 0) {
                    thread.setGoalHeight(height - 1);
                    thread.restart();
                }
            }
        });
    }

    class MoveThread extends Thread {
        int goalHeight; // 목표 높이
        boolean running = true;

        public void run() {
            while(true) {
                while(height != goalHeight) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (running) {
                        if (height < goalHeight) height++;
                        else if (height > goalHeight) height--;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(height + " cm");
                            }
                        });
                    }
                }
            }
        }

        public void setGoalHeight(int goalHeight) {
            this.goalHeight = goalHeight;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public void restart() {
            if (!running) this.running = true;
        }   // 비활성화 되있을 시 다시 활성화
    }
}