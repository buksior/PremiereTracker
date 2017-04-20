package com.example.karolinar.premieretracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Marta on 20.04.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends Product{

        @JsonProperty("title")
        @Override
        public String getTitle() {

            return super.getTitle();
        }

        @JsonProperty("release_date")
        @Override
        public Date getPremiereDate() {
            return super.getPremiereDate();
        }
    }