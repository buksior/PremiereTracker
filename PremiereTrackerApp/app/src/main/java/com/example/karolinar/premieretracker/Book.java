package com.example.karolinar.premieretracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Paulina on 16.04.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book extends Product {

    @JsonProperty("name")
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @JsonProperty("first_release_date")
    @Override
    public Date getPremiereDate() {
        return super.getPremiereDate();
    }

    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}