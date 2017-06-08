package com.example.karolinar.premieretracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.karolinar.premieretracker.R.id.btnAbout;
import static com.example.karolinar.premieretracker.R.id.btnConfig;
import static com.example.karolinar.premieretracker.R.id.btnContact;
import static com.example.karolinar.premieretracker.R.id.btnCreateNotification;
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

                if (isOnline()){
                    Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                    intent.putExtra("CREATOR", "");
                    intent.putExtra("CATEGORY", "");
                    startActivity(intent);
                }else{

                    Toast.makeText(MenuActivity.this.getApplicationContext(), "Aby korzystać z wyszukiwarki musisz się połączyć z Internetem!", Toast.LENGTH_SHORT).show();
                }

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

        Button buttonCreateNotification = (Button) findViewById(btnCreateNotification);
        buttonCreateNotification.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                createNotification("Wonder Woman","2017-10-10");

                // notyfikacje dla zmiany dat premiery produktów z bazy
           //     ModificationPremiereDate mpd= new ModificationPremiereDate();
         //       mpd.checkPremierDate();

            }
        });


    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


   public void createNotification(String productName, String newPremiereDate) {
        Intent intent = new Intent(this, ObservedListActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);


        Notification noti = new NotificationCompat.Builder(this)
               .setContentTitle("Zmiana terminu premiery!")
                .setContentText("Produkt: "+productName + " nowa data " + newPremiereDate )
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(icon)
                .setAutoCancel(true)
             .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);


    }

}
