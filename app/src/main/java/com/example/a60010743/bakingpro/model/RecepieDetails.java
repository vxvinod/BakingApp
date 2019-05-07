package com.example.a60010743.bakingpro.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "recepie_details_table")
public class RecepieDetails  {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @NonNull @SerializedName("name")
    private String recepieItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @SerializedName("ingredients")
    private String recepieIng;
    @SerializedName("steps")
    private String recepieSteps;
    @Expose(serialize = false)
    private boolean favourite;

    private String servings = "";
    private String image = "";

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Constructor
    public RecepieDetails(String recepieItem, String recepieIng, String recepieSteps, String servings,
                                String image) {
        this.recepieItem = recepieItem;
        this.recepieIng = recepieIng;
        this.recepieSteps = recepieSteps;
        this.favourite=false;
        this.servings = servings;
        this.image = image;
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
