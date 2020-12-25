package com.example.numberbaseball;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    int []com = new int[4];     // 실제 답
    int []user = new int[4];    // 플레이어가 입력한 답

    int strike, ball, answer;

    EditText playerAnswer;      // 플레이어 답 입력란
    ImageButton checkButton;    // 정답 체크 버튼
    Button showAnswerButton;    // 정답 보기 버튼

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        resetAnswer();
        playerAnswer = rootView.findViewById(R.id.yourAnswer);
        checkButton = rootView.findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((playerAnswer.getText().toString()).equals("")) {
                    user[0] = 0;
                    user[1] = 0;
                    user[2] = 0;
                    user[3] = 0;
                } else {
                    int yourAnswer = Integer.parseInt(playerAnswer.getText().toString());
                    user[0] = yourAnswer / 1000;
                    user[1] = (yourAnswer - (user[0] * 1000)) / 100;
                    user[2] = (yourAnswer - (user[0] * 1000 + user[1] * 100)) / 10;
                    user[3] = (yourAnswer - (user[0] * 1000 + user[1] * 100 + user[2] * 10));
                }   // 플레이어 답을 배열에 저장

                strike = 0;
                ball = 0;

                for (int i = 0; i < com.length; i++) {
                    for (int j = 0; j < user.length; j++) {
                        if (com[i] == user[j]) {
                            if (i == j) {
                                strike++;
                            } else {
                                ball++;
                            }
                        }
                    }
                }   // 스트라이크, 볼 판독
                if (strike == 4) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                    builder.setTitle("안내");
                    builder.setMessage("정답을 맞췄습니다! 축하드립니다.\n정답이 재설정됩니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    resetAnswer();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                    builder.setTitle("안내");
                    builder.setMessage(strike + " 스트라이크 " + ball + " 볼");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                };
                playerAnswer.setText("");
            }
        });
        showAnswerButton = rootView.findViewById(R.id.showAnswer);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                builder.setTitle("안내");
                builder.setMessage("정답은 "+answer);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return rootView;
    }

    private void resetAnswer() {
        com[0] = (int) (Math.random() * 9) + 1;
        do {
            com[1] = (int) (Math.random() * 9) + 1;
        } while (com[1] == com[0]);
        do {
            com[2] = (int) (Math.random() * 9) + 1;
        } while (com[2] == com[0] || com[2] == com[1]);
        do {
            com[3] = (int) (Math.random() * 9) + 1;
        } while (com[3] == com[0] || com[3] == com[1] || com[3] == com[2]);

        answer = com[0] * 1000 + com[1] * 100 + com[2] * 10 + com[3];
    }   // 정답 초기화
}
