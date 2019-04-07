package com.example.a60010743.bakingpro.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import retrofit2.http.DELETE;
@Dao
public interface RecepieDao {

    @Insert
    void insertRecepieDetails(RecepieDetails recepieDetails);

    @Delete
    void deleteRecepieDetails(RecepieDetails recepieDetails);

    @Query("DELETE FROM recepie_details_table")
    void deleteAll();

    @Query("UPDATE recepie_details_table SET favourite = :favourite where recepieItem = :recepieName")
    void updateFavourite(String recepieName, boolean favourite);

    @Query("SELECT * from recepie_details_table ORDER BY recepieItem ASC")
    LiveData<List<RecepieDetails>>  getAllRecepieDetails();

}
