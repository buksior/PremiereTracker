package com.example.karolinar.premieretracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigurationActivity extends AppCompatActivity {

    private static String SHARED = "DAYS_TO_PREMIERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        final EditText days = (EditText) findViewById(R.id.daysInput);
        days.setFilters(new InputFilter[]{new NumberValueFilter(1,90)});
        if(sharedPreferences.contains(SHARED)){
            days.setText(sharedPreferences.getInt(SHARED, 0) + "");
        }

        Button save = (Button) findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SHARED, Integer.parseInt(days.getText().toString()));
                editor.apply();
            }
        });
    }
}
