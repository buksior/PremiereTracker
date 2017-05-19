package com.example.karolinar.premieretracker;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marta on 28.04.2017.
 */

public class ModificationPremiereDate {


    private int notificationNumber = 0;
    private DatabaseManager dbManager;
    private NotificationManager notificationManager;
    private Context context;

    SearchService searchService = new SearchService();
    List<Product> productEntityList = new ArrayList<>();

    public void checkPremierDate() {

        List<ProductEntity> products = dbManager.GetProducts();

        String productName = " ";


        for(ProductEntity product : products) {
            productName = product.Name;

            switch (product.ProductType) {
                case "Book": {

                    productEntityList = searchService.findBookByTitle(productName);

                }
                case "Game": {
                    productEntityList = searchService.findGameByTitle(productName);
                }

                case "Movie": {
                    productEntityList = searchService.findMovieByTitle(productName);
                }

            } // switch
            for (int i=0;i<productEntityList.size();i++){

                if(!(productEntityList.get(i).getPremiereDate().equals(product.Premiere)))
                {
                    product.Premiere=productEntityList.get(i).getPremiereDate();
                       // menuAc.createNotification(productName,product.Premiere.toString());

                    NotificationCompat.Builder noti =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Zmiana terminu premiery!")
                                    .setContentText("Produkt: "+productName + " nowa data " + productEntityList.get(i).getPremiereDate());

                    notificationManager.notify(notificationNumber, noti.build());
                }
            }


        }


    }




}
