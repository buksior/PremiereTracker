package com.example.karolinar.premieretracker;


import android.util.Base64;
import java.io.IOException;
import java.net.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dominika on 02.05.2017.
 */

public class PriceService {

    private static final String login = "premieretracker@gmail.com";
    private static final String password = "pwr_ZWIS";

    public Double getProductPrice() {
        Double productPrice=0.0;
        try {
            URL url = new URL("http://api.skapiec.pl/meta_availableServices.json");


            HttpURLConnection conn = ((HttpURLConnection) url.openConnection());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
//            String encoding = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
//            conn.setRequestProperty("Authorization", "Basic " + encoding);
//            String output = conn.getResponseMessage();
//            System.out.println("output = " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productPrice;
    }
}

