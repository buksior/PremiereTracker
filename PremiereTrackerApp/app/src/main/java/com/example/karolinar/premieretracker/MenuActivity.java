package com.example.karolinar.premieretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.karolinar.premieretracker.R.id.btnAbout;
import static com.example.karolinar.premieretracker.R.id.btnConfig;
import static com.example.karolinar.premieretracker.R.id.btnContact;
import static com.example.karolinar.premieretracker.R.id.btnSearch;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

       Button next = (Button) findViewById(R.id.btnObserved);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ObservedListActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });


        Button buttonSearch = (Button) findViewById(btnSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(startIntent);
            }
        });

        Button buttonContact = (Button) findViewById(btnContact);
        buttonContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),ContactActivity.class);
                startActivity(startIntent);
            }
        });

        Button buttonAbout = (Button) findViewById(btnAbout);
        buttonAbout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(startIntent);
            }
        });

        Button buttonConfig = (Button) findViewById(btnConfig);
        buttonConfig.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),ConfigurationActivity.class);
                startActivity(startIntent);
            }
        });


    }
}
