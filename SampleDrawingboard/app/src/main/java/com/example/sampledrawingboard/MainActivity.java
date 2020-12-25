package com.example.sampledrawingboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private ConstraintLayout container;
    private View board;
    private Button colorPickBtn;
    private Button setRadiusBtn;
    private Button eraseBtn;

    int currentColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new PaintView(this);
        container = findViewById(R.id.container);
        container.addView(board);

        colorPickBtn = findViewById(R.id.colorPickBtn);
        colorPickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        setRadiusBtn = findViewById(R.id.setRadiusBtn);
        setRadiusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPickerDialog();
            }
        });

        eraseBtn = findViewById(R.id.eraseBtn);
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaintView) board).eraseAll();
            }
        });
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getApplicationContext(), color+"를 선택하셨습니다.", Toast.LENGTH_LONG).show();
                ((PaintView) board).setColor(color);
                ((PaintView) board).setPaintInfo();
            }
        });
        colorPicker.show();
    }

    public void showNumberPickerDialog() {
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.setValueChangeListener(this);
        numberPickerDialog.show(getSupportFragmentManager(), "NumberPickerDialog");
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        ((PaintView) board).setRadius(numberPicker.getValue());
        ((PaintView) board).setPaintInfo();
    }
}