package com.example.sampledrawingboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

public class NumberPickerDialog extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    String title = "굵기 설정";
    String subtitle = "굵기를 설정해주세요.";
    int minValue = 1;
    int maxValue = 30;
    int step = 1;
    int startValue = 15;

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        String[] values = getArrayWithSteps(minValue, maxValue, step);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((maxValue - minValue) / step);
        numberPicker.setDisplayedValues(values);

        numberPicker.setValue((startValue - minValue) / step);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(subtitle);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setView(numberPicker);

        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public String[] getArrayWithSteps(int min, int max, int step) {
        int number_of_array = (max - min) / step + 1;

        String[] result = new String[number_of_array];

        for (int i = 0; i < number_of_array; i++) {
            result[i] = String.valueOf(min + (step * i));
        }
        return result;
    }
}
