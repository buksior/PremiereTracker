package com.example.karolinar.premieretracker;

/**
 * Created by Karolina R on 27.04.2017.
 */

import android.text.InputFilter;
import android.text.Spanned;

public class NumberValueFilter implements InputFilter {
    private double minValue;
    private double maxValue;

    public NumberValueFilter(double minVal, double maxVal) {
        this.minValue = minVal;
        this.maxValue = maxVal;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try {
            String newVal = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd, dest.toString().length());
            newVal = newVal.substring(0, dStart) + source.toString() + newVal.substring(dStart, newVal.length());
            double input = Double.parseDouble(newVal);

            if (isInRange(minValue, maxValue, input)) {
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
