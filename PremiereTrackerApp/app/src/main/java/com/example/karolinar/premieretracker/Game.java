package com.example.karolinar.premieretracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Karolina R on 15.04.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game extends Product{

    @JsonProperty("name")
    @Override
    public String getName(){
        return super.getName();
    }

    @JsonProperty("first_release_date")
    @Override
    public Date getPremiere(){
        return super.getPremiere();
    }
}
