package com.example.karolinar.premieretracker;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Paulina on 22.04.2017.
 */

public class ApproachingPremiereNotificationManager {
    private int notificationNumber = 0;
    private DatabaseManager dbManager;
    private NotificationManager notificationManager;
    private Context context;

    public ApproachingPremiereNotificationManager(Context context, NotificationManager notificationManager) {
        this.context = context;
        dbManager = new DatabaseManager(context);
        this.notificationManager = notificationManager;
    }

    public void showNotyficationTodayPremiere() {
        String message = "Dziś jest premiera:";
        String title = "Dzisiejsze premiery!";
        showNotyficationDay(message, title, 0);
    }

    public void showNotyficationDaysBerforePremiere(int days) {
        String message = "Zbliża się premiera:";
        String title = "Już za 7 dni premiera!";
        showNotyficationDay(message, title, days);
    }

    private void showNotyficationDay(String messageStartText, String title, int days) {
        List<ProductEntity> products = dbManager.GetProducts();

        String message = messageStartText;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar date = Calendar.getInstance();
        date.clear(Calendar.HOUR); date.clear(Calendar.MINUTE); date.clear(Calendar.SECOND);
        date.add(Calendar.DATE, days);

        for(ProductEntity product : products) {
            if( sdf.format(product.Premiere).equals(sdf.format(date.getTime()))) {
                message += " " + product.Name + ",";
            }
        }

        message = message.substring(0, message.length() - 1) + ".";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setContentText(message);

        notificationManager.notify(notificationNumber, mBuilder.build());
        notificationNumber++;
    }
}
