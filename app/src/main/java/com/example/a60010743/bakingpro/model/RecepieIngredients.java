package com.example.a60010743.bakingpro.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RecepieIngredients implements Parcelable{

    private String ingredients;
    private String quantity;
    private String measure;

    public RecepieIngredients(String ingredients, String quantity, String measure) {
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.measure = measure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RecepieIngredients(Parcel in) {
        this.ingredients = in.readString();
        this.quantity = in.readString();
        this.measure = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredients);
        dest.writeString(quantity);
        dest.writeString(measure);
    }
    public static final Parcelable.Creator<RecepieIngredients> CREATOR = new Creator<RecepieIngredients>() {
        @Override
        public RecepieIngredients createFromParcel(Parcel in) {
            return new RecepieIngredients(in);
        }

        @Override
        public RecepieIngredients[] newArray(int size) {
            return new RecepieIngredients[size];
        }
    };


}


