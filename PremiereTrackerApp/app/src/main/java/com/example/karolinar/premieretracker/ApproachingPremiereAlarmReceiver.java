package com.example.karolinar.premieretracker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.karolinar.premieretracker.R.string.days;

/**
 * Created by Paulina on 24.04.2017.
 */

public class ApproachingPremiereAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        ApproachingPremiereNotificationManager m = new ApproachingPremiereNotificationManager(arg0, (NotificationManager) arg0.getSystemService(NOTIFICATION_SERVICE));
        m.showNotyficationTodayPremiere();
        SharedPreferences sharedPreferences = arg0.getSharedPreferences("DAYS_TO_PREMIERE", MODE_PRIVATE);
        m.showNotyficationDaysBerforePremiere(sharedPreferences.getInt("DAYS_TO_PREMIERE", 7));
    }
}
