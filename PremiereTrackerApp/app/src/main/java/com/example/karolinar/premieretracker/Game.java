package com.example.karolinar.premieretracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by Karolina R on 15.04.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game extends Product {

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

    @JsonProperty("developers")
    private List<Object> developers;

    public List<Object> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Object> developers) {
        this.developers = developers;
    }

    @JsonProperty("summary")
    private String description;

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}