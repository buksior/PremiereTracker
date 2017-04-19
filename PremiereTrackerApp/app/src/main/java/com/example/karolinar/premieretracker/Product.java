package com.example.karolinar.premieretracker;

import java.util.Date;

/**
 * Created by Karolina R on 14.04.2017.
 */

public class Product {

    public Product(){

    }

    public Product(String title, Date premiereDate){
        this.title = title;
        this.premiereDate = premiereDate;
    }

    protected Date premiereDate;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(Date premiereDate) {
        this.premiereDate = premiereDate;
    }
}