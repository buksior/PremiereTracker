package com.example.karolinar.premieretracker;

import java.util.Date;

/**
 * Created by Karolina R on 14.04.2017.
 */

public class Product {

    public Product(){

    }

    public Product(String title, Date premiereDate, boolean isFavorite){
        this.title = title;
        this.premiereDate = premiereDate;
        this.isFavorite = isFavorite;
    }



    protected Date premiereDate;
    private String title;
    private boolean isFavorite;

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    private String ProductType;

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

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    @Override
    public String toString() {
        return "Product: " + this.title
                + ", " + this.premiereDate
                + " (" + (this.isFavorite ? "fav" : "NOT fav") + ")";
    }

    /*public boolean exists(){
        ProductEntity entity = new ProductEntity();
        entity.Description = "";
        entity.Name = title;
        entity.Premiere = premiereDate;
        entity.ProductType = getClass().getSimpleName();
        entity.Creator = "";
        DatabaseManager db = new DatabaseManager()
    }*/
}