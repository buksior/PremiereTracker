package com.example.karolinar.premieretracker;

import java.util.Date;

/**
 * Created by Paulina on 08.04.2017.
 */

public class Product {
    public int Id;
    public String Name;
    public String ProductType;
    public Date Premiere;
    public String Description;
    public String Creator;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public Date getPremiere() {
        return Premiere;
    }

    public void setPremiere(Date premiere) {
        Premiere = premiere;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public Product(){}

    public Product(String title, Date premiereDate){
        this.Name = title;
        this.Premiere = premiereDate;
    }
}
