package com.example.a60010743.bakingpro.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecepieDBApi {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecepieDetails>> getRecepieDetails();
}
