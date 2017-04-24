package com.example.karolinar.premieretracker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Paulina on 24.04.2017.
 */

public class ApproachingPremiereAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        ApproachingPremiereNotificationManager m = new ApproachingPremiereNotificationManager(arg0, (NotificationManager) arg0.getSystemService(NOTIFICATION_SERVICE));
        m.showNotyficationTodayPremiere();
        m.showNotyficationDaysBerforePremiere(7);
    }
}
