package com.example.karolinar.premieretracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager dbManager = new DatabaseManager(this);

        if(dbManager.GetProductTypes().isEmpty()) {
            dbManager.AddProducsTypes();
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DAYS_TO_PREMIERE", MODE_PRIVATE);
        if(sharedPreferences.getInt("DAYS_TO_PREMIERE", -1) == -1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("DAYS_TO_PREMIERE", 7);
            editor.apply();
        }

        Intent alarmApproachingPremiereIntent = new Intent(this, ApproachingPremiereAlarmReceiver.class);
        PendingIntent pendingApproachingPremiereIntent = PendingIntent.getBroadcast(this, 0, alarmApproachingPremiereIntent, 0);

        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingApproachingPremiereIntent);

        setContentView(R.layout.activity_first);

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if (isOnline()){
                    Intent startIntent = new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(startIntent);
                }else{

                    Toast.makeText(MainActivity.this.getApplicationContext(), "Aby korzystać korzystać ze wszystkich możliwość aplikacji musisz się połączyć z Internetem!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
