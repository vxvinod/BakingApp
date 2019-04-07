package com.example.a60010743.bakingpro.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recepie_details_table")
public class RecepieDetails {

    @PrimaryKey @NonNull
    private String recepieItem;
    private String recepieIng;
    private String recepieSteps;
    private boolean favourite;

    public RecepieDetails(String recepieItem, String recepieIng, String recepieSteps) {
        this.recepieItem = recepieItem;
        this.recepieIng = recepieIng;
        this.recepieSteps = recepieSteps;
        this.favourite=false;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @NonNull
    public String getRecepieItem() {
        return recepieItem;
    }

    public void setRecepieItem(@NonNull String recepieItem) {
        this.recepieItem = recepieItem;
    }


    public String getRecepieIng() {
        return recepieIng;
    }

    public void setRecepieIng(String recepieIng) {
        this.recepieIng = recepieIng;
    }

    public String getRecepieSteps() {
        return recepieSteps;
    }

    public void setRecepieSteps(String recepieSteps) {
        this.recepieSteps = recepieSteps;
    }
}
