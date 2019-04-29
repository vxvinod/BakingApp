package com.example.a60010743.bakingpro.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class RecepieViewModel extends AndroidViewModel {

    private RecepieRepository mRepository;
    private LiveData<List<RecepieDetails>> mAllRecepies;
    private LiveData<List<String>> mAllRecepieItems;

    public RecepieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecepieRepository(application);
        mAllRecepies = mRepository.getAllRecepies();
    }

    public void insert(RecepieDetails recepieDetails) {
        mRepository.insert(recepieDetails);
    }

    public LiveData<List<RecepieDetails>> getAllRecepies(){
        return mAllRecepies;
    }

    public LiveData<List<String>> getmAllRecepieItems() {
        mAllRecepieItems = mRepository.getAllRecepieItems();
        return mAllRecepieItems;
    }

    public LiveData<String> getIngredients(String recepieItem) {
        LiveData<String> recepieIng = mRepository.getRecepieIng(recepieItem);
        return recepieIng;
    }

    public LiveData<String> getRecepieSteps(String recepieItem) {
        LiveData<String> recepieSteps = mRepository.getRecepieSteps(recepieItem);
        return recepieSteps;
    }

    public void updateFavouriteRecItem(String recItem, Boolean fav) {
        mRepository.updateFavouriteRecItem(recItem, fav);
    }

    public String getRecepieItem(String recepieItem) {
        return mRepository.getRecepieItem(recepieItem);
    }

    public LiveData<List<String>> getFavIngredients() {
        LiveData<List<String>> favIng = mRepository.getFavIngredients();
        return favIng;
    }

}
