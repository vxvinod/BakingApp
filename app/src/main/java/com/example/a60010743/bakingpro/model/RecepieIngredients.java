package com.example.a60010743.bakingpro.model;

public class RecepieIngredients {

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
}
