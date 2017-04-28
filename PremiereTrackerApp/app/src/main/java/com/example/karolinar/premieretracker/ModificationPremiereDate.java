package com.example.karolinar.premieretracker;

import android.app.NotificationManager;
import android.content.Context;

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

        String productName = "";
        MenuActivity menuAc = null;

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
            if(!(productEntityList.get(0).getPremiereDate().equals(product.Premiere)))
            {
                product.Premiere=productEntityList.get(0).getPremiereDate();
                menuAc.createNotification(productName,product.Premiere.toString());
            }

        } // for


    }




}
