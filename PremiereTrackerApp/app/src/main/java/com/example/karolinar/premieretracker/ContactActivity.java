package com.example.karolinar.premieretracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button btnMessage = (Button) findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(getApplicationContext(),MailContactActivity.class);
                startActivity(startIntent);
            }
        });
    }


}
